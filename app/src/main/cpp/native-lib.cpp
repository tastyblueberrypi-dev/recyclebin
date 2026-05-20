#include "header.h"

#define LOG_TAG "native_lib"
#define DEBUG 1 //0 is true and 1 is false
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

jint Java_com_aainc_recyclebin_storage_FileSystemHandler_nativeOpen(JNIEnv *env, jobject obj, jstring file_path)
{
    int fd = -1;
    const char *path = (env)->GetStringUTFChars(file_path, JNI_FALSE);

    try {
        fd = open(path, O_RDONLY);
    } catch(...){
        return -1;
    }

    return fd;
}

jboolean Java_com_aainc_recyclebin_storage_FileSystemHandler_nativeCopy(JNIEnv *env, jobject obj, jint fd_from, jlong size_of_bytes, jstring copy_destination)
{
    if (DEBUG == 0)
        LOGD("nativeCopy method\n");

    const char *path_new_file = (env)->GetStringUTFChars(copy_destination ,JNI_FALSE);
    errno = 0;

    try {
        int fd_to = open(path_new_file, O_RDWR | O_CREAT | O_EXCL , 0666);

        int buffer_length = 4096;
        char buffer[buffer_length];
        ssize_t nread;

        size_t size = buffer_length;

        if(size_of_bytes < size)
        {
            size = size_of_bytes;
        }

        while( (nread = read(fd_from, buffer, size) ) > 0)
        {
            int readingDataSize = (int ) nread;

            char *out_ptr = buffer;
            ssize_t nwritten;

            do {
                nwritten = write(fd_to, out_ptr, nread);

                int writingDataSize = (int) nwritten;
                if(nwritten > 0)
                {
                    nread -= nwritten;
                    out_ptr += nwritten;
                }
                else if(errno != EINTR)
                {
                    if (DEBUG == 0)
                        LOGD("Some error may occurred >> errno is %d\n", errno);

                    goto out_error;
                }

            }while(nread>0);

            size_of_bytes -= readingDataSize;
            if(size_of_bytes < size)
            {
                size = size_of_bytes;
            }
        }

        out_error:
        int result_code;
        int result_synchronization = fsync(fd_to);

        if(result_synchronization !=0 )
        {
            if (DEBUG == 0)
                LOGE("Synchronization with storage is fail. Some part of data can be lost\n");
        }

        result_code = close(fd_to);

        if(nread < 0 || result_code < 0)
        {
            char *exception_msg = "Fatal exception. Can't be copy file.";
            int throwCode = throwIOExceptionError(env, exception_msg);
            free(exception_msg);
            return throwCode;
        }
    } catch(...){
        return false;
    }

    return true;
}


jlong Java_com_aainc_recyclebin_storage_FileSystemHandler_nativeGetFileSize(JNIEnv *env, jobject obj, jint fd)
{
    long fileSize = -1;
    errno = 0;

    if( fcntl(fd, F_GETFD) !=-1 || errno != EBADF)
    {
        struct stat statbuf;
        fstat(fd, &statbuf);
        if (DEBUG == 0)
        {
            LOGD("I-node number of file: %lu" , statbuf.st_ino);
            LOGD("Number of hark link: %d" , statbuf.st_nlink);
            LOGD("Total file size: %ld" , statbuf.st_size);
        }

        fileSize = statbuf.st_size;

    }else{

        char *error_msg = "Incoming file descriptor is invalid.";
        char *code_error = (char*) malloc(30);

        itoa(errno, code_error);

        char *system_error_prefix = "System error message: ";
        char *system_error = strerror(errno);

        char new_line = '\n';
        char end_str= '\0';

        char *exception_msg = (char*) malloc(strlen(error_msg) + strlen(code_error) + strlen(system_error_prefix) +strlen(system_error) + 2);

        strcat(exception_msg, error_msg);
        strcat(exception_msg, code_error);
        strcat(exception_msg, &new_line);

        strcat(exception_msg, system_error_prefix);
        strcat(exception_msg, system_error);
        strcat(exception_msg, &end_str);

        int throwCode = throwIOExceptionError(env, exception_msg);
        free(code_error);
        free(exception_msg);
        return throwCode;
    }

    return fileSize;
}

jint Java_com_aainc_recyclebin_storage_FileSystemHandler_nativeClose(JNIEnv *env, jobject obj, jint fd)
{
    int result_code;
    errno = 0;

    result_code = close((int)fd);

    if(result_code != 0)
    {
        char *error_msg = "File or Dir can't be closed. Code error is ";
        char *code_error = (char *) malloc(30);
        itoa(errno, code_error);

        char *system_error_prefix = "System error message: ";
        char *system_error = strerror(errno);
        char new_line = '\n';
        char end_str= '\0';

        char *exception_msg = (char*) malloc(strlen(error_msg) + strlen(code_error) + strlen(system_error_prefix) +strlen(system_error) + 2);

        strcat(exception_msg, error_msg);
        strcat(exception_msg, code_error);
        strcat(exception_msg, &new_line);

        strcat(exception_msg, system_error_prefix);
        strcat(exception_msg, system_error);
        strcat(exception_msg, &end_str);

        int throwCode = throwIOExceptionError(env, exception_msg);
        free(code_error);
        free(exception_msg);
        return throwCode;
    }

    return result_code;
}

jint throwIOExceptionError(JNIEnv *env, char *message)
{
    jclass exClass;
    char *className = "java/io/IOException";

    exClass = (env)->FindClass(className);
    return (env)->ThrowNew(exClass, message);
}

void itoa(int n, char *str)
{
    int i, sign = n;

    if (sign<0)  /* record sign */
    {
        n = -n;          /* make n positive */
    }

    i = 0;
    do
    {
        *(str+i) = n % 10 + '0';
        i++;
    } while ((n /= 10) > 0);

    if(sign<0){
        *(str + i) = '-';
    }

    *(str + strlen(str)) = '\0';

    reverse(str);
}

void reverse(char *s)
{
    int i, j;
    char c;

    for (i = 0, j = strlen(s)-1; i<j; i++, j--) {
        c = s[i];
        s[i] = s[j];
        s[j] = c;
    }
}

