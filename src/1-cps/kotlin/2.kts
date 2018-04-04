
fun factorial(n: Int): Int {
    return if (n == 0) 1
    else n * factorial(n - 1)
}

fun factorialCPS(n: Int, continuation: (Int) -> Unit) {
    if (n == 0) continuation(1)
    else factorialCPS(n - 1, { continuation(n * it) })
}

println(factorial(5))

factorialCPS(5) { println(it) }

