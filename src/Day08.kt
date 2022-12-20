fun main() {


    fun part1(input: List<String>): Int {
        return input.mapIndexed { y: Int, row: String -> row.countVisibleTrees(y, input) }
            .sum()
    }

    fun part2(input: List<String>): Int {
        return input.mapIndexed { y, row -> row.calculateScenicScore(y, input).max() }.max()
    }

    val input = readInput("Day08")


    part1(input).println() // 1835
    part2(input).println() // 263670
}

private fun List<String>.toColumns() = first().mapIndexed() { index, _ -> column(index, this) }

private fun String.countVisibleTrees(y: Int, rows: List<String>): Int {
    val columns = rows.toColumns()
    return filterIndexed { x, tree -> tree.isVisible(x, y, rows, columns) }.length
}

private fun String.calculateScenicScore(y: Int, rows: List<String>): List<Int> {
    val columns = rows.toColumns()
    return mapIndexed { x, tree -> tree.scenicScore(x, y, rows, columns) }
}


private fun Char.isVisible(x: Int, y: Int, rows: List<String>, columns: List<String>): Boolean {
    val height = digitToInt()
    return x == 0 || y == 0 || x == columns.size - 1 || y == rows.size - 1 // edge tree
            || height > rows.left(x, y).max() // visible from left
            || height > rows.right(x, y).max() // visible from right
            || height > columns.top(x, y).max() // visible from top
            || height > columns.bottom(x, y).max() // visible from bottom
}

private fun Char.scenicScore(x: Int, y: Int, rows: List<String>, columns: List<String>): Int {
    if (x == 0 || y == 0 || x == columns.size - 1 || y == rows.size - 1) {
        return 0 // edge tree
    }
    val height = digitToInt()
    val scoreLeft = rows.left(x, y).reversed().takeUntil { it >= height }.size + 1
    val scoreRight = rows.right(x, y).takeUntil { it >= height }.size + 1
    val scoreUp = columns.top(x, y).reversed().takeUntil { it >= height }.size + 1
    val scoreDown = columns.bottom(x, y).takeUntil { it >= height }.size + 1
    val score = scoreLeft * scoreRight * scoreUp * scoreDown
    return score
}

fun List<String>.left(x: Int, y: Int) = this[y].take(x).map { it.digitToInt() }
fun List<String>.right(x: Int, y: Int) = this[y].drop(x + 1).map { it.digitToInt() }
fun List<String>.top(x: Int, y: Int) = this[x].take(y).map { it.digitToInt() }
fun List<String>.bottom(x: Int, y: Int) = this[x].drop(y + 1).map { it.digitToInt() }

fun <T> List<T>.takeUntil(predicate: (T) -> Boolean) =
    take(if (indexOfFirst(predicate) >= 0) indexOfFirst(predicate) else size - 1)

fun column(columnIndex: Int, lines: List<String>) = lines.map { it[columnIndex] }.joinToString("")
