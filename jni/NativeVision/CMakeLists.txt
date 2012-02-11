

project(nativevision)

include_directories(include)

add_executable(nativevision main.cpp vision.cpp  imgTrainer.cpp speaker.cpp
 
# YAML files
./yaml/aliasmanager.cpp ./yaml/conversion.cpp ./yaml/emitter.cpp ./yaml/scanscalar.cpp ./yaml/stream.cpp 
./yaml/exp.cpp ./yaml/emitfromevents.cpp ./yaml/regex.cpp ./yaml/scantag.cpp ./yaml/singledocparser.cpp 
./yaml/ostream.cpp ./yaml/simplekey.cpp ./yaml/null.cpp ./yaml/directives.cpp ./yaml/emitterstate.cpp ./yaml/scanner.cpp 
./yaml/parser.cpp ./yaml/contrib/graphbuilderadapter.cpp ./yaml/contrib/graphbuilder.cpp ./yaml/emitterutils.cpp 
./yaml/iterator.cpp ./yaml/nodeownership.cpp ./yaml/nodebuilder.cpp ./yaml/tag.cpp ./yaml/scantoken.cpp ./yaml/node.cpp 
)

add_executable(webcam_test webcam_test.cpp vision.cpp)

add_executable(test test.cpp vision.cpp  )


target_link_libraries (nativevision 

#OpenCV
opencv_core opencv_imgproc opencv_calib3d opencv_video opencv_features2d opencv_ml opencv_highgui opencv_objdetect opencv_contrib opencv_legacy

#E-speak
espeak
) 

target_link_libraries (webcam_test opencv_core opencv_imgproc opencv_calib3d opencv_video opencv_features2d opencv_ml opencv_highgui opencv_objdetect opencv_contrib opencv_legacy) 

target_link_libraries (test opencv_core opencv_imgproc opencv_calib3d opencv_video opencv_features2d opencv_ml opencv_highgui opencv_objdetect opencv_contrib opencv_legacy) 
