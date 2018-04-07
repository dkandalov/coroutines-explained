

		let resolvePromise1;
		let resolvePromise2;
		let promise1 = new Promise(resolve => resolvePromise1 = resolve);
		let promise2 = new Promise(resolve => resolvePromise2 = resolve);

		async function c() {
			console.log(2);
			await promise1;
			console.log(6);
			await promise2;
			console.log(7);
		}

		console.log(1);
		c();
		console.log(3);
		resolvePromise1();
		console.log(4);
		resolvePromise2();
		console.log(5);

