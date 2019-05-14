


				function* f(it) {
					console.log(yield it);
				}
				function* createGenerator() {
					[1, 2, 3].forEach(it => f(it));
				}

				const c = createGenerator();
				console.log(c.next("A is lost").value);
				console.log(c.next("B").value);
				console.log(c.next("C").value);
				console.log(c.next("D").value);

