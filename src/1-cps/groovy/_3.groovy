	import static java.util.concurrent.TimeUnit.SECONDS


	def readBytes(URL url, continuation) {
		def bytes = url.openStream().getBytes()
		continuation(bytes)
	}

	def saveToFile(bytes, continuation) {
		def file = new File("no-cat.jpg")
		file.newDataOutputStream().write(bytes)
		continuation(file)
	}

	def openFile(filePath, continuation) {
		def process = "idea $filePath".execute()
		process.waitFor(2, SECONDS)
		continuation(process.exitValue() == 0)
	}

	readBytes("https://i.pinimg.com/736x/35/f7/83/35f783f18d40b7d41fae5c51a25709d1.jpg".toURL()) { bytes ->
		saveToFile(bytes) { file ->
			openFile(file.absolutePath) {
				println("Opened: $it")
			}
		}
	}
