# camke编译版本
cmake_minimum_required(VERSION 3.4.1)
# 引入文件，使用bzip变量引用
file(GLOB bzip src/main/cpp/bzip/*)
# 添加动态编译文件
add_library( native-lib SHARED src/main/cpp/native-lib.cpp ${bzip} src/main/cpp/bspatch.c)
# 添加NDK中log库
find_library( log-lib log )
# 编译库
target_link_libraries( native-lib ${log-lib} )