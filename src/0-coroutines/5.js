


      function* foo() {
        console.log(2);
        yield;
        console.log(4);
        yield;
      }

      const c = foo();
      console.log(1);
      c.next();
      console.log(3);
      c.next();
      console.log(5);
      c.next();

