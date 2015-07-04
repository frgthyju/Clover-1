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
import com.clover.utils.Config;

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
    String message;

    @SuppressWarnings("deprecation")
    @Override
    public void onReceive(final Context context, Intent intent) {
        String json = intent.getStringExtra("msg");
        try {
            JSONObject jsonObject = new JSONObject(json);
            String ex = jsonObject.getString("ex");
            if(ex.equals("chat")){
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

                int extra = 0;
                if (ex.equals("SLEEP")) {
                    tag = Config.SLEEP_ACTION;
                    extra = 1;
                    message = context.getResources().getString(R.string.sleep_msg);
                } else if (ex.equals("GETUP")) {
                    tag = Config.SLEEP_ACTION;
                    extra = 2;
                    message = context.getResources().getString(R.string.getup_mas);
                } else if (ex.equals("EAT")) {
                    tag = Config.SLEEP_ACTION;
                    extra = 3;
                    message = context.getResources().getString(R.string.eat_msg);
                } else if (ex.equals("GAME")) {
                    tag = Config.SLEEP_ACTION;
                    extra = 4;
                    message = context.getResources().getString(R.string.game_msg);
                } else if (ex.equals("SHOP")) {
                    tag = Config.SLEEP_ACTION;
                    extra = 5;
                    message = context.getResources().getString(R.string.shop_msg);
                } else if (ex.equals("MISS")) {
                    tag = Config.SLEEP_ACTION;
                    extra = 6;
                    message = context.getResources().getString(R.string.miss_msg);
                } else if (ex.equals("APOLOGIZE")) {
                    tag = Config.SLEEP_ACTION;
                    extra = 7;
                    message = context.getResources().getString(R.string.apologize_msg);
                } else if (ex.equals("BORING")) {
                    tag = Config.SLEEP_ACTION;
                    extra = 8;
                    message = context.getResources().getString(R.string.boring_msg);
                } else if (ex.equals("DO")) {
                    tag = Config.SLEEP_ACTION;
                    extra = 9;
                    message = context.getResources().getString(R.string.do_msg);
                } else if (ex.equals("KISS")) {
                    tag = Config.SLEEP_ACTION;
                    extra = 10;
                    message = context.getResources().getString(R.string.kiss_msg);
                } else if (ex.equals("HUG")) {
                    tag = Config.SLEEP_ACTION;
                    extra = 11;
                    message = context.getResources().getString(R.string.hug_msg);
                } else if (ex.equals("MIAO")) {
                    tag = Config.SLEEP_ACTION;
                    extra = 12;
                    message = context.getResources().getString(R.string.miao_msg);
                } else if (ex.equals("WANG")) {
                    tag = Config.SLEEP_ACTION;
                    extra = 13;
                    message = context.getResources().getString(R.string.wang_msg);
                } else if (ex.equals("MENSES_COME")) {
                    tag = Config.MENSES_COME_ACTION;
                    extra = 1;
                    message = context.getResources().getString(R.string.menses_coming_msg);
                } else if (ex.equals("MENSES_GONE")) {
                    tag = Config.MENSES_COME_ACTION;
                    extra = 2;
                    message = context.getResources().getString(R.string.menses_going_msg);
                } else if (ex.equals("DISEASE_COME")) {
                    tag = Config.DISEASE_COME_ACTION;
                    extra = 1;
                    message = context.getResources().getString(R.string.disease_come_msg);
                } else if (ex.equals("DISEASE_GONE")) {
                    tag = Config.DISEASE_COME_ACTION;
                    extra = 2;
                    message = context.getResources().getString(R.string.disease_gone_msg);
                } else if (ex.equals("DRUG")) {
                    tag = Config.DISEASE_COME_ACTION;
                    extra = 3;
                    message = context.getResources().getString(R.string.eatdrug);
                }
                Intent reminder = new Intent(tag);
                reminder.putExtra("key",extra);
                context.sendBroadcast(reminder);

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