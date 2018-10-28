package tasks

import akka.actor.ActorSystem
import com.google.inject.Inject

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

class RecurrentTask@Inject() (actorSystem: ActorSystem)(implicit executionContext: ExecutionContext) {
  val interval: FiniteDuration = 3.seconds

  actorSystem.scheduler.schedule(initialDelay = 2.seconds, interval = interval) {
    println(s"Hello! I'm printing this every $interval seconds...")
  }
}
