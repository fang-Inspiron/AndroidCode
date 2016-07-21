package com.xrml.kuaican.fragment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.xrml.kuaican.R;
import com.xrml.kuaican.activity.ChangePassword;
import com.xrml.kuaican.activity.ChooseCity;
import com.xrml.kuaican.activity.FeedBack;
import com.xrml.kuaican.activity.LoginActivity;
import com.xrml.kuaican.activity.MyOrderActivity;
import com.xrml.kuaican.activity.MyOrderAddress;
import com.xrml.kuaican.activity.OrderExplain;
import com.xrml.kuaican.customer.toast.CustomToast;
import com.xrml.kuaican.data.App;
import com.xrml.kuaican.model.UpdateInfo;
import com.xrml.kuaican.util.DownloadUtil;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 主页的四个Fragment之一，我的
 * 
 * @author Administrator
 * 
 */
public class MyFragment extends Fragment {
	App app;
	private View rootView;
	private TextView user_phoneNumb;// 用户手机号
	@SuppressWarnings("unused")
	private TextView user_integral;// 用户积分
	private UpdateInfo info;
	// 是否下载新版本
	public boolean isDownLoad = false;
	// 下载线程
	public Thread load;
	private ProgressDialog pd;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DownloadUtil.PARSER_XML_ERROR:
				CustomToast.showToast(getActivity(), "解析xml失败",
						2000);
				break;
			case DownloadUtil.SERVER_ERROR:
				CustomToast.showToast(getActivity(), "服务器错误",
						2000);
				break;
			case DownloadUtil.URL_ERROR:
				CustomToast.showToast(getActivity(), "URL错误",
						2000);
				break;
			case DownloadUtil.NET_WORK_ERROR:
				CustomToast
						.showToast(getActivity(), "网络错误", 2000);
				break;
			case DownloadUtil.PARSER_XML_SUCCESS:
				if (DownloadUtil.getAppVersion(getActivity()).equals(
						info.getVersion())) {
					CustomToast.showToast(getActivity(), "已经是最新版本",
							2000);
				} else {
					showUpdateDialog();
				}
				break;
			case DownloadUtil.DOWNLOAD_SUCCESS:
				File file = (File) msg.obj;
				CustomToast
						.showToast(getActivity(), "下载成功", 2000);
				// 安装apk
				DownloadUtil.installApk(file, getActivity());
				getActivity().finish();
				break;
			case DownloadUtil.DOWNLOAD_FAILD:
				CustomToast
						.showToast(getActivity(), "下载失败", 2000);
				break;
			default:
				break;
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_my, container, false);
		initApplication();
		findViews();
		return rootView;
	}

	private void initApplication() {
		app = (App) getActivity().getApplication();
	}

	@Override
	public void onResume() {
		if (app.getUser() == null) {
			user_phoneNumb.setText("");
		} else {
			user_phoneNumb.setText(app.getUser().getPhone());
		}
		super.onResume();
	}

	private void findViews() {

		// 设置用户手机号
		user_phoneNumb = (TextView) rootView
				.findViewById(R.id.fragment_my_user_phoneNumb);
		if (app.getUser() == null) {
			user_phoneNumb.setText("");
		} else {
			user_phoneNumb.setText(app.getUser().getPhone());
		}
		// 设置用户积分
		user_integral = (TextView) rootView
				.findViewById(R.id.fragment_my_user_integral);
		// user_integral.setText("积分:" + "");
		MyButtonOnClick myButtonOnClick = new MyButtonOnClick();
		rootView.findViewById(R.id.fragment_my_change_password)
				.setOnClickListener(myButtonOnClick);
		rootView.findViewById(R.id.fragment_my_add).setOnClickListener(
				myButtonOnClick);
		rootView.findViewById(R.id.fragment_my_checkUpdate).setOnClickListener(
				myButtonOnClick);
		rootView.findViewById(R.id.fragment_my_feedBack).setOnClickListener(
				myButtonOnClick);
		rootView.findViewById(R.id.fragment_my_myOrder).setOnClickListener(
				myButtonOnClick);
		rootView.findViewById(R.id.fragment_my_orderExplain)
				.setOnClickListener(myButtonOnClick);
		rootView.findViewById(R.id.fragment_my_back).setOnClickListener(
				myButtonOnClick);
	}

	class MyButtonOnClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			switch (v.getId()) {
			// 修改密码
			case R.id.fragment_my_change_password:
				if (app.getUser() != null) {
					intent.setClass(getActivity(), ChangePassword.class);
					startActivity(intent);
				} else {
					CustomToast.showToast(getActivity(), "请先登录",
							2000);
					intent.setClass(getActivity(), LoginActivity.class);
					startActivity(intent);
				}
				break;
			// 我的订单
			case R.id.fragment_my_myOrder:
				if (app.getUser() != null) {
					intent.setClass(getActivity(), MyOrderActivity.class);
					startActivity(intent);
				} else {
					CustomToast.showToast(getActivity(), "请先登录",
							2000);
					intent.setClass(getActivity(), LoginActivity.class);
					startActivity(intent);
				}
				break;
			// 收货地址
			case R.id.fragment_my_add:
				if (app.getUser() != null) {
					intent.setClass(getActivity(), MyOrderAddress.class);
					startActivity(intent);
				} else {
					CustomToast.showToast(getActivity(), "请先登录",
							2000);
					intent.setClass(getActivity(), LoginActivity.class);
					startActivity(intent);
				}
				break;
			// 使用说明
			case R.id.fragment_my_orderExplain:
				if (app.getArea() == null
						|| app.getArea().getCurrAreaName() == null
						|| app.getArea().getCurrAreaName().equals("")) {
					CustomToast.showToast(getActivity(), "请先选择区域",
							2000);
					intent.setClass(getActivity(), ChooseCity.class);
					startActivity(intent);
				} else {
					intent.setClass(getActivity(), OrderExplain.class);
					startActivity(intent);
				}
				break;
			// 意见反馈
			case R.id.fragment_my_feedBack:
				if (app.getUser() != null) {
					intent.setClass(getActivity(), FeedBack.class);
					startActivity(intent);
				} else {
					CustomToast.showToast(getActivity(), "请先登录",
							2000);
					intent.setClass(getActivity(), LoginActivity.class);
					startActivity(intent);
				}
				break;
			// 检查更新
			case R.id.fragment_my_checkUpdate:
				pd = new ProgressDialog(getActivity());
				pd.setTitle("提示");
				pd.setMessage("检查中...");
				new Thread(new CheckVersionTask()).start();
				pd.show();
				break;
			// 退出
			case R.id.fragment_my_back:
				Builder bundle = new AlertDialog.Builder(getActivity());
				bundle.setTitle("校帮");
				bundle.setMessage("确认退出?");
				bundle.setPositiveButton("确认",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO 自动生成的方法存根
								SharedPreferences spData = getActivity()
										.getSharedPreferences("userinfo",
												Context.MODE_PRIVATE);
								spData.edit().putString("USERPASSWORD", "")
										.commit();
								app.setUser(null);
								app.setArea(null);
								System.exit(0);
							}
						});
				bundle.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO 自动生成的方法存根
								dialog.dismiss();
							}
						});
				bundle.create().show();
				break;
			}
		}

	}

	private class CheckVersionTask implements Runnable {

		@Override
		public void run() {
			// 避免时间过长，影响用户体验
			long startTime = System.currentTimeMillis();
			// 获取旧的消息
			Message msg = Message.obtain();
			try {
				URL url = new URL(DownloadUtil.updateURL);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(5000);
				conn.setReadTimeout(5000);
				if (conn.getResponseCode() == 200) {
					InputStream is = conn.getInputStream();
					info = DownloadUtil.getUpdateInfo(is);
					if (info == null) {
						// 解析xml失败
						msg.what = DownloadUtil.PARSER_XML_ERROR;
					} else {
						// 解析成功
						msg.what = DownloadUtil.PARSER_XML_SUCCESS;
					}
				} else {
					// 服务器错误
					msg.what = DownloadUtil.SERVER_ERROR;
				}
			} catch (MalformedURLException e) {
				msg.what = DownloadUtil.URL_ERROR;
				// e.printStackTrace();
			} catch (NotFoundException e) {
				msg.what = DownloadUtil.URL_ERROR;
				// e.printStackTrace();
			} catch (IOException e) {
				msg.what = DownloadUtil.NET_WORK_ERROR;
				// e.printStackTrace();
			} finally {
				long endTime = System.currentTimeMillis();
				long dTime = endTime - startTime;
				if (dTime < 2000) {
					try {
						Thread.sleep(2000 - dTime);
					} catch (InterruptedException e) {
						// e.printStackTrace();
					}
				}
				pd.dismiss();
				handler.sendMessage(msg);
			}
		}

	}

	/**
	 * 升级的提示对话框
	 */
	private void showUpdateDialog() {
		// 此处必须使用this的context(创建对话框必须挂载在activity上)
		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setTitle("升级提醒").setMessage(info.getDescription())
				.setCancelable(false)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						dialog.dismiss();
						String apkUrl = info.getApkurl();
						// 下载进度框
						pd = new ProgressDialog(getActivity());
						pd.setTitle("发现新版本:" + info.getVersion());
						pd.setMessage("正在下载");
						pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
							public void onCancel(DialogInterface dialog) {
								// 取消下载的对话框
								AlertDialog.Builder builder = new AlertDialog.Builder(
										getActivity());
								builder.setTitle("校帮");
								builder.setMessage("确认将退出下载,取消将后台下载");
								builder.setPositiveButton("确认",
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface arg0,
													int arg1) {
												DownloadUtil.isGoOnDownload = true;
												arg0.dismiss();
											}
										});
								builder.setNegativeButton("取消",
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface arg0,
													int arg1) {
												arg0.dismiss();
											}
										});
								builder.create().show();
							}
						});
						pd.setCancelable(true);
						pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
						pd.show();
						if (Environment.getExternalStorageState().equals(
								Environment.MEDIA_MOUNTED)) {
							final File file = new File(Environment
									.getExternalStorageDirectory(),
									DownloadUtil.getFileName(apkUrl));
							load = new Thread() {

								public void run() {
									File saveFile = DownloadUtil.download(
											info.getApkurl(),
											file.getAbsolutePath(), pd);
									Message msg = Message.obtain();
									if (saveFile != null) {
										// 下载成功
										msg.what = DownloadUtil.DOWNLOAD_SUCCESS;
										msg.obj = saveFile;
									} else {
										// 下载失败
										msg.what = DownloadUtil.DOWNLOAD_FAILD;
									}
									handler.sendMessage(msg);
									isDownLoad = false;
									pd.dismiss();
								};
							};
							isDownLoad = true;
							load.start();
						} else {
							CustomToast.showToast(getActivity(), "sd卡不可用",
									2000);
						}
					}
				});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

}
