package com.vaibhavlakhera.ppicalc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MyPreferenceActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pref_with_app_bar);

		Toolbar toolbar = (Toolbar) findViewById(R.id.preference_app_bar);
		setSupportActionBar(toolbar);

		/*assert getSupportActionBar() != null;

		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

		getFragmentManager().beginTransaction().replace(R.id.content_frame, new MyPreferenceFragment()).commit();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		/*if (item.getItemId() == R.id.homeAsUp)
		{
			NavUtils.navigateUpFromSameTask(this);
		}*/
		return super.onOptionsItemSelected(item);
	}
}
