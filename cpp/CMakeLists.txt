project (my_server C CXX)
cmake_minimum_required(VERSION 2.8)

set (CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")
#add_subdirectory(crow)
add_executable(my_server my_server.cpp)
#target_link_libraries(my_server crow)
find_package( Boost 1.52 COMPONENTS system thread REQUIRED )
target_link_libraries(my_server ${Boost_LIBRARIES})