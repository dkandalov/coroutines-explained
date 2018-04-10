


			let promise1 = new Promise(resolve => {
				setTimeout(() => resolve("🐶"), 2000);
			});
			let promise2 = new Promise(resolve => {
				setTimeout(() => resolve("🐷"), 4000);
			});

			async function c() {
				let dog = await promise1;
				let pig = await promise2;
				return dog + pig;
			}

			let overallPromise = c();
			overallPromise.then(it => console.log(it + "!"));

