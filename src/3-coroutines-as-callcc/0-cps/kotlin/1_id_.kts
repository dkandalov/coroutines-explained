


        fun <T> identityCPS(value: T, continuation: (T) -> Unit) {
            continuation(value)
        }

        identityCPS(234) { result -> println(result) }


