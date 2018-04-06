


		async function c() {
			await promise1;
			await promise2;
		}

		let resolvePromise1;
		let resolvePromise2;
		let promise1 = new Promise(it => resolvePromise1 = it);
		let promise2 = new Promise(it => resolvePromise2 = it);

		c();
		resolvePromise1();
		resolvePromise2();

