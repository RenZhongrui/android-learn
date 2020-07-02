# 将LOCAL_PATH变量定义成本文件所在目录路径
LOCAL_PATH := $(call my-dir)
LOCAL_PATH2 := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE           := xcrash_dumper
LOCAL_CFLAGS           := -std=c11 -fvisibility=hidden -fPIE
LOCAL_LDFLAGS          := -pie
LOCAL_LDLIBS           := -ldl -llog
LOCAL_STATIC_LIBRARIES := lzma
# 引入头文件，当前目录和common目录
LOCAL_C_INCLUDES       := $(LOCAL_PATH) $(LOCAL_PATH)/../common
# 引入源文件
LOCAL_SRC_FILES        := $(wildcard $(LOCAL_PATH)/*.c) $(wildcard $(LOCAL_PATH)/../common/*.c)
# 编译成xcrash_dumper可执行程序
include $(BUILD_EXECUTABLE)
# 包含另一个mkfile文件
include $(LOCAL_PATH)/lzma/Android.mk
# 生成libxcrash动态库
include $(LOCAL_PATH2)/../libxcrash/Android.mk
