#include <jni.h>
#include "calcstat.h"


JNIEXPORT jint JNICALL Java_rtrk_pnrs1_ra174_12014_taskmanager_Statistics_JniStats_calcStats
  (JNIEnv *env, jobject obj, jint total, jint done){
	int c = 0;
	c = ((double) done / (double) total) * 100;

	return (jint) c;

}