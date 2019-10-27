#include <jni.h>
#include <string>

extern "C" {
    extern int bspatch_main(int argc,char * argv[]);
}


extern "C"
JNIEXPORT jstring JNICALL
Java_com_learn_bsdiff_MainActivity_stringFromJNI(JNIEnv *env, jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT void JNICALL
Java_com_learn_bsdiff_MainActivity_bsPatch(JNIEnv *env, jobject instance, jstring oldApk_,
                                           jstring patch_, jstring output_) {
    const char *oldApk = env->GetStringUTFChars(oldApk_, 0);
    const char *patch = env->GetStringUTFChars(patch_, 0);
    const char *output = env->GetStringUTFChars(output_, 0);

    // 调用合成方法
    const char* argv[] = {"bsdiff",oldApk,output,patch};
    bspatch_main(4, (char **) argv);

    env->ReleaseStringUTFChars(oldApk_, oldApk);
    env->ReleaseStringUTFChars(patch_, patch);
    env->ReleaseStringUTFChars(output_, output);
}