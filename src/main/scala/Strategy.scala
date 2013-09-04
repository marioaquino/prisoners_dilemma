import Tournament.History

abstract trait Strategy {
   def decide(h: History): Play
}

trait AlwaysCooperate extends Strategy {
   def decide(h:History) = Cooperate
}

