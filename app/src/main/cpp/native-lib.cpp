#include <jni.h>
#include <string>
#include <android/log.h>
#include <fstream>
#include <sys/file.h>
#include <fcntl.h>
#include <sys/types.h>
#include <unistd.h>

#define TAG "cby NativeMethod"
#define SIZE 1


void create_Process(int lock_fd, char *file, int first) {
    __android_log_print(ANDROID_LOG_DEBUG, TAG, "cp1 begin !");
    int pid = fork();
    if (pid == 0) {
        close(lock_fd);
        lock_fd = open(file, O_CREAT | O_RDWR, 0666);
        if (flock(lock_fd, LOCK_EX) == 0) {
            __android_log_print(ANDROID_LOG_DEBUG, TAG, "process %d(pid) get lock1", getpid());
            if (!first) {
                __android_log_print(ANDROID_LOG_DEBUG, TAG,
                                    "process %d(pid) will create another process", getpid());
                create_Process(lock_fd, file, 0);
            }
            while (1) {
                __android_log_print(ANDROID_LOG_DEBUG, TAG, "process %d(pid) start endless loop",
                                    getpid());
                usleep(2000);
            }
        }
    } else {
        __android_log_print(ANDROID_LOG_DEBUG, TAG, "fork return process %d created process %d",
                            getpid(), pid);
        return;
    }
}


void create_Process2(int lock_fd, char *file, int first, int wait) {
    __android_log_print(ANDROID_LOG_DEBUG, TAG, "cp2 begin !");
    int pid = fork();
    if (pid == 0) {
        close(lock_fd);
        lock_fd = open(file, O_CREAT | O_RDWR, 0666);
        if (wait) sleep(10);
        if (flock(lock_fd, LOCK_EX) == 0) {
            __android_log_print(ANDROID_LOG_DEBUG, TAG, "process %d(pid) get lock2", getpid());
            if (!first) {
                __android_log_print(ANDROID_LOG_DEBUG, TAG,
                                    "process %d(pid) will create another process", getpid());
                create_Process2(lock_fd, file, 0, 0);
            }
            while (1) {
                __android_log_print(ANDROID_LOG_DEBUG, TAG, "process %d(pid) start endless loop",
                                    getpid());
                usleep(2000);
            }
        }
    } else {
        __android_log_print(ANDROID_LOG_DEBUG, TAG, "fork return process %d created process %d",
                            getpid(), pid);
        return;
    }
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_cby_forktest_1new_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */, jstring file_path) {
    std::string hello = "Hello from C++";
    const char *cFilePath = env->GetStringUTFChars(file_path, nullptr);
    char *tmp = (char *) malloc(1024);
    sprintf(tmp, "Native method called, file path: %s", cFilePath);
    __android_log_print(ANDROID_LOG_DEBUG, TAG, "good get file path ! %s", tmp);

    for (int i = 0; i < SIZE; i++) {
        char *tmp_txt = (char *) malloc(20);
        sprintf(tmp_txt, "%s/tmp%d.txt", cFilePath, i + 1 );
        std::ofstream cbyfile(tmp_txt);
        cbyfile.write("good", 4);
        cbyfile.close();
        int lock_fd = open(tmp_txt, O_CREAT | O_RDWR, 0666);
        create_Process(lock_fd, tmp_txt, 1);
        create_Process(lock_fd, tmp_txt, 0);
    }

    for (int i = 0; i < SIZE; i++) {
        char *tmp_txt = (char *) malloc(20);
        sprintf(tmp_txt, "%s/tmp%d.txt", cFilePath, i + 1 );
        std::ofstream cbyfile(tmp_txt);
        cbyfile.write("good", 4);
        cbyfile.close();
        int lock_fd2 = open(tmp_txt, O_CREAT | O_RDWR, 0666);
        create_Process2(lock_fd2, tmp_txt, 0, 1);
        create_Process2(lock_fd2, tmp_txt, 1, 0);
    }

    return env->NewStringUTF(hello.c_str());
}