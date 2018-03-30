
fun <T> identity(value: T): T {
    return value
}

fun <T> identityCPS(value: T, continuation: (T) -> Unit) {
    continuation(value)
}

println(identity(123))

identityCPS(234) { result -> println(result) }
