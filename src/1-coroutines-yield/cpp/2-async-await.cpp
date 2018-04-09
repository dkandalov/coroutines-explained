#include <iostream>
#include <future>
#include <experimental/coroutine>

#include "async-await.hpp"


std::future<int> asyncAdd(int a, int b) {
    auto fut = std::async([=]() {
        int c = a + b;
        return c;
    });
    return fut;
}

std::future<int> asyncFibonacci(int n) {
    if (n <= 2) co_return 1;
    int a = 1;
    int b = 1;
    for (int i = 0; i < n - 2; ++i) {
        int c = co_await asyncAdd(a, b);
        a = b;
        b = c;
    }
    co_return b;
}

int main() {
    auto fibFuture = asyncFibonacci(7);
    std::cout << fibFuture.get() << "\n";
}