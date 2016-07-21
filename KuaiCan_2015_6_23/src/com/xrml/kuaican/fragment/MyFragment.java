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
 * ��ҳ���ĸ�Fragment֮һ���ҵ�
 * 
 * @author Administrator
 * 
 */
public class MyFragment extends Fragment {
	App app;
	private View rootView;
	private TextView user_phoneNumb;// �û��ֻ���
	@SuppressWarnings("unused")
	private TextView user_integral;// �û�����
	private UpdateInfo info;
	// �Ƿ������°汾
	public boolean isDownLoad = false;
	// �����߳�
	public Thread load;
	private ProgressDialog pd;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DownloadUtil.PARSER_XML_ERROR:
				CustomToast.showToast(getActivity(), "����xmlʧ��",
						2000);
				break;
			case DownloadUtil.SERVER_ERROR:
				CustomToast.showToast(getActivity(), "����������",
						2000);
				break;
			case DownloadUtil.URL_ERROR:
				CustomToast.showToast(getActivity(), "URL����",
						2000);
				break;
			case DownloadUtil.NET_WORK_ERROR:
				CustomToast
						.showToast(getActivity(), "�������", 2000);
				break;
			case DownloadUtil.PARSER_XML_SUCCESS:
				if (DownloadUtil.getAppVersion(getActivity()).equals(
						info.getVersion())) {
					CustomToast.showToast(getActivity(), "�Ѿ������°汾",
							2000);
				} else {
					showUpdateDialog();
				}
				break;
			case DownloadUtil.DOWNLOAD_SUCCESS:
				File file = (File) msg.obj;
				CustomToast
						.showToast(getActivity(), "���سɹ�", 2000);
				// ��װapk
				DownloadUtil.installApk(file, getActivity());
				getActivity().finish();
				break;
			case DownloadUtil.DOWNLOAD_FAILD:
				CustomToast
						.showToast(getActivity(), "����ʧ��", 2000);
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

		// �����û��ֻ���
		user_phoneNumb = (TextView) rootView
				.findViewById(R.id.fragment_my_user_phoneNumb);
		if (app.getUser() == null) {
			user_phoneNumb.setText("");
		} else {
			user_phoneNumb.setText(app.getUser().getPhone());
		}
		// �����û�����
		user_integral = (TextView) rootView
				.findViewById(R.id.fragment_my_user_integral);
		// user_integral.setText("����:" + "");
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
			// �޸�����
			case R.id.fragment_my_change_password:
				if (app.getUser() != null) {
					intent.setClass(getActivity(), ChangePassword.class);
					startActivity(intent);
				} else {
					CustomToast.showToast(getActivity(), "���ȵ�¼",
							2000);
					intent.setClass(getActivity(), LoginActivity.class);
					startActivity(intent);
				}
				break;
			// �ҵĶ���
			case R.id.fragment_my_myOrder:
				if (app.getUser() != null) {
					intent.setClass(getActivity(), MyOrderActivity.class);
					startActivity(intent);
				} else {
					CustomToast.showToast(getActivity(), "���ȵ�¼",
							2000);
					intent.setClass(getActivity(), LoginActivity.class);
					startActivity(intent);
				}
				break;
			// �ջ���ַ
			case R.id.fragment_my_add:
				if (app.getUser() != null) {
					intent.setClass(getActivity(), MyOrderAddress.class);
					startActivity(intent);
				} else {
					CustomToast.showToast(getActivity(), "���ȵ�¼",
							2000);
					intent.setClass(getActivity(), LoginActivity.class);
					startActivity(intent);
				}
				break;
			// ʹ��˵��
			case R.id.fragment_my_orderExplain:
				if (app.getArea() == null
						|| app.getArea().getCurrAreaName() == null
						|| app.getArea().getCurrAreaName().equals("")) {
					CustomToast.showToast(getActivity(), "����ѡ������",
							2000);
					intent.setClass(getActivity(), ChooseCity.class);
					startActivity(intent);
				} else {
					intent.setClass(getActivity(), OrderExplain.class);
					startActivity(intent);
				}
				break;
			// �������
			case R.id.fragment_my_feedBack:
				if (app.getUser() != null) {
					intent.setClass(getActivity(), FeedBack.class);
					startActivity(intent);
				} else {
					CustomToast.showToast(getActivity(), "���ȵ�¼",
							2000);
					intent.setClass(getActivity(), LoginActivity.class);
					startActivity(intent);
				}
				break;
			// ������
			case R.id.fragment_my_checkUpdate:
				pd = new ProgressDialog(getActivity());
				pd.setTitle("��ʾ");
				pd.setMessage("�����...");
				new Thread(new CheckVersionTask()).start();
				pd.show();
				break;
			// �˳�
			case R.id.fragment_my_back:
				Builder bundle = new AlertDialog.Builder(getActivity());
				bundle.setTitle("У��");
				bundle.setMessage("ȷ���˳�?");
				bundle.setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO �Զ����ɵķ������
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
				bundle.setNegativeButton("ȡ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO �Զ����ɵķ������
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
			// ����ʱ�������Ӱ���û�����
			long startTime = System.currentTimeMillis();
			// ��ȡ�ɵ���Ϣ
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
						// ����xmlʧ��
						msg.what = DownloadUtil.PARSER_XML_ERROR;
					} else {
						// �����ɹ�
						msg.what = DownloadUtil.PARSER_XML_SUCCESS;
					}
				} else {
					// ����������
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
	 * ��������ʾ�Ի���
	 */
	private void showUpdateDialog() {
		// �˴�����ʹ��this��context(�����Ի�����������activity��)
		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setTitle("��������").setMessage(info.getDescription())
				.setCancelable(false)
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						dialog.dismiss();
						String apkUrl = info.getApkurl();
						// ���ؽ��ȿ�
						pd = new ProgressDialog(getActivity());
						pd.setTitle("�����°汾:" + info.getVersion());
						pd.setMessage("��������");
						pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
							public void onCancel(DialogInterface dialog) {
								// ȡ�����صĶԻ���
								AlertDialog.Builder builder = new AlertDialog.Builder(
										getActivity());
								builder.setTitle("У��");
								builder.setMessage("ȷ�Ͻ��˳�����,ȡ������̨����");
								builder.setPositiveButton("ȷ��",
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface arg0,
													int arg1) {
												DownloadUtil.isGoOnDownload = true;
												arg0.dismiss();
											}
										});
								builder.setNegativeButton("ȡ��",
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
										// ���سɹ�
										msg.what = DownloadUtil.DOWNLOAD_SUCCESS;
										msg.obj = saveFile;
									} else {
										// ����ʧ��
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
							CustomToast.showToast(getActivity(), "sd��������",
									2000);
						}
					}
				});
		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

}
