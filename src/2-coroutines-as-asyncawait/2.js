

		let resolvePromise1;
		let resolvePromise2;
		let promise1 = new Promise(resolve => resolvePromise1 = resolve);
		let promise2 = new Promise(resolve => resolvePromise2 = resolve);

		async function c() {
			let value1 = await promise1;
			let value2 = await promise2;
			return value1 + value2;
		}

		let overallPromise = c();
		overallPromise.then((it) => console.log(it));
		resolvePromise1("🐶");
		resolvePromise2("🐷");

