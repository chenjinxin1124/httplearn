package json

/**
  * @Author: chenjinxin
  * @Date: Created in 上午9:46 19-9-3
  * @Description:
  */
import cats.effect._

import io.circe._
import io.circe.generic.auto._
import io.circe.syntax._

import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.io._
import org.http4s.implicits._

object HelloWorldService extends App {
  import scala.concurrent.ExecutionContext.Implicits.global

  case class User(name: String)
  case class Hello(greeting: String)

  implicit val decoder = jsonOf[IO, User]

  val jsonApp = HttpRoutes.of[IO] {
    case req @ POST -> Root / "hello" =>
      for {
        // Decode a User request
        user <- req.as[User]
        // Encode a hello response
        resp <- Ok(Hello(user.name).asJson)
      } yield (resp)
  }.orNotFound

  // Needed by `BlazeServerBuilder`. Provided by `IOApp`.
  implicit val cs: ContextShift[IO] = IO.contextShift(global)
  implicit val timer: Timer[IO] = IO.timer(global)

  import org.http4s.server.blaze._
  val server = BlazeServerBuilder[IO].bindHttp(8080).withHttpApp(jsonApp).resource
  val fiber = server.use(_ => IO.never).start.unsafeRunSync()


  import org.http4s.client._
  import org.http4s.client.dsl.io._
  import org.http4s.client.blaze._
  import cats.effect.IO
  import io.circe.generic.auto._
  import fs2.Stream

  // Decode the Hello response
  def helloClient(name: String): Stream[IO, Hello] = {
    // Encode a User request
    val req = POST(User(name).asJson, Uri.uri("http://localhost:8080/hello"))
    // Create a client
    BlazeClientBuilder[IO](global).stream.flatMap { httpClient =>
      // Decode a Hello response
      Stream.eval(httpClient.expect(req)(jsonOf[IO, Hello]))
    }
  }
  val helloAlice = helloClient("Alice")
  helloAlice.compile.last.unsafeRunSync

  fiber.cancel.unsafeRunSync()
}
