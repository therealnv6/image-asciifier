package io.github.devrawr.asciifier

import io.github.devrawr.asciifier.color.Colorifier
import io.github.devrawr.asciifier.color.impl.LinuxColorifier
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

object Ascii
{
    private val normalSymbol = "■"
    private val grayScaleMap = hashMapOf<Color, String>(
        Color.BLACK to "■",
        Color.DARK_GRAY to "□",
        Color.GRAY to "|",
        Color.LIGHT_GRAY to "|"
    )

    var colorifier: Colorifier = LinuxColorifier

    inline fun <reified T : Colorifier> usePlatform()
    {
        this.colorifier = T::class.java.kotlin.objectInstance!!
    }

    fun asciify(
        image: File,
        dimensions: Pair<Int, Int>
    ): AsciiArt
    {
        return asciifyColorized(image, dimensions, grayScaleMap.keys.toList(), true)
    }

    fun asciifyColorized(
        image: File,
        dimensions: Pair<Int, Int>,
        colors: List<Color> = listOf(),
        grayScale: Boolean = false
    ): AsciiArt
    {
        val bufferedImage = scale(
            ImageIO.read(image), dimensions.first, dimensions.second
        )

        val lines = mutableListOf<String>()

        for (y in 0 until bufferedImage.height)
        {
            var line = ""

            for (x in 0 until bufferedImage.width)
            {
                val color = Color(
                    bufferedImage.getRGB(x, y)
                )

                line += if (grayScale)
                {
                    grayScaleMap[
                            colorifier.getClosestNeighbour(
                                color, colors
                            )
                    ]
                } else
                {
                    colorifier.colorize(this.normalSymbol, color)
                }
            }

            lines += line
        }

        return AsciiArt(lines)
    }

    fun scale(parent: BufferedImage, height: Int, width: Int): BufferedImage
    {
        val image = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)

        val parentWidth = parent.width
        val parentHeight = parent.height

        val verticals = IntArray(height)

        for (y in 0 until height)
        {
            verticals[y] = y * parentHeight / height
        }

        for (x in 0 until width)
        {
            val scaledX = x * parentWidth / width

            for (y in 0 until height)
            {
                image.setRGB(
                    x, y, parent.getRGB(scaledX, verticals[y])
                )
            }
        }

        return image
    }
}