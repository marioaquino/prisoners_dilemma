trait Move // sealed
case object Cooperate extends Move
case object Defect extends Move

case class Round(them: Move, me: Move)

// put this type alias in package object
   type History = Seq[Round]
   type PlayerId = String

abstract trait Strategy {
   def decide(h: History): Move
}

class Player(name: PlayerId) {
   self: Strategy =>

   var history: Map[PlayerId, History] = Map()

   def move(name:PlayerId): Move = decide(history.getOrElse(name, Seq()))

}

trait SuckerStrategy extends Strategy {
   def decide(h:History) = Cooperate
}

val joe = new Player("Joe") with SuckerStrategy
