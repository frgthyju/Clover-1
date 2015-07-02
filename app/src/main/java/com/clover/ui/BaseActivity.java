package com.clover.ui;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.clover.entities.User;
import com.clover.utils.CloverApplication;

import cn.bmob.im.BmobChat;
import cn.bmob.im.BmobChatManager;
import cn.bmob.im.BmobUserManager;

public class BaseActivity extends FragmentActivity {

	BmobUserManager userManager;
    BmobChatManager chatManager;
    String APPID = "85e40757e81851d007990f3e103ec5ae";

	protected void onCreate(android.os.Bundle arg0) {
		super.onCreate(arg0);
		userManager = BmobUserManager.getInstance(this);
        BmobChat.getInstance(this).init(APPID);
        chatManager = BmobChatManager.getInstance(this);

	};
	
	Toast mToast;
	public void ShowToast(final String text) {
		if (!TextUtils.isEmpty(text)) {
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (mToast == null) {
						mToast = Toast.makeText(getApplicationContext(), text,
								Toast.LENGTH_SHORT);
					} else {
						mToast.setText(text);
					}
					mToast.show();
				}
			});
			
		}
	}
	
	public void ShowLog(String msg){
		Log.i("zpfang",msg);
	}

}
