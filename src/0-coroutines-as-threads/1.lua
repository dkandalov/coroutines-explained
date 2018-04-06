



		c = coroutine.create(function()
			print(2)
			coroutine.yield()
			print(4)
			coroutine.yield()
			print("🙈")
		end)

		-- error: attempt to yield from outside a coroutine
		--coroutine.yield()

		print(1)
		coroutine.resume(c)
		print(3)
		coroutine.resume(c)
		print(5)

