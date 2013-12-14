package com.stonelab.ledctl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class WaveActivity extends Activity {

	private static final int SAMPLE_RATE = 44100;
	private static final int WAVE = 127;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wave);

		((Button) findViewById(R.id.b_start))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View view) {
						String rate = ((EditText) findViewById(R.id.et_rate))
								.getText().toString();

						System.out.println("play");
						track(Integer.valueOf(rate));
						System.out.println("stop");
					}
				});

		((Button) findViewById(R.id.b_record))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						record();
					}
				});
	}

	public void track(int rate) {
		System.out.println(rate);
		AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
				SAMPLE_RATE, AudioFormat.CHANNEL_OUT_MONO,
				AudioFormat.ENCODING_PCM_8BIT, SAMPLE_RATE,
				AudioTrack.MODE_STREAM);
		byte[] wave = sin(rate, SAMPLE_RATE);
		audioTrack.play();
		for (int i = 0; i < 30; i++) {
			audioTrack.write(wave, 0, wave.length);
		}
		audioTrack.stop();
	}

	public void record() {
		AudioRecord.getMinBufferSize(SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO,
				AudioFormat.ENCODING_PCM_16BIT);
		AudioRecord audioRecord = new AudioRecord(
				MediaRecorder.AudioSource.MIC, SAMPLE_RATE,
				AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT,
				SAMPLE_RATE);
		byte[] sin = new byte[SAMPLE_RATE];
		int[] wave = new int[SAMPLE_RATE / 2];
		audioRecord.startRecording();

		int len = audioRecord.read(sin, 0, SAMPLE_RATE);
		for (int i = 0; i < len / 2; i++) {
			int val = 0;
			val = ((val += sin[2 * i]) << 8) + sin[2 * i + 1];
			wave[i] = val;
			System.out.println(val);
		}
		System.out.println(Arrays.toString(wave));

		int[] waveRates = getWaveRates(wave);
		System.out.println(Arrays.toString(waveRates));

		audioRecord.stop();
	}

	protected byte[] sin(int rate, int sampleRate) {
		byte[] sin = new byte[sampleRate];
		for (int i = 0; i < sampleRate; i++) {
			sin[i] = (byte) (WAVE * (Math.sin(Math.PI * 2 / (sampleRate / rate)
					* i)));
		}
		return sin;
	}

	protected int[] getWaveRates(int[] wave) {
		List<Integer> rates = new ArrayList<Integer>();
		boolean up = false, down = false;
		int spots = 0;
		for (int i = 0; i < wave.length; i++) {
			spots++;
			if (i + 1 >= wave.length) {
				up = down = true;
			} else {
				// up
				if (wave[i + 1] > wave[i]) {
					up = true;
				}
				// down
				else {
					if (up) {
						down = true;
					}
				}
			}
			if (down) {
				rates.add(spots);
				up = down = false;
				spots = 0;
			}
		}
		int[] ret = new int[rates.size()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = rates.get(i);
		}
		return ret;
	}

}
