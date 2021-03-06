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
//启动界面
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
        
//        OkHttpClient client=new OkHttpClient();//构造OkHttpClient实例
        
        OkHttpClient client=Server.getOkHttpClient();
      //创建一个Request，获取url,method参数
        Request request=Server.requestuildApi("hello")
        		.method("get", null)
        		.build();//向服务器请求打开URL
        
        client.newCall(request)//唤起request
        .enqueue(new Callback() {//请求之后进入队列将数据显示到客户端中
			
			@Override
			public void onResponse(Call arg0, final Response arg1) throws IOException {//响应时执行
				BootActivity.this.runOnUiThread(new Runnable() {//把更新ui的代码创建在Runnable中，然后在需要更新ui时，把这个Runnable对象传给runOnUiThread(Runnable)
					
					@Override
					public void run() {
						try{
							Toast.makeText(BootActivity.this, arg1.body().string(),Toast.LENGTH_SHORT).show();//将arg1.body().string()内容显示在BootActivity中，显示长度为LENGTH_SHORT
						}catch(IOException e){
							e.printStackTrace();//打印异常的堆栈信息，指明错误原因（方便调试程序）
						}
						startLoginActivity();//启动登录页面
					}
				});				
			}			
			@Override
			public void onFailure(Call arg0, IOException arg1) {//失败时执行
			Toast.makeText(BootActivity.this, arg1.getLocalizedMessage(), Toast.LENGTH_SHORT).show();//将arg1.getLocalizedMessage()内容显示在BootActivity中，显示长度为LENGTH_SHORT
				
			}
		});
    }

    
    
    void startLoginActivity(){//定义startLoginActivity()方法
        Intent itnt = new Intent(this, LoginActivity.class);
        startActivity(itnt);//启动LoginActivity
        finish();
    }

}

