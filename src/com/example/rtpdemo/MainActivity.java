package com.example.rtpdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	private Button btn_init_sender;
	private Button btn_init_receiver;
	private Button btn_send;

	private InitSession session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btn_init_sender = (Button) findViewById(R.id.btn_init_sender);
		btn_init_receiver = (Button) findViewById(R.id.btn_init_receiver);
		btn_send = (Button) findViewById(R.id.btn_send);

		btn_init_sender.setOnClickListener(this);
		btn_init_receiver.setOnClickListener(this);
		btn_send.setOnClickListener(this);

	}

	public void openSession() {
		long teststart = System.currentTimeMillis();
		String str = "abce abcd abce abce abce abcd abcd abce " + "abcd abce abcd abce abcd abce abcd abce abcd abce "
				+ "abcd abce abcd abce abcd abce abcd abce abcd abce abcd " + "abce abcd abce abcd abce abcd abce abcd abce abcd abce "
				+ "abcd abce abcd abce abcd abce abcd abce abcd abce abcd " + "abce abcd abce abcd abce abcd abce abcd abce abcd abce "
				+ "abcd abce abcd abce abcd abce abcd abce abcd abce abcd " + "abce abcd abce abcd abce abcd abce abcd abce abcd abce "
				+ "abcd abce abcd abce abcd abce abcd abce abcd abce abcd " + "abce abcd abce abcd abce abcd abce abcd abce abcd abce "
				+ "abcd abce abcd abce abcd abce abcd abce abcd abce abcd " + "abce abcd abce abcd abce abcd abce abcd ";
		byte[] data = str.getBytes();
		System.out.println(data.length);
		int i = 0;
		while (i < data.length) {
			System.out.println("send " + i);
			session.rtpSession.sendData(data);
			i++;
		}
		long testend = System.currentTimeMillis();
		System.out.println("cost:" + (testend - teststart));
		System.out.println("start:" + teststart);
		System.out.println("end:" + testend);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_init_sender:
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					session = new InitSession(9020, 9030, 9000, 9010, "10.45.8.61");
				}
			}).start();
			break;
		case R.id.btn_init_receiver:
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					session = new InitSession(9000, 9010, 9020, 9030, "10.45.8.63");
					// session = new InitSession(9020, 9030, 9000, 9010, "10.45.8.61");
				}
			}).start();
			break;
		case R.id.btn_send:
			Thread sendThread = new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						openSession();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			sendThread.start();
			break;
		default:
			break;
		}
	}

}
