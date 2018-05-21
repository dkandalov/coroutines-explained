



				function factorial() {
					let n = 0;
					let result = 1;
					return {
						next: function() {
							let value = result;
							n++;
							result = result * n;
							return {
								value: value,
								done: false
							}
						}
					};
				}

				const f = factorial();
				console.log(f.next());
				console.log(f.next());
				console.log(f.next());
				console.log(f.next());
				console.log(f.next());
