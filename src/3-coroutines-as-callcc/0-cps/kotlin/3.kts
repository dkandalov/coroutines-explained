
        import java.io.File
        import java.net.URL
        import java.util.concurrent.TimeUnit.SECONDS


        fun readBytes(url: URL, continuation: (ByteArray) -> Unit) {
            val bytes = url.openStream().readBytes()
            continuation(bytes)
        }

        fun saveToFile(bytes: ByteArray, continuation: (File) -> Unit) {
            val file = File("no-cat.jpg")
            file.outputStream().write(bytes)
            continuation(file)
        }

        fun openFile(filePath: String, continuation: (Boolean) -> Unit) {
            val process = Runtime.getRuntime().exec("idea $filePath")
            process.waitFor(2, SECONDS)
            continuation(process.exitValue() == 0)
        }


        readBytes(URL("https://i.pinimg.com/736x/35/f7/83/35f783f18d40b7d41fae5c51a25709d1.jpg")) { bytes ->
            saveToFile(bytes) { file ->
                openFile(file.absolutePath) {
                    println("Opened: $it")
                }
            }
        }
