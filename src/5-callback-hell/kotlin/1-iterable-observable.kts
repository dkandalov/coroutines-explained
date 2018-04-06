


            interface Iterator<T> {
                fun moveNext(): Boolean // @Throws(Exception::class)
                fun current(): T
            }

            interface Iterable<T> {
                fun iterator(): Iterator<T>
            }

            // -----------------------------------

            interface Observer<T> {
                fun onNext(value: T)
                fun onError(e: Exception)
                fun onCompleted()
            }

            interface Observable<T> {
                fun subscribe(observer: Observer<T>)
            }


