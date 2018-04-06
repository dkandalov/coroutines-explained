

		function readPromiseFrom(dataSource) {
			return new Promise((resolve) => {
				dataSource.asyncRead(it => resolve(it));
			});
		}

		async function main() {
			let dataSource = createAsyncDataSource([1, 2, 3]);
			console.log(await readPromiseFrom(dataSource));
			console.log(await readPromiseFrom(dataSource));
			console.log(await readPromiseFrom(dataSource));
		}

		main();
		console.log("done");














		function createAsyncDataSource(data) {
			return {
				asyncRead: function(callback) {
					setTimeout(() => callback(data.shift()));
				}
			}
		}
