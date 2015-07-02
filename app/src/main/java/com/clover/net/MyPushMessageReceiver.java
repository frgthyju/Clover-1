package com.clover.net;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.clover.R;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.im.BmobChatManager;
import cn.bmob.im.bean.BmobMsg;
import cn.bmob.im.inteface.OnReceiveListener;

/**
 * Created by dan on 2015/6/26.
 */
public class MyPushMessageReceiver extends BroadcastReceiver {

    String tag = null;
    String SLEEP_ACTION = "SLEEP_ACTION";
    String GETUP_ACTION = "GETUP_ACTION";
    String EAT_ACTION = "EAT_ACTION";
    String GAME_ACTION = "GAME_ACTION";
    String SHOP_ACTION = "SHOP_ACTION";
    String MISS_ACTION = "MISS_ACTION";
    String APOLOGIZE_ACTION = "APOLOGIZE_ACTION";
    String BORING_ACTION = "BORING_ACTION";
    String DO_ACTION = "DO_ACTION";
    String KISS_ACTION = "KISS_ACTION";
    String HUG_ACTION = "HUG_ACTION";
    String MIAO_ACTION = "MIAO_ACTION";
    String WANG_ACTION = "WANG_ACTION";
    String MENSES_COME_ACTION = "MENSES_COME";
    String MENSES_GONE_ACTION = "MENSES_GONE";
    String DISEASE_COME_ACTION = "DISEASE_COME";
    String DISEASE_GONE_ACTION = "DISEASE_GONE";
    String DRUG_ACTION = "DRUG";
    String message;

    @SuppressWarnings("deprecation")
    @Override
    public void onReceive(final Context context, Intent intent) {
        String json = intent.getStringExtra("msg");
        Toast.makeText(context, json, Toast.LENGTH_LONG).show();
        try {
            JSONObject jsonObject = new JSONObject(json);
            String ex = jsonObject.getString("ex");
            if(ex.equals("ex")){
                BmobChatManager.getInstance(context).createReceiveMsg(json, new OnReceiveListener() {
                    @Override
                    public void onSuccess(BmobMsg bmobMsg) {
                        Intent intent1 = new Intent("NEW_MESSEAGE_COMING");
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("msg", bmobMsg);
                        intent1.putExtra("msg", bundle);
                        context.sendBroadcast(intent1);
                    }

                    @Override
                    public void onFailure(int i, String s) {

                    }
                });
            }else{
                if (json.contains("SLEEP")) {
                    tag = SLEEP_ACTION;
                    message = context.getResources().getString(R.string.sleep_msg);
                } else if (json.contains("GETUP")) {
                    tag = GETUP_ACTION;
                    message = context.getResources().getString(R.string.getup_mas);
                } else if (json.contains("EAT")) {
                    tag = EAT_ACTION;
                    message = context.getResources().getString(R.string.eat_msg);
                } else if (json.contains("GAME")) {
                    tag = GAME_ACTION;
                    message = context.getResources().getString(R.string.game_msg);
                } else if (json.contains("SHOP")) {
                    tag = SHOP_ACTION;
                    message = context.getResources().getString(R.string.shop_msg);
                } else if (json.contains("MISS")) {
                    tag = MISS_ACTION;
                    message = context.getResources().getString(R.string.miss_msg);
                } else if (json.contains("WANG")) {
                    tag = WANG_ACTION;
                    message = context.getResources().getString(R.string.wang_msg);
                } else if (json.contains("APOLOGIZE")) {
                    tag = APOLOGIZE_ACTION;
                    message = context.getResources().getString(R.string.apologize_msg);
                } else if (json.contains("BORING")) {
                    tag = BORING_ACTION;
                    message = context.getResources().getString(R.string.boring_msg);
                } else if (json.contains("DO")) {
                    tag = DO_ACTION;
                    message = context.getResources().getString(R.string.do_msg);
                } else if (json.contains("KISS")) {
                    tag = KISS_ACTION;
                    message = context.getResources().getString(R.string.kiss_msg);
                } else if (json.contains("HUG")) {
                    tag = HUG_ACTION;
                    message = context.getResources().getString(R.string.hug_msg);
                } else if (json.contains("MIAO")) {
                    tag = MIAO_ACTION;
                    message = context.getResources().getString(R.string.miao_msg);
                } else if (json.contains("MENSES_COME")) {
                    tag = MENSES_COME_ACTION;
                    message = context.getResources().getString(R.string.menses_coming_msg);
                } else if (json.contains("MENSES_GONE")) {
                    tag = MENSES_GONE_ACTION;
                    message = context.getResources().getString(R.string.menses_going_msg);
                } else if (json.contains("DISEASE_COME")) {
                    tag = DISEASE_COME_ACTION;
                    message = context.getResources().getString(R.string.disease_come_msg);
                } else if (json.contains("DISEASE_GONE")) {
                    tag = DISEASE_GONE_ACTION;
                    message = context.getResources().getString(R.string.disease_gone_msg);
                } else if (json.contains("DRUG")) {
                    tag = DRUG_ACTION;
                    message = context.getResources().getString(R.string.eatdrug);
                }
                Intent Reminder = new Intent(tag);
                context.sendBroadcast(Reminder);

                // 发送通知
                NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                Notification n = new Notification();
                n.icon = R.mipmap.ic_launcher;
                n.tickerText = "Clover收到消息";
                n.when = System.currentTimeMillis();
                Intent i = new Intent();
                PendingIntent pi = PendingIntent.getActivity(context, 0, i, 0);
                n.setLatestEventInfo(context, "消息", message, pi);
                n.defaults |= Notification.DEFAULT_SOUND;
                n.flags = Notification.FLAG_AUTO_CANCEL;
                nm.notify(1, n);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}