"use strict";

var main = function () {
	var _ref = _asyncToGenerator( /*#__PURE__*/regeneratorRuntime.mark(function _callee() {
		var dataSource;
		return regeneratorRuntime.wrap(function _callee$(_context) {
			while (1) {
				switch (_context.prev = _context.next) {
					case 0:
						dataSource = createAsyncDataSource([1, 2, 3]);
						_context.t0 = console;
						_context.next = 4;
						return readPromiseFrom(dataSource);

					case 4:
						_context.t1 = _context.sent;

						_context.t0.log.call(_context.t0, _context.t1);

						_context.t2 = console;
						_context.next = 9;
						return readPromiseFrom(dataSource);

					case 9:
						_context.t3 = _context.sent;

						_context.t2.log.call(_context.t2, _context.t3);

						_context.t4 = console;
						_context.next = 14;
						return readPromiseFrom(dataSource);

					case 14:
						_context.t5 = _context.sent;

						_context.t4.log.call(_context.t4, _context.t5);

					case 16:
					case "end":
						return _context.stop();
				}
			}
		}, _callee, this);
	}));

	return function main() {
		return _ref.apply(this, arguments);
	};
}();

function _asyncToGenerator(fn) { return function () { var gen = fn.apply(this, arguments); return new Promise(function (resolve, reject) { function step(key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { return Promise.resolve(value).then(function (value) { step("next", value); }, function (err) { step("throw", err); }); } } return step("next"); }); }; }

function readPromiseFrom(dataSource) {
	return new Promise(function (resolve, reject) {
		dataSource.asyncRead(function (it) {
			return resolve(it);
		});
	});
}

main();
console.log("done");

function createAsyncDataSource(data) {
	return {
		asyncRead: function asyncRead(callback) {
			setTimeout(function () {
				return callback(data.shift());
			});
		}
	};
}