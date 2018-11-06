import kotlin.coroutines.Continuation
import kotlin.coroutines.createCoroutine
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED
import kotlin.coroutines.resume

var count = 0
var savedContinuation: Continuation<Int>? = null

createCoroutine {
    println(100 + suspendCoroutineUninterceptedOrReturn { continuation: Continuation<Int> ->
        savedContinuation = continuation
        continuation.resume(100)
        println("🐶")
    })
    if (count < 3) {
        count += 1
        println("🚀")
        savedContinuation?.resume(count) // recursive
    }
}


fun createCoroutine(block: suspend Unit.() -> Unit) {
    block.createCoroutine(Unit, completion = MyContinuation(MyEmptyCoroutineContext)).resume(Unit)
}














class MyContinuation<Unit>(override val context: CoroutineContext): Continuation<Unit> {
    override fun resumeWith(result: Result<Unit>) {}
}

object MyEmptyCoroutineContext : CoroutineContext {
    @Suppress("UNCHECKED_CAST")
    override fun <E : CoroutineContext.Element> get(key: CoroutineContext.Key<E>): E? =
        if (key === ContinuationInterceptor.Key) (MyInterceptor as E?) else null
    override fun <R> fold(initial: R, operation: (R, CoroutineContext.Element) -> R): R = initial
    override fun plus(context: CoroutineContext): CoroutineContext = context
    override fun minusKey(key: CoroutineContext.Key<*>): CoroutineContext = this
    override fun hashCode(): Int = 0
    override fun toString(): String = "EmptyCoroutineContext"
}

object MyInterceptor : ContinuationInterceptor, AbstractCoroutineContextElement(Key) {
    object Key : CoroutineContext.Key<MyInterceptor>
    override fun <T> interceptContinuation(continuation: Continuation<T>) = continuation
}