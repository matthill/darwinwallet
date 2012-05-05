LOCAL_PATH := $(call my-dir)



include $(CLEAR_VARS)

OPENCV_CAMERA_MODULES := off
#OPENCV_MK_PATH:=/home/mhill/projects/darwin_wallet/opencv/OpenCV-2.3.1/share/OpenCV/OpenCV.mk
OPENCV_MK_PATH:=/home/mhill/projects/darwin_wallet/OpenCV-2.4.0Android/share/OpenCV/OpenCV.mk

include $(OPENCV_MK_PATH)
 
#Profiler 
#-include android-ndk-profiler.mk

#include ../includeOpenCV.mk
#ifeq ("$(wildcard $(OPENCV_MK_PATH))","")
#	#try to load OpenCV.mk from default install location
#	include $(TOOLCHAIN_PREBUILT_ROOT)/user/share/OpenCV/OpenCV.mk
#else
#	include $(OPENCV_MK_PATH)
#endif

#LOCAL_C_INCLUDES := /home/mhill/projects/darwin_wallet/OpenCV-2.3.2BetaAndroid/include
LOCAL_C_INCLUDES := /usr/local/include

LOCAL_MODULE    := native_wallet
LOCAL_SRC_FILES := jni_recognizer.cpp NativeVision/vision.cpp
LOCAL_CFLAGS=-ffast-math -O3 -funroll-loops
#LOCAL_CFLAGS=-O3 -funroll-loops

LOCAL_LDLIBS +=  -llog -ldl

#Profiling
#LOCAL_CFLAGS := -pg
#LOCAL_STATIC_LIBRARIES := andprof

include $(BUILD_SHARED_LIBRARY)
