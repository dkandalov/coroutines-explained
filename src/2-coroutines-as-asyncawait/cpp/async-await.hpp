#ifndef _CORO_ASYNCAWAIT_H
#define _CORO_ASYNCAWAIT_H

#include <experimental/coroutine>


template<typename R, typename... Args>
struct std::experimental::coroutine_traits<std::future<R>, Args...> {
    struct promise_type {
        std::promise <R> p;
        auto get_return_object() { return p.get_future(); }
        bool await_ready() { return false; }
        std::experimental::suspend_never initial_suspend() { return {}; }
        std::experimental::suspend_never final_suspend() { return {}; }
        void set_exception(std::exception_ptr e) { p.set_exception(std::move(e)); }
        template<typename U> void return_value(U &&u) { p.set_value(std::forward<U>(u)); }
        void unhandled_exception() { std::terminate(); }
    };
};

template <typename R>
auto operator co_await(std::future<R> &&f) {
    struct Awaiter {
        std::future<R> &&input;
        std::future<R> output;
        bool await_ready() { return false; }
        auto await_resume() { return output.get(); }
        void await_suspend(std::experimental::coroutine_handle<> coro) {
//            input.then([this, coro](auto result_future) {
//                this->output = std::move(result_future);
//                coro.resume();
//            });
            this->output = std::move(input);
            coro.resume();
        }
    };
    return Awaiter{static_cast<std::future<R>&&>(f)};
}

#endif // _CORO_ASYNCAWAIT_H
