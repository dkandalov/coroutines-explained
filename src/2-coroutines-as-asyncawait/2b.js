"use strict";

var c = function () {
	var _ref = _asyncToGenerator( /*#__PURE__*/regeneratorRuntime.mark(function _callee() {
		var value1, value2;
		return regeneratorRuntime.wrap(function _callee$(_context) {
			while (1) {
				switch (_context.prev = _context.next) {
					case 0:
						_context.prev = 0;
						_context.next = 3;
						return promise1;

					case 3:
						value1 = _context.sent;
						_context.next = 6;
						return promise2;

					case 6:
						value2 = _context.sent;

						console.log("🙈");
						return _context.abrupt("return", value1 + value2);

					case 11:
						_context.prev = 11;
						_context.t0 = _context["catch"](0);

						console.log("catch " + _context.t0);

					case 14:
						_context.prev = 14;

						console.log("finally");
						return _context.finish(14);

					case 17:
					case "end":
						return _context.stop();
				}
			}
		}, _callee, this, [[0, 11, 14, 17]]);
	}));

	return function c() {
		return _ref.apply(this, arguments);
	};
}();

function _asyncToGenerator(fn) { return function () { var gen = fn.apply(this, arguments); return new Promise(function (resolve, reject) { function step(key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { return Promise.resolve(value).then(function (value) { step("next", value); }, function (err) { step("throw", err); }); } } return step("next"); }); }; }

var promise1 = new Promise(function (resolve) {
	setTimeout(function () {
		return resolve("🐶");
	}, 100);
});
var promise2 = new Promise(function (resolve, reject) {
	setTimeout(function () {
		return reject("💥");
	}, 200);
});

var overallPromise = c();
overallPromise.then(function (it) {
	return console.log(it);
});