

#include "imgTrainer.h"
#include "vision.h"








//int main(int argc, char** argv)
//{
//    loadRecognitionSet("", getMatcher());
//
//    return 0;
//}


int loadRecognitionSet(string name, Ptr<DescriptorMatcher> descriptorMatcher, vector<string>& billMapping)
{
    std::ostringstream out; 
    out << "/home/mhill/projects/darwin_wallet/DarwinWallet/jni/NativeVision/currency/" << name << ".yaml";
    
    cout << "loading: " << out.str() << endl;
    std::ifstream fin(out.str().c_str());
    
    // Setup the detector, extractor, and matcher
    //ORB::CommonParams cp = ORB::CommonParams(1.2f,12U,5,1); 
    
    Ptr<FeatureDetector> detector = getTrainerDetector();
    //Ptr<FeatureDetector> detector2 = getQueryDetector();

    Ptr<DescriptorExtractor> descriptorExtractor = getExtractor();
    // = getMatcher();


    // Parse the yaml input file
    YAML::Parser parser(fin);

    YAML::Node doc;
    std::string detection_points;
    std::string scale_factor;
    
    cout << "parsing yaml" << endl;
    
    vector<Mat> trainImages;
    
    while(parser.GetNextDocument(doc)) {
      
      const YAML::Node& bills = doc["bills"];
      
      //doc["detection_points_per_image"] >> detection_points;
      //doc["scale_factor"] >> scale_factor;
      
      //cout << "Detection Points: " << detection_points << endl;
      //cout << "Scale factor: " << scale_factor << endl;
      
//      for(YAML::Iterator it=bills.begin();it!=bills.end();++it) {

//	  std::string key, value;
	  std::string img_path;
//	  it.first() >> key;
//	  
//	  const YAML::Node& bill = bills[key];
//	  std::cout << "Key: " << key << std::endl;
    
	  for(int i = 0; i < bills.size(); i++) {
	    
	    std::string patch, type;
	    bills[i]["type"] >> type;
	    bills[i]["patch"] >> patch;
	    std::cout << "  -- Patch: " << type << " (" << patch << ")" << std::endl;
	    
	    
	    out.str("");
	    out << "/home/mhill/projects/darwin_wallet/DarwinWallet/assets/" << name << "/" << type << "/" << patch << ".jpg";
	    img_path = out.str();
	    
	    std::cout << img_path << endl;
	    
	    // load and add patch to recognizer
	    cout << "< Reading image: " << type << endl;
	    Mat img1 = imread( img_path );
	    if( img1.empty() )
	    {
		cout << "Can not read images" << endl;
		return -1;
	    }
	    
	    billMapping.push_back(type);
	    Mat trainData = trainImage(img1, detector, descriptorExtractor, descriptorMatcher);
	    trainImages.push_back(trainData);
	  }
    
     // }
           
      
    }
    
    descriptorMatcher->add(trainImages);
    descriptorMatcher->train();
    
    //FileStorage fileobj = FileStorage("/tmp/outty", FileStorage::WRITE);
    //descriptorMatcher->write(fileobj);

    return 0;
}


