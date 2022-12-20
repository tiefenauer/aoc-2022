class Dir(val dirName: String, val parent: Dir?, val subDirs: MutableList<Dir>, var size: Int)

fun main() {

    val root = Dir("/", null, mutableListOf(), 0)

    fun part1(allDirs: List<Dir>): Int {
        return allDirs.filter { it.size <= 100000 }
            .map { it.size }
            .sumOf { it }
    }

    fun part2(allDirs: List<Dir>): Int {
        return allDirs
            .filter { it.size >= 30000000 - (70000000 - root.size) }
            .map { it.size }
            .minByOrNull { it }!!
    }

    val input = readInput("Day07")

    val allDirs = input.getAllDirs(root)

    part1(allDirs).println() // 1141028
    part2(allDirs).println() // 8278005
}

private fun List<String>.getAllDirs(root: Dir): MutableList<Dir> {
    var currentDir = root
    drop(1).forEach { line ->
        when {
            line == "$ cd .." -> currentDir = currentDir.parent!!
            line.startsWith("$ cd") ->
                currentDir = currentDir.subDirs.find { it.dirName == line.dirName() }!!

            line.startsWith("dir") ->
                currentDir.subDirs.add(Dir(line.dirName(), currentDir, mutableListOf(), 0))

            line.first().isDigit() -> currentDir.size += line.split(" ").first().toInt()
        }
    }
    calculateDirectorySize(root)
    val allDirs = mutableListOf<Dir>()
    listAllDirs(allDirs, root)
    return allDirs
}

private fun String.dirName() = split(" ").last()

private fun calculateDirectorySize(dir: Dir): Int {
    var dirSize = dir.size
    if (dir.subDirs.isNotEmpty()) {
        dirSize += dir.subDirs.map { calculateDirectorySize(it) }.sumOf { it }
    }
    dir.size = dirSize
    return dirSize
}

private fun listAllDirs(allDirs: MutableList<Dir>, dir: Dir): List<Dir> {
    allDirs.addAll(dir.subDirs)
    dir.subDirs.forEach { listAllDirs(allDirs, it) }
    return allDirs
}


