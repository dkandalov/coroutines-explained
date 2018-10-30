



				c = coroutine.create(function()
					print("2: c is " .. coroutine.status(c))
					coroutine.yield()
					print("4: c is " .. coroutine.status(c))
				end)

				print("1: c is " .. coroutine.status(c))
				coroutine.resume(c)
				print("3: c is " .. coroutine.status(c))
				coroutine.resume(c)
				print("5: c is " .. coroutine.status(c))


