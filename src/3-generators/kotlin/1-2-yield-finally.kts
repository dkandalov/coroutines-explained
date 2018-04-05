@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

import kotlin.coroutines.experimental.buildSequence

fun foo(): Sequence<Int> {
    return buildSequence {
        try {
            yield(1)
            yield(2)
            yield(3)
        } finally {
            println("finally")
        }
    }
}

foo().forEach {
    println(it)
}