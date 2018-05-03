


			let promise1 = new Promise(resolve => {
				setTimeout(() => resolve("🐶"), 200);
			});
			let promise2 = new Promise((resolve, reject) => {
				setTimeout(() => reject("💥"), 500);
			});

			async function c() {
				try {
					let value1 = await promise1;
					let value2 = await promise2;
					console.log("🙈");
					return value1 + value2;
				} catch (e) {
					console.log("catch " + e);
				} finally {
					console.log("finally");
				}
			}

			let overallPromise = c();
			overallPromise.then(it => console.log(it));

