package Fly;
import java.io.File;

import android.os.Bundle;
import android.util.Log;

import com.iflytek.cloud.Setting;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;

import Fly.util.Version;

import com.example.t1.UnityTestActivity;

public class FlyTTS {

	private String mText = "hello, what can I do for you?"; 
	
    // 璇煶鍚堟垚瀵硅薄 
    private SpeechSynthesizer mTts;
    private String VOICE_NAME = "henry";
    private String SPEED = "50";
    private String PITCH = "50";
    private String VOLUME = "50";
    private String TTS_AUDIO_PATH = "./sdcard/iflytek_tts.pcm";
    //褰撳墠瑕佹樉绀虹殑鏂囨湰
    private String mCurText = "";
    //鏇存柊鏂囨湰鐨勬墽琛屽璞�
	private TextRunnable mTextRunnable = new TextRunnable(); 
	private boolean isComplete;
	UnityTestActivity context = null;
	SpeechRecognizer mIat;
	
    public FlyTTS(UnityTestActivity ct) 
    {
    	this.context = ct;
    	// Setting.setShowLog( true );
    	SpeechUtility.createUtility(context, "appid=" + Version.getAppid());
	    // 鍒濆鍖栧悎鎴愬璞�
	    mTts = SpeechSynthesizer.createSynthesizer(context, null);
		// 璁剧疆鍙戦煶浜轰负灏忕嚂
		mTts.setParameter(SpeechConstant.VOICE_NAME, VOICE_NAME);
		// 璁剧疆鏈楄閫熷害涓�50
		mTts.setParameter(SpeechConstant.SPEED, SPEED);
		// 璁剧疆璇皟锛岃寖鍥�0~100
		mTts.setParameter(SpeechConstant.PITCH, "80");
		// 璁剧疆闊抽噺锛岃寖鍥�0~100
		mTts.setParameter(SpeechConstant.VOLUME, VOLUME);
		mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
		// 璁剧疆鍚堟垚闊抽淇濆瓨浣嶇疆锛堝彲鑷畾涔変繚瀛樹綅缃級锛屼繚瀛樺湪鈥�./iflytek.pcm鈥�
		// 濡傛灉涓嶉渶瑕佷繚瀛樺悎鎴愰煶棰戯紝娉ㄩ噴璇ヨ浠ｇ爜
		mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, TTS_AUDIO_PATH);
		
		// 鍚堟垚鏂囨湰涓篢EXT_CONTENT鐨勫彞瀛愶紝璁剧疆鐩戝惉鍣ㄤ负mSynListener 
		//mText = resultArea.getText().trim();
		isComplete = true;
		
		//2.设置听写参数，详见《MSC Reference Manual》SpeechConstant类

		String ENG_TYPE = SpeechConstant.TYPE_CLOUD;
		String DOMAIN = "iat";
		String LANGUAGE = "en_us";	//zh_cn
		String ASR_AUDIO_PATH = "./sdcard/iflytek_iat.pcm";
		String VAD_EOS = "1800";
		String VAD_BOS = "3000";
		String RESULT_TYPE = "plain";

		mIat= SpeechRecognizer.createRecognizer(context, null);
		mIat.setParameter(SpeechConstant.ENGINE_TYPE, ENG_TYPE);
		mIat.setParameter(SpeechConstant.DOMAIN, DOMAIN);
		mIat.setParameter(SpeechConstant.LANGUAGE, LANGUAGE);
		//mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, ASR_AUDIO_PATH);
		//mIat.setParameter(SpeechConstant.VAD_EOS, VAD_EOS);
		//mIat.setParameter(SpeechConstant.VAD_BOS, VAD_BOS);
		mIat.setParameter(SpeechConstant.VAD_ENABLE, "0");
		mIat.setParameter(SpeechConstant.RESULT_TYPE, RESULT_TYPE);
		
		//3.开始听写
		mIat.startListening(mRecoListener);
	}
    private RecognizerListener mRecoListener = new RecognizerListener(){
    	//听写结果回调接口(返回Json格式结果，用户可参见附录13.1)；
    	//一般情况下会通过onResults接口多次返回结果，完整的识别内容是多次结果的累加；
    	//关于解析Json的代码可参见Demo中JsonParser类；
    	//isLast等于true时会话结束。
    	public void onResult(RecognizerResult results, boolean isLast) {
    		if (results == null) {
    			Log.e(context.tag, "No results.");
    			return;
    		}
    		String res = results.getResultString();
    		Log.e(context.tag, "result:{" + res+"}"+res.length());
    		if (res.contains("Hello")) {
    			startSpeaking("Hello! What can I do for you?");
    		}
    		if (res.contains("Fuck you")) {
    			startSpeaking("Ass we can!");
    		}
    		if (res.contains("Move") || res.contains("move")) {
    			startSpeaking("ok!");
    		}
    		//word2 = 
    		if (results.getResultString() == "put the {1} {2}") {
    			
    		}
    		
    		if (results.getResultString() == "Fuck you.") {
    			startSpeaking("ass we can!");
    		}
	    	Log.e(context.tag, "is listening: " + mIat.isListening());
	    	if (!mIat.isListening()) {
    			mIat.startListening(mRecoListener);
	    	}
    	}
    	//会话发生错误回调接口
    	public void onError(SpeechError error) {
	    	//打印错误码描述
	    	Log.e(context.tag, "error:" + error.getPlainDescription(true));
	    	Log.e(context.tag, "is listening: " + mIat.isListening());
	    	if (!mIat.isListening()) {
    			mIat.startListening(mRecoListener);
	    	}
    	}
    	//开始录音
    	public void onBeginOfSpeech() {
    		updateText("onBeginOfSpeech");
    	}
    	//volume音量值0~30，data音频数据
    	public void onVolumeChanged(int volume, byte[] data){}
    	//结束录音
    	public void onEndOfSpeech() {
    		updateText("onEndOfSpeech");
	    	Log.e(context.tag, "is listening: " + mIat.isListening());
    		mIat.startListening(mRecoListener);
    	}
    	//扩展用接口
    	public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {}
    };
    
    public boolean isComplete() { return isComplete; }
    
    public void startSpeaking(String textToSpeak)
    {
    	isComplete = false;
		mTts.startSpeaking(textToSpeak, mSynListener);
		updateText("startSpeaking "+textToSpeak);
    }
    private SynthesizerListener mSynListener = new SynthesizerListener() {
    	@Override
    	public void onSpeakBegin() {
    		updateText("onSpeakBegin");
  		}

  		@Override
  		public void onBufferProgress(int progress, int beginPos, int endPos, String info)
  		{	updateText("--onBufferProgress--progress:" + progress
  					+ ",beginPos:" + beginPos + ",endPos:" + endPos);
  		}

  		@Override
  		public void onSpeakPaused() {
  		}

  		@Override
  		public void onSpeakResumed() {
  		}

  		@Override
  		public void onSpeakProgress(int progress, int beginPos, int endPos) 
  		{	updateText("onSpeakProgress enter progress:" + progress
  					+ ",beginPos:" + beginPos + ",endPos:" + endPos);
  			//updateText( mText.substring( beginPos, endPos+1 ) );
  			//DebugLog.Log( "onSpeakProgress leave" );
  		}

  		@Override
  		public void onCompleted(SpeechError error) 
  		{	updateText( "onCompleted enter" );
  			String text = mText;
  			if (null != error)
  			{	//DebugLog.Log("onCompleted Code锛�" + error.getErrorCode());
  				text = error.getErrorDescription();
  			}
  			updateText( text );
  			isComplete = true;
  			//DebugLog.Log( "onCompleted leave" );
  		}

		@Override
		public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
			// TODO Auto-generated method stub
			
		}
  	};

  	private class TextRunnable implements Runnable {
  		@Override
  		public void run()
  		{	//resultArea.setText( mCurText );
  		}	//end of function run
  	}	//end of class TextRunnable
  	
  	private void updateText( final String text ) 
  	{	//this.mCurText = text;
  		//SwingUtilities.invokeLater( mTextRunnable );
  		Log.e(context.tag, text);
  	}
}
