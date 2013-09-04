package pd

// When two players, are matched against each other, they play a series of rounds.
import Tournament._


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

     import scalaz._
    import scalaz.std.AllInstances._

   def pit(a: Player, b: Player): BattleResult = {
     val m = implicitly[Monoid[(Score,Score)]]
     val (aScore, bScore) = Stream.continually(score(singleRound(a,b))).take(numberOfRounds).reduce((x, y) =>m.append(x,y))
      BattleResult((a, aScore), (b, bScore))
   }


   private def singleRound(a: Player, b:Player): (Play, Play) =
     (a.play(b.name), b.play(a.name))


}

object GiantFightOfDoom {

  def everybodyFight(players: Seq[Player])(implicit battleConstructor: () => Battle): Map[Player,Score] = {
    import scalaz.Monoid
    import scalaz.std.AllInstances._

    val m = implicitly[Monoid[Map[Player,Score]]]

    val battleResults =
      for { p1 <- players
           p2 <- players
           if p1 != p2 }
    yield {
       battleConstructor().pit(p1,p2)
    }

    battleResults.map(t => Map(t.a, t.b)).reduce((x,y) => m.append(x,y))

  }

  def declareAWinner(scores: Map[Player, Score]): Seq[Player] = {
    val maxScore = scores.map(_._2).max
    scores.toSeq.collect{ case(p, score) if score == maxScore => p }
  }


}
