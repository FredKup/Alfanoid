package com.gtchrono2;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class ChronoActivity extends Activity {

	private TimerTask timerTask;
	private final Handler handler = new Handler();
	private Timer timer = new Timer();
	private double StartTime;
	private boolean inLap = false;
	private TextView textview;
	private ArrayList<String> times = new ArrayList<String>();
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chrono);
		this.textview = (TextView)findViewById(R.id.textView1);
		timerTask = new TimerTask() {
		    public void run() {
		            handler.post(new Runnable() {
		                    public void run() {
		                       writeTime();
		                    }
		           });
		    }};
		timer.schedule(timerTask, 1, 10);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.chrono, menu);
		return true;
	}
	
	public void setStartTime(){
		StartTime=System.currentTimeMillis();
	}
	
	public String getTime(){
		double millis=System.currentTimeMillis()-this.StartTime;
		return String.format("%s", millis);
	}
	
	public void writeTime(){
		if(!this.inLap)
		{
			setStartTime();
			this.inLap=true;
		}
		this.textview.setText(getTime()) ;
	}
	
	public void saveTime(){
		times.add(this.getTime());
	}
}
