


		async function c() {
			let value1 = await promise1;
			let value2 = await promise2;
			return value1 + value2;
		}

		let resolvePromise1;
		let resolvePromise2;
		let promise1 = new Promise(it => resolvePromise1 = it);
		let promise2 = new Promise(it => resolvePromise2 = it);

		let overallPromise = c();
		overallPromise.then((it) => console.log(it));
		resolvePromise1("🐶");
		resolvePromise2("🐱");

