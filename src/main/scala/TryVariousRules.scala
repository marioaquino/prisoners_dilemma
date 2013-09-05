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
      val n = 200

      val wiggleRoom = highestScore - lowestScore

      val results =
      for {loneC <- Range(lowestScore, highestScore+1)
           bothD <- Range(loneC, highestScore+1)
           bothC <- Range(bothD, highestScore+1) }
      yield {
        val loneD = highestScore
        val battle = new Battle(n) with Rules {
          val loneCooperator = loneC
          val bothDefect = bothD
          val bothCooperate = bothC
          val loneDefector = loneD

          def hasDifferentScoresForEachScenario = loneC < bothD && bothD < bothC && bothC < loneD
          def noCostToBothDefecting = bothD == bothC

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

      val winners = results.map(_._2)
      val winnersWithoutOverlap = results.filter(_._1.hasDifferentScoresForEachScenario).map(_._2)
      val winnersWithNoCostToBothDefecting = results.filter(_._1.noCostToBothDefecting).map(_._2)

      def printAboutWinner: PartialFunction[(Player,Double),Unit] = {
        case (player, pct) =>
          val p = (pct*100).toInt
          println(s"$p%: $player")
      }

      println("====== Overall ======")
      winnerStats(winners).foreach (printAboutWinner)
      println("====== No Identical Scores ======")
      winnerStats(winnersWithoutOverlap).foreach (printAboutWinner)
      println("====== No Penalty for Double-Defect ======")
      winnerStats(winnersWithNoCostToBothDefecting).foreach(printAboutWinner)
      println("======")

   }

   def winnerStats(winners: Seq[Seq[Player]]):Seq[(Player, Double)] = {
     val total = winners.size.toDouble
     winners.flatten.groupBy(a=>a).toSeq.map{ case (a, s) => (a, s.size/total)}.sortBy(_._2).reverse
   }

   private def printWinner(winners: Seq[Player]):String = winners match {
     case Seq(one) => one.toString
     case Seq(one, two) => s"tie: $one & $two"
     case longer => s"${longer.size}-way tie"
   }

}
