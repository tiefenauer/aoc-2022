
fun main() {

    val alphabet = ('a'..'z').joinToString("")
    val priorities = "${alphabet}${alphabet.uppercase()}"
        .mapIndexed { index, c -> Pair(c, index + 1) }
        .associate { it }

    fun part1(input: List<String>): Int {
        return input.map { it.splitInHalf() }
            .map { findInvalidElement(it.first, it.second) }
            .map { priorities[it] }
            .sumOf { it!! }
    }

    fun part2(input: List<String>): Int {
        return input.chunked(3)
            .map { it.findBadge() }
            .map { priorities[it] }
            .sumOf { it!! }
    }

    val input = readInput("Day03")
    part1(input).println() // 8233
    part2(input).println() // 2821
}

private fun List<String>.findBadge() = first().toCharArray().find { all { s -> s.contains(it) } }

private fun findInvalidElement(first: String, second: String) =
    first.toCharArray().first { second.contains(it) }

private fun String.splitInHalf() = Pair(substring(0, length / 2), substring(length / 2))
