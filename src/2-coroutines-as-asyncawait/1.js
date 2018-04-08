

		let promise1 = new Promise(resolve => {
			setTimeout(() => resolve("🐶"), 100);
		});
		let promise2 = new Promise(resolve => {
			setTimeout(() => resolve("🐷"), 200);
		});

		async function c() {
			let value1 = await promise1;
			let value2 = await promise2;
			return value1 + value2;
		}

		let overallPromise = c();
		overallPromise.then((it) => console.log(it + "!"));

