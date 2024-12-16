import kotlin.math.absoluteValue

fun main() {

    var ALTEZZA = 11 //103
    var LARGHEZZA = 7 //101


    data class Robot(val startX: Int, val startY: Int, val directionX: Int, val directionY: Int) {
        var posX = startX
        var posY = startY

        fun move() {
            posX += directionX
            posY += directionY
        }

    }

    data class Griglia(val x: Int, val y: Int) {
        var listaRobot = mutableListOf<Robot>()

        fun addRobot(robot: Robot) {
            listaRobot.add(robot)
        }

        fun moveAll() {
            listaRobot.forEach {
                it.move()
                if (it.posX < 0 || it.posX > x) {
                    it.posX = it.posX.mod(x).absoluteValue
                    println("Robot ${it.posX} ${it.posY} non valido")
                }
                if (it.posY < 0 || it.posY > y) {
                    it.posY = it.posY.mod(y).absoluteValue
                    println("Robot ${it.posX} ${it.posY} non valido")
                }
            }
        }

        fun find(x: Int, y: Int): Boolean =
            if (listaRobot.any { it.posX == x && it.posY == y }) {
                true
            } else {
                false
            }

        fun countAtPosition(x: Int, y: Int): Int =
            listaRobot.count { it.posX == x && it.posY == y }

        fun stampaGriglia() {
            for (j in 0 until y) {
                for (i in 0 until x) {
                    if (find(i, j)) {
                        print(countAtPosition(i, j))
                    } else {
                        print(".")
                    }
                }
                println("")
            }
        }

        fun getMultOfQuadrand(): Long {
            val xDimension = x / 2
            val yDimension = y / 2

            var q1 = getSum(0, xDimension, 0, yDimension)
            var q2 = getSum(xDimension + 1, xDimension * 2, 0, yDimension)
            var q3 = getSum(0, xDimension, yDimension + 1, yDimension * 2)
            var q4 = getSum(xDimension + 1, xDimension * 2, yDimension + 1, yDimension * 2)
            return q1*q2*q3*q4
        }

        fun getSum(xBegin: Int, xEnd: Int, yBegin: Int, yEnd: Int): Long {
            var total = 0
            for (y in yBegin..yEnd) {
                for (x in xBegin .. xEnd) {
                    total += listaRobot.count { it.posX == x && it.posY == y }
                }
            }
            return total.toLong()
        }

    }


    fun part1(input: List<String>): Long {
        val griglia = Griglia(LARGHEZZA, ALTEZZA)
        input.forEach {
            val posX = it.substringAfter("=").substringBefore(",").toInt()
            val posY = it.substringAfter(",").substringBefore(" ").toInt()
            val vel = it.substringAfter(" ")
            val directionX = vel.substringAfter("=").substringBefore(",").toInt()
            val directionY = vel.substringAfter(",").toInt()
            griglia.addRobot(Robot(posX, posY, directionX, directionY))
        }
        griglia.stampaGriglia()
        return griglia.getMultOfQuadrand()
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day14_test")
    check(part1(testInput) == 12L)

    val input = readInput("Day14")
    part1(input).println()
    part2(input).println()
}
