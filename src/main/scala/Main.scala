import cats.effect._
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.syntax._
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.server.blaze._

/**
  * @Author: chenjinxin
  * @Date: Created in 下午5:58 19-8-28
  * @Description:
  */
object Main extends IOApp{

  val helloWorldService = HttpRoutes.of[IO]{
    case GET -> Root / "hello" / name =>
      Ok(s"Hello,$name.")
  }.orNotFound

  def run(args:List[String]):IO[ExitCode]=
    BlazeServerBuilder[IO]
    .bindHttp(8080,"localhost")
    .withHttpApp(helloWorldService)
    .serve
    .compile
    .drain
    .as(ExitCode.Success)
}

object MainWithResource extends IOApp{
  override def run(args: List[String]): IO[ExitCode] =
    BlazeServerBuilder[IO]
    .bindHttp(8080,"localhost")
    .withHttpApp(Main.helloWorldService)
    .resource
    .use(_ => IO.never)
    .as(ExitCode.Success)
}
