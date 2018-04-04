



		function* factorial() {
			let n = 0;
			let result = 1;
			let skip = 0;
			while (true) {
				if (!skip) {
					skip = yield result;
				} else {
					skip--;
				}
				n++;
				result = result * n;
			}
		}

		const f = factorial();
		console.log(f.next());
		console.log(f.next());
		console.log(f.next());
		console.log(f.next());
		console.log(f.next(10));
