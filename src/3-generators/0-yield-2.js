



		function* createGenerator() {
			console.log(yield "🐶");
			console.log(yield "🐱");
		}

		const c = createGenerator();
		console.log(c.next("A"));
		console.log(c.next("B"));
		console.log(c.next("C"));

