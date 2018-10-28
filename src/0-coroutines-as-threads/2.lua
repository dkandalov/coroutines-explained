



				c = coroutine.create(function()
					print("c is: " .. coroutine.status(c))
					coroutine.yield()
					print("c is: " .. coroutine.status(c))
				end)

				print("c is: " .. coroutine.status(c))
				coroutine.resume(c)
				print("c is: " .. coroutine.status(c))
				coroutine.resume(c)
				print("c is: " .. coroutine.status(c))


