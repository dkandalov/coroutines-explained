
fun factorial(n: Int): Int {
    if (n == 0) return 1
    else return n * factorial(n - 1)
}

fun factorialCPS(n: Int, continuation: (Int) -> Unit) {
    if (n == 0) continuation(1)
    else factorialCPS(n - 1, { continuation(n * it) })
}

println(factorial(5))

factorialCPS(5) { println(it) }

