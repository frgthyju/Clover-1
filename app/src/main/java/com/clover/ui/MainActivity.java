package com.clover.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.clover.R;
import com.clover.entities.User;
import com.clover.ui.frgms.GamePage;
import com.clover.ui.frgms.MainPage;
import com.clover.ui.frgms.UserPage;
import com.clover.utils.CloverApplication;
import com.clover.utils.Config;
import com.clover.utils.TypegifView;
import com.clover.net.BmobRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.im.BmobChatManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class MainActivity extends BaseActivity {
    private ViewPager mPager;//页卡内容
    private ArrayList<Fragment> fragments; // Tab页面列表
    private ImageView cursor;// 动画图片
    private TextView t1, t2, t3;// 页卡头标
    private TextView tv_chat;
    private int offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private int bmpW;// 动画图片宽度
    private Button btn_Anniversary;//纪念日按钮
   // private LayoutInflater inflater;
    private LinearLayout sleepReminderLayout;
    private CloverApplication application;

    String targetId = "65fc7dff72";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if(userManager.getCurrentUser() == null){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            application = (CloverApplication)getApplication();
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.activity_main);

            chatManager = BmobChatManager.getInstance(this);
            InitTextView();
            InitImageView();
            InitViewPager();

            initApplication();
            registerBoradcastReceiver();
        }






    }

    /**
     * 初始化ViewPager
     */
    private void InitViewPager() {
        mPager = (ViewPager) findViewById(R.id.vPager);
        fragments = new ArrayList<Fragment>();
        Fragment fragment1 = new MainPage();
        Fragment fragment2 = new GamePage();
        Fragment fragment3 = new UserPage();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), fragments));
        mPager.setCurrentItem(0);
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    /**
     * 初始化头标
     */
    private void InitTextView() {
        t1 = (TextView) findViewById(R.id.text1);
        t2 = (TextView) findViewById(R.id.text2);
        t3 = (TextView) findViewById(R.id.text3);
        tv_chat = (TextView) findViewById(R.id.chart_bar);
        btn_Anniversary = (Button)findViewById(R.id.anniversary);

        t1.setOnClickListener(new MyOnClickListener(0));
        t2.setOnClickListener(new MyOnClickListener(1));
        t3.setOnClickListener(new MyOnClickListener(2));
        tv_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(application.getM_user() != null){
                    Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                    startActivity(intent);
                }else{
                    initApplication();
                }

            }
        });
    }

    /**
     * 头标点击监听
     */
    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mPager.setCurrentItem(index);
        }
    };

    /**
     * ViewPager适配器
     */
    public class MyPagerAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> list;
        public MyPagerAdapter(FragmentManager fm,ArrayList<Fragment> list) {
            super(fm);
            this.list = list;

        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Fragment getItem(int arg0) {
            return list.get(arg0);
        }


    }

    /**
     * 初始化动画
     */
    private void InitImageView() {
        cursor = (ImageView) findViewById(R.id.cursor);
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.a)
                .getWidth();// 获取图片宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        offset = (screenW / 3 - bmpW) / 2;// 计算偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        cursor.setImageMatrix(matrix);// 设置动画初始位置
    }

    /**
     * 页卡切换监听
     */
    public class MyOnPageChangeListener implements OnPageChangeListener {

        int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
        int two = one * 2;// 页卡1 -> 页卡3 偏移量

        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
                case 0:
                    if (currIndex == 1) {
                        animation = new TranslateAnimation(one, 0, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, 0, 0, 0);
                    }
                    break;
                case 1:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, one, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, one, 0, 0);
                    }
                    break;
                case 2:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, two, 0, 0);
                    } else if (currIndex == 1) {
                        animation = new TranslateAnimation(one, two, 0, 0);
                    }
                    break;
            }
            currIndex = arg0;
            animation.setFillAfter(true);// True:图片停在动画结束位置
            animation.setDuration(300);
            cursor.startAnimation(animation);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }


    /**
     * 主页面函数
     * Anniversary打开纪念日界面
     * Health打开健康界面
     */
    String tag;
    String msg;
    public void onMainClick(View view){

        Intent intent;
        switch (view.getId()){
            case R.id.anniversary:
                intent = new Intent(this,AnniversaryActivity.class);
                startActivity(intent);
                break;
            case R.id.health:
                intent = new Intent(this,HealthActivity.class);
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
                new AlertDialog.Builder(MainActivity.this)
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
                TypegifView testgv = new TypegifView(this);
                testgv.setSrc(R.raw.lion);
                final Dialog myDialog2 = CreatMyDialog(testgv);
                myDialog2.show();
                /*
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        myDialog2.hide();
                    }
                };
                new Timer().schedule(task,1000);
                */
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

    /**
     * 个人资料页面点击函数
     * EditUserInfo打开编辑个人资料的页面
     * MyLover打开添加情侣的界面
     * Setting打开设置界面
     * Quit退出当前账号
     */
    public void onUserClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.editUserInfo:
                intent = new Intent(this, UserInfoUpdateActivity.class);
                startActivity(intent);
                break;
            case R.id.mylover:
                break;
            case R.id.setting:
                break;
            case R.id.quit:
                userManager.logout();
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
    /**
     * 根据id查询用户
     */
    /*
    public User queryUserById(String objectId) {

        BmobQuery<User> query = new BmobQuery<User>();query.addWhereEqualTo("objectId", targetId);
        query.findObjects(this, new FindListener<User>() {

            @Override
            public void onSuccess(List<User> users) {
                if(users!=null&&(users.size()>0)){
                    application.getM_user() = users.get(0);
                }
            }
            @Override
            public void onError(int arg0, String arg1) {
            }
        });
        return user;
    }
    */

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
                    sleepReminderLayout = (LinearLayout)findViewById(R.id.sleep_reminder_layout);
                    sleepReminderLayout.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    sleepReminderLayout = (LinearLayout)findViewById(R.id.sleep_reminder_layout);
                    sleepReminderLayout.setVisibility(View.GONE);
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
            /*
            if(action.equals(Config.SLEEP_ACTION)){
                sleepReminderLayout = (LinearLayout)findViewById(R.id.sleep_reminder_layout);
                sleepReminderLayout.setVisibility(View.VISIBLE);
            }else if(action.equals(Config.MISS_ACTION)){
                final Dialog myDialog = CreatMyDialog(R.layout.dialog_miss_layout);
                myDialog.show();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        myDialog.dismiss();
                    }
                };
                new Timer().schedule(task,1000);
            }else if(action.equals(Config.APOLOGIZE_ACTION)){
                final Dialog myDialog = CreatMyDialog(R.layout.dialog_miss_layout);
                myDialog.show();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        myDialog.dismiss();
                    }
                };
                new Timer().schedule(task,1000);
            }else if(action.equals(Config.BORING_ACTION)){
                final Dialog myDialog = CreatMyDialog(R.layout.dialog_miss_layout);
                myDialog.show();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        myDialog.dismiss();
                    }
                };
                new Timer().schedule(task,1000);
            }else if(action.equals(Config.DO_ACTION)){
                final Dialog myDialog = CreatMyDialog(R.layout.dialog_miss_layout);
                myDialog.show();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        myDialog.dismiss();
                    }
                };
                new Timer().schedule(task,1000);
            }
            */
            //终结广播
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
        registerReceiver(mainReminderReceiver, myIntentFilter);
    }

    public Dialog CreatMyDialog(int ResId){
        Dialog myDialog = new Dialog(this);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(ResId);
        /*
         * 获取圣诞框的窗口对象及参数对象以修改对话框的布局设置,
         * 可以直接调用getWindow(),表示获得这个Activity的Window
         * 对象,这样这可以以同样的方式改变这个Activity的属性.
         */
        Window dialogWindow = myDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        /*
         * 将对话框的大小按屏幕大小的百分比设置
         */
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 0.65); // 宽度设置为屏幕的0.65
        dialogWindow.setAttributes(p);
        return myDialog;
    }

    public Dialog CreatMyDialog(View ResId){
        Dialog myDialog = new Dialog(this);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(ResId);
        /*
         * 获取圣诞框的窗口对象及参数对象以修改对话框的布局设置,
         * 可以直接调用getWindow(),表示获得这个Activity的Window
         * 对象,这样这可以以同样的方式改变这个Activity的属性.
         */
        Window dialogWindow = myDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        /*
         * 将对话框的大小按屏幕大小的百分比设置
         */
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 0.65); // 宽度设置为屏幕的0.65
        dialogWindow.setAttributes(p);
        return myDialog;
    }

    /**
     * 消息推送函数
     */
    public void pushMessageToLover(String msg, String tag){
        User user = application.getM_user();
        BmobRequest.pushMessageToLover(msg,tag,this,application.getM_user().getObjectId(),application.getM_user(),chatManager);
    }

    private void initApplication(){

        BmobQuery<User> query = new BmobQuery<>();
        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_THEN_NETWORK);
        query.addWhereEqualTo("username","1238");
        query.findObjects(this, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {

                if(list.size()<=0){
                    return;
                }else{
                    application.setM_user(list.get(0));
                }
                ShowLog("获取application对象成功");
            }

            @Override
            public void onError(int i, String s) {
                ShowToast("获取application对象失败");
                ShowLog("获取application对象失败");
                application.setM_user(null);
            }
        });

    }
}
