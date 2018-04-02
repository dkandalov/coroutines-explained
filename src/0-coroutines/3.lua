

	local c1 = coroutine.create(function()
		print(2)
		coroutine.yield()
		print(4)
	end)
	local c2 = coroutine.create(function()
		print(1)
		coroutine.resume(c1)
		print(3)
	end)

	coroutine.resume(c2)
	coroutine.resume(c1)
