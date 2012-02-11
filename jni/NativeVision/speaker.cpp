


#include "speaker.h"

  
  espeak_POSITION_TYPE position_type;

  char *path=NULL;
  int Buflength = 500, Options=0;
  void* user_data;
  t_espeak_callback *SynthCallback;
  espeak_PARAMETER Parm;

  char Voice[] = {"default"};

  //char text[20] = {"Hello World!"};

  unsigned int Size,position=0, end_position=0, flags=espeakCHARS_AUTO, *unique_identifier;


  void initSpeaker()
  {
      espeak_Initialize(AUDIO_OUTPUT_PLAYBACK, Buflength, path, Options ); 
      espeak_SetVoiceByName(Voice);
  }

  int speak_synchronously(string text)
  {
    int retval = speak(text);
    espeak_Synchronize( );
    
    return retval;
  }

  int speak(string text)
  {
      if (espeak_IsPlaying() == 1)
      {
	return -1;
      }
      
      int I, Run = 1, L;    
      Size = text.length() + 1;  
      //printf("Saying  '%s'",text);
      
      
      char *ctext;
      ctext = &text[0];
      espeak_Synth( ctext, Size, position, position_type, end_position, flags,
      unique_identifier, user_data );
      //espeak_Synchronize( );
      
      return 0;
  }

