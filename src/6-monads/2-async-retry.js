





	async function sequentialRead() {
		let dataSource = createAsyncDataSource([null, null, "🐶", "🙈"]);
		let value = null;
		let retryAttempt = 0;
		while (value === null && retryAttempt < 5) {
			value = await readPromiseFrom(dataSource);
			console.log("read: " + value);
			retryAttempt++;
		}
		return value;
	}

	sequentialRead().then(it => console.log(it + "!"));

















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
