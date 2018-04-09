



			#include <iostream>
			#include <experimental/coroutine>
			#include "generator.hpp"

			generator<int> createGenerator() {
			    co_yield 1;
			    co_yield 2;
			    co_yield 3;
			}

			int main() {
			    for (auto it: createGenerator() ) {
			        std::cout << it << "\n";
			    }
			}

