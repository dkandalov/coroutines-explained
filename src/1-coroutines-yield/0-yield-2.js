



				function* createGenerator() {
					yield;
					yield;
				}

				const c = createGenerator();
				console.log(c.next());
				console.log(c.next());
				console.log(c.next());

