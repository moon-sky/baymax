package com.dreamfac.xiaobin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dreamfac.baymax.R;
import com.dreamfac.xiaobin.mail.MailUtil;

public class FeedBackActivity extends Activity {
@Override
protected void onCreate(Bundle savedInstanceState) {

	super.onCreate(savedInstanceState);
	   // Setup the window
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
	setContentView(R.layout.dialog_feedback);
	final EditText et_content=(EditText) findViewById(R.id.et_content);
	final EditText et_name=(EditText)findViewById(R.id.et_name);
	final EditText et_qq=(EditText)findViewById(R.id.et_qq);
	Button btn_send=(Button)findViewById(R.id.btn_send);
	btn_send.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			String content=et_content.getText().toString();
			String name=et_name.getText().toString();
			String qq=et_qq.getText().toString();
			if(name==null||name.length()<=0){
				Toast.makeText(FeedBackActivity.this, "名字不能为空", Toast.LENGTH_SHORT).show();
				return;
			}
			if(qq==null||qq.length()<=0){
				Toast.makeText(FeedBackActivity.this, "QQ号不能为空", Toast.LENGTH_SHORT).show();
				return;
			}
				
			
			if(content!=null&&content.length()>0){
				new SendMail(name, qq, content).start();
				finish();
			}else{
				Toast.makeText(FeedBackActivity.this, "反馈内容不能为空", Toast.LENGTH_SHORT).show();
				return;
			}
			
		}
	});
}
class SendMail extends Thread{
	String user;
	String qq;
	String content;
	public SendMail(String user,String qq,String content){
		this.user=user;
		this.qq=qq;
		this.content=content;
	}
	@Override
	public void run() {
		MailUtil.sendEmail(user, qq, content);
		super.run();
	}
}
}
