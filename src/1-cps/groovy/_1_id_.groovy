

	def identityCPS(value, continuation) {
		continuation(value)
	}

	identityCPS(234) { result ->
		println(result)
	}
	