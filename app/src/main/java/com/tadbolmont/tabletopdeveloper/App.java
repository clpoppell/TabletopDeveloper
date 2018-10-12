package com.tadbolmont.tabletopdeveloper;

import android.app.Application;
import android.content.Context;
import android.view.View;

/**
 * Convenience class for providing a {@code Context} object.
 */
public class App extends Application{
	public static int ON= View.VISIBLE;
	public static int OFF= View.GONE;
	
	public static Context context;
	
	@Override
	public void onCreate(){
		super.onCreate();
		context= this;
	}
	
	protected static void toggleViewVisibility(int state, View...views){
		for(View v : views){
			v.setVisibility(state);
		}
	}
}