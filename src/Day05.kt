fun main() {

    fun part1(stacksById: Map<Char, String>, moves:List<Triple<Int, Char, Char>>): String {
        val stackPartOne = stacksById.toMutableMap()
        moves.forEach { stackPartOne.moveSequentially(it.first, it.second, it.third) }
        return stackPartOne.values.joinToString("") { it.firstOrNull().toString() }
    }

    fun part2(stacksById: Map<Char, String>, moves:List<Triple<Int, Char, Char>>): String {
        val stackPartTwo = stacksById.toMutableMap()
        moves.forEach { stackPartTwo.move(it.first, it.second, it.third) }
        return stackPartTwo.values.joinToString("") { it.firstOrNull().toString() }
    }

    val lines = readInput("Day05")
    val stackLines = lines.take(8)
    val stackIdLine = lines[8]
    val moves = lines.drop(10).map { it.toInstructions() }
    val stacksById = stackIdLine.replace(" ", "")
        .toCharArray()
        .map { stackId -> Pair(stackId, stackIdLine.indexOf(stackId)) }
        .associate { it }
        .mapValues { readColumn(it.value, stackLines) }

    part1(stacksById, moves).println() // DHBJQJCCW
    part2(stacksById, moves).println() // WJVRLSJJT
}

private fun MutableMap<Char, String>.moveSequentially(numberOfItems: Int, from: Char, to: Char) {
    for (i in 0 until numberOfItems) {
        move(1, from, to)
    }
}

private fun MutableMap<Char, String>.move(numberOfItems: Int, from: Char, to: Char) {
    val fromStack = get(from).orEmpty()
    set(from, fromStack.drop(numberOfItems))
    set(to, fromStack.take(numberOfItems) + get(to).orEmpty())
}

private fun readColumn(columnIndex: Int, stackLines: List<String>) =
    stackLines.map { it[columnIndex] }.joinToString("").replace(" ", "")

private fun String.toInstructions(): Triple<Int, Char, Char> {
    val (n, source, target) = split("move ", " from ", " to ").filter { it.isNotBlank() }
    return Triple(n.toInt(), source[0], target[0])
}
