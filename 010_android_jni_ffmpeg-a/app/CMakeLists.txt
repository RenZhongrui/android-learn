cmake_minimum_required(VERSION 3.4.1)

# 设置静态库目录
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -L${CMAKE_SOURCE_DIR}/src/main/cpp/libs/${ANDROID_ABI}")
# 加载头文件
include_directories(src/main/cpp/include)
# 加载库文件
add_library(ffmpeg-demo SHARED src/main/cpp/native-lib.cpp)
# 链接静态库
target_link_libraries(ffmpeg-demo avcodec avfilter avformat avutil swresample swscale log)
