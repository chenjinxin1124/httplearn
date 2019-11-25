/**
  * @Author: chenjinxin
  * @Date: Created in 下午5:21 19-9-2
  * @Description:
  */
import cats._, cats.effect._, cats.implicits._, cats.data._
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.server._
object Authentication extends App {
  case class User(id: Long, name: String)

  /*val authUser: Kleisli[OptionT[IO, ?], Request[IO], User] =
    Kleisli(_ => OptionT.liftF(IO(???)))

  val middleware: AuthMiddleware[IO, User] =
    AuthMiddleware(authUser)

  val authedRoutes: AuthedRoutes[User, IO] =
    AuthedRoutes.of {
      case GET -> Root / "welcome" as user => Ok(s"Welcome, ${user.name}")
    }

  val service: HttpRoutes[IO] = middleware(authedRoutes)*/

}
