



				function* createGenerator() {
					try {
						throw "🚀";
						yield "🙈";
					} catch (e) {
						console.log("catch");
						yield e + "💥";
					} finally {
						console.log("finally");
					}
				}

				const c = createGenerator();
				console.log(c.next());
				console.log(c.next());

