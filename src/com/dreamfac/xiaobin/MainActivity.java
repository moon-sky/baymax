package com.dreamfac.xiaobin;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.voicerecognition.android.ui.BaiduASRDigitalDialog;
import com.baidu.voicerecognition.android.ui.DialogRecognitionListener;
import com.dreamfac.baymax.R;
import com.dreamfac.xiaobin.util.Config;
import com.dreamfac.xiaobin.util.Constants;
import com.dreamfac.xiaobin.util.ImageUtil;
import com.feiwo.view.FwBannerManager;
import com.feiwo.view.FwInterstitialManager;
import com.umeng.analytics.MobclickAgent;

public class MainActivity extends Activity implements View.OnClickListener,
		PopupMenu.OnMenuItemClickListener, TextWatcher {
	LinearLayout ll_parent;
	RelativeLayout rl_right_bubble;
	RelativeLayout rl_left_bubble;
	RelativeLayout myAdonContainerView;
	ScrollView sv_content;
	TextView tv_left, tv_right;
	static final String key = "d2e1260339f719110d2b13023bea9b20";
	// static final String key="60a4f78f5391dcde946cc8c5d00f7b52";
	String url = "http://www.tuling123.com/openapi/api?key=";
	static final String feiwo_key = "tyklDR25RmLpe1k9sr1LtMqY";
	private String[] ABC = { "a", "b", "c", "d", "e", "f", "g", "k", "l", "m",
			"n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
			"1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };
	private static final int MSG_LINK_RESULT = 1;
	private static final int MSG_TEXT_RESULT = 0;
	EditText et_content;
	Button btn_submit;
	LayoutInflater inflater;
	private TextView tv_time;
	private ImageView iv_portrait;
	private ImageView iv_face;
	private ImageView iv_more;
	private ImageView iv_speaker;
	private ImageView iv_portrait_title;
	int index;
	RelativeLayout rl_layout;
	PopupMenu pop;

	private int[] img_id = { R.drawable.emoj1, R.drawable.emoj2, R.drawable.emoj3,
			R.drawable.emoj4, R.drawable.emoj5, R.drawable.emoj6,R.drawable.emoj7, R.drawable.emoj8, R.drawable.emoj9 };
	private String[] hints = { "你大爷的", "今天天气", "北京717", "讲个笑话", "郭美美是谁", };
	int request_count = 0;

	private BaiduASRDigitalDialog mDialog = null;

	private DialogRecognitionListener mRecognitionListener;
	String userid = "";
	private int mCurrentTheme = Config.DIALOG_THEME;
	private SoundPool pool;
	private int soundID;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initAd();
		// initYoumi();
		initFeiwo();
		initAudio();
		ll_parent = (LinearLayout) findViewById(R.id.ll_content);
		et_content = (EditText) findViewById(R.id.et_input);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		sv_content = (ScrollView) findViewById(R.id.sv_content);
		inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		addLeftBubble("欢饮你的到来！我的主人");
		pool.play(soundID, 1.0f, 1.0f, 80, 0, 1.0f);
		btn_submit.setOnClickListener(this);
		iv_more = (ImageView) findViewById(R.id.iv_more);
		iv_more.setOnClickListener(this);
		iv_speaker = (ImageView) findViewById(R.id.iv_speaker);
		iv_speaker.setOnClickListener(this);
		rl_layout = (RelativeLayout) findViewById(R.id.rl_title);
		et_content.addTextChangedListener(this);
		userid = getRandomUserId();
		mRecognitionListener = new DialogRecognitionListener() {

			@Override
			public void onResults(Bundle results) {
				ArrayList<String> rs = results != null ? results
						.getStringArrayList(RESULTS_RECOGNITION) : null;
				if (rs != null && rs.size() > 0) {
					et_content.setText(rs.get(0));
				}
			}
		};
	}

	/**
	 * 初始化音频信息
	 */
	private void initAudio() {
		pool=new SoundPool(1, AudioManager.STREAM_MUSIC, 80);
		soundID=pool.load(this, R.raw.baymax, 1);
	
		
	}

	private void initAd() {/*
							 * AppConnect.getInstance(
							 * "984f057f45845a991e31753e853da60c", "default",
							 * this);
							 * AppConnect.getInstance(this).initFunAd(this);
							 * String uriString = "http://www.waps.cn";
							 * AppConnect.getInstance(this).showBrowser(this,
							 * uriString);
							 */
	}

	/*
	 * private void initYoumi() {
	 * AdManager.getInstance(this).init("4b2766e4657d1346", "3938da652141af39",
	 * false); SpotManager.getInstance(this).loadSpotAds(); }
	 */
	private void initFeiwo() {
		// FwInterstitialManager.init(this,feiwo_key);
		FwInterstitialManager.init(this, feiwo_key);
		FwBannerManager.init(this, feiwo_key);
		myAdonContainerView = (RelativeLayout) findViewById(R.id.rl_ad);
		FwBannerManager.setParentView((ViewGroup) myAdonContainerView);
		// FwInterstitialManager.showInterstitial();
	}

	private String getTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"------yyyy-MM-dd HH:mm:ss------");
		Date curDate = new Date(System.currentTimeMillis());
		String time = dateFormat.format(curDate);
		return time;
	}

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			request_count++;
			if (request_count % 10 == 0
					&& myAdonContainerView.getVisibility() != View.VISIBLE)
				myAdonContainerView.setVisibility(View.VISIBLE);
			if (request_count % 10 == 0)
				FwInterstitialManager.showInterstitial();
			switch (msg.what) {
			case MSG_TEXT_RESULT:
				String result = (String) msg.obj;
				if (result != null && result.length() > 0){
					addLeftBubble(result);
					if(result.startsWith("我是")){
						pool.play(soundID, 1.0f, 1.0f, 80, 0, 1.0f);
					}
				}
					
				break;
			case MSG_LINK_RESULT:
				LinkEntity entity = (LinkEntity) msg.obj;
				if (entity != null) {
					String text = entity.getText();
					if (text != null && text.length() > 0) {
						addLeftBubble(text);
					}
					String url = entity.getUrl();
					openUrl(url);
				}
				break;

			default:
				break;
			}

		};

	};

	private void openUrl(String url) {
		try {
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			intent.addCategory("android.intent.category.BROWSABLE");
			startActivity(intent);
		} catch (Exception e) {
		}
	}

	class GetResult extends Thread {
		String input;
		Handler handler;

		public GetResult(String input, Handler handler) {
			this.input = input;
			this.handler = handler;
		}

		@Override
		public void run() {
			getHttpResult(input, handler);
			super.run();
		}
	}

	@Override
	protected void onResume() {
		MobclickAgent.onResume(this);
		super.onResume();
	}

	@Override
	protected void onPause() {
		MobclickAgent.onPause(this);
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		/* SpotManager.getInstance(this).unregisterSceenReceiver(); */
		if (mDialog != null) {
			mDialog.dismiss();
		}
		super.onDestroy();
	}

	private void showRecognizeDialog() {
		mCurrentTheme = Config.DIALOG_THEME;
		if (mDialog != null) {
			mDialog.dismiss();
		}
		Bundle params = new Bundle();
		params.putString(BaiduASRDigitalDialog.PARAM_API_KEY, Constants.API_KEY);
		params.putString(BaiduASRDigitalDialog.PARAM_SECRET_KEY,
				Constants.SECRET_KEY);
		params.putInt(BaiduASRDigitalDialog.PARAM_DIALOG_THEME,
				Config.DIALOG_THEME);
		mDialog = new BaiduASRDigitalDialog(this, params);
		mDialog.setDialogRecognitionListener(mRecognitionListener);
		// }
		mDialog.getParams().putInt(BaiduASRDigitalDialog.PARAM_PROP,
				Config.CURRENT_PROP);
		mDialog.getParams().putString(BaiduASRDigitalDialog.PARAM_LANGUAGE,
				Config.getCurrentLanguage());
		Log.e("DEBUG", "Config.PLAY_START_SOUND = " + Config.PLAY_START_SOUND);
		mDialog.getParams().putBoolean(
				BaiduASRDigitalDialog.PARAM_START_TONE_ENABLE,
				Config.PLAY_START_SOUND);
		mDialog.getParams().putBoolean(
				BaiduASRDigitalDialog.PARAM_END_TONE_ENABLE,
				Config.PLAY_END_SOUND);
		mDialog.getParams().putBoolean(
				BaiduASRDigitalDialog.PARAM_TIPS_TONE_ENABLE,
				Config.DIALOG_TIPS_SOUND);
		mDialog.show();
	}

	public void getHttpResult(String input, Handler handler) {
		HttpGet get = new HttpGet(url + key + "&info=" + input + "&userid="
				+ userid);
		HttpClient client = new DefaultHttpClient();
		try {
			HttpResponse response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				String result;
				result = EntityUtils.toString(response.getEntity());

				JSONObject jsObj = new JSONObject(result);

				int code = jsObj.getInt("code");
				switch (code) {

				case 200000:// 链接类
					LinkEntity entity = new LinkEntity();
					entity.setText(jsObj.getString("text"));
					entity.setUrl(jsObj.getString("url"));
					handler.obtainMessage(MainActivity.MSG_LINK_RESULT, entity)
							.sendToTarget();
					break;
				case 100000:// 文字类
				default:
					if (jsObj.getString("text") != null) {
						handler.obtainMessage(MainActivity.MSG_TEXT_RESULT,
								jsObj.getString("text")).sendToTarget();
					}
					break;
				}

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void addLeftBubble(String text) {
		View leftBubble = inflater.inflate(R.layout.left_bubble, null);
		TextView tv_bubble = (TextView) leftBubble.findViewById(R.id.tv_left);
		tv_time = (TextView) leftBubble.findViewById(R.id.tv_time);
		iv_portrait = (ImageView) leftBubble.findViewById(R.id.iv_portrait);
		iv_portrait_title = (ImageView) findViewById(R.id.iv_portrait_title);
		tv_time.setText(getTime());
		tv_bubble.setText(text);
		ll_parent.addView(leftBubble);
		ll_parent.invalidate();
		ll_parent.getBottom();
		index = (int) (Math.random() * (img_id.length - 1));
		setBackground(iv_portrait);
		setBackground(iv_portrait_title);
		View view = ll_parent.getChildAt(ll_parent.getChildCount() - 1);
		mHandler.postDelayed(new ScrollToPostion(ll_parent.getBottom()), 200);
		
	}

	private void setBackground(ImageView iv_portrait) {

		Bitmap bmp = BitmapFactory
				.decodeResource(getResources(), img_id[index]);
		iv_portrait.setImageBitmap(ImageUtil.getRoundedBitmap(bmp));
		bmp.recycle();
	}

	class ScrollToPostion implements Runnable {

		View mView;
		int bottom;

		public ScrollToPostion(View view) {
			this.mView = view;
		};

		public ScrollToPostion(int bottom) {
			this.bottom = bottom;
		};

		@Override
		public void run() {
			sv_content.smoothScrollBy(0, bottom);

		}

	}

	private void addRightBubble(String text) {

		View leftBubble = inflater.inflate(R.layout.right_bubble, null);
		TextView tv_bubble = (TextView) leftBubble.findViewById(R.id.tv_right);
		tv_bubble.setText(text);
		ll_parent.addView(leftBubble);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_submit:
			addRightBubble(et_content.getText().toString());
			new GetResult(et_content.getText().toString(), mHandler).start();
			et_content.setText("");
			break;
		case R.id.iv_more:
			// TODO 弹出popuwindow
			showPopuWindow(iv_more);
			break;
		case R.id.iv_speaker:
			showRecognizeDialog();
			break;
		default:
			break;
		}
	}

	private void showPopuWindow(View view) {
		 pop= new PopupMenu(this, view);
		pop.getMenuInflater().inflate(R.menu.main, pop.getMenu());
		pop.setOnMenuItemClickListener(this);
		pop.show();
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		switch (item.getItemId()) {
/*		case R.id.action_mode:
			if(item.getTitle().equals(this.getString(R.string.night_mode))){
				sv_content.setBackgroundResource(R.color.white);
				pop.getMenuInflater().inflate(R.menu.main, pop.getMenu());
				pop.setOnMenuItemClickListener(this);
			}
			else{
				sv_content.setBackgroundResource(R.color.dark_blue);
				pop.inflate(R.menu.main_night);
				pop.setOnMenuItemClickListener(this);
			}
			break;*/
		case R.id.action_feedback:
			Intent it = new Intent(MainActivity.this, FeedBackActivity.class);
			startActivity(it);
			break;

		default:
			break;
		}
		return true;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTextChanged(Editable s) {
		if (s.toString().length() <= 0) {
			iv_speaker.setVisibility(View.VISIBLE);
			btn_submit.setVisibility(View.INVISIBLE);
		} else {
			iv_speaker.setVisibility(View.INVISIBLE);
			btn_submit.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 获取随机userId
	 * 
	 * @return
	 */
	private String getRandomUserId() {
		int length = 0;
		StringBuilder result = new StringBuilder();
		while (length == 0 || length > 32) {
			length = (int) (Math.random() * ABC.length);
		}
		for (int i = 0; i < length; i++) {
			int index = (int) (Math.random() * (ABC.length - 1));
			result.append(ABC[index]);
		}
		return result.toString();
	}

}
