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
