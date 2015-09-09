package fpdemo.poly.adhoc


case class Cat(name: String)
case class Fish(name: String)

trait CanGiveBirth[T] {
  def giveBirth(a: T, name: String): T
}


object Fee {

  implicit val catCanGivBirth = new CanGiveBirth[Cat] {
    override def giveBirth(a: Cat, name: String): Cat = Cat(name)
  }

  implicit val fishCanGiveBirth = new CanGiveBirth[Fish] {
    override def giveBirth(a: Fish, name: String): Fish = Fish(name)
  }

  // POLYMORPHISM
  def givesBirth[T](a: T, name: String)(implicit canGiveBirth: CanGiveBirth[T]): T =
    canGiveBirth.giveBirth(a, name)


  // Alternative syntax
  def givesBirth2[T : CanGiveBirth](a: T, name: String): T = {
    val canGiveBirth = implicitly[CanGiveBirth[T]]

    canGiveBirth.giveBirth(a, name)

  }

  val c = Cat("Felix")
  val f = Fish("Nemo")


  givesBirth(c, "Foo")

  givesBirth(f, "Foo")


  trait Monoid[T] {
    def append(a: T, b:T): T

    def zero: T
  }

  val intMonoid = new Monoid[Int] {
    override def append(a: Int, b: Int): Int = a * b

    override def zero: Int = 1
  }









  // "Pimping" a class
  implicit class PimpCanGiveBirth[T](val t:T) extends AnyVal {
    def giveBirthTo(child:String)(implicit canGiveBirth: CanGiveBirth[T]) = givesBirth(t, child)
  }


  c.giveBirthTo("Foo")

}

