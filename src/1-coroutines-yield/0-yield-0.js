



				function* createGenerator() {
					yield "🐶";
					yield "🐷";
					yield "🙈";
				}

				const c = createGenerator();
				console.log(c.next().value);
				console.log(c.next().value);


