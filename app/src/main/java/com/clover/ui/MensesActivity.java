package com.clover.ui;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.clover.R;
import com.clover.entities.User;
import com.clover.net.BmobRequest;
import com.clover.utils.CloverApplication;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;


public class MensesActivity extends BaseActivity {

    private Button btn_sendMensesMessage;
    boolean tag = true;
    private CloverApplication application;
    String msg;
    String MENSES_COME_ACTION = "MENSES_COME";
    String MENSES_GONE_ACTION = "MENSES_GONE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menses);

        application = (CloverApplication)getApplication();
        btn_sendMensesMessage = (Button)findViewById(R.id.sendmensesmessage);
        btn_sendMensesMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tag) {
                    msg = getResources().getString(R.string.menses_tell_msg);
                    BmobRequest.pushMessageToLover(msg, "MENSES_COME", MensesActivity.this, application.getM_user().getObjectId(), application.getM_user(), chatManager);
                    tag=false;
                    btn_sendMensesMessage.setText(R.string.menses_gone_msg);
                }else {
                    msg = getResources().getString(R.string.menses_gone_msg);
                    BmobRequest.pushMessageToLover(msg, "MENSES_GONE", MensesActivity.this, application.getM_user().getObjectId(), application.getM_user(), chatManager);
                    tag=true;
                    btn_sendMensesMessage.setText(R.string.menses_tell_msg);
                }
            }
        });
    }
    /**
     * 广播接收，对方姨妈提醒
     */
    private BroadcastReceiver mensesReminderReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(MENSES_COME_ACTION)){

            }else if(action.equals(MENSES_GONE_ACTION)){

            }
            //
            abortBroadcast();
        }
    };
    /**
     * 注册该广播接收
     */
    public void registerBoradcastReceiver(){
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(MENSES_COME_ACTION);
        myIntentFilter.addAction(MENSES_GONE_ACTION);
        //注册广播
        registerReceiver(mensesReminderReceiver, myIntentFilter);
    }

    /**
     * 返回
     */
    public void back(View view){
        this.finish();
    }
}
