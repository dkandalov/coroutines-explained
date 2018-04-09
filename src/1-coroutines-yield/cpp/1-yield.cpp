
			#include <iostream>
			#include <experimental/coroutine>
			#include "generator.hpp"

			generator<int> factorial() {
				auto n = 0;
				auto result = 1;
				while (true) {
					co_yield result;
					n++;
					result = result * n;
				}
			}

			int main() {
				auto f = factorial();
				auto it = f.begin();
				std::cout << *(++it) << "\n";
				std::cout << *(++it) << "\n";
				std::cout << *(++it) << "\n";
				std::cout << *(++it) << "\n";
				std::cout << *(++it) << "\n";
				f.end();
			}
