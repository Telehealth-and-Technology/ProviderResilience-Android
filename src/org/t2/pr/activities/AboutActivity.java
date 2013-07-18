package org.t2.pr.activities;

import org.t2.pr.R;
import org.t2.pr.classes.AccessibleWebView;
import org.t2.pr.classes.Global;

import SwipeView.PageControl;
import SwipeView.SwipeView;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

/**
 * Shows a browser based information screen
 * @author stephenody
 *
 */
public class AboutActivity extends ABSActivity
{
	boolean shownav = true;
	SwipeView mSwipeView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		onEvent("About Page");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.walkthrough);

		System.gc();
		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
	            LayoutParams.FILL_PARENT);
		
		Intent intent = this.getIntent();
		shownav = intent.getBooleanExtra("shownav", true);
		
		PageControl mPageControl = (PageControl) findViewById(R.id.page_control);
		mSwipeView = (SwipeView) findViewById(R.id.swipe_view);

		//Add the views
		View view = getLayoutInflater().inflate(R.layout.aboutimage, null);
		LinearLayout ll = (LinearLayout)view.findViewById(R.id.container);
		AccessibleWebView awv = new AccessibleWebView(this);
		awv.setWebChromeClient(new WebChromeClient());
		awv.setBackgroundColor(Color.TRANSPARENT);
		awv.setVerticalScrollBarEnabled(true);
		WebSettings settings = awv.getSettings();
		settings.setJavaScriptEnabled(true);
		awv.loadDataWithBaseURL("fake:/blah", getString(R.string.about_content), "text/html", "utf-8", null);
		//awv.loadData(getString(R.string.about_content), "text/html", "utf-8");
		awv.setVisibility(View.VISIBLE);
		ll.addView(awv);
		mSwipeView.addView(view);
		
		BitmapFactory.Options opts=new BitmapFactory.Options();
		if(android.os.Build.VERSION.SDK_INT >= 11)
			opts.inMutable = true;
		opts.inDither=false;
		opts.inPurgeable=true;
		opts.inInputShareable=true;
		opts.inTempStorage=new byte[32 * 1024];
		opts.inPreferredConfig = Bitmap.Config.RGB_565;
		
		if(android.os.Build.VERSION.SDK_INT >= 11)
			Global.setCardBitmap1(BitmapFactory.decodeResource(this.getResources(), R.drawable.aboutcard1, opts));
		else
			Global.setCardBitmap1(BitmapFactory.decodeResource(this.getResources(), R.drawable.aboutcard1, opts).copy(Bitmap.Config.ARGB_8888, true));
			
		View view2 = getLayoutInflater().inflate(R.layout.about, null);
		LinearLayout ll2 = (LinearLayout)view2.findViewById(R.id.container);
		ImageView v = new ImageView(this);
		v.setImageBitmap(Global.bmpCard1);
		v.setScaleType(ScaleType.FIT_XY);
		ll2.addView(v);
		ll2.setLayoutParams(params);
		mSwipeView.addView(view2);

		View view3 = getLayoutInflater().inflate(R.layout.about, null);

		if(android.os.Build.VERSION.SDK_INT >= 11)
			Global.setCardBitmap2(BitmapFactory.decodeResource(this.getResources(), R.drawable.aboutcard2, opts));
		else
			Global.setCardBitmap2(BitmapFactory.decodeResource(this.getResources(), R.drawable.aboutcard2, opts).copy(Bitmap.Config.ARGB_8888, true));
			
		LinearLayout ll3 = (LinearLayout)view3.findViewById(R.id.container);
		ImageView v2 = new ImageView(this);
		v2.setImageBitmap(Global.bmpCard2);
		v2.setScaleType(ScaleType.FIT_XY);
		ll3.addView(v2);
		ll3.setLayoutParams(params);
		mSwipeView.addView(view3);
		
		mSwipeView.setPageControl(mPageControl);

		if(shownav)
		{
			this.SetMenuVisibility(View.VISIBLE);
			this.btnMainAbout.setChecked(true);
		}
		else
			this.SetMenuVisibility(View.GONE);
		
		Toast.makeText(this, "Swipe left/right to view additional instructions.", Toast.LENGTH_SHORT).show();
	}

}
