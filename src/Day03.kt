fun main() {
    fun hasAdjacentSymbol(
        charPositionY: Int,
        charStartPositionX: Int,
        charEndPositionX: Int,
        schematic: List<List<Char>>,
    ): Boolean {
        val lineLength = schematic[charPositionY].size - 1
        val startRangeY = if (charPositionY - 1 > 0) charPositionY - 1 else 0
        val endRangeY = if (charPositionY + 1 < schematic.size) charPositionY + 1 else schematic.size - 1
        val startRangeX = if (charStartPositionX - 1 > 0) charStartPositionX - 1 else 0
        val endRangeX = if (charEndPositionX + 1 < lineLength) charEndPositionX + 1 else lineLength - 1

        for (i in startRangeY..endRangeY) {
            for (j in startRangeX..endRangeX) {
                if (!schematic[i][j].isDigit() && schematic[i][j] != '.') {
                    return true
                }
            }
        }
        return false
    }

    fun part1(input: List<String>): Int {
        val schematic = input.map { it.toList() } // [y], [x]
        val foundNumbers = mutableListOf<Int>()
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
                    if (hasAdjacentSymbol(y, charStartPositionX, x - 1, schematic)) {
                        foundNumbers.add(number.toInt())
                    }
                    number = ""
                    charStartPositionX = 0
                }

            }
        }
        return foundNumbers.sum()
    }

    val input = readInput("Day03")
    println(part1(input))
}
