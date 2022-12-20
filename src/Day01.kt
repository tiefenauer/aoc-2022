fun main() {
    fun part1(input: List<String>): Int {
        return input.groupNonEmptyItems()
            .map { calories -> calories.fold(0) { acc, cal -> acc + cal.toInt() } }
            .maxOf { it }
    }

    val input = readInput("Day01")
    part1(input).println() // 68787
}

fun List<String>.groupNonEmptyItems() = fold(ArrayList<MutableList<String>>()) { list, item ->
    list.apply {
        if (item.isBlank() || list.isEmpty()) {
            add(mutableListOf())
        } else {
            last().add(item)
        }
    }
}.map { it.toList() }
