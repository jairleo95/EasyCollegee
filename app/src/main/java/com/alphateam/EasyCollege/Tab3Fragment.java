/**
 *
 */
package com.alphateam.easycollege;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Tab3Fragment extends Fragment {
	private NotificationManager myNotificationManager;
	private int notificationIdTwo = 112;
	private int numMessagesTwo = 0;
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_frag3_layout,container, false);
		Button notTwoBtn = (Button) view.findViewById(R.id.notificationTwo);
		Button btnMapa =(Button)view.findViewById(R.id.bntMapa);
		Button btnTask =(Button)view.findViewById(R.id.btntask);
		Button btnMain =(Button)view.findViewById(R.id.btnMain);
		notTwoBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				displayNotificationTwo();
			}
		});
		btnMapa.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity().getApplicationContext(),Mapa.class);
				startActivity(i);
			}
		});
		btnTask.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity().getApplicationContext(),MainActivity.class);
				startActivity(i);
			}
		});
		btnMain.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
			/*	Intent i = new Intent(getActivity().getApplicationContext(),Main.class);
				startActivity(i);*/
			}
		});
		return view;
	}
	protected void displayNotificationTwo() {
		// Invoking the default notification service
		NotificationCompat.Builder  mBuilder = new NotificationCompat.Builder(getActivity().getApplicationContext());
		mBuilder.setContentTitle("New Message with implicit intent");
		mBuilder.setContentText("New message from alphateam received...");
		mBuilder.setTicker("Implicit: New Message Received!");
		mBuilder.setSmallIcon(R.drawable.notification_template_icon_bg);
		NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
		String[] events = new String[3];
		events[0] = new String("1) Message for implicit intent");
		events[1] = new String("2) big view Notification");
		events[2] = new String("3) from alphateam!");
		// Sets a title for the Inbox style big view
		inboxStyle.setBigContentTitle("More Details:");
		// Moves events into the big view
		for (int i=0; i < events.length; i++) {
			inboxStyle.addLine(events[i]);
		}
		mBuilder.setStyle(inboxStyle);
		// Increase notification number every time a new notification arrives
		mBuilder.setNumber(++numMessagesTwo);
		// when the user presses the notification, it is auto-removed
		mBuilder.setAutoCancel(true);
		// Creates an implicit intent
		Intent resultIntent = new Intent("com.alphateam.easycollege.TEL_INTENT", Uri.parse("tel:123456789"));
		resultIntent.putExtra("from", "alphateam");
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity().getApplicationContext());
		stackBuilder.addParentStack(NotificationTwo.class);
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =stackBuilder.getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT );
		mBuilder.setContentIntent(resultPendingIntent);
		myNotificationManager = (NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
		myNotificationManager.notify(notificationIdTwo, mBuilder.build());
		Vibrator vibrator =(Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(200);
	}


}
