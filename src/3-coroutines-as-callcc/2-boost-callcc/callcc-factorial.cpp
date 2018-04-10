


			#include <iostream>
			#include <boost/context/all.hpp>

			using namespace boost::context;

			int main() {
			    int result;
			    continuation factorial = callcc([&result](continuation &&c) {
			        result = 1;
			        int n = 1;
			        while (true) {
			            c = c.resume();
			            result *= n++;
			        }
			        return std::move(c);
			    });
			    for (int i = 0; i < 10; i++) {
			        std::cout << result << std::endl;
			        factorial = factorial.resume();
			    }
			}
