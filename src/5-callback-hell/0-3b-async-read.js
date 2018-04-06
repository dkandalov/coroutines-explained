"use strict";

var main = function () {
  var _ref = _asyncToGenerator( /*#__PURE__*/regeneratorRuntime.mark(function _callee() {
    var dataSource, it;
    return regeneratorRuntime.wrap(function _callee$(_context) {
      while (1) {
        switch (_context.prev = _context.next) {
          case 0:
            dataSource = createDataSource([1, 2, 3]);
            _context.next = 3;
            return readPromiseFrom(dataSource);

          case 3:
            it = _context.sent;

            console.log(it);
            _context.next = 7;
            return readPromiseFrom(dataSource);

          case 7:
            it = _context.sent;

            console.log(it);
            _context.next = 11;
            return readPromiseFrom(dataSource);

          case 11:
            it = _context.sent;

            console.log(it);

          case 13:
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

function createDataSource(data) {
  return {
    asyncRead: function asyncRead(callback) {
      setTimeout(function () {
        return callback(data.shift());
      });
    }
  };
}

function readPromiseFrom(dataSource) {
  return new Promise(function (resolve, reject) {
    dataSource.asyncRead(function (it) {
      return resolve(it);
    });
  });
}

main();
console.log("done");