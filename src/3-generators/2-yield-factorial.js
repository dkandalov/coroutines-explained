function* foo() {
  yield 1;
  yield 2;
  // ["a", "b", "c"].forEach(function(it){ yield it; })
}

function* factorial() {
  let n = 0;
  let result = 1;
  let skip = 0;
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

const g = foo();
console.log(g.next());
console.log(g.next());
console.log(g.next());

const f = factorial();
console.log(f.next());
console.log(f.next());
console.log(f.next());
console.log(f.next());
console.log(f.next(10));
