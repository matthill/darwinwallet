#include "opencv2/highgui/highgui.hpp"
#include "opencv2/calib3d/calib3d.hpp"
#include "opencv2/imgproc/imgproc.hpp"
#include "opencv2/features2d/features2d.hpp"
#include "opencv2/video/tracking.hpp"

#include "imgTrainer.h"
#include "speaker.h"
#include "vision.h"

#include <time.h>
#include <iostream>

using namespace cv;
using namespace std;


const string winName = "correspondences";

#define DRAW_RICH_KEYPOINTS_MODE     0
#define DRAW_OUTLIERS_MODE           0

static int prev_match_index = -1;

double diffclock(clock_t clock1,clock_t clock2)
{
	double diffticks=clock2-clock1;
	//double diffms=(diffticks*1000)/ CLOCKS_PER_SEC;
	double diffms = diffticks;
	return diffms;
}




int main(int argc, char** argv)
{


    cout << "< Creating detector, descriptor extractor and descriptor matcher ..." << endl;
    //Ptr<FeatureDetector> detector = FeatureDetector::create( argv[1] );
    //http://opencv.itseez.com/modules/features2d/doc/feature_detection_and_description.html
    //        CommonParams(float scale_factor = 1.2f, unsigned int n_levels = DEFAULT_N_LEVELS,
    //         int edge_threshold = 31, unsigned int first_level = DEFAULT_FIRST_LEVEL);
             

    Ptr<ORB> detector = getQueryDetector();

    Ptr<DescriptorMatcher> descriptorMatcher = getMatcher();


    
    cout << ">" << endl;
    if( detector.empty() || descriptorMatcher.empty()  )
    {
        cout << "Can not create detector or descriptor exstractor or descriptor matcher of given types" << endl;
        return -1;
    }
		
    cout << "< Reading the images..." << endl;
    
    
    vector<string> billMapping;
    
    int success = loadRecognitionSet("ca", descriptorMatcher, billMapping);
    
    for (int i = 0; i < billMapping.size(); i++)
    {
      cout << billMapping[i] << endl;
    }
    
    if( success != 0 )
    {
        cout << "Failed training images" << endl;
    }    
    
    
    Mat img2;
    
    //initSpeaker();
    //speak_synchronously("Initification");
    
    namedWindow(winName, 1);
 
    const int BORDER_SIZE = 28;
    
    CvCapture* cap = cvCaptureFromCAM(0);
    //CvCapture* cap = cvCreateFileCapture("/tmp/shot2.jpg");

    cvSetCaptureProperty( cap, CV_CAP_PROP_FRAME_WIDTH, 800 );

    cvSetCaptureProperty( cap, CV_CAP_PROP_FRAME_HEIGHT, 400 );
    
    
    int count = 0;
    for(;;)
    {
      
	IplImage* img2 =  cvQueryFrame(cap);
	
	IplImage* mgray_small = cvCreateImage(cvSize(400,200),
	    img2->depth,img2->nChannels);
	cvResize(img2, mgray_small);

	IplImage* externsrc = mgray_small;
	//IplImage* externsrc = cvCreateImage( cvSize(320+BORDER_SIZE*2,240+BORDER_SIZE*2), 8, 3 );
	//cvCopyMakeBorder( img2, externsrc, cvPoint(BORDER_SIZE, BORDER_SIZE), IPL_BORDER_CONSTANT, CV_RGB(185, 186, 72));
	
	Mat drawImg;
	
	int debug_matches[billMapping.size()];
	RecognitionResult result = recognize( externsrc, true, &drawImg, detector, descriptorMatcher, billMapping, 
					      true, debug_matches);
	
	if (result.haswinner == true)
	{
	 //speak(result.winner); 
	}
	
	count++;
	if (count % 10 == 0)
	{
	  cout << "-----------------------------------" << endl;
	  for (int i = 0; i < billMapping.size(); i++)
	    cout << billMapping[i] << ": " << debug_matches[i] << endl;
	  cout << "-----------------------------------" << endl;
	}
	//putText(drawImg, "boom", Point(15, 27), FONT_HERSHEY_DUPLEX, 1.1, CV_RGB(0, 0, 0), 2);
	imshow( winName, drawImg );
	
	waitKey(1);

    }


    return 0;
}