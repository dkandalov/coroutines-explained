


			let promise1 = new Promise(resolve => {
				setTimeout(() => resolve(), 3000);
			});
			let promise2 = new Promise(resolve => {
				setTimeout(() => resolve(), 6000);
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

