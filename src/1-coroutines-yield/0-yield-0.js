



				function* createGenerator() {
					yield 1;
					yield 2;
					yield 3;
					yield "🙈";
				}

				const c = createGenerator();
				console.log(c.next().value);
				console.log(c.next().value);
				console.log(c.next().value);


