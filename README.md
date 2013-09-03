prisoners_dilemma
=================

An implementation of the Prisoner's Dilemma puzzle in Scala

The concepts from the game are as follows:

Score keeper
  Tallys number of rounds and returns a collection of players sorted by winner-> PlayerName: NumOfWins W / NumOfLosses L

Player
  Has a name/description/identifier and exposes a way to play
  During a match, each player is told who the opponent is responds with a their play in this round (true == cooperate, false == defect)

Round
  Represents a single match between two players

Tournament
  Given 10 or more player types
  See who scores the best after n (odd number) rounds
  The winner of the series of rounds will be matched against the winner another series for more rounds of play
  By process of elimination, the most winning strategy will be determined


