package org.t2.pr.activities;

import java.util.Random;

import org.t2.pr.R;
import org.t2.pr.classes.PreferenceHelper;
import org.t2.pr.classes.SimpleGestureFilter;
import org.t2.pr.classes.SimpleGestureFilter.SimpleGestureListener;
import org.t2.pr.classes.TransitionView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Displays images from a set of slides. Handles swipe navigation and transitions
 * @author stephenody
 *
 */
public class CardsActivity extends ABSActivity implements OnClickListener, SimpleGestureListener{


	private String[] cardNames;
	private int cardIndex = -1;
	private int cardSide = 0;

	private boolean randomizeCard = false;

	
	private TransitionView ivCard;
	private SimpleGestureFilter detector;

	Button btnDone;
	TextView tvHeader;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		onEvent("Opened Cards Activity");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.cards);

		Intent intent = this.getIntent();
		randomizeCard = intent.getBooleanExtra("random", false);

		if(!randomizeCard)
		{
			this.SetMenuVisibility(View.VISIBLE);
			this.btnMainCards.setChecked(true);
		}
		else
		{
			this.SetMenuVisibility(View.GONE);
			btnDone = (Button) this.findViewById(R.id.btn_Done);
			btnDone.setVisibility(View.VISIBLE);
			btnDone.setOnClickListener(this);
			tvHeader = (TextView)this.findViewById(R.id.tv_header);
			tvHeader.setVisibility(View.VISIBLE);
		}

		detector = new SimpleGestureFilter(this,this);

		ivCard = (TransitionView)this.findViewById(R.id.ivcard);
		
		LoadCards();
		
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
		case R.id.btn_Done:
			onEvent("Cards Activity: Done button Clicked");

			this.finish();
			break;
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent me){
		this.detector.onTouchEvent(me);
		return super.dispatchTouchEvent(me);
	}

	@Override
	public void onSwipe(int direction) {

		onEvent("Cards Activity: Swiped left/right");

		switch (direction) {

		case SimpleGestureFilter.SWIPE_RIGHT : PrevCard();
		break;
		case SimpleGestureFilter.SWIPE_LEFT :  NextCard();
		break;
		case SimpleGestureFilter.SWIPE_DOWN :  FlipCard();
		break;
		case SimpleGestureFilter.SWIPE_UP :    FlipCard();
		break;

		}
	}

	@Override
	public void onDoubleTap() {
	}

	@Override
	public void onPause()
	{
		PreferenceHelper.setCardIndex(cardIndex);
		super.onPause();
	}

	@Override
	public void onResume()
	{
		if(randomizeCard)
		{
			Random rnd = new Random();
			cardIndex = rnd.nextInt(cardNames.length - 1);
		}
		else
		{
		cardIndex = (PreferenceHelper.getCardIndex() - 1);
		}
		super.onResume();
		NextCard();
	}

	private void NextCard()
	{
		cardSide = 0;
		if(cardIndex < (cardNames.length -1))
			cardIndex++;
		else
			cardIndex = 0;

		int resID = getResources().getIdentifier(cardNames[cardIndex] + "_front", "drawable", "org.t2.pr");
		ivCard.changePage(resID, 0);
		ivCard.refreshDrawableState();
	}

	private void PrevCard()
	{
		cardSide = 0;
		if(cardIndex > 0)
			cardIndex--;
		else
			cardIndex = (cardNames.length -1);

		int resID = getResources().getIdentifier(cardNames[cardIndex] + "_front", "drawable", "org.t2.pr");
		ivCard.changePage(resID, 1);
		ivCard.refreshDrawableState();
	}

	private void FlipCard()
	{
		if(cardSide == 0)
		{
			cardSide = 1;
			int resID = getResources().getIdentifier(cardNames[cardIndex] + "_reverse", "drawable", "org.t2.pr");
			ivCard.changePage(resID, 2);
		}
		else
		{
			cardSide = 0;
			int resID = getResources().getIdentifier(cardNames[cardIndex] + "_front", "drawable", "org.t2.pr");
			ivCard.changePage(resID, 2);
		}
		ivCard.refreshDrawableState();

	}

	private void LoadCards()
	{
		//Load cards
		cardNames = getResources().getStringArray(R.array.VirtueCards);    
	}

}
