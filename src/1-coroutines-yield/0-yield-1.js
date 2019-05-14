



				function* createGenerator() {
					console.log(yield "🐶");
					console.log(yield "🐷");
					console.log(yield "🙈");
				}

				const c = createGenerator();
				console.log(c.next("A is lost").value);
				console.log(c.next("B").value);

