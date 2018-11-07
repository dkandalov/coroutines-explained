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

    suspend fun yield() {
        return suspendCoroutineOrReturn { it: Continuation<Unit> ->
            c = it
            COROUTINE_SUSPENDED
        }
    }

    fun resume() {
        val cont = c
        c = null
        cont?.resume(Unit)
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
    println(2)
    yield()
    println(4)
    yield()
    println(6)
}
println(1)
f.resume()
println(3)
f.resume()
println(5)
f.resume()
println(7)


