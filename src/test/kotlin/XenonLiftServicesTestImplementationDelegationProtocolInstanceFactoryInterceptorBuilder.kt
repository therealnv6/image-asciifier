import io.github.devrawr.asciifier.Ascii
import org.junit.jupiter.api.Test
import java.io.File

class XenonLiftServicesTestImplementationDelegationProtocolInstanceFactoryInterceptorBuilder
{
    @Test
    fun test()
    {
        val file = File("splash.png")
        val art = Ascii.asciifyColorized(
            file,
            Pair(80, 180)
        )

        println(art.toString())
    }
}