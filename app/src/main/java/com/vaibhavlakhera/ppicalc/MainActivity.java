package com.vaibhavlakhera.ppicalc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

	private EditText screenSizeEditText, screenHeightEditText, screenWidthEditText;
	private Button calculatePpiButton, autoDetectButton, clearButton;
	private TextView displayPpiTextView;
	private Boolean accurateResult;

	private int screenHeight = 0, screenWidth = 0;
	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initializeViews();

		calculatePpiButton.setOnClickListener(this);
		autoDetectButton.setOnClickListener(this);
		clearButton.setOnClickListener(this);

		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		accurateResult = sharedPreferences.getBoolean("accurateCheckBox", false);
	}

	private void setScreenDimensions()
	{
		WindowManager windowManager = this.getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();

		if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17)
		{
			Log.i(TAG, "SDK < 17");
			try
			{
				screenHeight = (int) Display.class.getMethod("getRawHeight").invoke(display);
				screenWidth = (int) Display.class.getMethod("getRawWidth").invoke(display);
			}
			catch (Exception e)
			{
				Log.i(TAG, e.toString());
			}
		}
		else if (Build.VERSION.SDK_INT >= 17)
		{
			Log.i(TAG, "SDK >= 17");
			try
			{
				getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
				screenHeight = metrics.heightPixels;
				screenWidth = metrics.widthPixels;
			}
			catch (Exception e)
			{
				Log.i(TAG, e.toString());
			}
		}

		screenHeightEditText.setText(String.valueOf(screenHeight));
		screenWidthEditText.setText(String.valueOf(screenWidth));
	}

	private void initializeViews()
	{
		screenSizeEditText = (EditText) findViewById(R.id.screenSize);
		screenHeightEditText = (EditText) findViewById(R.id.screenHeight);
		screenWidthEditText = (EditText) findViewById(R.id.screenWidth);
		calculatePpiButton = (Button) findViewById(R.id.calculatePpi);
		autoDetectButton = (Button) findViewById(R.id.autoDetect);
		clearButton = (Button) findViewById(R.id.clear);
		displayPpiTextView = (TextView) findViewById(R.id.displayPpi);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.action_settings:
			{
				Intent intent = new Intent(this, MyPreferenceActivity.class);
				startActivity(intent);
				break;
			}

			case R.id.preset_settings:
			{
				Intent intent = new Intent(this, PresetActivity.class);
				startActivity(intent);
				break;
			}
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v)
	{
		InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		mgr.hideSoftInputFromWindow(screenWidthEditText.getWindowToken(), 0);            //To hide the keyboard after pressing the button.
		switch (v.getId())
		{
			case R.id.calculatePpi:
				calculatePpi();
				break;

			case R.id.autoDetect:
				setScreenDimensions();
				break;

			case R.id.clear:
				clearValues();
				break;

			default:
				break;
		}
	}

	private void calculatePpi()
	{
		try
		{
			double screenSize = Double.parseDouble(screenSizeEditText.getText().toString());
			screenHeight = Integer.parseInt(screenHeightEditText.getText().toString());
			screenWidth = Integer.parseInt(screenWidthEditText.getText().toString());

			if (accurateResult)
			{
				double screenPpi = (Math.sqrt((screenHeight * screenHeight) + (screenWidth * screenWidth)) / screenSize);
				displayPpiTextView.setText(String.format("%.4f", screenPpi) + " PPI");
			}
			else
			{
				int screenPpi = (int) (Math.sqrt((screenHeight * screenHeight) + (screenWidth * screenWidth)) / screenSize);
				displayPpiTextView.setText(Integer.toString(screenPpi) + " PPI");
			}
		}
		catch (Exception e)
		{
			Log.i(TAG, "Exception in calculatePpi()");
		}

	}

	private void clearValues()
	{
		Log.i(TAG, "clearValues()");
		screenSizeEditText.setText("");
		screenHeightEditText.setText("");
		screenWidthEditText.setText("");
		displayPpiTextView.setText("");
	}
}