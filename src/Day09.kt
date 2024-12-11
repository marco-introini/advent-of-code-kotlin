data class DiskPoint(val fileId: Int)

data class Disk(
    val size: Int,
    val map: MutableMap<Int, DiskPoint>
) {
    override fun toString(): String {
        val stringBuilder = StringBuilder()
        with(stringBuilder) {
            for (i in 0 until size) {
                append(map[i]?.fileId ?: '.')
            }
        }
        return stringBuilder.toString()
    }

    fun hasNoHoles(): Boolean {
        val total = map.size
        for (i in 0 until total) {
            if (!map.containsKey(i)) {
                return false
            }
        }
        return true
    }

    fun reArrange() {
        for (i in size - 1 downTo 0) {
            if (map.containsKey(i)) {
                if (hasNoHoles()) {
                    return
                }
                for (j in 0 until i) {
                    if (!map.containsKey(j)) {
                        val tempPoint = map[i]
                        if (tempPoint != null) {
                            map.remove(i)
                            map[j] = tempPoint
                            //println("Inserito il punto ${tempPoint} nella posizione ${j}")
                            break
                        } else println("Errore per i $i e j $j")
                    }
                }
            }
        }
    }

    fun Disk.findFirstEmptySpace(n: Int): Int? {
        var consecutiveEmpty = 0
        var startIndex = -1

        for (i in 0 until size) {
            if (!map.containsKey(i)) {
                if (consecutiveEmpty == 0) {
                    startIndex = i
                }
                consecutiveEmpty++
                if (consecutiveEmpty >= n) {
                    return startIndex
                }
            } else {
                consecutiveEmpty = 0
                startIndex = -1
            }
        }

        // Nessuno spazio trovato
        return null
    }

    fun reArrangeByLength() {
        // gli elementi sono già ordinati. Parto da quello con fileId maggiore
        val maxValue = map.entries.maxBy { it.value.fileId }.value.fileId

        for (i in maxValue downTo 1) {
            val fileSize = map.count { it.value.fileId == i }
            val firstSpace = findFirstEmptySpace(fileSize)
            val firstKey = map.keys.firstOrNull { key -> map[key]?.fileId == i }!!
            //println("$i con inizio $firstKey -> dimensione $fileSize -> firstSpace $firstSpace")

            if (firstSpace != null && (firstSpace < firstKey)) {
                val tempPoint = DiskPoint(i)
                val iterator = map.iterator()
                while (iterator.hasNext()) {
                    val entry = iterator.next()
                    if (entry.value.fileId == tempPoint.fileId) {
                        iterator.remove()
                    }
                }
                for (i in firstSpace until firstSpace + fileSize) {
                    map[i] = tempPoint
                }
            }
        }
    }

}


fun main() {

    fun generateDisk(input: String): Disk {
        var disk = mutableMapOf<Int, DiskPoint>()
        var dimension = 0
        input.forEachIndexed { index, value ->
            val intValue = Integer.parseInt(value.toString())
            if (index % 2 == 0) {
                repeat(intValue) {
                    disk[dimension] = DiskPoint(index / 2)
                    dimension++
                }
            } else {
                repeat(intValue) {
                    dimension++
                }
            }
        }

        return Disk(dimension, disk)
    }


    fun part1(input: List<String>): Long {
        val disk = generateDisk(input[0])
        disk.reArrange()

        var result = 0L
        disk.map.forEach { index, it ->
            result += index * it.fileId.toLong()
        }

        return result
    }

    fun part2(input: List<String>): Long {
        val disk = generateDisk(input[0])
        disk.reArrangeByLength()

        var result = 0L
        disk.map.forEach { index, it ->
            result += index * it.fileId.toLong()
        }

        return result
    }

    val testInput = readInput("Day09_test")
    val disk = generateDisk(testInput[0])
    println("TEST PART1")
    println(disk.toString())
    check(disk.toString() == "00...111...2...333.44.5555.6666.777.888899")
    disk.reArrange()
    println("TEST PART2")
    val disk2 = generateDisk(testInput[0])
    disk2.reArrangeByLength()
    check(disk2.toString() == "00992111777.44.333....5555.6666.....8888..")

    check(part1(testInput) == 1928L)
    check(part2(testInput) == 2858L)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
