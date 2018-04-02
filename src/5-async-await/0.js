

function createDataSource(data) {
  return {
    blockingRead: function() {
      return data.shift();
    },
    asyncRead: function(callback) {
      setTimeout(() => callback(data.shift()));
    }
  }
}

function sequentialBlockingRead() {
  let dataSource = createDataSource([1, 2, 3]);
  console.log(dataSource.blockingRead());
  console.log(dataSource.blockingRead());
  console.log(dataSource.blockingRead());
}

function sequentialAsyncRead() {
  let dataSource = createDataSource([1, 2, 3]);
  dataSource.asyncRead(it => {
    console.log(it);
    dataSource.asyncRead(it => {
      console.log(it);
      dataSource.asyncRead(it => {
        console.log(it);
      });
    });
  });
  console.log("finished");
}

function readPromiseFrom(dataSource) {
  return new Promise((resolve, reject) => {
    dataSource.asyncRead(it => resolve(it));
  });
}

function sequentialAsyncPromiseRead() {
  let dataSource = createDataSource([1, 2, 3]);

  readPromiseFrom(dataSource)
    .then(it => {
      console.log(it);
      return readPromiseFrom(dataSource);
    })
    .then(it => {
      console.log(it);
      return readPromiseFrom(dataSource);
    })
    .then(it => {
      console.log(it);
    })
}

async function sequentialAsyncAwaitRead() {
  let dataSource = createDataSource([1, 2, 3]);

  let promise = readPromiseFrom(dataSource);
  let it = await promise;
  console.log(it);
  it = await readPromiseFrom(dataSource);
  console.log(it);
  it = await readPromiseFrom(dataSource);
  console.log(it);
}

console.log(sequentialAsyncAwaitRead());
console.log("done");

