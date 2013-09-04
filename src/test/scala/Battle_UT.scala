package pd

import org.scalatest._

class BattleTestTronTest_UT extends FunSpec with ShouldMatchers {


   describe("the heat of battle") {
     val player1 = new Player("Bobby Jo") with AlwaysCooperate
     val player2 = new Player("Joey Bob") with AlwaysCooperate

     def fight(times:Int) = {
        val b = new Battle(times) with OneSetOfRules
        b.pit(player1, player2)
     }

     it("can fight! fight!") {
        val result = fight(1)

        result.a should equal(player1, 300)
        result.b should equal(player2, 300)
     }

     it("can fight 3 times") {
        val result = fight(3)

        result.a should equal(player1, 900)
        result.b should equal(player2, 900)
     }
   }
}

class BigFightTestOfDoom extends FunSpec with ShouldMatchers {
  import GiantFightOfDoom._

  describe("doom") {
    implicit val battleConstructor = () => new Battle(3) with OneSetOfRules

    it("can pit Bobby Jo against Joey Bob") {
     val player1 = new Player("Bobby Jo") with AlwaysCooperate
     val player2 = new Player("Joey Bob") with AlwaysCooperate

     val result = everybodyFight(Seq(player1, player2))

     result.get(player1).get should equal(1800)
     result.get(player2).get should equal(1800)

    }
    it("can pit Fluffy against Bobby Jo against Joey Bob") {
     val player1 = new Player("Bobby Jo") with AlwaysCooperate
     val player2 = new Player("Joey Bob") with AlwaysCooperate
     val player3 = new Player("Fluffy") with AlwaysCooperate

     val result = everybodyFight(Seq(player1, player2, player3))

     result.get(player1).get should equal(3600)
     result.get(player2).get should equal(3600)
     result.get(player3).get should equal(3600)

    }
  }

  describe("Choosing the winner") {
    it("picks Bobby Jo") {
     val player1 = new Player("Bobby Jo") with AlwaysCooperate
     val player2 = new Player("Joey Bob") with AlwaysCooperate

     declareAWinner(Map(player1 -> 900, player2 -> 100)) should be (Seq(player1))
    }
    it("picks Joey Bob") {
     val player1 = new Player("Bobby Jo") with AlwaysCooperate
     val player2 = new Player("Joey Bob") with AlwaysCooperate

     declareAWinner(Map(player1 -> -900, player2 -> 100)) should be (Seq(player2))
    }
    it("recognizes a tie") {
     val player1 = new Player("Bobby Jo") with AlwaysCooperate
     val player2 = new Player("Joey Bob") with AlwaysCooperate

     val result = declareAWinner(Map(player1 -> 900, player2 -> 900))

     assert(result.contains(player1))
     assert(result.contains(player2))
    }


  }
}

class Holmgang extends FunSpec with ShouldMatchers {
  import GiantFightOfDoom._

  describe("for evolution!") {
    implicit val battleConstructor = () => new Battle(100) with OneSetOfRules

    it("picks the evolutionarily stable strategy") {
      val sucker = new Player("Always Cooperates") with AlwaysCooperate
      val cheat = new Player("Always Defects") with AlwaysDefect
      val titForTat =
        new Player("Starts with cooperation. If defected against, TFT responds with a defect. Otherwise TFT cooperates.") with TitForTat
      val titForTwoTats =
        new Player("Starts with cooperate. If defected against twice in a row, TFTT defects; otherwise it cooperates.") with TitForTwoTats
      val cooperateDefect = new Player("Alternates between cooperate and defect") with CooperateDefect
      val cooperateCooperateDefect = new Player("Cooperates twice then defects, no matter what.") with CooperateCooperateDefect
      val defectDefectCooperate = new Player("Defects twice then cooperates, no matter what.") with DefectDefectCooperate
      val spiteful = new Player("Defects each time after it has been defected against. Initially cooperates.") with Spiteful
      val mistrust = new Player("Initially defects. Defects if it has been defected against in the last move. Otherwise cooperates.") with Mistrust
      val pavlov = new Player("Initially cooperates. Cooperates if the last move was the same as the opponents. Otherwise defects.") with Pavlov

      val fightResults = everybodyFight(Seq(sucker, cheat, titForTat, titForTwoTats, cooperateDefect, cooperateCooperateDefect,
       defectDefectCooperate, spiteful, mistrust, pavlov))

      fightResults.foreach {case(player, score) => println("-> " + player + ": " + score)}

      val winners = declareAWinner(fightResults)

      winners.head should equal(titForTwoTats)
    }
  }
}
