


				function createBlockingDataSource(data) {
					return {
						blockingRead: function() {
							return data.shift();
						}
					}
				}

				let dataSource = createBlockingDataSource([1, 2, 3]);

				console.log(dataSource.blockingRead());
				console.log(dataSource.blockingRead());
				console.log(dataSource.blockingRead());
				console.log("done");
