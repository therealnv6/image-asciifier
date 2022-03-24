package io.github.devrawr.asciifier.color.impl

import io.github.devrawr.asciifier.color.Colorifier
import java.awt.Color
import kotlin.math.abs

object LinuxColorifier : Colorifier
{
    private val colors = mapOf(
        Color.BLACK to 30,
        Color.RED to 31,
        Color.GREEN to 32,
        Color.YELLOW to 33,
        Color.BLUE to 34,
        Color.MAGENTA to 35,
        Color.CYAN to 36,
        Color.LIGHT_GRAY to 37,
        Color.GRAY to 90,
        Color.WHITE to 97
    )

    private val cachedColors = hashMapOf<Int, Int>()

    override fun colorize(string: String, closestNeighbour: Color): String
    {
        val rgb = closestNeighbour.rgb

        if (cachedColors.containsKey(rgb))
        {
            return string.ansify(cachedColors[rgb]!!)
        }

        return string.ansify(
            colors[getClosestNeighbour(closestNeighbour, this.colors.keys.toList())]!!
        )
    }

    override fun getClosestNeighbour(color: Color, allowedColors: List<Color>): Color
    {
        var distance = Int.MAX_VALUE
        var closest = colors.keys.first()

        for (entry in this.colors)
        {
            if (!allowedColors.contains(entry.key))
            {
                continue
            }

            val color2 = entry.key
            val dist = abs(color2.red - color.red) + abs(color2.green - color.green) + abs(color2.blue - color.blue)

            if (dist < distance)
            {
                distance = dist
                closest = entry.key
            }
        }

        return closest
    }


    fun String.ansify(color: Int, prefix: String = "\u001B"): String
    {
        return "${prefix}[0;${color}m${this}"
    }
}