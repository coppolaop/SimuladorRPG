package com.coppolaop.utils

import java.util.*

object RpgUtils {
    fun rollDice(dice: String): Int {
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
            result += Random().nextInt(size) + 1
        }

        return result
    }
}