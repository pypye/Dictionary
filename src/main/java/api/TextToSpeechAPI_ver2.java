package api;

import java.io.FileOutputStream;
import com.voicerss.tts.AudioCodec;
import com.voicerss.tts.AudioFormat;
import com.voicerss.tts.Languages;
import com.voicerss.tts.SpeechDataEvent;
import com.voicerss.tts.SpeechDataEventListener;
import com.voicerss.tts.SpeechErrorEvent;
import com.voicerss.tts.SpeechErrorEventListener;
import com.voicerss.tts.VoiceParameters;
import com.voicerss.tts.VoiceProvider;


public class TextToSpeechAPI_ver2 {
    public static void main (String args[]) throws Exception {
        VoiceProvider tts = new VoiceProvider("600b8fac7a214e5e91fff8c9baf69a4a");

        VoiceParameters params = new VoiceParameters("Hello, world!", Languages.English_UnitedStates);
        params.setCodec(AudioCodec.WAV);
        params.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
        params.setBase64(false);
        params.setSSML(false);
        params.setRate(0);

        byte[] voice = tts.speech(params);

        FileOutputStream fos = new FileOutputStream("voice.mp3");
        fos.write(voice, 0, voice.length);
        fos.flush();
        fos.close();
    }
}