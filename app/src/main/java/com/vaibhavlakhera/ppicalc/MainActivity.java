package com.vaibhavlakhera.ppicalc;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
	private Boolean accurateResult, darkTheme;

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



		/*if (darkTheme)
			setTheme(R.style.MaterialDarkTheme);
		else
			setTheme(R.style.MaterialLightTheme);*/
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
	public void onClick(View v)
	{
		//Hide the keyboard on button press.

		InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		mgr.hideSoftInputFromWindow(screenWidthEditText.getWindowToken(), 0);

		switch (v.getId())
		{
			case R.id.calculatePpi:
				calculatePpi();
				break;

			case R.id.autoDetect:
				setScreenDimensions();
				displayPpiTextView.setText("");
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
		displayPpiTextView.setText("");
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
			Log.i(TAG, "Exception in calculatePpi() " + e.toString());
		}
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
				Intent intent = new Intent(this, MyPreferenceActivity.class);
				startActivity(intent);
				break;

			case R.id.preset_settings:
				buildPresetDialog();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void buildPresetDialog()
	{
		final String[] resolutionPresets = { "360p", "360p Wide", "480p VGA", "480p DVD NTSC", "576p DVD PAL", "720p HD", "768p FWXGA", "900p HD+", "1080p FHD", "1200p WUXGA", "1440p WQHD", "1600p WQXGA", "2160p 4K UHD", "4320p 8K UHD" };
		AlertDialog.Builder dialogBuilder;
		dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setTitle("Choose from presets:");
		dialogBuilder.setItems(resolutionPresets, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				switch (which)
				{
					case 0:
						screenWidthEditText.setText("480");
						screenHeightEditText.setText("360");
						break;
					case 1:
						screenWidthEditText.setText("640");
						screenHeightEditText.setText("360");
						break;
					case 2:
						screenWidthEditText.setText("640");
						screenHeightEditText.setText("480");
						break;
					case 3:
						screenWidthEditText.setText("720");
						screenHeightEditText.setText("480");
						break;
					case 4:
						screenWidthEditText.setText("720");
						screenHeightEditText.setText("576");
						break;
					case 5:
						screenWidthEditText.setText("1280");
						screenHeightEditText.setText("720");
						break;
					case 6:
						screenWidthEditText.setText("1280");
						screenHeightEditText.setText("768");
						break;
					case 7:
						screenWidthEditText.setText("1600");
						screenHeightEditText.setText("900");
						break;
					case 8:
						screenWidthEditText.setText("1920");
						screenHeightEditText.setText("1080");
						break;
					case 9:
						screenWidthEditText.setText("1920");
						screenHeightEditText.setText("1200");
						break;
					case 10:
						screenWidthEditText.setText("2560");
						screenHeightEditText.setText("1440");
						break;
					case 11:
						screenWidthEditText.setText("2560");
						screenHeightEditText.setText("1600");
						break;
					case 12:
						screenWidthEditText.setText("3840");
						screenHeightEditText.setText("2160");
						break;
					case 13:
						screenWidthEditText.setText("7680");
						screenHeightEditText.setText("4320");
						break;
					default:
						screenWidthEditText.setText("");
						screenHeightEditText.setText("");
						break;
				}
			}
		});
		AlertDialog dialogPresets = dialogBuilder.create();
		dialogPresets.show();
		displayPpiTextView.setText("");
	}

	private void clearValues()
	{
		Log.i(TAG, "clearValues()");
		screenSizeEditText.setText("");
		screenHeightEditText.setText("");
		screenWidthEditText.setText("");
		displayPpiTextView.setText("");
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		accurateResult = sharedPreferences.getBoolean("accurateCheckBox", false);
		darkTheme = sharedPreferences.getBoolean("darkThemeCheckBox", false);
	}

	@Override
	protected void onPause()
	{
		super.onPause();
	}
}