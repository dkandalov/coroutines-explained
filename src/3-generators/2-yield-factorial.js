function* foo() {
  yield 1;
  yield 2;
  // ["a", "b", "c"].forEach(function(it){ yield it; })
}

function* factorial() {
  var n = 0;
  var result = 1;
  var skip = 0;
  while (true) {
    if (!skip) {
      skip = yield result;
    } else {
      skip--;
    }
    n++;
    result = result * n;
  }
}

var g = foo();
console.log(g.next());
console.log(g.next());
console.log(g.next());

var f = factorial();
console.log(f.next());
console.log(f.next());
console.log(f.next());
console.log(f.next());
console.log(f.next(10));
