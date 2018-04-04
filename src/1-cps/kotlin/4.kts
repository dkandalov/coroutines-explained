

        typealias Fun = (Any) -> Unit

        fun callcc(f: (Fun) -> Unit, continuation: Fun) {
            f(fun(it: Any) { continuation(it) })
        }

        var count = 0
        var savedCC: Fun? = null

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
                savedCC?.invoke(count)
            }
            System.exit(0)
        }