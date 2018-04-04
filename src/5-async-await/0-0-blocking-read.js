

      function createDataSource(data) {
        return {
          blockingRead: function() {
            return data.shift();
          }
        }
      }

      let dataSource = createDataSource([1, 2, 3]);
      console.log(dataSource.blockingRead());
      console.log(dataSource.blockingRead());
      console.log(dataSource.blockingRead());
