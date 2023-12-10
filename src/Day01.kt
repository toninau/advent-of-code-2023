fun main() {
    val spelledToDigit = mapOf(
        "one" to '1',
        "two" to '2',
        "three" to '3',
        "four" to '4',
        "five" to '5',
        "six" to '6',
        "seven" to '7',
        "eight" to '8',
        "nine" to '9',
    )

    fun String.firstDigit(): Char {
        var string = ""
        for (char in this) {
            string += char
            for (digit in spelledToDigit) {
                if (string.contains(digit.value) || string.contains(digit.key)) {
                    return digit.value
                }
            }
        }
        throw Exception("no digit found in string $string")
    }

    fun String.lastDigit(): Char {
        var string = ""
        for (char in this.reversed()) {
            string = "$char$string"
            for (digit in spelledToDigit) {
                if (string.contains(digit.value) || string.contains(digit.key)) {
                    return digit.value
                }
            }
        }
        throw Exception("no digit found in string $string")
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val firstDigit = line.first { it.isDigit() }
            val lastDigit = line.last { it.isDigit() }
            "${firstDigit}${lastDigit}".toInt()
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line -> "${line.firstDigit()}${line.lastDigit()}".toInt() }
    }

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
