


		def factorialCPS(n, continuation) {
			if (n == 0) continuation(1)
			else factorialCPS(n - 1, { continuation(n * it) })
		}

		factorialCPS(5, {
			println(it)
		})


