typealias Round = Pair<OpponentChoice, MyChoice>

enum class OpponentChoice(val choice: String) {
    ROCK("A"),
    PAPER("B"),
    SCISSORS("C");

    companion object {
        fun parse(s: String) = values().find { it.choice == s }
    }
}

enum class MyChoice(val choice: String) {
    ROCK("X"),
    PAPER("Y"),
    SCISSORS("Z");

    companion object {
        fun parse(s: String) = MyChoice.values().find { it.choice == s }
    }
}

fun main() {

    fun part1(input: List<String>): Int {
        return input.createRounds()
            .sumOf { calculateTotalScore(it) }
    }

    fun part2(input: List<String>): Int {
        return input.createRounds()
            .map { it.playStrategy() }
            .sumOf { calculateTotalScore(it) }
    }

    val input = readInput("Day02")
    part1(input).println() // 15691
    part2(input).println() // 12989
}

private fun List<String>.createRounds() = map { it.split(" ") }.mapNotNull { it.toRound() }

private fun List<String>.toRound() = when (true) {
    (size == 2) -> Pair(OpponentChoice.parse(first())!!, MyChoice.parse(last())!!)
    else -> null
}

private fun Round.playStrategy() = when (second) {
    MyChoice.ROCK -> Pair(first, first.getLoss())
    MyChoice.PAPER -> Pair(first, first.getDraw())
    MyChoice.SCISSORS -> Pair(first, first.getWin())
}

private fun calculateTotalScore(round: Round) =
    calculateShapeScore(round.second) + calculateWinOrLoss(round)

private fun calculateShapeScore(myChoice: MyChoice) = when (myChoice) {
    MyChoice.ROCK -> 1
    MyChoice.PAPER -> 2
    MyChoice.SCISSORS -> 3
}

private fun calculateWinOrLoss(round: Round) = when (true) {
    (round.isDraw()) -> 3
    (round.isWin()) -> 6 // win
    else -> 0 // loss
}

private fun Round.isDraw() = when (true) {
    (first == OpponentChoice.PAPER && second == MyChoice.PAPER),
    (first == OpponentChoice.SCISSORS && second == MyChoice.SCISSORS),
    (first == OpponentChoice.ROCK && second == MyChoice.ROCK) -> true

    else -> false
}

private fun Round.isWin() = when (true) {
    (first == OpponentChoice.PAPER && second == MyChoice.SCISSORS),
    (first == OpponentChoice.SCISSORS && second == MyChoice.ROCK),
    (first == OpponentChoice.ROCK && second == MyChoice.PAPER) -> true

    else -> false
}

private fun OpponentChoice.getLoss() = when (this) {
    OpponentChoice.ROCK -> MyChoice.SCISSORS
    OpponentChoice.PAPER -> MyChoice.ROCK
    OpponentChoice.SCISSORS -> MyChoice.PAPER
}

private fun OpponentChoice.getDraw() = when (this) {
    OpponentChoice.ROCK -> MyChoice.ROCK
    OpponentChoice.PAPER -> MyChoice.PAPER
    OpponentChoice.SCISSORS -> MyChoice.SCISSORS
}

private fun OpponentChoice.getWin() = when (this) {
    OpponentChoice.ROCK -> MyChoice.PAPER
    OpponentChoice.PAPER -> MyChoice.SCISSORS
    OpponentChoice.SCISSORS -> MyChoice.ROCK
}
