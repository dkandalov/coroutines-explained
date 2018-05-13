



				function* createGenerator() {
					console.log(yield "🐶");
					console.log(yield "🐷");
				}

				const c = createGenerator();
				console.log(c.next("will be lost"));
				console.log(c.next("B"));
				console.log(c.next("C"));

