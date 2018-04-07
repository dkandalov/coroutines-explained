



		function* createGenerator() {
			try {
				let value = yield;
				if (value === "🚀") throw value;
				yield "🙈";
			} catch (e) {
				console.log("catch");
				yield "💥";
			} finally {
				console.log("finally");
			}
		}

		const c = createGenerator();
		console.log(c.next());
		console.log(c.next("🚀"));
		console.log(c.next());

