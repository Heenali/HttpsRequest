package com.tutorialsface.httpsrequest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity{

	TextView textResponse;
	Button btnGetRequest, btnPostRequest;
	RestClient restClient;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	private void init() {
		getViews();
		setListeners();
		restClient = RestClient.getInstance();
	}

	private void getViews() {
		btnGetRequest = (Button) findViewById(R.id.btnGetRequest);
		btnPostRequest = (Button) findViewById(R.id.btnPostRequest);
		textResponse = (TextView) findViewById(R.id.textResponse);
	}

	private void setListeners() {
		btnGetRequest.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				new Thread(new Runnable()
				{
					@Override
					public void run()
					{
						String response = restClient.getRequesthtpps("https://test.trukker.ae/trukkerUAEApitest/Api/postorder/GetParameterDetails?paramvalue=PP");
						setText("HTTPS Get Response:-\n" + response);
					}
				}).start();
			}
		});
		btnPostRequest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					@Override
					public void run()
					{

						JSONObject prms = new JSONObject();
						JSONObject prmsLogin = new JSONObject();
						try {
							prmsLogin.put("user_id", "0123456789");
							prmsLogin.put("password", "asdf");
							prmsLogin.put("load_inquiry_no", "");
							prmsLogin.put("device_id", "e59becbc24fc8113");
							prmsLogin.put("device_type", "Android-Mobile");
							prms.put("Login", prmsLogin);
						} catch (JSONException e) {
							e.printStackTrace();
						}

						Log.e("params",prms.toString());
								String response = restClient.postRequesthtpps("https://test.trukker.ae/trukkerUAEApitest/Api/login/CheckIn",prms.toString());
						setText("HTTPS Post Response:-\n" + response);
					}
				}).start();
			}
		});
	}
	
	private void setText(final String response) {
		MainActivity.this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				textResponse.setText(response);
			}
		});
	}
}
