/* Copyright (C) 2014 M. Steve Todd mstevetodd@enginedriver.rrclubs.org

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package jmri.enginedriver;

import jmri.enginedriver.logviewer.ui.LogViewerActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class power_control extends Activity {

	private threaded_application mainapp;  // hold pointer to mainapp
	private Drawable power_on_drawable;  //hold background graphics for power button
	private Drawable power_off_drawable;
	private Drawable power_unknown_drawable;
	private Menu PMenu;


	//Handle messages from the communication thread back to this thread (responses from withrottle)
	@SuppressLint("HandlerLeak")
	class power_control_handler extends Handler {

		public void handleMessage(Message msg) {
			switch(msg.what) {
			case message_type.RESPONSE:
				String response_str = msg.obj.toString();
				if (response_str.length() >= 3 && response_str.substring(0,3).equals("PPA")) {  //refresh power state 
					refresh_power_control_view();
				}
				break;
			case message_type.WIT_CON_RETRY:
				refresh_power_control_view(); 
				break;
			case message_type.DISCONNECT:
			case message_type.SHUTDOWN:
				disconnect();
				break;
			};
		}
	}

	public class button_listener implements View.OnClickListener	  {

		public void onClick(View v) {
			int newState = 1;
			if (mainapp.power_state.equals("1")) { //toggle to opposite value 0=off, 1=on
				newState = 0;
			}
			mainapp.sendMsg(mainapp.comm_msg_handler, message_type.POWER_CONTROL, "", newState);
		};
	}

	//Set the button text based on current power state  TODO: improve code 
	@SuppressWarnings("deprecation")
	public void refresh_power_control_view() {
		Button b=(Button)findViewById(R.id.power_control_button);
		Drawable currentImage = power_unknown_drawable;
		if (mainapp.power_state == null) {
			b.setEnabled(false);
			TextView tv=(TextView)findViewById(R.id.power_control_text);
			tv.setText(getString(R.string.power_control_not_allowed)); 
		} 
		else { 
			b.setEnabled(true);
			if (mainapp.power_state.equals("1")) {
				currentImage = power_on_drawable;
			} 
			else if(mainapp.power_state.equals("2"))
			{
				currentImage = power_unknown_drawable;
			}
			else {
				currentImage = power_off_drawable;
			}
		}		 

		if(PMenu != null)
		{
			mainapp.displayEStop(PMenu);
		}

		b.setBackgroundDrawable(currentImage);
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)  {
		super.onCreate(savedInstanceState);
		mainapp=(threaded_application)getApplication();
//		SharedPreferences prefs  = getSharedPreferences("jmri.enginedriver_preferences", 0);
		if(mainapp.isForcingFinish()) {		// expedite
			return;
		}
		setContentView(R.layout.power_control);

//		String defaultName = getApplicationContext().getResources().getString(R.string.prefThrottleNameDefaultValue);
//		setTitle(getApplicationContext().getResources().getString(R.string.app_name_power_control) + "    |    Throttle Name: " + 
//				prefs.getString("throttle_name_preference", defaultName));

		//put pointer to this activity's handler in main app's shared variable (If needed)
		mainapp.power_control_msg_handler=new power_control_handler();

		power_on_drawable=getResources().getDrawable(R.drawable.power_green);
		power_off_drawable=getResources().getDrawable(R.drawable.power_red);
		power_unknown_drawable=getResources().getDrawable(R.drawable.power_yellow);


		//Set the button callbacks, storing the command to pass for each
		Button b=(Button)findViewById(R.id.power_control_button);
		button_listener click_listener=new button_listener();
		b.setOnClickListener(click_listener);

	};

	@Override
	public void onResume() {
		super.onResume();
		if(mainapp.isForcingFinish()) {	//expedite
			this.finish();
			return;
		}
		mainapp.setActivityOrientation(this);  //set screen orientation based on prefs
		mainapp.cancelRunningNotify();
		
		if(PMenu != null)
		{
			mainapp.displayEStop(PMenu);
		}
		//update route list
		refresh_power_control_view();
	}

	@Override
	public void onPause()
	{
		super.onPause();
		if(this.isFinishing()) {		//if finishing, expedite it and don't invoke setContentIntentNotification
			return;
		}
		mainapp.setContentIntentNotification(this.getIntent());
	}

		/** Called when the activity is finished. */
	@Override
	public void onDestroy() {
		mainapp.power_control_msg_handler = null;
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.power_menu, menu);
		PMenu = menu;
		mainapp.displayEStop(menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle all of the possible menu actions.
		Intent in;
		switch (item.getItemId()) {
		case R.id.throttle_mnu:
			this.finish();
			connection_activity.overridePendingTransition(this, R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.turnouts_mnu:
			this.finish();
			in=new Intent().setClass(this, turnouts.class);
			startActivity(in);
			connection_activity.overridePendingTransition(this, R.anim.push_right_in, R.anim.push_right_out);
			break;
		case R.id.routes_mnu:
			this.finish();
			in = new Intent().setClass(this, routes.class);
			startActivity(in);
			connection_activity.overridePendingTransition(this, R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.web_mnu:
			this.finish();
			in=new Intent().setClass(this, web_activity.class);
			startActivity(in);
			connection_activity.overridePendingTransition(this, R.anim.fade_in, R.anim.fade_out);
			break;
		case R.id.exit_mnu:
			mainapp.checkExit(this);
			break;
		case R.id.preferences_mnu:
			in=new Intent().setClass(this, preferences.class);
			startActivityForResult(in, 0);
			connection_activity.overridePendingTransition(this, R.anim.fade_in, R.anim.fade_out);
			break;
		case R.id.about_mnu:
			this.finish();
			in=new Intent().setClass(this, about_page.class);
			startActivity(in);
			connection_activity.overridePendingTransition(this, R.anim.fade_in, R.anim.fade_out);
			break;
		case R.id.logviewer_menu:
			this.finish();
			Intent logviewer=new Intent().setClass(this, LogViewerActivity.class);
			startActivity(logviewer);
			connection_activity.overridePendingTransition(this, R.anim.fade_in, R.anim.fade_out);
			break;
		case R.id.EmerStop:
			mainapp.sendEStopMsg();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	//Handle pressing of the back button to end this activity
	@Override
	public boolean onKeyDown(int key, KeyEvent event) {
		if(key==KeyEvent.KEYCODE_BACK)
		{
			this.finish();  //end this activity
			connection_activity.overridePendingTransition(this, R.anim.fade_in, R.anim.fade_out);
		}
		return(super.onKeyDown(key, event));
	};

	private void disconnect() {
		this.finish();
	}

}