package fpdemo.poly.inherit


trait Animal {
  def giveBirthTo(child: String): Animal
}

case class Cat(name: String) extends Animal {

  override def giveBirthTo(child: String): Animal = Cat(child)
}

case class Fish(name: String) extends Animal {

  override def giveBirthTo(child: String): Animal = Cat(child)
}


object Foo {


  // POLYMORPHISM
  def givesBirth(a: Animal, name: String): Animal =
    a.giveBirthTo(name)

  val c = Cat("Felix")
  val f = Fish("Nemo")


  givesBirth(c, "Foo")

  givesBirth(f, "Foo")



}
