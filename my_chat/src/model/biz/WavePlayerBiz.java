package model.biz;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class WavePlayerBiz {
	private AudioFormat format;
	private byte[] samples;
	private SourceDataLine line;

	public static void main(String args[]) throws Exception {
		WavePlayerBiz sound = new WavePlayerBiz("music/ai.wav");

		InputStream stream = new ByteArrayInputStream(sound.getSamples());
		
		sound.play(stream);
		
		System.out.println("before");
		// play the sound
		// exit
		while (true) {
			
		}
	}

	public WavePlayerBiz(String filename) {
		try {
			// open the audio input stream
			AudioInputStream stream = AudioSystem.getAudioInputStream(new File(
					filename));
			format = stream.getFormat();
			// get the audio samples
			samples = getSamples(stream);
		} catch (UnsupportedAudioFileException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public byte[] getSamples() {
		return samples;
	}

	private byte[] getSamples(AudioInputStream audioStream) {
		// get the number of bytes to read
		int length = (int) (audioStream.getFrameLength() * format
				.getFrameSize());
		// read the entire stream
		byte[] samples = new byte[length];
		DataInputStream is = new DataInputStream(audioStream);
		try {
			is.readFully(samples);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		// return the samples
		return samples;
	}

	public void stop() {
		line.stop();
	}

	public void play(InputStream source) {
		new Thread(new PlayRun(source)).start();
	}

	private class PlayRun implements Runnable {
		InputStream source;

		public PlayRun(InputStream source) {
			this.source = source;
		}

		@Override
		public void run() {
			// use a short, 100ms (1/10th sec) buffer for real-time
			// change to the sound stream
			int bufferSize = format.getFrameSize()
					* Math.round(format.getSampleRate() / 10);
			byte[] buffer = new byte[bufferSize];
			// create a line to play to

			try {
				DataLine.Info info = new DataLine.Info(SourceDataLine.class,
						format);
				line = (SourceDataLine) AudioSystem.getLine(info);
				line.open(format, bufferSize);
			} catch (LineUnavailableException ex) {
				ex.printStackTrace();
				return;
			}
			// start the line
			line.start();
			// copy data to the line
			try {
				int numBytesRead = 0;
				while (numBytesRead != -1) {
					numBytesRead = source.read(buffer, 0, buffer.length);
					if (numBytesRead != -1) {
						line.write(buffer, 0, numBytesRead);
					}
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			// wait until all data is played, then close the line
			line.drain();
			line.close();
		}

	}
}
