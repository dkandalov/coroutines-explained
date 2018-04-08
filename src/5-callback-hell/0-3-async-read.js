
		function createAsyncDataSource(data) {
			return {
				asyncRead: function(callback) {
					setTimeout(() => callback(data.shift()), 10);
				}
			}
		}

		function readPromiseFrom(asyncDataSource) {
			return new Promise((resolve) => {
				asyncDataSource.asyncRead(it => resolve(it));
			});
		}

		async function coroutine() {
			let dataSource = createAsyncDataSource([1, 2, 3]);
			console.log(await readPromiseFrom(dataSource));
			console.log(await readPromiseFrom(dataSource));
			console.log(await readPromiseFrom(dataSource));
		}

		coroutine();
		console.log("done");














