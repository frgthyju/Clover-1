package com.clover.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.clover.R;
import com.clover.entities.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;


public class HealthActivity extends BaseActivity {

    private Button ib_emm;//按钮
    private Button ib_disease;//按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        ib_emm = (Button)findViewById(R.id.menses);
        ib_disease = (Button)findViewById(R.id.disease);
        ib_emm.setOnClickListener(new ImageButtonClickListener());
        ib_disease.setOnClickListener(new ImageButtonClickListener());
    }

    /**
     * 健康页面中的按钮点击事件
     */
    private class ImageButtonClickListener implements View.OnClickListener{
        Intent intent;
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.menses:
                    intent = new Intent(HealthActivity.this, MensesActivity.class);
                    startActivity(intent);
                    break;
                case R.id.disease:
                    intent = new Intent(HealthActivity.this, DiseaseActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }

    /**
     * 返回
     */
    public void back(View view){
        this.finish();
    }

}
