



		async function sequentialRead() {
			let dataSource = createAsyncDataSource([1, 2, 3]);
			let i = 0;
			while (i++ < 3) {
				console.log(await readPromiseFrom(dataSource));
			}
		}

		sequentialRead().then((it) => console.log(it));

















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
