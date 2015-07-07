package com.clover.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.clover.R;
import com.clover.net.BmobRequest;

public class DiseaseActivity extends BaseActivity {

    private Button btn_diseaseReminder;
    private Button btn_sendDrugReminder;
    private LinearLayout lv_drugReminder;
    boolean tag = true;
    String msg;
    String DISEASE_COME_ACTION = "DISEASE_COME";
    String DISEASE_GONE_ACTION = "DISEASE_GONE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease);

        initToolbar(getResources().getString(R.string.title_activity_disease),new Intent(this, HealthActivity.class), this);
        btn_diseaseReminder = (Button)findViewById(R.id.diseaseReminder);
        btn_sendDrugReminder = (Button)findViewById(R.id.sendDrugReminder);
        lv_drugReminder = (LinearLayout)findViewById(R.id.DrugReminderLayout);
        btn_diseaseReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tag) {
                    msg = getResources().getString(R.string.disease_come_msg);
                    BmobRequest.pushMessageToLover(msg, "DISEASE_COME", DiseaseActivity.this, application.getOne_user().getObjectId(), application.getOne_user(), chatManager);
                    tag=false;
                    btn_diseaseReminder.setText(R.string.disease_gone_msg);
                }else {
                    msg = getResources().getString(R.string.disease_gone_msg);
                    BmobRequest.pushMessageToLover(msg, "DISEASE_GONE", DiseaseActivity.this, application.getOne_user().getObjectId(), application.getOne_user(), chatManager);
                    tag=true;
                    btn_diseaseReminder.setText(R.string.disease_come_msg);
                }
            }
        });
        btn_sendDrugReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg = getResources().getString(R.string.eatdrug);
                BmobRequest.pushMessageToLover(msg, "DRUG", DiseaseActivity.this, application.getOne_user().getObjectId(), application.getOne_user(), chatManager);
            }
        });
        registerBoradcastReceiver();
    }

    /**
     * 广播接收，对方健康提醒
     */
    private BroadcastReceiver diseaseReminderReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            int extra = intent.getExtras().getInt("key");
            switch(extra){
                case 1:
                    lv_drugReminder.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    lv_drugReminder.setVisibility(View.GONE);
                    break;
            }
            //终结广播
            abortBroadcast();
        }
    };
    /**
     * 注册该广播接收
     */
    public void registerBoradcastReceiver(){
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(DISEASE_COME_ACTION);
        //注册广播
        registerReceiver(diseaseReminderReceiver, myIntentFilter);
    }
}
