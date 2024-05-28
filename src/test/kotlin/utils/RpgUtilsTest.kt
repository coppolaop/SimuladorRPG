package utils

import com.coppolaop.utils.RpgUtils
import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class RpgUtilsTest {

    @Test
    internal fun rollDice_simple() {
        mockkObject(RpgUtils)
        every { RpgUtils.randomize(any<Int>()) } returns 10

        val result = RpgUtils.rollDice("1d20")

        assertEquals(10, result)
    }

    @Test
    internal fun rollDice_MultipleDice() {
        mockkObject(RpgUtils)
        every { RpgUtils.randomize(any<Int>()) } returns 3

        val result = RpgUtils.rollDice("6d6")

        assertEquals(18, result)
    }

    @Test
    fun maxDice_simple() {
        val result = RpgUtils.maxDice("1d20")

        assertEquals(20, result)
    }

    @Test
    fun maxDice_MultipleDice() {
        val result = RpgUtils.maxDice("6d6")

        assertEquals(36, result)
    }

}