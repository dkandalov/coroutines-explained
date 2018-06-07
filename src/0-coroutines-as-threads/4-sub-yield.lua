



				function sub_function()
					print(3)
					coroutine.yield()
					--print(debug.traceback())
					print(5)
				end

				c = coroutine.create(function()
					print(2)
					sub_function()
					print(6)
				end)

				print(1)
				coroutine.resume(c)
				print(4)
				coroutine.resume(c)
				print(7)

