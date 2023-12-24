fun main() {
    fun parse(input: List<String>): List<Scratchcard> {
        return input.map { line ->
            val scratchcardNumbers = line.substringAfter(':').split('|').map { numbers ->
                numbers.trim().split(' ')
                    .filter { it.isNotBlank() }
                    .map { it.toInt() }
            }
            Scratchcard(winningNumbers = scratchcardNumbers[0], guessedNumbers = scratchcardNumbers[1])
        }
    }

    fun part1(scratchcards: List<Scratchcard>): Int {
        return scratchcards.sumOf { scratchcard ->
            scratchcard.winningNumbers.intersect(scratchcard.guessedNumbers.toSet())
                .takeIf { it.isNotEmpty() }
                ?.fold(0.5) { sum, _ -> sum * 2 }?.toInt() ?: 0
        }
    }

    val input = readInput("Day04")
    val scratchcards = parse(input)
    println(part1(scratchcards))
}

data class Scratchcard(val winningNumbers: List<Int>, val guessedNumbers: List<Int>)
