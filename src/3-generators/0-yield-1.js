



		function* createGenerator() {
			console.log(yield);
			console.log(yield);
		}

		const c = createGenerator();
		console.log(c.next());
		console.log(c.next());
		console.log(c.next());

