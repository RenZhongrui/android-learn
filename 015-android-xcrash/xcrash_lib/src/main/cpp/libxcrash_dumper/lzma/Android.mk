LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE            := lzma
LOCAL_CFLAGS            := -std=c11 \
                           -Wno-enum-conversion \
                           -Wno-reserved-id-macro \
                           -Wno-undef \
                           -Wno-missing-prototypes \
                           -Wno-missing-variable-declarations \
                           -Wno-cast-align \
                           -Wno-sign-conversion \
                           -Wno-assign-enum \
                           -Wno-unused-macros \
                           -Wno-padded \
                           -Wno-cast-qual \
                           -Wno-strict-prototypes \
                           -fPIE \
                           -D_7ZIP_ST
# 引入头文件，在当前目录下
LOCAL_C_INCLUDES        := $(LOCAL_PATH)
# 抛出头文件
LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)
LOCAL_SRC_FILES         := 7zCrc.c      \
                           7zCrcOpt.c   \
                           CpuArch.c    \
                           Bra.c        \
                           Bra86.c      \
                           BraIA64.c    \
                           Delta.c      \
                           Lzma2Dec.c   \
                           LzmaDec.c    \
                           Sha256.c     \
                           Xz.c         \
                           XzCrc64.c    \
                           XzCrc64Opt.c \
                           XzDec.c
# 编译成静态库
include $(BUILD_STATIC_LIBRARY)
