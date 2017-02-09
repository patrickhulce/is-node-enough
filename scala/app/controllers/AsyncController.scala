package controllers

import akka.actor.ActorSystem
import javax.inject._

import redis.{RedisClient, RedisDispatcher}
import com.sksamuel.scrimage.Image
import com.sksamuel.scrimage.nio.JpegWriter
import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.concurrent.duration._

case class SubPayload(name: String, value: String)
case class Payload(id: Int, name: String, name_2: String, metadata: SubPayload)

/**
 * This controller creates an `Action` that demonstrates how to write
 * simple asynchronous code in a controller. It uses a timer to
 * asynchronously delay sending a response for 1 second.
 *
 * @param actorSystem We need the `ActorSystem`'s `Scheduler` to
 * run code after a delay.
 * @param exec We need an `ExecutionContext` to execute our
 * asynchronous code.
 */
@Singleton
class AsyncController @Inject() (actorSystem: ActorSystem)(implicit exec: ExecutionContext) extends Controller {
  implicit val subPayloadReads: Reads[SubPayload] = ((JsPath \ "name").read[String] and (JsPath \ "val").read[String])(SubPayload.apply _)
  implicit val payloadReads: Reads[Payload] = (
    (JsPath \ "id").read[Int] and
    (JsPath \ "name").read[String] and
    (JsPath \ "name_2").read[String] and
    (JsPath \ "metadata").read[SubPayload]
  )(Payload.apply _)

  implicit val subPayloadWrites: Writes[SubPayload] = (
    (JsPath \ "name").write[String] and
      (JsPath \ "val").write[String]
    )(unlift(SubPayload.unapply))
  implicit val payloadWrites = Json.writes[Payload]

  implicit val redisDispatcher = RedisDispatcher("akka.actor.default-dispatcher")
  implicit val system = ActorSystem()
  val client = RedisClient()



  /**
   * Create an Action that returns a plain text message after a delay
   * of 1 second.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/message`.
   */
  def message = Action.async {
    getFutureMessage(1.second).map { msg => Ok(msg) }
  }

  def redisreply = Action.async { request =>
    val json = request.body.asJson.get
    val payload = Json.fromJson(json)(payloadReads).get
    val str = Json.stringify(Json.toJson(payload))

    client.set("scalakey", str) flatMap {
      x => client.get[String]("scalakey")
    } map { s =>
      val item = Json.fromJson(Json.parse(s.get))(payloadReads).get
      Ok(Json.stringify(Json.toJson(item)))
    }
  }

  def redisreplyquic = Action.async { request =>
    val json = request.body.asJson.get
    val str = Json.stringify(json)

    client.set("scalakey", str) flatMap {
      x => client.get[String]("scalakey")
    } map { s =>
      val item = Json.parse(s.get)
      Ok(Json.stringify(item))
    }
  }

  def image = Action.async { request =>
    implicit val writer = JpegWriter().withCompression(80)
    Future { Ok(Image.fromFile(request.body.asRaw.get.asFile).scaleTo(200, 200).bytes) }
  }

  private def getFutureMessage(delayTime: FiniteDuration): Future[String] = {
    val promise: Promise[String] = Promise[String]()
    actorSystem.scheduler.scheduleOnce(delayTime) { promise.success("Hi!") }
    promise.future
  }

}
