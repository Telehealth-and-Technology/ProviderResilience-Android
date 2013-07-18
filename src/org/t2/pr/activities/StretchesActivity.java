package org.t2.pr.activities;

import org.t2.pr.R;
import org.t2.pr.classes.SimpleGestureFilter;
import org.t2.pr.classes.SimpleGestureFilter.SimpleGestureListener;
import org.t2.pr.classes.TransitionView;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class StretchesActivity extends ABSActivity implements SimpleGestureListener{


	private String[] cardNames;
	private int cardIndex = -1;

	private TransitionView ivCard;
	private SimpleGestureFilter detector;

	public TextView tv_permission;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		onEvent("Opened Stretches Activity");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.cards);

		this.SetMenuVisibility(1);
		this.btnMainTools.setChecked(true);

		detector = new SimpleGestureFilter(this,this);

		ivCard = (TransitionView)this.findViewById(R.id.ivcard);
		ivCard.downSample = false;
		
		tv_permission = (TextView)this.findViewById(R.id.tv_permission);
		tv_permission.setVisibility(View.GONE);
		
		LoadCards();
		NextCard();
	}

	@Override
	public void onStart()
	{

		super.onStart();
	}

	@Override
	public void onClick(View v) 
	{
		super.onClick(v);

		switch(v.getId()) 
		{

		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent me){
		this.detector.onTouchEvent(me);
		return super.dispatchTouchEvent(me);
	}
	
	@Override
	public void onSwipe(int direction) {

		onEvent("Stretches Activity: Swipe left/right");

		switch (direction) {

		case SimpleGestureFilter.SWIPE_RIGHT : PrevCard();
		break;
		case SimpleGestureFilter.SWIPE_LEFT :  NextCard();
		break;
		}
	}

	@Override
	public void onDoubleTap() {
	}



	private void NextCard()
	{
		if(cardIndex < (cardNames.length -1))
			cardIndex++;
		else
			cardIndex = 0;

		int resID = getResources().getIdentifier(cardNames[cardIndex], "drawable", "org.t2.pr");
		ivCard.changePage(resID, 0);
		ivCard.refreshDrawableState();
	}

	private void PrevCard()
	{
		if(cardIndex > 0)
			cardIndex--;
		else
			cardIndex = (cardNames.length -1);

		int resID = getResources().getIdentifier(cardNames[cardIndex], "drawable", "org.t2.pr");
		ivCard.changePage(resID, 1);
		ivCard.refreshDrawableState();
	}

	private void LoadCards()
	{
		//Load cards
		cardNames = getResources().getStringArray(R.array.StretchCards);    
	}

}
