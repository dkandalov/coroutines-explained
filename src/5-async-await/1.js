
function sequentialPromiseWhileRead() {
  let dataSource = createDataSource([1, 2, 3]);
  let i = 0;
  let promise = Promise.resolve();
  while (i++ < 3) {
    promise = promise
      .then(() => readPromiseFrom(dataSource))
      .then((it) => console.log(it));
  }
}

async function sequentialAsyncAwaitWhileRead() {
  let dataSource = createDataSource([1, 2, 3]);
  let i = 0;
  while (i++ < 3) {
    let it = await readPromiseFrom(dataSource);
    console.log(it);
  }
}

sequentialPromiseWhileRead();
console.log("done");



function createDataSource(data) {
  return {
    asyncRead: function(callback) {
      setTimeout(() => callback(data.shift()));
    }
  }
}

function readPromiseFrom(dataSource) {
  return new Promise((resolve, reject) => {
    dataSource.asyncRead(it => resolve(it));
  });
}
