

	def printCPS(message, continuation) {
		print(message)
		continuation()
	}

	printCPS("hello ", {
		printCPS("world", {})
	})
	
