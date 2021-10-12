package api;

import java.util.Locale;
import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

public class TextToSpeechAPIOffline {
    public static void getTextToSpeech(String input) {
        try {
            System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
            Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
            Synthesizer synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.ENGLISH));
            synthesizer.allocate();
            synthesizer.resume();
            synthesizer.speakPlainText(input, null);
            synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        getTextToSpeech("hello");
        getTextToSpeech("one");
        getTextToSpeech("world");
        long end = System.currentTimeMillis();
        System.out.println("\n" + (end - start));
    }
}
