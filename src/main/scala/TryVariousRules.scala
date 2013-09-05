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
      for {loneC <- Range(lowestScore, highestScore)
           bothD <- Range(loneC, highestScore)
           bothC <- Range(bothD, highestScore)
           loneD <- Range(bothC, highestScore) } // todo: always use highestScore here
      yield {
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
            s"$s1*$s2*$s3*$s4*"
          }
        }

        import GiantFightOfDoom._
        import Combatants.allCombatants
        val winner = declareAWinner(everybodyFight(allCombatants)(() => battle))

        (battle, winner)
      }

      results.foreach { case(battle, winner) => println(s"$battle $winner")}

   }

}
