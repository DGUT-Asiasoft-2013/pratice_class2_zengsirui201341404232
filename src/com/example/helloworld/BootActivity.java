package com.example.helloworld;

import java.io.IOException;

import org.apache.http.conn.ClientConnectionManager;

import android.app.Activity;
import android.content.Intent;
import okhttp3.Request;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import com.example.helloworld.entity.Server;
//��������
public class BootActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boot);
    }
    @Override
    protected void onResume() {
        super.onResume();

//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//           // private int abcd = 0;
//
//            public void run() {
//                startLoginActivity();
//            }
//        }, 1000);
        
//        OkHttpClient client=new OkHttpClient();//����OkHttpClientʵ��
        
        OkHttpClient client=Server.getOkHttpClient();
      //����һ��Request����ȡurl,method����
        Request request=Server.requestuildApi("hello")
        		.method("get", null)
        		.build();//������������URL
        
        client.newCall(request)//����request
        .enqueue(new Callback() {//����֮�������н�������ʾ���ͻ�����
			
			@Override
			public void onResponse(Call arg0, final Response arg1) throws IOException {//��Ӧʱִ��
				BootActivity.this.runOnUiThread(new Runnable() {//�Ѹ���ui�Ĵ��봴����Runnable�У�Ȼ������Ҫ����uiʱ�������Runnable���󴫸�runOnUiThread(Runnable)
					
					@Override
					public void run() {
						try{
							Toast.makeText(BootActivity.this, arg1.body().string(),Toast.LENGTH_SHORT).show();//��arg1.body().string()������ʾ��BootActivity�У���ʾ����ΪLENGTH_SHORT
						}catch(IOException e){
							e.printStackTrace();//��ӡ�쳣�Ķ�ջ��Ϣ��ָ������ԭ�򣨷�����Գ���
						}
						startLoginActivity();//������¼ҳ��
					}
				});				
			}			
			@Override
			public void onFailure(Call arg0, IOException arg1) {//ʧ��ʱִ��
			Toast.makeText(BootActivity.this, arg1.getLocalizedMessage(), Toast.LENGTH_SHORT).show();//��arg1.getLocalizedMessage()������ʾ��BootActivity�У���ʾ����ΪLENGTH_SHORT
				
			}
		});
    }

    
    
    void startLoginActivity(){//����startLoginActivity()����
        Intent itnt = new Intent(this, LoginActivity.class);
        startActivity(itnt);//����LoginActivity
        finish();
    }

}

