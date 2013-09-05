package pd

object Combatants {
  val sucker = new Player("Always Cooperate") with AlwaysCooperate
  val cheat = new Player("Always Defect") with AlwaysDefect
  val titForTat = new Player("Tit-For-Tat") with TitForTat
  val titForTwoTats = new Player("Tit-For-Two-Tats") with TitForTwoTats
  val cooperateDefect = new Player("Cooperate Defect") with CooperateDefect
  val cooperateCooperateDefect = new Player("Cooperate Cooperate Defect") with CooperateCooperateDefect
  val defectDefectCooperate = new Player("Defect Defect Cooperate") with DefectDefectCooperate
  val spiteful = new Player("Spiteful") with Spiteful
  val mistrust = new Player("Mistrust") with Mistrust
  val pavlov = new Player("Pavlov") with Pavlov

  val allCombatants = Seq(sucker, cheat, titForTat, titForTwoTats, cooperateDefect, cooperateCooperateDefect,
       defectDefectCooperate, spiteful, mistrust, pavlov)

}
