@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

import kotlin.coroutines.experimental.Continuation
import kotlin.coroutines.experimental.CoroutineContext
import kotlin.coroutines.experimental.EmptyCoroutineContext
import kotlin.coroutines.experimental.createCoroutine
import kotlin.coroutines.experimental.intrinsics.COROUTINE_SUSPENDED
import kotlin.coroutines.experimental.intrinsics.suspendCoroutineOrReturn

class YieldingFunction<T> {
    private var start: Continuation<Unit>? = null
    private var c: Continuation<T>? = null
    private var yieldedValue: T? = null

    fun resume(n: T): T? {
        if (start != null) {
            start!!.resume(Unit)
            start = null
        }
        else {
            val cont = c
            c = null
            yieldedValue = null
            cont?.resume(n)
        }
        return yieldedValue
    }

    suspend fun yield(n: T): T {
        return suspendCoroutineOrReturn { it: Continuation<T> ->
            yieldedValue = n
            c = it
            COROUTINE_SUSPENDED
        }
    }

    companion object {
        fun <T> create(block: suspend YieldingFunction<T>.() -> Unit): YieldingFunction<T> {
            val f = YieldingFunction<T>()
            f.start = block.createCoroutine(f, completion = EmptyContinuation)
            return f
        }
    }
}

private object EmptyContinuation: Continuation<Unit> {
    override val context: CoroutineContext = EmptyCoroutineContext
    override fun resume(value: Unit) {}
    override fun resumeWithException(exception: Throwable) = throw exception
}


val f = YieldingFunction.create<String> {
    println(1)
    println("🙈 " + yield("b"))
    println(3)
    println("🙈 " + yield("d"))
    println(5)
}
println(0)
println("🐶 " + f.resume("a")) // starts "f" (resume argument is ignored)
println(2)
println("🐶 " + f.resume("c")) // continues from "yield b"
println(4)
println("🐶 " + f.resume("e")) // continues from "yield d"
println(6)
println("🐶 " + f.resume("f"))
println("🐶 " + f.resume("g"))