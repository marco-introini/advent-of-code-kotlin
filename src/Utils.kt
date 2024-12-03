import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.math.abs

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun areNumbersMonotonic(numbers: List<Int>): Boolean {
    if (numbers.isEmpty() || numbers.size == 1) {
        return true
    }

    var isIncreasing = true
    var isDecreasing = true

    for (i in 1 until numbers.size) {
        if (numbers[i] < numbers[i - 1]) {
            isIncreasing = false
        }
        if (numbers[i] > numbers[i - 1]) {
            isDecreasing = false
        }
    }

    return isIncreasing || isDecreasing
}


