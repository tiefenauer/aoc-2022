import kotlin.math.abs

enum class Direction {
    L, R, D, U
}


class Position(val x: Int, val y: Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Position

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }

    override fun toString(): String {
        return "Position(x=$x, y=$y)"
    }
}

fun main() {

    val heads = mutableListOf(Position(0, 0))
    val tails = mutableListOf(Position(0, 0))

    fun part1(input: List<String>): Int {
        input.map { it.split(" ") }
            .map { Pair(Direction.valueOf(it.first()), it.last().toInt()) }
            .forEach { it.moveHead(heads, tails) }
        return tails.toSet().size
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val input = readInput("Day09")


    part1(input).println() // 6037
    part2(input).println() // 2485
}

fun Pair<Direction, Int>.moveHead(heads: MutableList<Position>, tails: MutableList<Position>) {
    for (i in 0 until second) {
        val currentHead = heads.last()
        val currentTail = tails.last()

        val nextHead = when (first) {
            Direction.R -> Position(currentHead.x + 1, currentHead.y)
            Direction.L -> Position(currentHead.x - 1, currentHead.y)
            Direction.U -> Position(currentHead.x, currentHead.y + 1)
            Direction.D -> Position(currentHead.x, currentHead.y - 1)
        }
        heads.add(nextHead)
        tails.add(nextHead.calculateTail(currentTail))
    }
}

private fun Position.calculateTail(tail: Position): Position {
    // 1/2/3/4/5/6/7/8 a/b --> tail is still touching
    if (abs(x - tail.x) <= 1 && abs(y - tail.y) <= 1) {
        return tail
    }
    // 1b: head moved 1 to right
    if (x - tail.x == 2 && y == tail.y) {
        return Position(tail.x + 1, tail.y)
    }
    // 2b: head moved 1 to left
    if (x - tail.x == -2 && y == tail.y) {
        return Position(tail.x - 1, tail.y)
    }
    // 3b: head moved 1 up
    if (x == tail.x && y - tail.y == 2) {
        return Position(tail.x, tail.y + 1)
    }
    // 4b: head moved 1 down
    if (x == tail.x && y - tail.y == -2) {
        return Position(tail.x, tail.y - 1)
    }
    // 5b: head is diagonal right up
    if ((x - tail.x == 1 && y - tail.y == 2) ||
        (x - tail.x == 2 && y - tail.y == 1)
    ) {
        return Position(tail.x + 1, tail.y + 1)
    }
    // 6b: head is diagonal right down
    if ((x - tail.x == 1 && y - tail.y == -2) ||
        (x - tail.x == 2 && y - tail.y == -1)
    ) {
        return Position(tail.x + 1, tail.y - 1)
    }
    // 7b: head is diagonal left down
    if ((x - tail.x == -2 && y - tail.y == -1) ||
        (x - tail.x == -1 && y - tail.y == -2)
    ) {
        return Position(tail.x - 1, tail.y - 1)
    }
    // 8b: head is diagonal left up
    if ((x - tail.x == -2 && y - tail.y == 1) ||
        (x - tail.x == -1 && y - tail.y == 2)
    ) {
        return Position(tail.x - 1, tail.y + 1)
    }
    println("THIS SHOULD NEVER HAPPEN")
    return Position(99, 99)
}




