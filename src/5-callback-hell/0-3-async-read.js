


				function readPromiseFrom(asyncDataSource) {
					return new Promise(resolve => {
						asyncDataSource.asyncRead(it => resolve(it));
					});
				}

				let dataSource = createAsyncDataSource([1, 2, 3]);

				async function coroutine() {
					console.log(await readPromiseFrom(dataSource));
					console.log(await readPromiseFrom(dataSource));
					console.log(await readPromiseFrom(dataSource));
				}

				coroutine();
				console.log("done");
















				function createAsyncDataSource(data) {
					return {
						asyncRead: function(callback) {
							setTimeout(() => callback(data.shift()), 10);
						}
					}
				}
