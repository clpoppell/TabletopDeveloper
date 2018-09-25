package com.tadbolmont.tabletopdeveloper;

import android.app.Application;
import android.content.Context;

/**
 * Convenience class for providing a {@code Context} object.
 */
public class App extends Application{
	
	public static Context mContext;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mContext= this;
	}
	
	
}