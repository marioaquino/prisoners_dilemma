package pd


sealed trait Play

case object Cooperate extends Play
case object Defect extends Play

case class Round(me: Play, them: Play)
