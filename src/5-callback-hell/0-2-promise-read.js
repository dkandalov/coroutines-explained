

		function createDataSource(data) {
			return {
				asyncRead: function(callback) {
					setTimeout(() => callback(data.shift()));
				}
			}
		}

		function readPromiseFrom(dataSource) {
			return new Promise((resolve, reject) => {
				dataSource.asyncRead(it => resolve(it));
			});
		}

		let dataSource = createDataSource([1, 2, 3]);

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
