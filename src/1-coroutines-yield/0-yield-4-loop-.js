


				function* f(it) {
					console.log(yield it);
				}
				function* createGenerator() {
					console.log(yield "🐶");
					[1, 2, 3].forEach(it => f(it));
				}

				const c = createGenerator();
				console.log(c.next("A is lost"));
				console.log(c.next("B"));
				console.log(c.next("C"));
				console.log(c.next("D"));

