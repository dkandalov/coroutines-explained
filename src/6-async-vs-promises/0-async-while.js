








			async function sequentialRead() {
				let dataSource = createAsyncDataSource([1, 2, 3, 4, 5]);
				let i = 0;
				while (i++ < 3) {
					console.log(await readPromiseFrom(dataSource));
				}
			}

			sequentialRead();

















			function createAsyncDataSource(data) {
				return {
					asyncRead: function(callback) {
						setTimeout(() => callback(data.shift()));
					}
				}
			}

			function readPromiseFrom(dataSource) {
				return new Promise((resolve) => {
					dataSource.asyncRead(it => resolve(it));
				});
			}
