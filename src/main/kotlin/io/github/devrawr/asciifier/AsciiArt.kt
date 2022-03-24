package io.github.devrawr.asciifier

class AsciiArt(val list: List<String>)
{
    fun joinToString(spliterator: String, transform: ((String) -> CharSequence)): String
    {
        return list.joinToString(
            separator = spliterator,
            transform = transform
        )
    }

    override fun toString(): String
    {
        return list.joinToString("\n") { it }
    }
}