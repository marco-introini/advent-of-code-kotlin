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
            for (i in 0 until x) {
                for (j in 0 until y) {
                    if (find(x, y)) {
                        print(countAtPosition(x, y))
                    } else {
                        print(".")
                    }
                }
                println()
            }
        }

    }


    fun part1(input: List<String>): Int {
        val griglia = Griglia(LARGHEZZA, ALTEZZA)
        input.forEach {
            val posX = it.substringAfter("=").substringBefore(",").toInt()
            val posY = it.substringAfter(",").substringBefore(" ").toInt()
            val vel = it.substringAfter(" ")
            val directionX = vel.substringAfter("=").substringBefore(",").toInt()
            val directionY = it.substringAfter(",").toInt()
            griglia.addRobot(Robot(posX, posY, directionX, directionY))
        }
        griglia.stampaGriglia()

        return 0

    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day14_test")
    check(part1(testInput) == 12)

    val input = readInput("Day14")
    part1(input).println()
    part2(input).println()
}
