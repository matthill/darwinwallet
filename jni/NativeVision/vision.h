#include "opencv2/highgui/highgui.hpp"
#include "opencv2/calib3d/calib3d.hpp"
#include "opencv2/imgproc/imgproc.hpp"
#include "opencv2/features2d/features2d.hpp"
#include "opencv2/video/tracking.hpp"

using namespace cv;
using namespace std;

Ptr<ORB> getQueryDetector();
Ptr<ORB> getTrainerDetector();
Ptr<ORB> getTrainerDetector(int detection_points);

Ptr<DescriptorMatcher> getMatcher();

struct RecognitionResult {
  bool haswinner;
  string winner;
  int confidence;
} ;

void surfStyleMatching( Ptr<DescriptorMatcher>& descriptorMatcher,
                     const Mat& queryDescriptors, const Mat& trainingDescriptors,
                     vector<DMatch>& matches12 );


void surfStyleMatching( Ptr<DescriptorMatcher>& descriptorMatcher,
                     const Mat& queryDescriptors,
                     vector<DMatch>& matches12 );

Mat trainImage( const Mat& img1,
                  Ptr<ORB>& detector, 
                  Ptr<DescriptorMatcher>& descriptorMatcher );

RecognitionResult recognize( const Mat& queryImg, bool drawOnImage, Mat* outputImage,
                  Ptr<ORB>& detector, 
                  Ptr<DescriptorMatcher>& descriptorMatcher, vector<string>& billMapping,
		  bool debug_on, int* debug_matches_array );