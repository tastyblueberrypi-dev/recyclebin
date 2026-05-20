#pragma once
// #ifndef FILESPROTECTION_HEADER_H
// #define FILESPROTECTION_HEADER_H

#pragma GCC diagnostic ignored "-Wwrite-strings"

#include <jni.h>
#include <fcntl.h>
#include <stdio.h>
#include <errno.h>
#include <sys/stat.h>
#include <android/log.h>

#include <stdlib.h>
#include <string.h>

#include <unistd.h>

#ifdef __cplusplus
extern "C"{
#endif


JNIEXPORT jint JNICALL Java_com_aainc_recyclebin_storage_FileSystemHandler_nativeOpen(JNIEnv *env, jobject obj, jstring file_path);

JNIEXPORT jboolean JNICALL Java_com_aainc_recyclebin_storage_FileSystemHandler_nativeCopy(JNIEnv *env, jobject obj, jint fd_from, jlong size_of_bytes, jstring copy_destination);

JNIEXPORT jlong JNICALL Java_com_aainc_recyclebin_storage_FileSystemHandler_nativeGetFileSize(JNIEnv *env, jobject obj, jint descriptor);

JNIEXPORT jint JNICALL Java_com_aainc_recyclebin_storage_FileSystemHandler_nativeClose(JNIEnv *env, jobject obj, jint descriptor);

jint throwIOExceptionError(JNIEnv *env, char *message);

void itoa(int n, char *str);
void reverse(char *s);

#ifdef __cplusplus
}
#endif
// #endif //FILESPROTECTION_HEADER_H
