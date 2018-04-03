
function* foo() {
  yield 1;
  yield 2;
  // ["a", "b", "c"].forEach(function(it){ yield it; })
}

const g = foo();
console.log(g.next());
console.log(g.next());
console.log(g.next());

