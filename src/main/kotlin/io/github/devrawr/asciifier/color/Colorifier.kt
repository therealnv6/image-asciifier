package io.github.devrawr.asciifier.color

import java.awt.Color

interface Colorifier
{
    fun colorize(string: String, closestNeighbour: Color): String
    fun getClosestNeighbour(color: Color, allowedColors: List<Color>): Color
}