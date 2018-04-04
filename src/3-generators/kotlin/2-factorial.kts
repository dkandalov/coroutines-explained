@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

import kotlin.coroutines.experimental.buildSequence


fun factorial(): Sequence<Int> {
    var result = 1
    var n = 0
    return buildSequence {
        while (true) {
            yield(result)
            n++
            result *= n
        }
    }
}

println(factorial().take(10))

