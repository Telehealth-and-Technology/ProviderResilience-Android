package org.t2.pr.activities;

import org.t2.pr.R;
import org.t2.pr.classes.ActivityFactory;
import org.t2.pr.classes.PreferenceHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Displays an EULA screen
 * 
 */
public class EulaActivity extends ABSWebViewActivity 
{
	Button btnAccept;
	Button btnDeny;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		onEvent("Open EULA Activity");

		super.onCreate(savedInstanceState);

		btnAccept = (Button) this.findViewById(R.id.leftButton);
		btnAccept.setText(getString(R.string.eula_accept));
		btnAccept.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
			{
				AcceptPressed();
			}
		});

		btnDeny = (Button) this.findViewById(R.id.rightButton);
		btnDeny.setText("Decline");
		btnDeny.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
			{
				DeclinePressed();
			}
		});

		this.setTitle(getString(R.string.eula_title));
		this.setContent(getString(R.string.eula_content));
	}

	public void AcceptPressed() 
	{
		onEvent("Accepted EULA");

		PreferenceHelper.setIsEulaAccepted(true);
		this.startActivity(ActivityFactory.getAboutActivity(this));
		this.finish();
	}

	public void DeclinePressed() 
	{
		onEvent("Declined EULA");

		this.setResult(0);
		this.finish();
	}

}
