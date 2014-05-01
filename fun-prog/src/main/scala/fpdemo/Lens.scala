package fpdemo

trait Lens[A,B] {

  def get(a:A): B

  def set(a:A, b:B): A

  def andThen[C](l: Lens[B,C]) :Lens[A,C] = Lens(a => l.get(get(a)), (a,c) => set(a, l.set(get(a), c)))


  def setState(b: B) : State[A,Unit] = State(a => (set(a,b), ()))

  def :=(b:B) = setState(b)

}

object Lens {

  type @>[A,B] = Lens[A,B]

  def apply[A,B](g: A=>B, s: (A,B) => A) :Lens[A,B] = new Lens[A,B] {
    override def get(a: A) = g(a)

    override def set(a: A, b: B) = s(a,b)
  }


}


case class Address(street:String, city: String, zip:Int)

case class Person(firstName: String, lastName:String, address: Address)

object LensExample {

  import Lens._


  def personState(a: Address): State[Person, Unit] = State (p =>
    (p.copy(address = a), ())
  )

  def personState2(a:Address): State[Person, Unit] = addressL := a

  def zipState(zip:Int): State[Address, Unit] = zopL := zip


  def personZipState(zip:Int) :State[Person,Unit] = zipState(zip) transform addressL

  val addressL: Person @> Address  = Lens(_.address, (p,a) => p.copy(address = a))

  val zopL: Address @> Int = Lens(_.zip, (a,z) => a.copy(zip = z))

}
