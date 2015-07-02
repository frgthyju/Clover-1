package com.clover.utils;

import android.content.Context;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;



public class TypegifView extends View implements Runnable {
	TypegifOpenHelper gHelper;
	
	int delta;
	String title;
	
	Bitmap bmp;
	
	//construct - refer for java
	public TypegifView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		//init();
		
	}
	
	//construct - refer for xml
	/*public TypegifView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        
        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.TypegifView);
        int n = ta.getIndexCount();
        
        for(int i =0;i < n;i++){
        	int attr = ta.getIndex(i); 
        	
        	switch(attr){
        	case R.styleable.TypegifView_src:
        		int id = ta.getResourceId(
        				R.styleable.TypegifView_src, 0);
        		setSrc(id);
        		
        	case R.styleable.TypegifView_delta:
        		int idelta = ta.getInteger(R.styleable.TypegifView_delta, 1);
        		setDelta(idelta);
        		
        	default:
        		break;
        	}
        	
        }
        
        ta.recycle();
	}
	*/
	/*public void init(){
		// do some initial 
		
	}
	*/

    public void setWidth(int width){
        gHelper.setWidth(width);
    }

    public void setHeight(int height){
        gHelper.setHeight(height);
    }
	
	public void setSrc(int id){
		gHelper = new TypegifOpenHelper();
		gHelper.read(this.getResources().openRawResource(id));
		
		 bmp=gHelper.getFrame(0);
		 Thread updateTimer =new Thread(this);
		 updateTimer.start();
	}
	
	public void setDelta(int is){
		delta = is;
	}
	
	//to meaure its Width & Height
	/*@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));
    }
	
    private int measureWidth(int measureSpec) {
        return gHelper.getWidth();
    }

    private int measureHeight(int measureSpec) {
        return gHelper.getHeigh();
    }*/
	
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawBitmap(bmp, 0,0,null);
		bmp=gHelper.nextBitmap();
		
	}

	public void run() {
		// TODO Auto-generated method stub
		while(true){
	    	  try{
	    		this.postInvalidate();
	    		Thread.sleep(gHelper.nextDelay()/delta) ; 
	    	  }catch(Exception ex){
	    		  
	    	  }
	      }
	}

}
