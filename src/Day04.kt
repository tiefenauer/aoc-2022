fun main() {

    fun part1(input: List<String>): Int {
        return input.toRanges()
            .filter { it.overlapsFully() }
            .size
    }

    fun part2(input: List<String>): Int {
        return input.toRanges()
            .filter { it.overlapsPartially() }
            .size
    }

    val input = readInput("Day04")
    part1(input).println() // 475
    part2(input).println() // 825
}

private fun List<String>.toRanges() = map { it.split(",") }
    .map { it.map { s -> s.split("-").toRange() } }

private fun List<String>.toRange() = Pair(first().toInt(), last().toInt())

private fun List<Pair<Int, Int>>.overlapsPartially() =
    overlapsFully() || first().overlapsPartiallyWith(last()) || last().overlapsPartiallyWith(first())

private fun List<Pair<Int, Int>>.overlapsFully() =
    first().overlapsFullyWith(last()) || last().overlapsFullyWith(first())

private fun Pair<Int, Int>.overlapsFullyWith(other: Pair<Int, Int>) =
    first <= other.first && second >= other.second

private fun Pair<Int, Int>.overlapsPartiallyWith(other: Pair<Int, Int>) =
    first in other.first..other.second || second in other.first..other.second
