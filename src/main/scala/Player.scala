object Tournament {
  type History = Seq[Round]
  type PlayerId = String
  type Score = Int
}
import Tournament._

abstract trait Strategy {
   def decide(h: History): Play
}

trait AlwaysCooperate extends Strategy {
   def decide(h:History) = Cooperate
}

class Player(val name: PlayerId) {

   self: Strategy =>

   var history: Map[PlayerId, History] = Map()

   def play(opponent: PlayerId): Play = decide(history.getOrElse(opponent, Seq()))

   def addToHistory(opponent: PlayerId, round: Round) = history = history + ((opponent, round +: history.getOrElse(opponent, Seq())))
}


