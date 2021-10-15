package api;

import java.io.*;
import javax.sound.sampled.*;

public class AudioManager {
    private static TargetDataLine microphone;
    private static SourceDataLine speaker;
    public static boolean RECORDING = false;

    public static boolean isRecording() {
        return RECORDING;
    }

    public static void startRecording() {
        try {
            RECORDING = true;
            AudioFormat format = new AudioFormat(8000, 8, 1, true, true);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            microphone = AudioSystem.getTargetDataLine(format);
            microphone = (TargetDataLine) AudioSystem.getLine(info);
            microphone.open(format);
            microphone.start();
            AudioInputStream ais = new AudioInputStream(microphone);
            AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new File("src/main/java/data/temp.wav"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void stopRecording() {
        microphone.stop();
        microphone.close();
        RECORDING = false;
    }

    public static void startPlaying(byte[] audio) {
        try {
            AudioInputStream ais = new AudioInputStream(
                    new ByteArrayInputStream(audio),
                    new javax.sound.sampled.AudioFormat(44100, 16, 2, true, false),
                    audio.length
            );
            AudioFormat format = ais.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            speaker = (SourceDataLine) AudioSystem.getLine(info);
            speaker.open(format);
            speaker.start();
            int buffer;
            byte[] data = new byte[4096];
            while ((buffer = ais.read(data)) != -1) speaker.write(data, 0, buffer);
            speaker.drain();
            speaker.close();
            ais.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}