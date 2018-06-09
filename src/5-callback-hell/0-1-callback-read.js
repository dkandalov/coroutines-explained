


				function createAsyncDataSource(data) {
					return {
						asyncRead: function(callback) {
							setTimeout(() => callback(data.shift()));
						}
					}
				}

				let dataSource = createAsyncDataSource([1, 2, 3]);

				dataSource.asyncRead(it => {
					console.log(it);
					dataSource.asyncRead(it => {
						console.log(it);
						dataSource.asyncRead(it => {
							console.log(it);
						});
					});
				});
				console.log("done");
