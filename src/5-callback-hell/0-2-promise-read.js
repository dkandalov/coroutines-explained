


				function readPromiseFrom(asyncDataSource) {
					return new Promise(resolve => {
						asyncDataSource.asyncRead(it => resolve(it));
					});
				}

				let dataSource = createAsyncDataSource([1, 2, 3]);

				readPromiseFrom(dataSource)
					.then(it => {
						console.log(it);
						return readPromiseFrom(dataSource);
					})
					.then(it => {
						console.log(it);
						return readPromiseFrom(dataSource);
					})
					.then(it => {
						console.log(it);
					});
				console.log("done");










				function createAsyncDataSource(data) {
					return {
						asyncRead: function(callback) {
							setTimeout(() => callback(data.shift()));
						}
					}
				}
