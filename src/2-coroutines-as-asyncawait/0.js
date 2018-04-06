


		async function c() {
			console.log(2);
			await promise1;
			console.log(6);
			await promise2;
			console.log(7);
		}

		let resolvePromise1;
		let resolvePromise2;
		let promise1 = new Promise(it => resolvePromise1 = it);
		let promise2 = new Promise(it => resolvePromise2 = it);

		console.log(1);
		c();
		console.log(3);
		resolvePromise1();
		console.log(4);
		resolvePromise2();
		console.log(5);

