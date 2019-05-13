


			let promise1 = new Promise(resolve => {
				setTimeout(() => resolve("🐶"), 2000);
			});
			let promise2 = new Promise(resolve => {
				setTimeout(() => resolve("🐷"), 5000);
			});

			async function dog() {
				return await promise1 + "_";
			}
			async function pig() {
				return await promise2 + "_";
			}
			async function c() {
				return await dog() + await pig();
			}

			let overallPromise = c();
			overallPromise.then(result => console.log(result + "!"));
			console.log("end of main");
