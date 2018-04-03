


            interface Iterator<T> {
                @Throws(Exception::class)
                fun moveNext(): Boolean
                fun current(): T
            }

            interface Iterable<T> {
                fun iterator(): Iterator<T>
            }


            interface Observer<T> {
                fun onNext(value: T)
                fun onError(e: Exception)
                fun onCompleted()
            }

            interface Observable<T> {
                fun subscribe(observer: Observer<T>)
            }


