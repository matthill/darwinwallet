#include "opencv2/highgui/highgui.hpp"
#include "opencv2/calib3d/calib3d.hpp"
#include "opencv2/imgproc/imgproc.hpp"
#include "opencv2/features2d/features2d.hpp"
#include "opencv2/video/tracking.hpp"

#include <fstream>
#include "yaml-cpp/yaml.h"

#include <time.h>
#include <iostream>


using namespace cv;
using namespace std;

int loadRecognitionSet(string name, Ptr<DescriptorMatcher> descriptorMatcher, vector<string>& billMapping);