fun main() {
    val redCubesMax = 12
    val greenCubesMax = 13
    val blueCubesMax = 14

    data class CubeSet(val red: Int, val green: Int, val blue: Int)
    data class Game(val gameId: Int, val cubeSets: List<CubeSet>)

    fun lineToGame(input: List<String>): List<Game> {
        return input.map { line ->
            val gameId = line.substring(5, line.indexOf(':')).toInt()
            val cubeSets = line.substringAfter(':').split(';')
                .map { set ->
                    val red = "(\\d+) red".toRegex().find(set)?.groupValues?.get(1)?.toInt() ?: 0
                    val green = "(\\d+) green".toRegex().find(set)?.groupValues?.get(1)?.toInt() ?: 0
                    val blue = "(\\d+) blue".toRegex().find(set)?.groupValues?.get(1)?.toInt() ?: 0
                    CubeSet(red, green, blue)
                }
            Game(gameId, cubeSets)
        }
    }

    fun part1(input: List<String>): Int {
        return lineToGame(input)
            .filter { line ->
                !line.cubeSets.any { it.red > redCubesMax || it.green > greenCubesMax || it.blue > blueCubesMax }
            }.sumOf { it.gameId }
    }

    fun part2(input: List<String>): Int {
        return lineToGame(input).sumOf { game ->
            val red = game.cubeSets.maxOf { it.red }
            val blue = game.cubeSets.maxOf { it.blue }
            val green = game.cubeSets.maxOf { it.green }
            red * blue * green
        }
    }

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
