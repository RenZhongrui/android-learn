#include <jni.h>
#include <string>
#include <android/log.h>


extern "C" {
#include <libavcodec/avcodec.h>
}

extern "C" JNIEXPORT jstring
JNICALL
Java_com_learn_cmake_MainActivity_stringFromJNI(JNIEnv *env, jobject /* this */) {
    std::string hello = "Hello from C++";
    // __android_log_print(ANDROID_LOG_ERROR, "jni", "libtest.a 里面的 test 方法:%d", test());
    return env->NewStringUTF(av_version_info());
}
