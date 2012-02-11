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

#include <fstream>  

using namespace cv;
using namespace std;


char* readFileBytes(const char *name)  
{  
    ifstream fl(name);  
    fl.seekg( 0, ios::end );  
    size_t len = fl.tellg();  
    char *ret = new char[len];  
    fl.seekg(0, ios::beg);   
    fl.read(ret, len);  
    fl.close();  
    return ret;  
}  


int main(int argc, char** argv)
{
  int bytelength = 110651;
  
  char* bytearray = readFileBytes("/home/mhill/projects/darwin_wallet/DarwinWallet/assets/eu/100b/full_pic.jpg");
  
  //Mat mcolor(height + height/2, width, CV_8UC1, (unsigned char *)bytearray);
  Mat mgray(1, bytelength, CV_8U, (unsigned char *)bytearray);
  
  
  Mat img = imdecode(mgray, 0);
  
  
  vector<KeyPoint> v;


  //std::vector<char> buffvector((char*)imgBuffer, (char*)imgBuffer + sizeof((char*)imgBuffer));

  //Mat grayImg = imdecode(buffvector, 0);

  ORB::CommonParams cp = ORB::CommonParams(1.2f,12U,5,1);
  OrbFeatureDetector detector(500, cp);
  detector.detect(img, v);
	
  //std::vector<char> buffvector(bytearray, bytearray + sizeof(bytearray));
		
  //Mat img = imdecode(buffvector, 0);

  drawKeypoints(img, v, img);
  
  imshow( "test window", img );

	
	waitKey(10000);
    return 0;
}
   