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
   }
}
