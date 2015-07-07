package com.clover.ui.frgms;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.clover.R;
import com.clover.net.BmobRequest;
import com.clover.ui.AnniversaryActivity;
import com.clover.ui.HealthActivity;
import com.clover.utils.CloverApplication;
import com.clover.utils.Config;
import com.clover.utils.TypegifView;

import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.im.BmobChatManager;

public class MainPage extends Fragment  implements View.OnClickListener {

    private Button btn_anniversary;
    private Button btn_health;
    private Button btn_sleepReminder;
    private Button btn_eatReminder;
    private Button btn_gameReminder;
    private Button btn_shopReminder;
    private Button btn_missReminder;
    private Button btn_apologizeReminder;
    private Button btn_boringReminder;
    private Button btn_doReminder;
    private Button btn_kissReminder;
    private Button btn_hugReminder;
    private Button btn_miaoReminder;
    private Button btn_wangReminder;
    private Button btn_getupReminder;
    private LinearLayout lv_sleepReminderLayout;
    private CloverApplication application;
    private Activity activity;
    BmobChatManager chatManager;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_page, container, false);

        initView(view);
        registerBoradcastReceiver();
		return view;
	}

    private void initView(View view){
        activity = getActivity();
        application = (CloverApplication) activity.getApplication();
        chatManager = BmobChatManager.getInstance(activity);
        btn_anniversary = (Button)view.findViewById(R.id.anniversary);
        btn_health = (Button)view.findViewById(R.id.health);
        btn_sleepReminder = (Button)view.findViewById(R.id.SleepReminder);
        btn_eatReminder = (Button)view.findViewById(R.id.EatReminder);
        btn_gameReminder = (Button)view.findViewById(R.id.GameReminder);
        btn_shopReminder = (Button)view.findViewById(R.id.health);
        btn_missReminder = (Button)view.findViewById(R.id.ShopReminder);
        btn_apologizeReminder = (Button)view.findViewById(R.id.ApologizeReminder);
        btn_boringReminder = (Button)view.findViewById(R.id.BoringReminder);
        btn_doReminder = (Button)view.findViewById(R.id.DoReminder);
        btn_kissReminder = (Button)view.findViewById(R.id.KissReminder);
        btn_hugReminder = (Button)view.findViewById(R.id.HugReminder);
        btn_miaoReminder = (Button)view.findViewById(R.id.MiaoReminder);
        btn_wangReminder = (Button)view.findViewById(R.id.WangReminder);
        btn_getupReminder = (Button)view.findViewById(R.id.getupReminder);
        lv_sleepReminderLayout = (LinearLayout)view.findViewById(R.id.sleep_reminder_layout);


        btn_anniversary.setOnClickListener(this);
        btn_health.setOnClickListener(this);
        btn_sleepReminder.setOnClickListener(this);
        btn_eatReminder.setOnClickListener(this);
        btn_gameReminder.setOnClickListener(this);
        btn_shopReminder.setOnClickListener(this);
        btn_missReminder.setOnClickListener(this);
        btn_apologizeReminder.setOnClickListener(this);
        btn_boringReminder.setOnClickListener(this);
        btn_doReminder.setOnClickListener(this);
        btn_kissReminder.setOnClickListener(this);
        btn_hugReminder.setOnClickListener(this);
        btn_miaoReminder.setOnClickListener(this);
        btn_wangReminder.setOnClickListener(this);
        btn_getupReminder.setOnClickListener(this);

    }


    String tag;
    String msg;
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.anniversary:
                intent = new Intent(activity,AnniversaryActivity.class);
                startActivity(intent);
                break;
            case R.id.health:
                intent = new Intent(activity,HealthActivity.class);
                startActivity(intent);
                break;
            case R.id.SleepReminder:
                tag = "SLEEP";
                msg = getResources().getString(R.string.sleep_msg);
                pushMessageToLover(msg,tag);
                break;
            case R.id.getupReminder://发送闹钟，这个在被隐藏的布局中
                tag = "GETUP";
                msg = getResources().getString(R.string.getup_mas);
                new AlertDialog.Builder(activity)
                        .setTitle(R.string.kindreminder)
                        .setMessage(R.string.kindreminder)
                        .setNegativeButton(R.string.cancel,null)
                        .setPositiveButton(R.string.makesure, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                pushMessageToLover(msg,tag);
                            }
                        })
                        .show();
                break;
            case R.id.EatReminder:
                msg = getResources().getString(R.string.eat_msg);
                tag = "EAT";
                pushMessageToLover(msg, tag);
                break;
            case R.id.GameReminder:
                msg = getResources().getString(R.string.sleep_msg);
                tag = "GAME";
                pushMessageToLover(msg,tag);
                break;
            case R.id.ShopReminder:

                msg = getResources().getString(R.string.shop_msg);
                tag = "SHOP";
                pushMessageToLover(msg,tag);
                break;
            case R.id.MissReminder:
                tag = "MISS";
                msg = getResources().getString(R.string.miss_msg);
                pushMessageToLover(msg,tag);
                break;
            case R.id.ApologizeReminder:
                tag = "APOLOGIZE";
                msg = getResources().getString(R.string.apologize_msg);
                pushMessageToLover(msg,tag);
                break;
            case R.id.BoringReminder:
                tag = "BORING";
                msg = getResources().getString(R.string.boring_msg);
                pushMessageToLover(msg,tag);
                break;
            case R.id.DoReminder:
                msg = getResources().getString(R.string.do_msg);
                tag = "DO";
                pushMessageToLover(msg,tag);
                break;
            case R.id.KissReminder:
                tag = "KISS";
                msg = getResources().getString(R.string.kiss_msg);
                pushMessageToLover(msg,tag);
                TypegifView testgv = new TypegifView(activity);
                testgv.setSrc(R.raw.lion);
                //final Dialog myDialog2 = CreatMyDialog(testgv);
                //myDialog2.show();
                break;
            case R.id.HugReminder:
                msg = getResources().getString(R.string.hug_msg);
                tag = "HUG";
                pushMessageToLover(msg,tag);
                break;
            case R.id.MiaoReminder:
                tag = "MIAO";
                msg = getResources().getString(R.string.miao_msg);
                pushMessageToLover(msg,tag);
                break;
            case R.id.WangReminder:
                tag = "WANG";
                msg = getResources().getString(R.string.wang_msg);
                pushMessageToLover(msg,tag);
                break;
        }
    }


    public void pushMessageToLover(String msg, String tag){
        if(application.getOne_user()!=null) {
            BmobRequest.pushMessageToLover(msg, tag, activity, application.getOne_user().getObjectId(), application.getOne_user(), chatManager);
        }else {
            Toast.makeText(activity, "请添加恋人",Toast.LENGTH_LONG).show();
        }
    }

    public Dialog CreatMyDialog(int ResId){
        Dialog myDialog = new Dialog(activity);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(ResId);
        Window dialogWindow = myDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        WindowManager m = activity.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 0.65); // 宽度设置为屏幕的0.65
        dialogWindow.setAttributes(p);
        myDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        return myDialog;
    }

    public Dialog CreatMyDialog(View ResId){
        Dialog myDialog = new Dialog(activity);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(ResId);
        Window dialogWindow = myDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        WindowManager m = activity.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 0.65); // 宽度设置为屏幕的0.65
        dialogWindow.setAttributes(p);
        myDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        return myDialog;
    }

    /**
     * 广播接收，对方睡眠提醒
     */
    private BroadcastReceiver mainReminderReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            int extra = intent.getExtras().getInt("key");
            switch(extra){
                case 1:
                    lv_sleepReminderLayout.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    lv_sleepReminderLayout.setVisibility(View.GONE);
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    final Dialog missDialog = CreatMyDialog(R.layout.dialog_miss_layout);
                    missDialog.show();
                    TimerTask missTask = new TimerTask() {
                        @Override
                        public void run() {
                            missDialog.dismiss();
                        }
                    };
                    new Timer().schedule(missTask,1000);
                    break;
                case 7:
                    final Dialog apologizeDialog = CreatMyDialog(R.layout.dialog_miss_layout);
                    apologizeDialog.show();
                    TimerTask apologizeTask = new TimerTask() {
                        @Override
                        public void run() {
                            apologizeDialog.dismiss();
                        }
                    };
                    new Timer().schedule(apologizeTask,1000);
                    break;
                case 8:
                    final Dialog boringDialog = CreatMyDialog(R.layout.dialog_miss_layout);
                    boringDialog.show();
                    TimerTask boringTask = new TimerTask() {
                        @Override
                        public void run() {
                            boringDialog.dismiss();
                        }
                    };
                    new Timer().schedule(boringTask,1000);
                    break;
                case 9:
                    final Dialog doDialog = CreatMyDialog(R.layout.dialog_miss_layout);
                    doDialog.show();
                    TimerTask doTask = new TimerTask() {
                        @Override
                        public void run() {
                            doDialog.dismiss();
                        }
                    };
                    new Timer().schedule(doTask,1000);
                    break;
                case 10:
                    break;
                case 11:
                    break;
                case 12:
                    break;
                case 13:
                    break;
            }
            abortBroadcast();
        }
    };
    /**
     * 注册该广播接收
     */
    public void registerBoradcastReceiver(){
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(Config.SLEEP_ACTION);
        //注册广播
        activity.registerReceiver(mainReminderReceiver, myIntentFilter);
    }

}
