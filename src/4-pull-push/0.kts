@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

import _0.CoDataSource.Companion.runInAsyncContext
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.function.Supplier
import kotlin.coroutines.experimental.Continuation
import kotlin.coroutines.experimental.CoroutineContext
import kotlin.coroutines.experimental.EmptyCoroutineContext
import kotlin.coroutines.experimental.createCoroutine
import kotlin.coroutines.experimental.intrinsics.COROUTINE_SUSPENDED
import kotlin.coroutines.experimental.intrinsics.suspendCoroutineOrReturn

interface DataSource {
    fun blockingRead(): Int
    fun asyncRead(listener: (Int) -> Unit)
}

fun sequentialBlockingRead() {
    val dataSource = DataSourceList(listOf(1, 2, 3))
    println(dataSource.blockingRead())
    println(dataSource.blockingRead())
    println(dataSource.blockingRead())
}

fun sequentialAsyncRead() {
    val dataSource = DataSourceList(listOf(1, 2, 3))
    dataSource.asyncRead {
        println(it)
        dataSource.asyncRead {
            println(it)
            dataSource.asyncRead {
                println(it)
                dataSource.asyncRead {
                    println("will never be called")
                }
            }
        }
    }
}

fun sequentialReadWithCoroutines() {
    val dataSource = DataSourceList(listOf(1, 2, 3))
    runInAsyncContext(dataSource) {
        println(read())
        println(read())
        println(read())
        // read().printed() // if uncommented "end" will never be printed
    }
    dataSource.waitToBeEmpty()
}

sequentialReadWithCoroutines()


class DataSourceList(data: List<Int>): DataSource {
    private val executor = Executors.newCachedThreadPool { runnable -> Thread(runnable).apply {
        name = "IO"
        isDaemon = true
    } }
    private val data = ArrayList(data)

    override fun blockingRead(): Int {
        return synchronized(data) {
            data.removeAt(0)
        }
    }

    override fun asyncRead(listener: (Int) -> Unit) {
        executor.submit {
            val value = synchronized(data) {
                if (data.isNotEmpty()) blockingRead() else null
            }
            if (value != null) listener(value)
        }
    }

    fun waitToBeEmpty() {
        while (synchronized(data) { data.isNotEmpty() }) {
            Thread.sleep(20)
        }
    }
}


class CoDataSource(private val dataSource: DataSource) {
    suspend fun read(): Int {
        return suspendCoroutineOrReturn { continuation: Continuation<Int> ->
            dataSource.asyncRead { continuation.resume(it) }
            COROUTINE_SUSPENDED
        }
    }

    suspend fun parallelRead(count: Int): List<Int> {
        return suspendCoroutineOrReturn { continuation: Continuation<List<Int>> ->
            val values = CopyOnWriteArrayList<Int>()
            0.until(count).forEach {
                dataSource.asyncRead {
                    values.add(it)
                    if (values.size == count) {
                        continuation.resume(values)
                    }
                }
            }
            COROUTINE_SUSPENDED
        }
    }

    companion object {
        fun runInAsyncContext(dataSource: DataSource, callback: suspend CoDataSource.() -> Unit) {
            val result = CoDataSource(dataSource)
            callback.createCoroutine(result, completion = EmptyContinuation).resume(Unit)
        }
    }
}


object EmptyContinuation: Continuation<Unit> {
    override val context: CoroutineContext = EmptyCoroutineContext
    override fun resume(value: Unit) {}
    override fun resumeWithException(exception: Throwable) = throw exception
}
