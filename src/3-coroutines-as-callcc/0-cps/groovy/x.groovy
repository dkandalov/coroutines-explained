

		def callcc(f, continuation) {
			f({ it -> continuation(it) })
		}

		def count = 0
		def savedCC = null

		println(1)
		callcc({ cc ->
			println(2)
			savedCC = cc
			cc(3)
			println("💥")
		}) {
			println(it)
			println(4)
			if (count++ < 3) {
				println("🚀")
				savedCC(count)
			}
			System.exit(0)
		}