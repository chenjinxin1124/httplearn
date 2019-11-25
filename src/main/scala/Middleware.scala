/**
  * @Author: chenjinxin
  * @Date: Created in 下午4:39 19-9-2
  * @Description:
  */
import cats.data.Kleisli
import cats.effect._
import cats.implicits._
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.implicits._
object Middleware {
  def myMiddle(service: HttpRoutes[IO], header: Header): HttpRoutes[IO] = Kleisli { req: Request[IO] =>
    service(req).map {
      case Status.Successful(resp) =>
        resp.putHeaders(header)
      case resp =>
        resp
    }
  }

  val service = HttpRoutes.of[IO] {
    case GET -> Root / "bad" =>
      BadRequest()
    case _ =>
      Ok()
  }

  val goodRequest = Request[IO](Method.GET, Uri.uri("/"))

  val badRequest = Request[IO](Method.GET, Uri.uri("/bad"))

  service.orNotFound(goodRequest).unsafeRunSync

  service.orNotFound(badRequest).unsafeRunSync

  val wrappedService = myMiddle(service, Header("SomeKey", "SomeValue"));

  wrappedService.orNotFound(goodRequest).unsafeRunSync

  wrappedService.orNotFound(badRequest).unsafeRunSync


}

object MyMiddle {
  def addHeader(resp: Response[IO], header: Header) =
    resp match {
      case Status.Successful(resp) => resp.putHeaders(header)
      case resp => resp
    }

  def apply(service: HttpRoutes[IO], header: Header) =
    service.map(addHeader(_, header))

}