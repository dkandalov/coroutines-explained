

		function sequentialRead() {
			let dataSource = createAsyncDataSource([null, null, "🐶", "🙈"]);
			let value = null;
			let retryAttempt = 0;
			let promise = Promise.resolve();
			while (value === null && retryAttempt < 5) {
				promise = promise
					.then(() => {
						if (value === null) {
							value = readPromiseFrom(dataSource);
						}
						return value;
					})
					.then(value => {
						console.log("read: " + value);
						return value;
					});
				retryAttempt++;
			}
			return promise;
		}

		sequentialRead().then(it => console.log(it));












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
