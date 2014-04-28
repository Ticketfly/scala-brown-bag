package akkademo

import akka.actor._

case object Get

class Foo extends Actor {

  var i: Int = 0

  def receive = {

    case m:String =>
      println(s"Hello $m")
      i = i +1


    case Get => sender ! i


    case e:Throwable => throw e

  }

}
