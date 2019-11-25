package json

/**
  * @Author: chenjinxin
  * @Date: Created in 上午9:24 19-9-3
  * @Description:
  */
import cats.effect._
import io.circe._
import io.circe.literal._
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.circe._
import org.http4s.client._
import org.http4s.client.dsl.io._
object Demo1 extends App {
  def hello(name: String): Json =
    json"""{"hello": $name}"""

  val greeting = hello("world")

  Ok(greeting).unsafeRunSync
  println(greeting)

  POST(json"""{"name": "Alice"}""", Uri.uri("/hello")).unsafeRunSync

  case class Hello(name: String)
  case class User(name: String)
  import io.circe.syntax._
  implicit val HelloEncoder: Encoder[Hello] =
    Encoder.instance { hello: Hello =>
      json"""{"hello": ${hello.name}}"""
    }
  println(Hello("Alice").asJson)

  import io.circe.generic.auto._
  println(User("Alice").asJson)

  println(Ok(Hello("Alice").asJson).unsafeRunSync)
  println(POST(User("Bob").asJson, Uri.uri("/hello")).unsafeRunSync)

  println(Ok("""{"name":"Alice"}""").flatMap(_.as[Json]).unsafeRunSync)
  println(POST("""{"name":"Bob"}""", Uri.uri("/hello")).flatMap(_.as[Json]).unsafeRunSync)

  implicit val userDecoder = jsonOf[IO, User]
  println(Ok("""{"name":"Alice"}""").flatMap(_.as[User]).unsafeRunSync)
  println(POST("""{"name":"Bob"}""", Uri.uri("/hello")).flatMap(_.as[User]).unsafeRunSync)
}
