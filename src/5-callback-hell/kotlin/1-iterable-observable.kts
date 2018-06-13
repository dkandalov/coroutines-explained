


                interface Iterator<T> {
                    fun current(): T // () -> T
                    fun moveNext(): Boolean // @Throws(Exception::class)
                }

                interface Iterable<T> {
                    fun iterator(): Iterator<T>
                }

                // -----------------------------------

                interface Observer<T> {
                    fun onNext(value: T) // T -> ()
                    fun onCompleted()
                    fun onError(e: Exception)
                }

                interface Observable<T> {
                    fun subscribe(observer: Observer<T>)
                }


