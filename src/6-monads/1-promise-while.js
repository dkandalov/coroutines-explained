








		function sequentialRead() {
			let dataSource = createAsyncDataSource([1, 2, 3]);
			let i = 0;
			let promise = Promise.resolve();
			while (i++ < 3) {
				promise = promise
					.then(() => readPromiseFrom(dataSource))
					.then((it) => console.log(it));
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
