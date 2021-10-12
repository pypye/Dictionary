package api;

import java.io.*;
import java.util.Arrays;
import javax.sound.sampled.*;

public class AudioManager {
    private static TargetDataLine microphone;
    private static SourceDataLine speaker;
    public static boolean RECORDING = false;

    public static boolean isRecording() {
        return RECORDING;
    }

    public static byte[] startRecording() {
        try {
            RECORDING = true;
            AudioFormat format = new AudioFormat(44100, 16, 2, true, false);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            microphone = AudioSystem.getTargetDataLine(format);
            microphone = (TargetDataLine) AudioSystem.getLine(info);
            microphone.open(format);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] data = new byte[microphone.getBufferSize() / 5];
            microphone.start();
            int numBytesRead;
            int bytesRead = 0;
            while (microphone.isOpen() && bytesRead < 100000) {
                numBytesRead = microphone.read(data, 0, 1024);
                bytesRead += numBytesRead;
                out.write(data, 0, numBytesRead);
            }
            byte[] ans = out.toByteArray();
            try {
                FileOutputStream fos = new FileOutputStream("voice.mp3");
                fos.write(ans, 0, ans.length);
                fos.flush();
                fos.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            //startPlaying(ans);
            //System.out.println(Arrays.toString(ans));
            return ans;
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void stopRecording() {
        microphone.stop();
        microphone.close();
        RECORDING = false;
        System.out.println("Finished");
    }

    public static void startPlaying(byte[] audio) {
        try {
            AudioInputStream audioStream = new AudioInputStream(
                    new ByteArrayInputStream(audio),
                    new javax.sound.sampled.AudioFormat(44100, 16, 2, true, false),
                    audio.length
            );
            AudioFormat audioFormat = audioStream.getFormat();
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
            speaker = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            speaker.open(audioFormat);
            speaker.start();
            int bytesRead;
            byte[] bytesBuffer = new byte[4096];
            while ((bytesRead = audioStream.read(bytesBuffer)) != -1) {
                speaker.write(bytesBuffer, 0, bytesRead);
            }
            speaker.drain();
            speaker.close();
            audioStream.close();
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }
}