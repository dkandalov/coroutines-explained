function* foo() {
  yield 1;
  yield 2;
}

function* fibonacci() {
  var n1 = 0;
  var n2 = 1;
  while (true) {
    var result = n1 + n2;
    yield result;
    n1 = n2;
    n2 = result;
  }
}

var g = foo();
console.log(g.next());
console.log(g.next());
console.log(g.next());

var f = fibonacci();
console.log(f.next());
console.log(f.next());
console.log(f.next());
console.log(f.next());
console.log(f.next());
