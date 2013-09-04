// When two players, are matched against each other, they play a series of rounds.
import Tournament._

case class BattleResult(a: (Player, Score), bScore: (Player, Score))

trait Rules {
   def score(plays: (Play, Play)): (Score, Score) = plays match {
     case (Cooperate, Cooperate) => (bothCooperate, bothCooperate)
     case (Cooperate, Defect) => (loneCooperator, loneDefector)
     case (Defect, Cooperate) => (loneDefector, loneCooperator)
     case (Defect, Defect) => (bothDefect, bothDefect)
   }

   def bothCooperate: Score
   def bothDefect: Score
   def loneDefector: Score
   def loneCooperator: Score
}

trait OneSetOfRules extends Rules {
  val bothCooperate = 300
  val bothDefect = -300
  val loneCooperator = -500
  val loneDefector = 500
}

class Battle(numberOfRounds: Int) {
   self: Rules =>

   def pit(a: Player, b: Player): BattleResult = {
      val resultOfOne = score(singleRound(a,b))
      BattleResult((a, resultOfOne._1), (b, resultOfOne._2))
   }

   private def singleRound(a: Player, b:Player): (Play, Play) =
     (a.play(b.name), b.play(a.name))


}
