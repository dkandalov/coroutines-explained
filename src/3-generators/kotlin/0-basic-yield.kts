@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package kotlin

import kotlin.coroutines.experimental.Continuation
import kotlin.coroutines.experimental.CoroutineContext
import kotlin.coroutines.experimental.EmptyCoroutineContext
import kotlin.coroutines.experimental.createCoroutine
import kotlin.coroutines.experimental.intrinsics.COROUTINE_SUSPENDED
import kotlin.coroutines.experimental.intrinsics.suspendCoroutineOrReturn

class ResumableFunction {
    private var c: Continuation<Unit>? = null

    fun resume() {
        val cont = c
        c = null
        cont?.resume(Unit)
    }

    suspend fun yield() {
        return suspendCoroutineOrReturn { it: Continuation<Unit> ->
            c = it
            COROUTINE_SUSPENDED
        }
    }

    companion object {
        fun create(block: suspend ResumableFunction.() -> Unit): ResumableFunction {
            val f = ResumableFunction()
            f.c = block.createCoroutine(f, completion = EmptyContinuation)
            return f
        }
    }
}

object EmptyContinuation: Continuation<Unit> {
    override val context: CoroutineContext = EmptyCoroutineContext
    override fun resume(value: Unit) {}
    override fun resumeWithException(exception: Throwable) = throw exception
}


val f = ResumableFunction.create {
    println(1)
    yield()
    println(3)
    yield()
    println(5)
}
println(0)
f.resume()
println(2)
f.resume()
println(4)
f.resume()
println(6)


