
def identity(value) {
	value
}
def identityCPS(value, continuation) {
	continuation(value)
}

println(identity(123))

identityCPS(234) { result -> println(result) }
