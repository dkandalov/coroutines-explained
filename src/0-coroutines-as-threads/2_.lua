



				c = coroutine.create(function(n)
					print("c <- " .. n)
					n = coroutine.yield(2)
					print("c <- " .. n)
					n = coroutine.yield(4)
					print("c <- " .. n)
				end)

				_, n = coroutine.resume(c, 1)
				print("main <- " .. tostring(n))
				_, n = coroutine.resume(c, 3)
				print("main <- " .. tostring(n))
				_, n = coroutine.resume(c, 5)


