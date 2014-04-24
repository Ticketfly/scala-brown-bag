package fpdemo

trait Lens[A,B] {

  def get(a:A): B

  def set(a:A, b:B): A

  def andThen[C](l : Lens[B,C] ) : Lens[A,C] = Lens(a => l.get(get(a)), (a,c) => set(a, l.set(get(a),c)))

  def :=(b: B): State[A,B] = State{ a => (set(a,b), b) }
}


object Lens {

  type @>[A,B] = Lens[A,B]

  def apply[A,B](g : A => B, s: (A,B) => A): A @> B  = new Lens[A,B] {
    override def get(a: A) = g(a)

    override def set(a: A, b: B) = s(a,b)
  }






  case class Address(street: String, city: String, zip:Int)
  case class Person(name: String, address:Address)


  val cityL: Address @> String = Lens(_.city, (a,c) => a.copy(city = c))

  val addressL: Person @> Address = Lens(_.address, (u,a) => u.copy(address = a))

  val userCityL = addressL andThen cityL


  val a = Address("76 Fedral", "San Francisco", 94107)
  val me = Person("Dragisa", a)

  val c = cityL.get(a)
  val a2 = cityL.set(a, "Frisco")

  userCityL.set(me, "Frisco")

  def setCity(city: String): State[Person, String] = for {
    c <- userCityL := city
  } yield c




}
