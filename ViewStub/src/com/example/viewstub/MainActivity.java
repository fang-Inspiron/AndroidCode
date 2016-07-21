package com.example.viewstub;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.widget.Button;

public class MainActivity extends Activity {
	
	private Button button1, button2;
	private ViewStub viewStub;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        button1 = (Button) findViewById(R.id.showBtn);
        button2 = (Button) findViewById(R.id.deleteBtn);
        viewStub = (ViewStub) findViewById(R.id.viewstub);
        
        button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// 表示填充动态布局
				viewStub.inflate();
				//viewStub.inflate();方法只能被调用一次，多次点击会出错
				//或者viewStub.setVisibility(View.VISIBLE);可多次点击
			}
		});
        button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// 隐藏动态加载的布局
				viewStub.setVisibility(View.GONE);
			}
		});
    }

}
