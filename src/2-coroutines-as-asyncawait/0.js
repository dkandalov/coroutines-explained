

		let promise1 = new Promise(resolve => {
			setTimeout(() => resolve(), 100);
		});
		let promise2 = new Promise(resolve => {
			setTimeout(() => resolve(), 200);
		});

		async function c() {
			console.log(2);
			await promise1;
			console.log(4);
			await promise2;
			console.log(5);
		}

		console.log(1);
		c();
		console.log(3);

