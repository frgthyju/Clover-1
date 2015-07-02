package com.clover.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;

/**
 * Created by dan on 2015/7/1.
 */
public class GifViewUtils extends View {
    private int gifResource;
    private Movie mMovie;
    private long movieStart;

    public GifViewUtils(Context contex , byte[] data){
        super(contex);
        //initializeView()
        setGIFResource(data);
    }

    public GifViewUtils(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
        initializeView();
    }

    public GifViewUtils(Context context, AttributeSet attrs){
        super(context, attrs);
        initializeView();
    }

    /**
     * 初始化控件
     */
    private void initializeView(){
        if(gifResource != 0){
            InputStream is = getContext().getResources().openRawResource(gifResource);
            byte[] array = streamToBytes(is);
            mMovie = Movie.decodeByteArray(array, 0 ,array.length);
            movieStart = 0;
            this.invalidate();
        }
    }

    /**
     * 设置GIF资源ID
     */
    public void setGIFResource(int id){
        this.gifResource = id;
        initializeView();
    }

    /**
     * 获取资源ID
     */
    public int getGIFResource(){
        return gifResource;
    }

    /**
     * 设置GIF资源数据
     */
    public void setGIFResource(byte[] bytes){
        if(bytes == null || bytes.length <= 0){
            return;
        }
        mMovie = Movie.decodeByteArray(bytes , 0 , bytes.length);
        movieStart = 0;
        this.invalidate();
    }

    /**
     * 设置GIF资源数据
     */
    public void setGIFResource(InputStream is){
        if(is == null){
            return;
        }
        byte[] array = streamToBytes(is);
        mMovie = Movie.decodeByteArray(array,0,array.length);
        movieStart = 0;
        this.invalidate();
    }

    /**
     * 设置GIF资源图片
     */
    public void setGIFResource(Bitmap bitmap){
        if(bitmap == null){
            return;
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,0,bos);
        byte[] bitmapdata = bos.toByteArray();
        mMovie = Movie.decodeByteArray(bitmapdata,0,bitmapdata.length);
        movieStart = 0;
        this.invalidate();
    }

    private static byte[] streamToBytes(InputStream is){
        ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = is.read(buffer)) >= 0) {
                os.write(buffer, 0, len);
            }
        }catch (java.io.IOException e){
        }
        return os.toByteArray();
    }

    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawColor(Color.TRANSPARENT);
        super.onDraw(canvas);
        long now = android.os.SystemClock.uptimeMillis();
        if(movieStart == 0){
            movieStart =now;
        }
        int d = mMovie.duration();
        if(d<=0){
            d=1500;
        }
        if(mMovie!=null){
            int relTime = (int)((now-movieStart)%d);
            mMovie.setTime(relTime);
            mMovie.draw(canvas,(getWidth()-mMovie.width())/2, (getHeight()-mMovie.height())/2);
            this.invalidate();
        }
    }
}
