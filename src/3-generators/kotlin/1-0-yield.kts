@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

import kotlin.coroutines.experimental.buildSequence

val sequence = buildSequence {
    yield(1)
    yield(2)
    yield(3)
}
// yield(4)

sequence.forEach {
    println(it)
}