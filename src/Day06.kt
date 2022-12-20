fun main() {

    fun String.findMarker(markerLength: Int): Int {
        var result = 0
        run breaking@{
            indices.forEach {
                if (hasMarkerAfter(it, markerLength)) {
                    result = it + markerLength
                    return@breaking
                }
            }
        }
        return result
    }

    fun part1(input: List<String>): Int {
        return input.first().findMarker(4)
    }

    fun part2(input: List<String>): Int {
        return input.first().findMarker(14)

    }

    val input = readInput("Day06")

    part1(input).println() // 1155
    part2(input).println() // 2789
}

private fun String.hasMarkerAfter(position: Int, markerLength: Int): Boolean {
    val chars = substring(position, position + markerLength).toList().sorted()
    for (i in chars.indices) {
        if (i > 0 && chars[i] == chars[i - 1]) {
            return false
        }
    }
    return true
}
