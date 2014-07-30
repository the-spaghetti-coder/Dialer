package com.example.dialer;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DialerMainActivity extends Activity {

	Intent intent;
	Button dial;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dialer_main);
		
		intent = new Intent(Intent.ACTION_DIAL);
		intent.setData(Uri.parse("tel:8583616642"));
		
		
		dial = (Button)findViewById(R.id.dialButton);
		
		dial.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startActivity(intent);
				
			}
		});
		
		Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
		phones.moveToLast();
		
		  String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
		  String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
		  String lastTimeContact = phones.getString(phones.getColumnIndex(ContactsContract.Contacts.LAST_TIME_CONTACTED));
		  Log.i("Contacts Info", name + " - " + phoneNumber + " - " + lastTimeContact);
		
		phones.close();
		
		Date date = new Date();
		long lengthOfDayInMillis = 86400000;
		long currentDate = date.getTime();
		long phoneLong = Long.valueOf(lastTimeContact);
		long difference = (currentDate - phoneLong) / lengthOfDayInMillis;
		Log.i("Calculation", "Current date: " + String.valueOf(currentDate) + " Difference: " + String.valueOf(difference));
		if (difference!=0){
			Log.i("Time Since", "It has been at least one day since you've last contacted" + name);
		} else {
			Log.i("Time Since", "It hasn't even been a day.");
		}
			}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dialer_main, menu);
		return true;
	}

}
