object Tournament {
  type History = Seq[Round]
  type PlayerId = String
}

import Tournament._
class Player(name: PlayerId) {

   self: Strategy =>

   var history: Map[PlayerId, History] = Map()

   def play(opponent: PlayerId): Play = decide(history.getOrElse(opponent, Seq()))

   def addToHistory(opponent: PlayerId, round: Round) = history = history + (opponent -> Seq(round))
}


