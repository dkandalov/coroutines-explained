@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

import kotlin.coroutines.experimental.buildSequence

val sequence = buildSequence {
    (1..3).forEach {
        yield(it)
    }
}

sequence.forEach {
    println(it)
}