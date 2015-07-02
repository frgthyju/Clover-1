package com.clover.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.clover.R;

public class UserInfoUpdateActivity extends BaseActivity{

    private RelativeLayout rv_head;//头像
    private RelativeLayout rv_nick;//昵称
    private RelativeLayout rv_age;//年龄
    private LinearLayout lv_selectWaysForAvator;//选择拍照还是从相册中选择
    private RelativeLayout rv_takePhoto;
    private RelativeLayout rv_chooseFromPhone;
    private ImageView iv_head;//头像
    private TextView tv_nick;//昵称
    private TextView tv_age;//年龄

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_update);

        rv_head = (RelativeLayout)findViewById(R.id.layout_head);
        rv_nick = (RelativeLayout)findViewById(R.id.layout_nick);
        rv_age = (RelativeLayout)findViewById(R.id.layout_age);
        lv_selectWaysForAvator = (LinearLayout)findViewById(R.id.avator_select);
        rv_takePhoto = (RelativeLayout)findViewById(R.id.layout_photo);
        rv_chooseFromPhone = (RelativeLayout)findViewById(R.id.layout_choose);
        iv_head = (ImageView)findViewById(R.id.iv_set_avator);
        tv_nick = (TextView)findViewById(R.id.tv_set_nick);
        tv_age = (TextView)findViewById(R.id.tv_set_age);

        rv_age.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                lv_selectWaysForAvator.setVisibility(View.VISIBLE);
            }
        });
    }


    /**
     * 返回
     */
    public void back(View view){
        this.finish();
    }
}
