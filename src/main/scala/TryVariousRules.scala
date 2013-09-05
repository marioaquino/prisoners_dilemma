package pd

object TryVariousRules {

  /*
   *  -50                                                 50
   *   |--------------------------------------------------|
   *    |         |          |              |
   *    loneCoop  |          |              |
   *              bothDefect |              |
   *                         bothCooperate  |
   *                                        loneDefector
   */
   def main(args: Array[String]) {

      val lowestScore = -5
      val highestScore = 5
      val n = 5

      val wiggleRoom = highestScore - lowestScore

      val results =
      for {loneCooperator <- Range(lowestScore, highestScore)
           bothDefect <- Range(loneCooperator, highestScore)
           bothCooperate <- Range(bothDefect, highestScore)
           loneDefector <- Range(bothCooperate, highestScore) } // todo: always use highestScore here
      yield {
        val battle = new Battle(n) with Rules {
          val bothCooperate = bothCooperate
          val bothDefect = bothDefect
          val loneCooperator = loneCooperator
          val loneDefector = loneDefector

          override def toString() = {
            val s1 = "-" * (loneCooperator - lowestScore)
            val s2 = "-" * (bothDefect - loneCooperator)
            val s3 = "-" * (bothCooperate - bothDefect)
            val s4 = "-" * (loneDefector - bothCooperate)
            s"$s1*$s2*$s3*$s4*$s5"
          }
        }

        val winner = GiantFightOfDoom.declareAWinner(GiantFightOfDoom.everybodyFight(allCombatants)(() => battle))

        (battle, winner)
      }

      results.foreach { case(battle, winner) => println(s"$battle $winner")}

   }

}
