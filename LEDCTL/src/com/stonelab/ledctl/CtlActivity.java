package com.stonelab.ledctl;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;

public class CtlActivity extends Activity {

	private LedCtl ledCtl = LedCtl.getLedCtl();
	private boolean all;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ctl);

		onSwitch(R.id.on1, LedCtl.CTL_OFF1, LedCtl.CTL_ON1);
		onSwitch(R.id.on2, LedCtl.CTL_OFF2, LedCtl.CTL_ON2);
		onSwitch(R.id.on3, LedCtl.CTL_OFF3, LedCtl.CTL_ON3);
		onSwitch(R.id.on4, LedCtl.CTL_OFF4, LedCtl.CTL_ON4);

		onButton(R.id.b_alloff, LedCtl.CTL_OFF, false);
		onButton(R.id.b_allon, LedCtl.CTL_ON, true);
		onButton(R.id.b_scene, LedCtl.CTL_SCENE, false);
		onButton(R.id.b_store, LedCtl.CTL_STORE);
	}

	@SuppressLint("NewApi")
	private void onSwitch(int id, final byte off, final byte on) {
		((Switch) findViewById(id))
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton b,
							boolean checked) {
						if (all) {
							return;
						}
						byte ctl = checked ? on : off;
						ledCtl.ctl(ctl);
					}
				});
	}

	@SuppressLint("NewApi")
	private void onButton(int id, final byte ctl) {
		((Button) findViewById(id)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ledCtl.ctl(ctl);
			}
		});
	}

	@SuppressLint("NewApi")
	private void onButton(int id, final byte ctl, final boolean on) {
		((Button) findViewById(id)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ledCtl.ctl(ctl);

				all = true;
				((Switch) findViewById(R.id.on1)).setChecked(on);
				((Switch) findViewById(R.id.on2)).setChecked(on);
				((Switch) findViewById(R.id.on3)).setChecked(on);
				((Switch) findViewById(R.id.on4)).setChecked(on);
				all = false;
			}
		});

	}
}
