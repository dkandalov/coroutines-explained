


          let resolve1;
          let resolve2;
          let p1 = new Promise(it => resolve1 = it);
          let p2 = new Promise(it => resolve2 = it);

          async function c() {
            console.log(2);
            await p1;
            console.log(4);
            await p2;
            console.log(6);
          }

          console.log(1);
          c();
          console.log(3);
          resolve1();
          console.log(5);
          resolve2();
          console.log(7);

