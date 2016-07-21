package com.fastfood.thread;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import com.bean.GoodsBean;
import com.fastfood.utils.HttpUtil;
import com.fastfood.utils.JsonUtil;
import android.os.Handler;
import android.os.Message;

public class ShoppingCartThread extends Thread {

	public static final int SUCCESS = 801;
	public static final int NETWORK_ERROR = 800;

	private Handler handler;
	private List<GoodsBean> goodsInfo;

	public ShoppingCartThread(Handler handler,
			List<GoodsBean> goodsInfo) {
		super();
		this.handler = handler;
		this.goodsInfo = goodsInfo;
	}

	@Override
	public void run() {
		Map<String, String> rawParams = new HashMap<String, String>();
		String strb = "";
		if (goodsInfo.size() > 1) {
			int i;
			strb += "[";
			for (i = 0; i < goodsInfo.size() - 1; i++) {
				strb += "{" + "'goodsId':" + goodsInfo.get(i).getGoodsId()
						+ "," + "'goodsCount':"
						+ goodsInfo.get(i).getGoodsCount() + "}" + ",";
			}
			strb += "{" + "'goodsId':" + goodsInfo.get(i).getGoodsId()
					+ "," + "'goodsCount':"
					+ goodsInfo.get(i).getGoodsCount() + "}" + "]";
		} else {
			strb = "[" + "{" + "'goodsId':" + goodsInfo.get(0).getGoodsId()
					+ "," + "'goodsCount':"
					+ goodsInfo.get(0).getGoodsCount() + "}" + "]";
		}
		rawParams.put("cartListStr", strb + "");
		String result = HttpUtil.queryStringForPost(HttpUtil.SHOPPING_CART,
				rawParams);
		if (result == null) {
			handler.sendEmptyMessage(NETWORK_ERROR);
		} else {
			Map<String, Object> data = new HashMap<String, Object>();
			data = JsonUtil.getShoppingCart(result);
			Message msg = Message.obtain();
			msg.what = SUCCESS;
			msg.obj = data;
			handler.sendMessage(msg);
		}
		super.run();
	}

}
