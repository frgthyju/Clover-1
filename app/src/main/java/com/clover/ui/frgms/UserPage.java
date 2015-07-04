package com.clover.ui.frgms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.clover.R;
import com.clover.ui.LoginActivity;
import com.clover.ui.LoverManagerActivity;
import com.clover.ui.UserInfoUpdateActivity;

import cn.bmob.im.BmobUserManager;

public class UserPage extends Fragment implements OnClickListener{

    private RelativeLayout rl_userinfo_update;
    private RelativeLayout rl_lover_set;
    private RelativeLayout rl_setting;
    private ImageButton ib_logout;
    private Activity activity;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_user_page, container, false);
        initView(view);
		return view;
	}


	private void initView(View view){
        activity = getActivity();
        rl_userinfo_update = (RelativeLayout) view.findViewById(R.id.layout_set_userinfo);
        rl_lover_set = (RelativeLayout) view.findViewById(R.id.layout_lover_set);
	    rl_setting = (RelativeLayout) view.findViewById(R.id.layout_setting);
        ib_logout = (ImageButton) view.findViewById(R.id.ib_logout);

        rl_userinfo_update.setOnClickListener(this);
        rl_lover_set.setOnClickListener(this);
        rl_setting.setOnClickListener(this);
        ib_logout.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.layout_set_userinfo:
                intent = new Intent(activity, UserInfoUpdateActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_lover_set:
                intent = new Intent(activity, LoverManagerActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_setting:
                break;
            case R.id.ib_logout:
                BmobUserManager userManager = BmobUserManager.getInstance(activity);
                userManager.logout();
                intent = new Intent(activity, LoginActivity.class);
                startActivity(intent);
                activity.finish();
                break;
        }
    }
}
