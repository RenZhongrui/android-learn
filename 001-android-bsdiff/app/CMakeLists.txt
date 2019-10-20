cmake_minimum_required(VERSION 3.4.1)
file(GLOB bzip src/main/cpp/bzip/*)
add_library( native-lib SHARED src/main/cpp/native-lib.cpp ${bzip} src/main/cpp/bspatch.c)

find_library( log-lib log )

target_link_libraries( native-lib ${log-lib} )