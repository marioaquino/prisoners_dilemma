package pd

class Combatants {
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

  val allCombatants = Seq(sucker, cheat, titForTat, titForTwoTats, cooperateDefect, cooperateCooperateDefect,
       defectDefectCooperate, spiteful, mistrust, pavlov)

}
