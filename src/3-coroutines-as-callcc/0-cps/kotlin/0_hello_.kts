


        fun printCPS(message: String, continuation: () -> Unit) {
            print(message)
            continuation()
        }

        printCPS("hello ") {
            printCPS("world", {})
        }

