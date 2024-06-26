package com.coppolaop.utils

import kotlin.random.Random

object RpgUtils {
    private val MAX = "MAX"
    private val NORMAL = "NORMAL"
    private val MIN = "MIN"

    fun rollDice(dice: String): Int {
        return rollDice(dice, NORMAL)
    }

    fun maxDice(dice: String): Int {
        return rollDice(dice, MAX)
    }

    private fun rollDice(dice: String, mode: String): Int {
        var result = 0
        val values = dice.split("[Dd]".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        var quantity = 0
        var size = 0

        quantity = if (values[0] == "") {
            1
        } else {
            values[0].toInt()
        }

        size = values[1].toInt()

        for (i in 0 until quantity) {
            when (mode) {
                NORMAL -> result += Random.nextInt(size) + 1
                MAX -> result += size
                MIN -> result++
            }
        }
        return result
    }
}