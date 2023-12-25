fun main() {
    data class Scratchcard(val index: Int, val winningNumbers: List<Int>, val guessedNumbers: List<Int>)

    fun parse(input: List<String>): List<Scratchcard> {
        return input.mapIndexed { index, line ->
            val scratchcardNumbers = line.substringAfter(':').split('|').map { numbers ->
                numbers.trim().split(' ')
                    .filter { it.isNotBlank() }
                    .map { it.toInt() }
            }
            Scratchcard(index = index, winningNumbers = scratchcardNumbers[0], guessedNumbers = scratchcardNumbers[1])
        }
    }

    fun part1(scratchcards: List<Scratchcard>): Int {
        return scratchcards.sumOf { scratchcard ->
            scratchcard.winningNumbers.intersect(scratchcard.guessedNumbers.toSet())
                .takeIf { it.isNotEmpty() }
                ?.fold(0.5) { sum, _ -> sum * 2 }?.toInt() ?: 0
        }
    }

    fun part2(scratchcards: List<Scratchcard>): Int {
        val deque = ArrayDeque(scratchcards)
        deque.forEach {
            val wonScratchcardsAmount = it.winningNumbers.intersect(it.guessedNumbers.toSet()).size
            val wonScratchcards = scratchcards.slice(it.index + 1..it.index + wonScratchcardsAmount)
            deque.addAll(wonScratchcards)
        }
        return deque.size
    }

    val input = readInput("Day04")
    val scratchcards = parse(input)
    println(part1(scratchcards))
    println(part2(scratchcards))
}
