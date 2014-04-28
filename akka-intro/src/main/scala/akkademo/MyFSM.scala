package akkademo

import akka.actor._
import scala.concurrent.duration._

sealed trait State
case object Idle extends State
case object Active extends State

sealed trait Data
case object Uninitialized extends Data
case class Count(value: Int = 0) extends Data

class MyFSM extends Actor with FSM[State, Data] {

  startWith(Idle, Uninitialized)

  when(Idle) {
    case Event(cnt: Int) =>
      goto (Active) using Count(cnt)
  }

  when(Active, stateTimeout = 1 second) {
    case Event(_, Count(cnt)) =>
      println(cnt)
      stay using Count(cnt+1)

    case Event(StateTimeout, Count(count)) =>
      goto(Idle) using Uninitialized
  }

}
