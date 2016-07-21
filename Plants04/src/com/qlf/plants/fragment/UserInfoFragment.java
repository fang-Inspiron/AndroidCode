package com.qlf.plants.fragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.Header;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qlf.plants.MainActivity;
import com.qlf.plants.R;
import com.qlf.plants.activity.ChooseActivity;
import com.qlf.plants.activity.SelectPicPopupWindow;
import com.qlf.plants.http.AsyncHttpClient;
import com.qlf.plants.http.AsyncHttpResponseHandler;
import com.qlf.plants.http.RequestParams;
import com.qlf.plants.thread.UploadThread;
import com.qlf.plants.userdata.UserData;
import com.qlf.plants.utils.HttpUtils;
import com.qlf.plants.view.CircleImageView;

public class UserInfoFragment extends Fragment {

	View rootView;
	ImageView back;

	private EditText editTextUserName;
	private EditText editTextUserQianMing;
	private TextView textViewUserName;
	private TextView textViewUserQianMing;
	private Bitmap bitmap;

	private Button motifyUserName;
	private Button motifyUserQianMing;
	
	private Button exitLogin;
	
	public static  CircleImageView user_head;
	UserData userData;
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what == UploadThread.SUCCESS){
				Toast.makeText(getActivity(), "上传成功", Toast.LENGTH_SHORT).show();
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView = inflater.inflate(R.layout.userinfo_main, container, false);
		initView();
		return rootView;
	}

	public void initView() {
		user_head = (CircleImageView) rootView.findViewById(R.id.user_head);
		editTextUserName = (EditText) rootView.findViewById(R.id.edit_username);
		editTextUserQianMing = (EditText) rootView
				.findViewById(R.id.edit_userqianming);
		textViewUserName = (TextView) rootView.findViewById(R.id.user_name);
		textViewUserQianMing = (TextView) rootView
				.findViewById(R.id.user_qianming);
		motifyUserName = (Button) rootView.findViewById(R.id.motify_user_name);
		motifyUserQianMing = (Button) rootView
				.findViewById(R.id.motify_user_qianming);
		exitLogin =  (Button) rootView.findViewById(R.id.exit_login);
		userData = new UserData(getActivity());
		
		String username="";
		String sign = "";
		if(userData.getUserNameAndSign()!=null){
			username = userData.getUserNameAndSign().get("username");
			sign = userData.getUserNameAndSign().get("sign");
			
			textViewUserName.setText(username);
			textViewUserQianMing.setText(sign);
		}
		
		motifyUserName.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v.getTag().equals("1")) {
					textViewUserName.setVisibility(View.GONE);
					editTextUserName.setVisibility(View.VISIBLE);
					editTextUserName.setText(textViewUserName.getText().toString());
					v.setTag("0");
				} else {
					editTextUserName.setVisibility(View.GONE);
					textViewUserName.setVisibility(View.VISIBLE);
					textViewUserName.setText(editTextUserName.getText().toString());
					userData.saveUserNameAndSign(textViewUserName.getText().toString(), textViewUserQianMing.getText().toString());
					v.setTag("1");
					MainActivity.textViewUserName.setText(editTextUserName.getText().toString());
				}
			}
		});
		motifyUserQianMing.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v.getTag().equals("1")) {
					textViewUserQianMing.setVisibility(View.GONE);
					editTextUserQianMing.setVisibility(View.VISIBLE);
					editTextUserQianMing.setText(textViewUserQianMing.getText().toString());
					v.setTag("0");
				} else {
					editTextUserQianMing.setVisibility(View.GONE);
					textViewUserQianMing.setVisibility(View.VISIBLE);
					textViewUserQianMing.setText(editTextUserQianMing.getText().toString());
					userData.saveUserNameAndSign(textViewUserName.getText().toString(), textViewUserQianMing.getText().toString());
					v.setTag("1");
					MainActivity.textViewSign.setText(editTextUserQianMing.getText().toString());
				}
			}
		});
		
		exitLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), ChooseActivity.class);
				startActivity(intent);
				getActivity().finish();
			}
		});
		
		user_head.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						SelectPicPopupWindow.class);
				intent.putExtra("type", "1");
				startActivity(intent);
			}
		});
		upload();
	}
	/*
	 * 上传图片
	 */
	public void upload() {
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.head_image);
		String userImg = saveCroppedImage(bitmap);
		System.out.println("OK----------------------");
		new UploadThread(handler, userData.getUserInfo().get("userId").toString(), userImg,userData.getUserInfo().get("certificate").toString()).start();
	}
	
	private String saveCroppedImage(Bitmap bmp) {
        File file = new File("/sdcard/myFolder");
        if (!file.exists())
            file.mkdir();
 
        file = new File("/sdcard/temp.jpg".trim());
        String fileName = file.getName();
        String mName = fileName.substring(0, fileName.lastIndexOf("."));
        String sName = fileName.substring(fileName.lastIndexOf("."));
 
        // /sdcard/myFolder/temp_cropped.jpg
        String newFilePath = "/sdcard/myFolder" + "/" + mName + "_cropped" + sName;
        file = new File(newFilePath);
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(CompressFormat.JPEG, 50, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return newFilePath;
    }

}
