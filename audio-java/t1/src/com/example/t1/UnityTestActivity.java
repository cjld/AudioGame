package com.example.t1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
 
import android.util.Log;

import com.unity3d.player.UnityPlayerActivity;
import Fly.FlyTTS;

public class UnityTestActivity extends UnityPlayerActivity {
	FlyTTS flytts = null;
	Context mContext = null;
	public String tag = "UnityTestActivity" ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	flytts = new FlyTTS(this);
        super.onCreate(savedInstanceState);
        mContext = this;
    }
 
    public void StartActivity0(String name)
    {
    	Log.e(tag, "StartActivity0 "+name);
    	flytts.startSpeaking(name);
    }
 
    public void StartActivity1(String name)
    {
    	Log.e(tag, "StartActivity1"+name);
    }
}
