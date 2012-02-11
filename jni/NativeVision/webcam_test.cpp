#include "opencv2/highgui/highgui.hpp"
#include "opencv2/calib3d/calib3d.hpp"
#include "opencv2/imgproc/imgproc.hpp"
#include "opencv2/features2d/features2d.hpp"
#include "opencv2/video/tracking.hpp"

#include "vision.h"

#include <time.h>
#include <iostream>

using namespace cv;
using namespace std;



#define DRAW_RICH_KEYPOINTS_MODE     0
#define DRAW_OUTLIERS_MODE           0

const string winName = "correspondences";

enum { NONE_FILTER = 0, CROSS_CHECK_FILTER = 1, RADIUS_FILTER = 2 };

int getMatcherFilterType( const string& str )
{
    if( str == "NoneFilter" )
        return NONE_FILTER;
    if( str == "CrossCheckFilter" )
        return CROSS_CHECK_FILTER;
    if( str == "RadiusFilter" )
        return RADIUS_FILTER;
    CV_Error(CV_StsBadArg, "Invalid filter name");
    return -1;
}

    

/*
int getObjectIndex(const DMatch& match)
{
  for (size_t objInd = 0; objInd < objectSizes.size(); objInd++)
  {
    if (match.imgIdx < objectSizes[objInd])
      return (int)objInd;
  }
  return -1;
}
*/

void radiusMatching( Ptr<DescriptorMatcher>& descriptorMatcher,
                     const Mat& descriptors1, const Mat& descriptors2,
                     vector<DMatch>& filteredMatches12, float maxradius=30  )
{


	cout << "boom1" << flush;
    filteredMatches12.clear();
	cout << "boom2" << flush;
    
    vector<vector<DMatch> > matches12;
    descriptorMatcher->radiusMatch( descriptors1, descriptors2, matches12, maxradius );
	cout << "boom3" << flush;

	cout << "Size: " << matches12.size() << flush;
    for (size_t m = 0; m < matches12.size(); m++)
    {
      for( size_t fk = 0; fk < matches12[m].size(); fk++ )
        {
		DMatch forward = matches12[m][fk];
		cout << "booms: " << forward.distance << flush;
		filteredMatches12.push_back(forward);
		break;

      }
    }

}



void crossCheckMatching( Ptr<DescriptorMatcher>& descriptorMatcher,
                         const Mat& descriptors1, const Mat& descriptors2,
                         vector<DMatch>& filteredMatches12, int knn=1 )
{
    filteredMatches12.clear();
    vector<vector<DMatch> > matches12, matches21;
    descriptorMatcher->knnMatch( descriptors1, descriptors2, matches12, knn );
    descriptorMatcher->knnMatch( descriptors2, descriptors1, matches21, knn );
    for( size_t m = 0; m < matches12.size(); m++ )
    {
        bool findCrossCheck = false;
        for( size_t fk = 0; fk < matches12[m].size(); fk++ )
        {
            DMatch forward = matches12[m][fk];

            for( size_t bk = 0; bk < matches21[forward.trainIdx].size(); bk++ )
            {
                DMatch backward = matches21[forward.trainIdx][bk];
                if( backward.trainIdx == forward.queryIdx )
                {
                    filteredMatches12.push_back(forward);
                    findCrossCheck = true;
                    break;
                }
            }
            if( findCrossCheck ) break;
        }
    }
}

double diffclock(clock_t clock1,clock_t clock2)
{
	double diffticks=clock2-clock1;
	double diffms=(diffticks*1000)/CLOCKS_PER_SEC;
	return diffms;
}

void doIteration( const Mat& img1, IplImage* img2, 
                  vector<KeyPoint>& keypoints1, const Mat& descriptors1,
                  Ptr<FeatureDetector>& detector, Ptr<DescriptorExtractor>& descriptorExtractor,
                  Ptr<DescriptorMatcher>& descriptorMatcher )
{

    clock_t begin;
    clock_t end;
    clock_t truebegin;

    assert( !img1.empty() );
    Mat H12;

    //assert( !img2.empty()/* && img2.cols==img1.cols && img2.rows==img1.rows*/ );

    truebegin=begin=clock();
    cout << endl << "< Extracting keypoints from second image..." << endl;
    vector<KeyPoint> keypoints2;
    detector->detect( img2, keypoints2 );
    end=clock();

    cout << keypoints2.size() << " points in :" << diffclock(begin, end) << "ms" << endl << ">" << endl;

    
    if (keypoints2.size() <= 5)
    {
      return;
    }
    
    begin=clock();
    cout << "< Computing descriptors for keypoints from second image..." << endl;
    Mat descriptors2;
    descriptorExtractor->compute( img2, keypoints2, descriptors2 );
    end=clock();
    cout << ">completed in: " << diffclock(begin, end) << "ms" << endl;


    begin=clock();
    cout << "< Matching descriptors..." << endl;
    vector<DMatch> filteredMatches;

    surfStyleMatching( descriptorMatcher, descriptors2, descriptors1, filteredMatches );



    end=clock();
    cout << "> " << diffclock(begin, end) << "ms"  << endl;


    vector<int> queryIdxs( filteredMatches.size() ), trainIdxs( filteredMatches.size() );
    for( size_t i = 0; i < filteredMatches.size(); i++ )
    {
        queryIdxs[i] = filteredMatches[i].queryIdx;
        trainIdxs[i] = filteredMatches[i].trainIdx;
    }







 
    begin=clock();

	    Mat drawImg;
	    //if( !H12.empty() ) // filter outliers
	    if( true ) // filter outliers
	    {
		vector<char> matchesMask( filteredMatches.size(), 0 );
		//vector<Point2f> points1; KeyPoint::convert(keypoints1, points1, queryIdxs);
		//vector<Point2f> points2; KeyPoint::convert(keypoints2, points2, trainIdxs);
		//Mat points1t; perspectiveTransform(Mat(points1), points1t, H12);

		int realmatchcount = filteredMatches.size();
		
//		for( size_t i1 = 0; i1 < matchesMask.size(); i1++ )
//		{
//		    if (filteredMatches[i1].distance < 37)
//		    {
//		        //if( norm(points2[i1] - points1t.at<Point2f>((int)i1,0)) <= maxInlierDist ) // inlier
			//{
		        	//matchesMask[i1] = 1;
				//realmatchcount++;
			//}
//	 	    }
//		}


    		end=clock();

	    cout << filteredMatches.size() << " - " << realmatchcount << " matches." << endl;
	    cout << "filtered in: " << diffclock(begin, end) << "ms"  << endl;
	    cout << "E2E time:" << diffclock(truebegin, end) << "ms" << endl << ">" << endl;

		// draw inliers
		drawMatches(  img2, keypoints2, img1, keypoints1, filteredMatches, drawImg, CV_RGB(0, 255, 0), CV_RGB(0, 0, 255) 
		           );
	    }
	    else
	    {
		//drawMatches( img1, keypoints1, img2, keypoints2, filteredMatches, drawImg );
	    }

	    //vector<KeyPoint> match_keypoints( filteredMatches.size() )
	    //rawKeypoints( img2, keypoints2, drawImg );
	    
	    imshow( winName, drawImg );
    
}



int main(int argc, char** argv)
{


    cout << "< Creating detector, descriptor extractor and descriptor matcher ..." << endl;
    
    Ptr<FeatureDetector> detector1 = getTrainerDetector();
    Ptr<FeatureDetector> detector2 = getQueryDetector();

    Ptr<DescriptorExtractor> descriptorExtractor = getExtractor();
    Ptr<DescriptorMatcher> descriptorMatcher = getMatcher();
   

    
    cout << ">" << endl;
    if( detector1.empty() || detector2.empty() || descriptorExtractor.empty() || descriptorMatcher.empty()  )
    {
        cout << "Can not create detector or descriptor exstractor or descriptor matcher of given types" << endl;
        return -1;
    }
		
    cout << "< Reading the images..." << endl;
    Mat img1 = imread( "/home/mhill/projects/darwin_wallet/DarwinWallet/assets/ca/5b/full_pic.jpg" );
    //Mat img1 = imread( "/tmp/20f.jpg" );
    
    //Mat img2;
    //Mat img2_bordered;
    
    
 
 
    cout << ">" << endl;
    if( img1.empty() )
    {
        cout << "Can not read images" << endl;
        return -1;
    }

    cout << endl << "< Extracting keypoints from first image..." << endl;
    vector<KeyPoint> keypoints1;
    detector1->detect( img1, keypoints1 );
    cout << keypoints1.size() << " points" << endl << ">" << endl;

    cout << "< Computing descriptors for keypoints from first image..." << endl;
    Mat descriptors1;
    descriptorExtractor->compute( img1, keypoints1, descriptors1 );
    cout << ">" << endl;

    namedWindow(winName, 1);
    RNG rng = theRNG();


    //VideoCapture cap;

    //cap.open(1);
    
    const int BORDER_SIZE = 28;
    
    CvCapture* cap = cvCaptureFromCAM(0);

    cvSetCaptureProperty( cap, CV_CAP_PROP_FRAME_WIDTH, 320 );

    cvSetCaptureProperty( cap, CV_CAP_PROP_FRAME_HEIGHT, 240 );
    for(;;)
    {
	IplImage* img2 =  cvQueryFrame(cap);
	//IplImage* img2 = cvCreateImage( cvSize(320,240), 8, 3 );

	IplImage* externsrc = img2;
	//IplImage* externsrc = cvCreateImage( cvSize(320+BORDER_SIZE*2,240+BORDER_SIZE*2), 8, 3 );
	
	//cvCopyMakeBorder( img2, externsrc, cvPoint(BORDER_SIZE, BORDER_SIZE), IPL_BORDER_CONSTANT, cvScalarAll(255));
	
            doIteration( img1, externsrc, keypoints1, descriptors1,
                         detector2, descriptorExtractor, descriptorMatcher );
	waitKey(2);

    }

    return 0;
}