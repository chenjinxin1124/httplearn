/**
  * @Author: chenjinxin
  * @Date: Created in 下午6:09 19-9-2
  * @Description:
  */
import cats.effect._
import cats.implicits._
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.staticcontent._
import org.http4s.syntax.kleisli._

object StaticFiles extends App {

  /*val httpApp: HttpApp[IO] =
    Router(
      "api"    -> anotherService
  "assets" -> fileService(FileService.Config("./assets))
  ).orNotFound*/

}

object SimpleHttpServer extends IOApp {
  override def run(args: List[String]): IO[ExitCode] =
    BlazeServerBuilder[IO]
      .bindHttp(8080)
      .withHttpApp(fileService[IO](FileService.Config(".")).orNotFound)
      .serve
      .compile.drain.as(ExitCode.Success)
}