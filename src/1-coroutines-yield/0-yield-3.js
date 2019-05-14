



				function* createGenerator() {
					yield;
					yield;
				}

				const c = createGenerator();
				console.log("done: " + c.next().done);
				console.log("done: " + c.next().done);
				console.log("done: " + c.next().done);

