package pd

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
   def decide(h: History) = Cooperate
}

trait AlwaysDefect extends Strategy {
  def decide(h: History) = Defect
}

trait TitForTat extends Strategy {
  def decide(h: History) = h match {
    case Seq( Round(_, opponentMove), _*) => opponentMove
    case _ => Cooperate
  }
}

trait TitForTwoTats extends Strategy {
  def decide(h: History) = h match {
    case Seq( Round(_, Defect), Round(_, Defect), _*) => Defect
    case _ => Cooperate
  }
}

trait CooperateDefect extends Strategy {
  def decide(h: History) = h.size % 2 match {
    case 0 => Cooperate
    case 1 => Defect
  }
}

trait CooperateCooperateDefect extends Strategy {
  def decide(h: History) = h.size % 3 match {
    case 0 | 1 => Cooperate
    case 2 => Defect
  }
}

trait DefectDefectCooperate extends Strategy {
  def decide(h: History) = h.size % 3 match {
    case 0 | 1 => Defect
    case 2 => Cooperate
  }
}

trait Spiteful extends Strategy {
  def decide(h: History) =
    if (h.exists( {case(Round(_, hisMove)) => hisMove == Defect} )) Defect
    else Cooperate
}

trait Mistrust extends Strategy {
  def decide(h: History) = h match {
    case Seq() => Defect
    case Seq(Round(_, Defect), _*) => Defect
    case _ => Cooperate
  }
}

trait Pavlov extends Strategy {
  def decide(h: History) = h match {
    case Seq() => Cooperate
    case Seq(Round(myMove, opponentMove), _*) if myMove == opponentMove => Cooperate
    case _ => Defect
  }
}

class Player(val name: PlayerId) {

   self: Strategy =>

   var history: History = Seq()

   def play(opponent: PlayerId): Play = decide(history)

   def addToHistory(round: Round) { history = round +: history }

   def resetHistory { history = Seq() }

   override def equals(o: Any) = o match {
      case p: Player => p.name == name
      case _ => false
   }

   override def toString() = name
}


