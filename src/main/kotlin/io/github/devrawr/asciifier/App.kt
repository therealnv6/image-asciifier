package io.github.devrawr.asciifier

import java.io.File

object App
{
    @JvmStatic
    fun main(args: Array<String>)
    {
        val file = File("splash.png")
        val art = Ascii.asciifyColorized(
            file,
            Pair(80, 180)
        )

        println(art.toString())
    }
}
