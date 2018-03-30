
def factorial(n) {
	if (n == 0) 1
	else n * factorial(n - 1)
}

def factorialCPS(n, continuation) {
	if (n == 0) continuation(1)
	else factorialCPS(n - 1, { continuation(n * it) })
}

println(factorial(5))

factorialCPS(5) { println(it) }

