package com.clover.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.clover.R;
import com.clover.entities.User;
import com.clover.utils.Config;
import com.clover.utils.ImageLoadOptions;
import com.clover.utils.PhotoUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class UserInfoUpdateActivity extends BaseActivity{

    private RelativeLayout rv_head;//头像
    private RelativeLayout rv_nick;//昵称
    private RelativeLayout rv_age;//年龄
    private RelativeLayout rv_sex;//性别
    private ImageView iv_head;//头像
    private TextView tv_nick;//昵称
    private TextView tv_age;//年龄
    private TextView tv_sex;//性别
    User user;
    int age;
    String avatarPath = "";//路径

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_update);

        initView();
    }

    private void initView(){

        user = userManager.getCurrentUser(User.class);
        initToolbar("编辑个人资料",new Intent(this, MainActivity.class), this);
        rv_head = (RelativeLayout)findViewById(R.id.layout_head);
        rv_nick = (RelativeLayout)findViewById(R.id.layout_nick);
        rv_age = (RelativeLayout)findViewById(R.id.layout_age);
        rv_sex = (RelativeLayout)findViewById(R.id.layout_sex);
        iv_head = (ImageView)findViewById(R.id.avator);
        tv_nick = (TextView)findViewById(R.id.nick);
        tv_age = (TextView)findViewById(R.id.age);
        tv_sex = (TextView)findViewById(R.id.sex);

        rv_head.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(UserInfoUpdateActivity.this);
                final String[] avator = {UserInfoUpdateActivity.this.getResources().getString(R.string.takephoto), UserInfoUpdateActivity.this.getResources().getString(R.string.choosefrommibile)};
                builder.setItems(avator , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent;
                        switch (which){
                            case 0:
                                //拍照
                                File avator = new File(Config.MyAvatarDir);
                                if(!avator.exists()){
                                    //创建文件夹,
                                    avator.mkdirs();
                                }
                                File file = new File(avator, new SimpleDateFormat("yyMMddHHmmss").format(new Date()));
                                avatarPath = file.getAbsolutePath();//获取相片绝对路径
                                Uri uri = Uri.fromFile(file);

                                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                startActivityForResult(intent, Config.REQUESTCODE_UPLOADAVATAR_CAMERA);
                                break;
                            case 1:
                                //相册
                                intent = new Intent(Intent.ACTION_PICK, null);
                                intent.setDataAndType(
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                startActivityForResult(intent,
                                        Config.REQUESTCODE_UPLOADAVATAR_LOCATION);
                                break;
                        }
                    }
                });
                builder.show();
            }
        });
        rv_nick.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nickIntent = new Intent(UserInfoUpdateActivity.this, UpdateUserNickActivity.class);
                startActivity(nickIntent);
            }
        });


        rv_age.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();

                new DatePickerDialog(UserInfoUpdateActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // TODO Auto-generated method stub
                                age = CalculateAge(dayOfMonth,monthOfYear + 1,year);
                                String ageOfString = String.valueOf(age);
                                tv_age.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                                tv_age.setText(ageOfString);
                                updateAge(age);
                            }
                        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        rv_sex.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(UserInfoUpdateActivity.this);
                builder.setTitle(UserInfoUpdateActivity.this.getResources().getString(R.string.pleaseselectsex));
                final String[] sex = {UserInfoUpdateActivity.this.getResources().getString(R.string.man), UserInfoUpdateActivity.this.getResources().getString(R.string.woman)};
                builder.setItems(sex , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tv_sex.setText(sex[which]);
                        updateSex(sex[which]);
                    }
                });
                builder.show();
            }
        });


        refreshUser();
    }

    private void refreshUser(){
        refreshAvatar();
        tv_nick.setText(userManager.getCurrentUser(User.class).getNick());
        tv_age.setText(String.valueOf(userManager.getCurrentUser(User.class).getAge()));
        refreshSex();
    }

    /**
     * 刷新头像
     */
    private void refreshAvatar(){
        String avatarUrl = userManager.getCurrentUser(User.class).getAvatar();
        if (avatarUrl != null && !avatarUrl.equals("")) {
            ImageLoader.getInstance().displayImage(avatarUrl, iv_head,
                    ImageLoadOptions.getOptions());
        } else {
            iv_head.setImageResource(R.drawable.head);
        }
    }

    /**
     * 刷新性别
     */
    private void refreshSex(){
        if(userManager.getCurrentUser(User.class).getSex()){
            tv_sex.setText(UserInfoUpdateActivity.this.getResources().getString(R.string.woman));
        }else{
            tv_sex.setText(UserInfoUpdateActivity.this.getResources().getString(R.string.man));
        }
    }

    /**
     * 计算年龄
     */
    private int CalculateAge(int dayOfBirth, int monthOfBirth, int yearOfBirth){
        int age=0;
        Calendar now = Calendar.getInstance();
        int yearOfNow = now.get(Calendar.YEAR);
        int monthOfNow = now.get(Calendar.MONTH)+1;
        int dayOfNow = now.get(Calendar.DAY_OF_MONTH);
        if(yearOfBirth>yearOfNow||(yearOfBirth==yearOfNow&&monthOfBirth>monthOfNow)||(yearOfBirth==yearOfNow&&monthOfBirth==monthOfNow&&dayOfBirth>dayOfNow)){
            Toast.makeText(UserInfoUpdateActivity.this, R.string.wrongbirthdate, Toast.LENGTH_LONG).show();
        }else{
            age = yearOfNow - yearOfBirth;
            if(monthOfNow<=monthOfBirth){
                if(monthOfNow==monthOfBirth){
                    if(dayOfNow<dayOfBirth){
                        age--;
                    }else {
                    }
                }else {
                        age--;
                }
            }else{

            }
        }
        return age;
    }

    /**
     * 修改年龄
     */
    private void updateAge(int age){
        final User user = userManager.getCurrentUser(User.class);
        User u = new User();
        u.setAge(age);
        u.setObjectId(user.getObjectId());
        u.update(this, new UpdateListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(UserInfoUpdateActivity.this, R.string.updatesuccess, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(UserInfoUpdateActivity.this, R.string.updatesuccess, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 修改性别
     */
    private void updateSex(String sex){
        boolean tag;
        if(sex==this.getResources().getString(R.string.man)){
            tag=false;
        }else{
            tag=true;
        }
        final User user = userManager.getCurrentUser(User.class);
        User u = new User();
        u.setSex(tag);
        u.setObjectId(user.getObjectId());
        u.update(this, new UpdateListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(UserInfoUpdateActivity.this, R.string.updatesuccess, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(UserInfoUpdateActivity.this,R.string.updatesuccess, Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 裁剪
     */
    private void startImageAction(Uri uri, int outputX, int outputY,
                                  int requestCode, boolean isCrop) {
        Intent intent;
        if (isCrop) {
            intent = new Intent("com.android.camera.action.CROP");
        } else {
            intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        }
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, requestCode);
    }

    /**
     * 保存裁剪的头像
     */
    String path;
    private void saveCropAvatar(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Bitmap bitmap = extras.getParcelable("data");
            Log.i("life", "avatar - bitmap = " + bitmap);
            if (bitmap != null) {
                bitmap = PhotoUtil.toRoundCorner(bitmap, 10);
                if (isFromCamera && degree != 0) {
                    bitmap = PhotoUtil.rotaingImageView(degree, bitmap);
                }
                iv_head.setImageBitmap(bitmap);
                // 保存图片
                String filename = new SimpleDateFormat("yyMMddHHmmss")
                        .format(new Date())+".png";
                path = Config.MyAvatarDir + filename;
                PhotoUtil.saveBitmap(Config.MyAvatarDir, filename,
                        bitmap, true);
                // 上传头像
                if (bitmap != null && bitmap.isRecycled()) {
                    bitmap.recycle();
                }
            }
        }
    }

    /**
     * 根据intent值来判断是拍照还是从相册选择
     */

    boolean isFromCamera = false;// 区分是否来自拍照
    int degree = 0;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Config.REQUESTCODE_UPLOADAVATAR_CAMERA:// 拍照
                if (resultCode == RESULT_OK) {
                    if (!Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED)) {
                        ShowToast("SD不可用");
                        return;
                    }
                    isFromCamera = true;
                    File file = new File(avatarPath);
                    degree = PhotoUtil.readPictureDegree(file.getAbsolutePath());
                    Log.i("life", "拍照后的角度：" + degree);
                    startImageAction(Uri.fromFile(file), 200, 200,
                            Config.REQUESTCODE_UPLOADAVATAR_CROP, true);
                }
                break;
            case Config.REQUESTCODE_UPLOADAVATAR_LOCATION:// 本地修改头像
                Uri uri = null;
                if (data == null) {
                    return;
                }
                if (resultCode == RESULT_OK) {
                    if (!Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED)) {
                        ShowToast("SD不可用");
                        return;
                    }
                    isFromCamera = false;
                    uri = data.getData();
                    startImageAction(uri, 200, 200,
                            Config.REQUESTCODE_UPLOADAVATAR_CROP, true);
                } else {
                    ShowToast("照片获取失败");
                }

                break;
            case Config.REQUESTCODE_UPLOADAVATAR_CROP:// 裁剪头像返回
                if (data == null) {
                    // Toast.makeText(this, "取消选择", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    saveCropAvatar(data);
                }
                // 初始化文件路径
                avatarPath = "";
                // 上传头像
                uploadAvatar();
                break;
            default:
                break;

        }
    }

    /**
     * 上传头像
     */
    private void uploadAvatar(){
        final BmobFile avatar = new BmobFile(new File(path));
        avatar.upload(this, new UploadFileListener() {
            @Override
            public void onSuccess() {
                String url = avatar.getFileUrl(UserInfoUpdateActivity.this);
                updateAvatar(url);
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(UserInfoUpdateActivity.this, "失败！！！",Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 修改用户头像
     */
    private void updateAvatar(String url){
        final User user = userManager.getCurrentUser(User.class);
        User u = new User();
        u.setAvatar(url);
        u.setObjectId(user.getObjectId());
        u.update(this, new UpdateListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(UserInfoUpdateActivity.this, R.string.updatesuccess, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(UserInfoUpdateActivity.this,R.string.updatesuccess, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 返回
     */
    public void back(View view){
        this.finish();
    }

    @Override
    public void onResume(){
        super.onResume();
        refreshUser();
    }
}
