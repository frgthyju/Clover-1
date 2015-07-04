package com.clover.utils;

/**
 * Created by dan on 2015/7/2.
 */
public class Config {
    public static String SLEEP_ACTION = "SLEEP_ACTION";
    public static String MENSES_COME_ACTION = "MENSES_COME";
    public static String DISEASE_COME_ACTION = "DISEASE_COME";
    /**
     * 我的头像保存目录
     */
    public static String MyAvatarDir = "/sdcard/bmobimdemo/avatar/";
    /**
     * 拍照回调
     */
    public static final int REQUESTCODE_UPLOADAVATAR_CAMERA = 1;//拍照修改头像
    public static final int REQUESTCODE_UPLOADAVATAR_LOCATION = 2;//本地相册修改头像
    public static final int REQUESTCODE_UPLOADAVATAR_CROP = 3;//系统裁剪头像

    public static final int REQUESTCODE_TAKE_CAMERA = 0x000001;//拍照
    public static final int REQUESTCODE_TAKE_LOCAL = 0x000002;//本地图片
    public static final int REQUESTCODE_TAKE_LOCATION = 0x000003;//位置
}
