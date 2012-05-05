#include <jni.h>
#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/features2d/features2d.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <vector>

#include "NativeVision/vision.h"


#include <time.h>

#include <android/log.h>

using namespace std;
using namespace cv;

#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,   "JNI_DEBUGGING", __VA_ARGS__)



double diffclock(clock_t clock1,clock_t clock2)
{
	double diffticks=clock2-clock1;
	double diffms=(diffticks*1000)/ CLOCKS_PER_SEC;

	return diffms;
}

static Ptr<ORB> detector;
static Ptr<DescriptorMatcher> descriptorMatcher;

static vector<Mat> trainImages;
static bool training_complete = false;

static vector<string> billMapping;

extern "C" {
	JNIEXPORT void JNICALL Java_com_ndu_mobile_darwinwallet_Recognizer_nvInitialize(JNIEnv* env, jobject thiz)
	{

		LOGD( "Started nvInitialize" );

		detector = getQueryDetector();
		descriptorMatcher = getMatcher();

		LOGD( "Finished nvInitialize" );

	}
}

extern "C" {
	JNIEXPORT void JNICALL Java_com_ndu_mobile_darwinwallet_Recognizer_nvResetTrainedDatabase(JNIEnv* env, jobject thiz)
	{
		LOGD( "Started nvResetTrainedDatabase" );

		training_complete = false;
		descriptorMatcher = getMatcher();
		trainImages.clear();
		billMapping.clear();

		LOGD( "Finished nvResetTrainedDatabase" );
	}
}
extern "C" {
	JNIEXPORT void JNICALL Java_com_ndu_mobile_darwinwallet_Recognizer_nvTrainImage(JNIEnv* env, jobject thiz, jstring billname, jstring billpath)
	{
		//char* _imgBytes  = (char*) env->GetPrimitiveArrayCritical(imgBytes, 0);
	    const char * _billpath = env->GetStringUTFChars(billpath, 0);
	    const char * _billname = env->GetStringUTFChars(billname, 0);

		LOGD( "Started nvTrainImage" );

	    std::ostringstream out;

		//std::ostringstream out;
		//out << " : billname: " << _billname << ": BILLPATH: " << billpath << endl;
		LOGD( out.str().c_str() );
		//LOGD( "nvTrainImage: 1" );
	    //Mat mgray(1, bytelength, CV_8U, (unsigned char *)_imgBytes);
		//LOGD( "nvTrainImage: 2" );
	    Mat img = imread(_billpath, 0);
		//Mat img = imread("/sdcard/wallet/us/100b/full_pic.jpg", 0);

		//LOGD( "nvTrainImage: 3" );
	    Mat trainData = trainImage( img,  detector, descriptorMatcher );

	    out << "nvTrainImage: " << _billpath << " (" << trainData.rows << " x " << trainData.cols << ")" << endl;
		LOGD( out.str().c_str() );

	    trainImages.push_back(trainData);

	    string billstr(_billname);
	    billMapping.push_back(billstr);

		LOGD( "Finished nvTrainImage" );
	    env->ReleaseStringUTFChars(billpath, _billpath);
	    env->ReleaseStringUTFChars(billname, _billname);
		//env->ReleasePrimitiveArrayCritical(imgBytes, _imgBytes, 0);
	}
}
extern "C" {
	JNIEXPORT void JNICALL Java_com_ndu_mobile_darwinwallet_Recognizer_nvFinalizeTraining(JNIEnv* env, jobject thiz)
	{
		LOGD( "Started nvFinalizeTraining" );
	    descriptorMatcher->add(trainImages);
	    descriptorMatcher->train();

		training_complete = true;

		LOGD( "Finished nvFinalizeTraining" );
	}
}

extern "C" {
	JNIEXPORT jstring JNICALL Java_com_ndu_mobile_darwinwallet_Recognizer_nvRecognize(JNIEnv* env, jobject thiz, jint width, jint height, jbyteArray yuv)
	{
		jbyte* _yuv  = env->GetByteArrayElements(yuv, 0);
		//jint*  _bgra = env->GetIntArrayElements(bgra, 0);

		LOGD( "Started nvFindFeatures" );


		jstring response = env->NewStringUTF("");

		if (training_complete == true)
		{

			clock_t begin;
			clock_t end;



			//Mat myuv(height + height/2, width, CV_8UC1, (unsigned char *)_yuv);
			//Mat mbgra(height, width, CV_8UC4);
			Mat mgray(height, width, CV_8UC1, (unsigned char *)_yuv);


			//Mat myuv(width, 1, CV_8U, (unsigned char *)_yuv);
			//Mat mgray = imdecode(myuv, 0);
			//Please make attention about BGRA byte order
			//ARGB stored in java as int array becomes BGRA at native level
			//cvtColor(myuv, mbgra, CV_YUV420sp2BGR, 4);

			vector<KeyPoint> v;

			//FastFeatureDetector detector(50);
			//detector.detect(mgray, v);

			float divisor = 1;

			if (height < width)
			{
				divisor = (((double) height) / 240);
			}
			else
			{
				divisor = (((double) width) / 240);
			}

			if (divisor == 0)
				divisor = 1;

			Size idealSize(width/divisor, height/divisor);

			Mat mgray_small;
			resize(mgray, mgray_small, idealSize);


			Mat descriptors;
			vector<DMatch> matches;



			begin=clock();
			//detector->detect(mgray_small, v);
			//descriptorExtractor->compute( mgray_small, v, descriptors );
			//surfStyleMatching( descriptorMatcher, descriptors, matches );

			Mat* dummy;

			//imwrite("/sdcard/wallet_debug.jpg", mgray_small );

			bool debug_on = true;
			int debug_matches[billMapping.size()];
			RecognitionResult result = recognize( mgray_small, false, dummy, detector, descriptorMatcher, billMapping,
					debug_on, debug_matches);

			end=clock();

			std::ostringstream out;
			out << "time: " << diffclock(begin, end) << " ms | matches: " << matches.size() << endl;

			if (debug_on)
			{
				for (int k = 0; k < billMapping.size(); k++)
					out << " --" << billMapping[k] << " : " << debug_matches[k] << endl;
			}

			out << "orig_width: " << width << "orig_height: " << height << endl;
			out << "divisor: " << divisor << endl;
			//LOGD( (char*) out.str().c_str());

			if (result.haswinner == false)
				out << "No winner :(" << endl;
			else
			{
				out << "Big Winner!  " << result.winner << " : " << result.confidence << endl;

				std::ostringstream responsetext;

				responsetext << result.winner << "," << result.confidence;
				response = env->NewStringUTF(responsetext.str().c_str());
			}

			LOGD( (char*) out.str().c_str());

			//for( size_t i = 0; i < v.size(); i++ )
			//	circle(mbgra, Point(v[i].pt.x, v[i].pt.y), 10, Scalar(0,0,255,255));


		}



		LOGD( "Finished nvFindFeatures" );
		//env->ReleaseIntArrayElements(bgra, _bgra, 0);
		env->ReleaseByteArrayElements(yuv, _yuv, 0);

		return response;
	}

}
