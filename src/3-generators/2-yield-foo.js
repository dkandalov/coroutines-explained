
function* foo() {
  yield 1;
  yield 2;
  // ["a", "b", "c"].forEach(function(it){ yield it; })
}

const generator = foo();
console.log(generator.next());
console.log(generator.next());
console.log(generator.next());

