package fpdemo



trait Cache {
  def get(key: Int): (Cache, Option[String])
}



object Cache {


  // ouch!
  def example(cache: Cache, key1: Int, key2: Int) {

    val (c1, a) = cache.get(key1)
    val (c2, b) = c1.get(key2)

  }

  def getFromCache(key: Int): State[Cache, Option[String]] = State {
    cache =>
      cache.get(key)
  }

  def example2(cache: Cache, key1: Int, key2: Int) {


    // same as: getFromCache(key1).flatMap(a => getFromCache(key2).map(b => (a,b)))
    val st :State[Cache, (Option[String], Option[String])]= for {
      a <- getFromCache(key1)
      b <- getFromCache(key2)

    } yield (a,b)



    st(cache)

  }
}

trait State[S,A] {
  def run(state: S): (S,A)

  def apply(state:S) = run(state)


  def map[B](f: A=> B): State[S,B] = State {
    state =>
      val(s1, a) = run(state)
      (s1, f(a))
  }

  def flatMap[B](f: A => State[S,B]) : State[S,B]  =  State {
    state =>
      val (s1, a) = run(state)
      f(a).run(s1)
  }


  def transform[R](l: Lens[R, S]): State[R,A] = State {
    r =>
      val (s1, a) = run(l.get(r))
      (l.set(r, s1) ,a)
  }


}



object State {
  def apply[S,A](r: S => (S,A)) = new State[S,A] {
    override def run(state: S) = r(state)
  }

  def state[S]: State[S,S] = State( st => (st,st))

  def set[S](state:S) :State[S, Unit] = State(_ => (state, ()))

}


case class Seat(id: Long)
case class Section(id: Long, seats: List[Seat])
case class Event(id: Long, sections: List[Section])

object Event {

  import State._

  type SectionState[A] = State[Section, A]
  type EventState[A]  = State[Event, A]


  val e = Event(123, List(Section(345, List(Seat(222)))))

  val e1 = e.copy(sections = Section(667, List()) :: e.sections)


  def addSection(section: Section) :EventState[Unit] = State(e => (e.copy(sections = e.sections :+ section), ()))

  def addSection2(section: Section) :EventState[Unit] = for {
    event <- state
    _     <- set (event.copy(sections = event.sections :+ section))
  } yield ()


}





