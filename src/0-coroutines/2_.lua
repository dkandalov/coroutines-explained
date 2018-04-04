



		c = coroutine.create(function(n)
			print("c: " .. coroutine.status(c))
			n = coroutine.yield(2)
			print("c: " .. coroutine.status(c))
			n = coroutine.yield(4)
			print("c: " .. coroutine.status(c))
		end)

		print("main: " .. coroutine.status(c))

		_, n = coroutine.resume(c, 1)
		print("main: " .. coroutine.status(c))
		_, n = coroutine.resume(c, 3)
		print("main: " .. coroutine.status(c))
		_, n = coroutine.resume(c, 5)
		print("main: " .. coroutine.status(c))


