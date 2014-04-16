package fpdemo

import scala.util.Try

sealed trait Maybe[+A] {

  def get :A
  def isDefined :Boolean

  def map[B](f: A=> B): Maybe[B]
  def flatMap[B]( f: A => Maybe[B]): Maybe[B]

}


case class Present[A](v: A) extends Maybe[A] {
  override def isDefined = true

  override def get = v

  override def map[B](f: (A) => B) = Present(f(get))

  override def flatMap[B](f: (A) => Maybe[B]) = f(get)
}

case object Absent extends Maybe[Nothing] {
  override def isDefined = false

  override def get = throw new IllegalAccessError("You can't get something out of nothing")

  override def map[B](f: (Nothing) => B) = Absent

  override def flatMap[B](f: (Nothing) => Maybe[B]) = Absent
}

object Maybe {
  def sequence[A](l :List[Maybe[A]]) : Maybe[List[A]] = l match {
    case Nil => Present(Nil)
    case h :: t => for {
      hd    <- h
      tail  <- sequence(t)
    } yield hd :: tail
  }
}


object Demo {

  def add(a: Maybe[Int], b:Maybe[Int]): Maybe[Int] = a.flatMap(x => b.map(_ + x))


  def add2(a: Maybe[Int], b:Maybe[Int]): Maybe[Int] =
    for {
      x <- a
      y <- b
    } yield x + y


  def lift[A,B](f: A => B): Maybe[A] => Maybe[B] = _ map f



  def t(a: Int, b:Int) = Try {
    // can throw exception

    a/b
  }



}



