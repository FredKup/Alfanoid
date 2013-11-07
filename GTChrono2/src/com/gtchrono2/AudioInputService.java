package com.gtchrono2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AudioInputService extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		if (intent.getAction().equals("android.media.AUDIO_BECOMING_NOISY"))
		{
			Toast.makeText(context, "Input detected", Toast.LENGTH_LONG).show();	
		}
	}

}

