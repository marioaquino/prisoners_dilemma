package pd

// When two players, are matched against each other, they play a series of rounds.
import Tournament._
import scalaz._
import scalaz.std.AllInstances._
import scalaz.syntax.foldable._

case class BattleResult(a: (Player, Score), b: (Player, Score))

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
     val res = Stream.continually(score(singleRound(a,b))).take(numberOfRounds).concatenate
     val (aScore, bScore) = res
      BattleResult((a, aScore), (b, bScore))
   }


   private def singleRound(a: Player, b:Player): (Play, Play) = {
     val bMove = b.play(a.name)
     val aMove = a.play(b.name)
     // ew, a side effect. Will eliminate
     a.addToHistory(b.name, Round(aMove,bMove))
     b.addToHistory(a.name, Round(bMove,aMove))
     (aMove, bMove)
   }


}

object GiantFightOfDoom {

  def everybodyFight(players: Seq[Player])(implicit battleConstructor: () => Battle): Map[Player,Score] = {

    val battleResults =
      for { p1 <- players
            p2 <- players
            if p1 != p2 }
    yield {
       battleConstructor().pit(p1,p2)
    }

    battleResults.map(t => Map(t.a, t.b)).toList.concatenate
  }

  def declareAWinner(scores: Map[Player, Score]): Seq[Player] = {
    val MaxScore = scores.map(_._2).max
    scores.toSeq.collect{ case(p, MaxScore) => p }  // dirty little piece of pattern-matching IMO
  }


}
