package pd

object Tournament {
  type PlayerId = String
  type History = Seq[Round]
  type Score = Int
  type Description = String
}
import Tournament._

class Player(val name: PlayerId) {

   self: Strategy =>

   var history: History = Seq()

   def play: Play = decide

   def addToHistory(round: Round) { history = round +: history }

   def resetHistory { history = Seq() }

   override def equals(o: Any) = o match {
      case p: Player => p.name == name
      case _ => false
   }

   override def toString() = s"($name) $description"
}

abstract trait Strategy {
  def description: Description
  def history: History
  def decide: Play
}

trait AlwaysCooperate extends Strategy {
  def description = "Always Cooperates"
  def decide = Cooperate
}

trait AlwaysDefect extends Strategy {
  def description = "Always Defects"
  def decide = Defect
}

trait TitForTat extends Strategy {
  def description = "Starts with cooperation. If defected against, TFT responds with a defect. Otherwise TFT cooperates."
  def decide = history match {
    case Seq( Round(_, opponentMove), _*) => opponentMove
    case _ => Cooperate
  }
}

trait TitForTwoTats extends Strategy {
  def description = "Starts with cooperate. If defected against twice in a row, TFTT defects; otherwise it cooperates."
  def decide = history match {
    case Seq( Round(_, Defect), Round(_, Defect), _*) => Defect
    case _ => Cooperate
  }
}

trait CooperateDefect extends Strategy {
  def description = "Alternates between cooperate and defect"
  def decide = history.size % 2 match {
    case 0 => Cooperate
    case 1 => Defect
  }
}

trait CooperateCooperateDefect extends Strategy {
  def description = "Cooperates twice then defects, no matter what."
  def decide = history.size % 3 match {
    case 0 | 1 => Cooperate
    case 2 => Defect
  }
}

trait DefectDefectCooperate extends Strategy {
  def description = "Defects twice then cooperates, no matter what."
  def decide = history.size % 3 match {
    case 0 | 1 => Defect
    case 2 => Cooperate
  }
}

trait Spiteful extends Strategy {
  def description = "Defects each time after it has been defected against. Initially cooperates."
  def decide =
    if (history.exists( {case(Round(_, opponentMove)) => opponentMove == Defect} )) Defect
    else Cooperate
}

trait Mistrust extends Strategy {
  def description = "Initially defects. Defects if it has been defected against in the last move. Otherwise cooperates."
  def decide = history match {
    case Seq() => Defect
    case Seq(Round(_, Defect), _*) => Defect
    case _ => Cooperate
  }
}

trait Pavlov extends Strategy {
  def description = "Initially cooperates. Cooperates if the last move was the same as the opponents. Otherwise defects."
  def decide = history match {
    case Seq() => Cooperate
    case Seq(Round(myMove, opponentMove), _*) if myMove == opponentMove => Cooperate
    case _ => Defect
  }
}
