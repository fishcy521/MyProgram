package com.example.administrator.myphone.activity;


import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;

import com.example.administrator.myphone.R;
import com.example.administrator.myphone.camera.CameraInterface;
import com.example.administrator.myphone.camera.preview.CameraSurfaceView;
import com.example.administrator.myphone.ui.MaskView;
import com.example.administrator.myphone.util.DisplayUtil;

public class CameraActivity extends Activity implements CameraInterface.CamOpenOverCallback {
	private static final String TAG = "YanZi";
	CameraSurfaceView surfaceView = null;
	ImageButton shutterBtn;
	MaskView maskView = null;
	float previewRate = -1f;
	int DST_CENTER_RECT_WIDTH = 200; //��λ��dip
	int DST_CENTER_RECT_HEIGHT = 200;//��λ��dip
	Point rectPictureSize = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Thread openThread = new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				CameraInterface.getInstance().doOpenCamera(CameraActivity.this);
			}
		};
		openThread.start();
		setContentView(R.layout.activity_camera);
		initUI();
		initViewParams();
		
		shutterBtn.setOnClickListener(new BtnListeners());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.camera, menu);
		return true;
	}

	private void initUI(){
		surfaceView = (CameraSurfaceView)findViewById(R.id.camera_surfaceview);
		shutterBtn = (ImageButton)findViewById(R.id.btn_shutter);
		maskView = (MaskView)findViewById(R.id.view_mask);
	}
	private void initViewParams(){
		LayoutParams params = surfaceView.getLayoutParams();
		Point p = DisplayUtil.getScreenMetrics(this);
		params.width = p.x;
		params.height = p.y;
		Log.i(TAG, "screen: w = " + p.x + " y = " + p.y);
		previewRate = DisplayUtil.getScreenRate(this); //Ĭ��ȫ���ı���Ԥ��
		surfaceView.setLayoutParams(params);

		//�ֶ���������ImageButton�Ĵ�СΪ120dip��120dip,ԭͼƬ��С��64��64
		LayoutParams p2 = shutterBtn.getLayoutParams();
		p2.width = DisplayUtil.dip2px(this, 80);
		p2.height = DisplayUtil.dip2px(this, 80);;		
		shutterBtn.setLayoutParams(p2);	

	}

	@Override
	public void cameraHasOpened() {
		// TODO Auto-generated method stub
		SurfaceHolder holder = surfaceView.getSurfaceHolder();
		CameraInterface.getInstance().doStartPreview(holder, previewRate);
		if(maskView != null){
			Rect screenCenterRect = createCenterScreenRect(DisplayUtil.dip2px(this, DST_CENTER_RECT_WIDTH)
					,DisplayUtil.dip2px(this, DST_CENTER_RECT_HEIGHT));
			maskView.setCenterRect(screenCenterRect);
		}
	}
	private class BtnListeners implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.btn_shutter:
				if(rectPictureSize == null){
					rectPictureSize = createCenterPictureRect(DisplayUtil.dip2px(CameraActivity.this, DST_CENTER_RECT_WIDTH)
							,DisplayUtil.dip2px(CameraActivity.this, DST_CENTER_RECT_HEIGHT));
				}
				CameraInterface.getInstance().doTakePicture(rectPictureSize.x, rectPictureSize.y);
				break;
			default:break;
			}
		}

	}
	
	/**�������պ�ͼƬ���м���εĿ�Ⱥ͸߶�
	 * @param w ��Ļ�ϵľ��ο�ȣ���λpx
	 * @param h ��Ļ�ϵľ��θ߶ȣ���λpx
	 * @return
	 */
	private Point createCenterPictureRect(int w, int h){
		
		int wScreen = DisplayUtil.getScreenMetrics(this).x;
		int hScreen = DisplayUtil.getScreenMetrics(this).y;
		int wSavePicture = CameraInterface.getInstance().doGetPrictureSize().y; //��ΪͼƬ��ת�ˣ����Դ˴���߻�λ
		int hSavePicture = CameraInterface.getInstance().doGetPrictureSize().x; //��ΪͼƬ��ת�ˣ����Դ˴���߻�λ
		float wRate = (float)(wSavePicture) / (float)(wScreen);
		float hRate = (float)(hSavePicture) / (float)(hScreen);
		float rate = (wRate <= hRate) ? wRate : hRate;//Ҳ���԰�����С���ʼ���
		
		int wRectPicture = (int)( w * wRate);
		int hRectPicture = (int)( h * hRate);
		return new Point(wRectPicture, hRectPicture);
		
	}
	/**
	 * ������Ļ�м�ľ���
	 * @param w Ŀ����εĿ��,��λpx
	 * @param h	Ŀ����εĸ߶�,��λpx
	 * @return
	 */
	private Rect createCenterScreenRect(int w, int h){
		int x1 = DisplayUtil.getScreenMetrics(this).x / 2 - w / 2;
		int y1 = DisplayUtil.getScreenMetrics(this).y / 2 - h / 2;
		int x2 = x1 + w;
		int y2 = y1 + h;
		return new Rect(x1, y1, x2, y2);
	}

}
