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
				// ��ʾ��䶯̬����
				viewStub.inflate();
				//viewStub.inflate();����ֻ�ܱ�����һ�Σ���ε�������
				//����viewStub.setVisibility(View.VISIBLE);�ɶ�ε��
			}
		});
        button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// ���ض�̬���صĲ���
				viewStub.setVisibility(View.GONE);
			}
		});
    }

}
