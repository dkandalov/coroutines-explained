



		c2 = coroutine.create(function()
			print(2)
			coroutine.yield()
			print(4)
			coroutine.yield()
		end)

		c1 = coroutine.create(function()
			print(1)
			coroutine.resume(c2)
			print(3)
			coroutine.resume(c2)
			print(5)
		end)

		coroutine.resume(c1)


