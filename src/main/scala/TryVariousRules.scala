package pd

object TryVariousRules {

  /*
   *  -50                                      50
   *   |---------------------------------------|
   *       |         |          |              |
   *       loneCoop  |          |              |
   *                 bothDefect |              |
   *                            bothCooperate  |
   *                                           loneDefector
   */
   def main(args: Array[String]) {

      val lowestScore = -5
      val highestScore = 5
      val n = 50

      val wiggleRoom = highestScore - lowestScore

      val results =
      for {loneC <- Range(lowestScore, highestScore+1)
           bothD <- Range(loneC, highestScore+1)
           bothC <- Range(bothD, highestScore+1) }
      yield {
        val loneD = highestScore
        val battle = new Battle(n) with Rules {
          val bothCooperate = bothC
          val bothDefect = bothD
          val loneCooperator = loneC
          val loneDefector = loneD

          override def toString() = {
            val s1 = "-" * (loneCooperator - lowestScore)
            val s2 = "-" * (bothDefect - loneCooperator)
            val s3 = "-" * (bothCooperate - bothDefect)
            val s4 = "-" * (loneDefector - bothCooperate)
            s"${s1}c${s2}D${s3}C${s4}d"
          }
        }

        import GiantFightOfDoom._
        import Combatants.allCombatants
        val winner = declareAWinner(everybodyFight(allCombatants)(() => battle))

        (battle, winner)
      }


      results.foreach { case(battle, winner) => println(s"$battle ${printWinner(winner)}")}

   }

   private def printWinner(winners: Seq[Player]):String = winners match {
     case Seq(one) => one.toString
     case Seq(one, two) => s"$one & $two tied"
     case longer => s"${longer.size}-way tie"
   }

}
