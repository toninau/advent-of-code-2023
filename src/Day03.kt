fun main() {
    data class FoundNumber(val id: Int, val number: Int)
    data class Point(val x: Int, val y: Int)

    fun findPointAdjacentNumbers(
        point: Point,
        schematic: List<List<Char>>,
        numbers: Map<Point, FoundNumber>
    ): Set<FoundNumber> {
        val lineLength = schematic[point.y].size - 1
        val startRangeY = if (point.y - 1 > 0) point.y - 1 else 0
        val endRangeY = if (point.y + 1 < schematic.size) point.y + 1 else schematic.size - 1
        val startRangeX = if (point.x - 1 > 0) point.x - 1 else 0
        val endRangeX = if (point.x + 1 < lineLength) point.x + 1 else lineLength - 1
        val points = mutableSetOf<FoundNumber>()

        for (y in startRangeY..endRangeY) {
            for (x in startRangeX..endRangeX) {
                if (schematic[y][x].isDigit()) {
                    numbers[Point(x, y)]?.let { points.add(it) }
                }
            }
        }
        return points.toSet()
    }

    fun findAllNumbers(schematic: List<List<Char>>): Map<Point, FoundNumber> {
        val foundNumbers = mutableMapOf<Point, FoundNumber>()
        schematic.forEachIndexed { y, line ->
            var number = ""
            var charStartPositionX = 0
            line.forEachIndexed { x, character ->
                if (character.isDigit() && number == "") {
                    charStartPositionX = x
                }
                if (character.isDigit()) {
                    number += character
                }
                if ((x == line.size - 1 && number != "") || (!character.isDigit() && number != "")) {
                    val foundNumber = FoundNumber("$y$charStartPositionX".toInt(), number.toInt())
                    foundNumbers[Point(charStartPositionX, y)] = foundNumber
                    foundNumbers[Point(x - 1, y)] = foundNumber
                    number = ""
                    charStartPositionX = 0
                }

            }
        }
        return foundNumbers.toMap()
    }

    fun part1(schematic: List<List<Char>>, allNumbers: Map<Point, FoundNumber>): Int {
        val numbersAdjacentToSymbol = mutableSetOf<FoundNumber>()

        schematic.forEachIndexed { y, line ->
            line.forEachIndexed { x, character ->
                if (!character.isDigit() && character != '.') {
                    numbersAdjacentToSymbol.addAll(findPointAdjacentNumbers(Point(x, y), schematic, allNumbers))
                }
            }
        }
        return numbersAdjacentToSymbol.sumOf { it.number }
    }

    fun part2(schematic: List<List<Char>>, allNumbers: Map<Point, FoundNumber>): Int {
        val gearRatios = mutableListOf<Int>()

        schematic.forEachIndexed { y, line ->
            line.forEachIndexed { x, character ->
                if (character == '*') {
                    val adjacentNumbers = findPointAdjacentNumbers(Point(x, y), schematic, allNumbers)
                    if (adjacentNumbers.size == 2) {
                        gearRatios.add(adjacentNumbers.fold(1) { sum, element -> sum * element.number })
                    }
                }
            }
        }
        return gearRatios.sum()
    }

    val schematic = readInput("Day03").map { it.toList() } // [y][x]
    val allNumbers = findAllNumbers(schematic)
    println(part1(schematic, allNumbers))
    println(part2(schematic, allNumbers))
}
