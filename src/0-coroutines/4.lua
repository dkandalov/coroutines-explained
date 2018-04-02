

	function sub_function()
		print(3)
		print(debug.traceback())
		coroutine.yield()
		print(5)
	end

	local c = coroutine.create(function(n)
		print(2)
		sub_function()
		print(6)
	end)

	print(1)
	coroutine.resume(c)
	print(4)
	coroutine.resume(c)
	print(7)

