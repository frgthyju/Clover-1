package com.clover.ui.frgms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.clover.R;
import com.clover.ui.LoginActivity;
import com.clover.ui.LoverManagerActivity;
import com.clover.ui.SettingActivity;
import com.clover.ui.UserInfoUpdateActivity;
import com.clover.utils.CloverApplication;

import cn.bmob.im.BmobUserManager;

public class UserPage extends Fragment implements OnClickListener{

    private RelativeLayout rl_userinfo_update;
    private RelativeLayout rl_lover_set;
    private RelativeLayout rl_setting;
    private TextView tv_logout;
    private Activity activity;
    private CloverApplication application;
    private TextView tv_loverinfo_vg;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        application = (CloverApplication) getActivity().getApplication();
        View view = inflater.inflate(R.layout.fragment_user_page, container, false);
        initView(view);

		return view;
	}


	private void initView(View view){
        activity = getActivity();
        rl_userinfo_update = (RelativeLayout) view.findViewById(R.id.layout_set_userinfo);
        rl_lover_set = (RelativeLayout) view.findViewById(R.id.layout_lover_set);
	    rl_setting = (RelativeLayout) view.findViewById(R.id.layout_setting);
        tv_logout = (TextView) view.findViewById(R.id.tv_logout);

        tv_loverinfo_vg = (TextView) view.findViewById(R.id.tv_loverinfo_vg);


        rl_userinfo_update.setOnClickListener(this);
        rl_lover_set.setOnClickListener(this);
        rl_setting.setOnClickListener(this);
        tv_logout.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        checkLover();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.layout_set_userinfo:
                intent = new Intent(activity, UserInfoUpdateActivity.class);
                intent.putExtra("tag","me");
                startActivity(intent);
                break;
            case R.id.layout_lover_set:
                if(application.getOne_user() == null){
                    intent = new Intent(activity, LoverManagerActivity.class);
                    startActivity(intent);
                }else {
                    intent = new Intent(activity, UserInfoUpdateActivity.class);
                    intent.putExtra("tag","lover");
                    startActivity(intent);
                }
                break;
            case R.id.layout_setting:
                intent = new Intent(activity, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_logout:
                BmobUserManager userManager = BmobUserManager.getInstance(activity);
                userManager.logout();
                intent = new Intent(activity, LoginActivity.class);
                startActivity(intent);
                activity.finish();
                break;
        }
    }

    private void checkLover(){
        if(application.getOne_user() != null){
            tv_loverinfo_vg.setText(application.getOne_user().getUsername());

        }else {
            tv_loverinfo_vg.setText("点击绑定情侣");
        }
    }
}
