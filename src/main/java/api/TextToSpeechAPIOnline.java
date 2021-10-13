package api;

import com.voicerss.tts.AudioCodec;
import com.voicerss.tts.AudioFormat;
import com.voicerss.tts.Languages;
import com.voicerss.tts.VoiceParameters;
import com.voicerss.tts.VoiceProvider;

public class TextToSpeechAPIOnline {
    public static void getTextToSpeech(String text) {
        try {
            VoiceProvider tts = new VoiceProvider("600b8fac7a214e5e91fff8c9baf69a4a");
            VoiceParameters params = new VoiceParameters(text, Languages.English_UnitedStates);
            params.setCodec(AudioCodec.WAV);
            params.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
            params.setBase64(false);
            params.setSSML(false);
            params.setRate(0);
            byte[] voice = tts.speech(params);
            AudioManager.startPlaying(voice);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}