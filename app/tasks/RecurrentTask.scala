package tasks

import akka.actor.ActorSystem
import com.google.inject.Inject
import scala.concurrent.duration._

import scala.concurrent.ExecutionContext

class RecurrentTask@Inject() (actorSystem: ActorSystem)(implicit executionContext: ExecutionContext) {

  val interval = 5.seconds

  actorSystem.scheduler.schedule(initialDelay = 10.seconds, interval = interval) {
    // the block of code that will be executed
    print(s"Hello! I'm printing this every $interval seconds...")
  }
}
