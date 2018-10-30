



				c = coroutine.create(function(n)
					print("c received: " .. n)
					n = coroutine.yield(2)
					print("c received: " .. n)
					n = coroutine.yield(4)
					print("c received: " .. n)
				end)

				_, m = coroutine.resume(c, 1)
				print("main received: " .. m)
				_, m = coroutine.resume(c, 3)
				print("main received: " .. m)
				_, m = coroutine.resume(c, 5)


