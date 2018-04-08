



		c = coroutine.create(function()
			print("c: " .. coroutine.status(c))
			coroutine.yield()
			print("c: " .. coroutine.status(c))
			coroutine.yield()
			print("c: " .. coroutine.status(c))
		end)

		print("main: " .. coroutine.status(c))

		coroutine.resume(c)
		print("main: " .. coroutine.status(c))
		coroutine.resume(c)
		print("main: " .. coroutine.status(c))
		coroutine.resume(c)
		print("main: " .. coroutine.status(c))


