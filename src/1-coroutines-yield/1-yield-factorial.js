



				function* factorial() {
					let n = 0;
					let result = 1;
					while (true) {
						yield result;
						n++;
						result = result * n;
					}
				}

				const f = factorial();
				console.log(f.next());
				console.log(f.next());
				console.log(f.next());
				console.log(f.next());
				console.log(f.next());
