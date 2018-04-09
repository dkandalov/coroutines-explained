cmake_minimum_required(VERSION 3.9)

project(CXX)
set(CMAKE_CXX_STANDARD 17)

set(GHSMULTI 1)
set(Boost_USE_STATIC_LIBS 1)
set(CMAKE_FIND_LIBRARY_PREFIXES "")
set(CMAKE_FIND_LIBRARY_SUFFIXES "")
find_package(Boost COMPONENTS thread context coroutine chrono system date_time atomic REQUIRED)
include_directories(${Boost_INCLUDE_DIRS})

add_executable(callcc-factorial.cpp callcc-factorial.cpp)
add_executable(callcc.cpp callcc.cpp)
target_link_libraries(callcc-factorial.cpp ${Boost_LIBRARIES})
target_link_libraries(callcc.cpp ${Boost_LIBRARIES})

MESSAGE(STATUS "!!!!! Boost_LIBRARIES: ${Boost_LIBRARIES}")
MESSAGE(STATUS "!!!!! Boost_INCLUDE_DIR: ${Boost_INCLUDE_DIRS}")
MESSAGE(STATUS "!!!!! Boost_LIBRARY_DIRS: ${Boost_LIBRARY_DIRS}")