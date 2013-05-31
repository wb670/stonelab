package com.stonelab.ledctl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.stonelab.ledctl.LedCtl.LedCtlException;

public class MainActivity extends Activity {

	private LedCtl ledCtl = LedCtl.getLedCtl();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		((Button) findViewById(R.id.b_connect))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String gateway = ((EditText) findViewById(R.id.et_gateway))
								.getText().toString();
						String port = ((EditText) findViewById(R.id.et_port))
								.getText().toString();

						try {
							ledCtl.connect(gateway, port);

							Intent i = new Intent(MainActivity.this,
									CtlActivity.class);
							startActivity(i);
						} catch (LedCtlException e) {
							showAlertDialog();
						}
					}
				});

		((Button) findViewById(R.id.b_cancel))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (ledCtl.isConnected()) {
							ledCtl.close();
						}
						finish();
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		menu.getItem(0).setOnMenuItemClickListener(
				new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem item) {
						if (ledCtl.isConnected()) {
							ledCtl.close();
						}
						finish();
						return true;
					}
				});
		return true;
	}

	private void showAlertDialog() {
		AlertDialog.Builder b = new AlertDialog.Builder(this);
		b.setTitle(R.string.warning);
		b.setMessage(R.string.connect_fail);
		b.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		b.show();
	}
}
