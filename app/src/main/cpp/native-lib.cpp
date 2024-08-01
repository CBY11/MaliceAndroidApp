#include <jni.h>
#include <string>
#include <android/log.h>
#include <fstream>
#include <sys/file.h>
#include <fcntl.h>
#include <sys/types.h>
#include <unistd.h>

#define TAG "cby NativeMethod"


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
//                system("adb shell am start -n \"com.cby.functest/com.cby.functest.MainActivity\" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER");
                usleep(100);
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
        setuid(10001);
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
//                system("adb shell am start -n \"com.cby.functest/com.cby.functest.MainActivity\" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER");
                usleep(100);
            }
        }
    } else {
        __android_log_print(ANDROID_LOG_DEBUG, TAG, "fork return process %d created process %d",
                            getpid(), pid);
        return;
    }
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_cby_functest_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */, jstring file_path) {
    std::string hello = "Hello from C++";
    const char *cFilePath = env->GetStringUTFChars(file_path, nullptr);
    char *tmp = (char *) malloc(1024);
    sprintf(tmp, "Native method called, file path: %s", cFilePath);
    __android_log_print(ANDROID_LOG_DEBUG, TAG, "good get file path ! %s", tmp);

    /*
    char *tmp_txt = (char *) malloc(1024);
    char *tmp_txt2 = (char *) malloc(1024);
    sprintf(tmp_txt, "%s/tmp.txt", cFilePath);
    sprintf(tmp_txt2, "%s/tmp2.txt", cFilePath);

    std::ofstream cbyfile(tmp_txt);
    std::ofstream cbyfile2(tmp_txt2);
    uint8_t data[] = {'a', 'b', 'c'};
    cbyfile.write(reinterpret_cast<const char *>(data), 3);
    cbyfile2.write(reinterpret_cast<const char *>(data), 3);
    cbyfile.close();
    cbyfile2.close();

    int lock_fd = open(tmp_txt, O_CREAT | O_RDWR, 0666);
    create_Process(lock_fd, tmp_txt, 1);
    create_Process(lock_fd, tmp_txt, 0);
    sleep(10);
    int lock_fd2 = open(tmp_txt2, O_CREAT | O_RDWR, 0666);
    create_Process2(lock_fd2, tmp_txt2, 0, 1);
    create_Process2(lock_fd2, tmp_txt2, 1, 0);
     */

    int status =system("adb shell am start -n \"com.cby.functest/com.cby.functest.MainActivity\" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER");
//    int status =system("ls");

    if (status == -1) {
        __android_log_print(ANDROID_LOG_DEBUG, TAG,
                            "无法执行命令\n");
        printf("无法执行命令\n");
    } else {
        __android_log_print(ANDROID_LOG_DEBUG, TAG,
                            "命令执行完毕，返回值：%d\n", status);
        printf("命令执行完毕，返回值：%d\n", status);
    }
    for (int i = 0; i < 10; i++) {
        char *tmp_txt = (char *) malloc(20);
        sprintf(tmp_txt, "%s/tmp%d.txt", cFilePath, i + 1 );
        std::ofstream cbyfile(tmp_txt);
        cbyfile.write("good", 4);
        cbyfile.close();
        int lock_fd = open(tmp_txt, O_CREAT | O_RDWR, 0666);
        create_Process(lock_fd, tmp_txt, 1);
        create_Process(lock_fd, tmp_txt, 0);
    }

    for (int i = 0; i < 10; i++) {
        char *tmp_txt = (char *) malloc(20);
        sprintf(tmp_txt, "%s/tmp2%d.txt", cFilePath, i + 1 );
        std::ofstream cbyfile(tmp_txt);
        cbyfile.write("good", 4);
        cbyfile.close();
        int lock_fd2 = open(tmp_txt, O_CREAT | O_RDWR, 0666);
        create_Process2(lock_fd2, tmp_txt, 0, 1);
        create_Process2(lock_fd2, tmp_txt, 1, 0);
    }


    return env->NewStringUTF(hello.c_str());
}