



				function* createGenerator() {
					console.log(yield "🐶");
					for (let it of [1, 2, 3]) {
						console.log(yield it);
					}
				}

				const c = createGenerator();
				console.log(c.next("A"));
				console.log(c.next("B"));
				console.log(c.next("C"));

