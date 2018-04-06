

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

		async function main() {
			let dataSource = createDataSource([1, 2, 3]);
			console.log(await readPromiseFrom(dataSource));
			console.log(await readPromiseFrom(dataSource));
			console.log(await readPromiseFrom(dataSource));
		}

		main();
		console.log("done");

