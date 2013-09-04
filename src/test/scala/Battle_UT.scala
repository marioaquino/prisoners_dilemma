package pd

import org.scalatest._

class BattleTestTronTest_UT extends FunSpec with ShouldMatchers {

   describe("the heat of battle") {
     it("can fight! fight!") {

        val player1 = new Player("Bobby Jo") with AlwaysCooperate
        val player2 = new Player("Joey Bob") with AlwaysCooperate

        val b = new Battle(1) with OneSetOfRules
        result = b.pit(player1, player2)

        result.a should equal(player1, 300)
        result.b should equal(player2, 300)
     }
   }
}
