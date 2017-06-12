LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := libjnistats
LOCAL_MODULE_TAGS := optional
LOCAL_SRC_FILES := calcstat.c

include $(BUILD_SHARED_LIBRARY)
