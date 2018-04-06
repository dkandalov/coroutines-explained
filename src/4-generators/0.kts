@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

import java.io.Closeable
import java.io.File
import kotlin.coroutines.experimental.buildSequence

class DatabaseTable {
    fun readRows(): List<Int> {
        return listOf(1, 2, 3, 4, 5)
    }
}

inline fun <T : Closeable, R> T.use2(block: (T) -> R): R {
    try {
        return block(this)
    } catch (e: Throwable) {
        throw e
    } finally {
        println("finally")
        try {
            this.close()
        } catch (closeException: Throwable) {
            // cause.addSuppressed(closeException) // ignored here
        }
    }
}

fun foo() {
    File("foo.txt").bufferedWriter().use2 { writer ->
        buildSequence {
            yield(1)
            yield(2)
            yield(3)
        }.forEach {
            println(it)
            writer.append(it.toString())
        }
    }
}

foo()