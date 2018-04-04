

      let resolve1;
      let resolve2;
      let resolve3;
      let p1 = new Promise(it => resolve1 = it);
      let p2 = new Promise(it => resolve2 = it);
      let p3 = new Promise(it => resolve3 = it);

      async function c() {
        console.log(2);
        console.log(await p1);
        console.log(6);
        console.log(await p2);
        console.log(7);
        console.log(await p3);
      }

      console.log(1);
      c();
      console.log(3);
      resolve1("🐶");
      console.log(4);
      resolve2("🐸");
      console.log(5);
      resolve3("🙈");

