/**
  * @Author: chenjinxin
  * @Date: Created in 下午6:23 19-9-2
  * @Description:
  */

import cats.effect._
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.server.blaze._

import scala.concurrent.ExecutionContext.global

object HTTPClient {

  import scala.concurrent.ExecutionContext.global

  implicit val cs: ContextShift[IO] = IO.contextShift(global)
  implicit val timer: Timer[IO] = IO.timer(global)

  val app = HttpRoutes.of[IO] {
    case GET -> Root / "hello" / name =>
      Ok(s"Hello, $name.")
  }.orNotFound
  val server = BlazeServerBuilder[IO].bindHttp(8080, "localhost").withHttpApp(app).resource
  val fiber = server.use(_ => IO.never).start.unsafeRunSync()

  {
    import org.http4s.client.blaze._
    import org.http4s.client._
    import scala.concurrent.ExecutionContext.Implicits.global
    BlazeClientBuilder[IO](global).resource.use { client =>
      // use `client` here and return an `IO`.
      // the client will be acquired and shut down
      // automatically each time the `IO` is run.
      IO.unit
    }
  }



}

