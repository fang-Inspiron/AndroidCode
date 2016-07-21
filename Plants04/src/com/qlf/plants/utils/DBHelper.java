package com.qlf.plants.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import com.qlf.plants.R;
import com.qlf.plants.bean.PlantBean;

public class DBHelper {
	private final String DATABASE_PATH = android.os.Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/dictionary";
	private final String DATABASE_FILENAME = "dictionary.db3";
	private Context context;
	SQLiteDatabase database;
	Cursor cursor;

	public DBHelper(Context context) {
		this.context = context;
		database = openDatabase();
	}

	private SQLiteDatabase openDatabase() {
		try {
			// 获得dictionary.db文件的绝对路径
			String databaseFilename = DATABASE_PATH + "/" + DATABASE_FILENAME;
			File dir = new File(DATABASE_PATH);
			// 如果/sdcard/dictionary目录中存在，创建这个目录
			if (!dir.exists())
				dir.mkdir();
			// 如果在/sdcard/dictionary目录中不存在
			// dictionary.db文件，则从res\raw目录中复制这个文件到
			// SD卡的目录（/sdcard/dictionary）
			if (!(new File(databaseFilename)).exists()) {
				// 获得封装dictionary.db文件的InputStream对象
				InputStream is = context.getResources().openRawResource(
						R.raw.plantdb_zh);
				FileOutputStream fos = new FileOutputStream(databaseFilename);
				byte[] buffer = new byte[8192];
				int count = 0;
				// 开始复制dictionary.db文件
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}

				fos.close();
				is.close();
			}
			// 打开/sdcard/dictionary目录中的dictionary.db文件
			SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(
					databaseFilename, null);
			return database;
		} catch (Exception e) {
		}
		return null;
	}
	
	public Drawable getImg(String path){
		String imgFilePath = Environment.getExternalStorageDirectory().toString()
				+"/dictionary/PlantDBImages/"+path;
//		Bitmap bitmap = BitmapFactory.decodeFile(imgFilePath);
		Drawable bitmap = BitmapDrawable.createFromPath(imgFilePath);
		//SoftReference<Bitmap> softBitmap = new SoftReference<Bitmap>(bitmap);
		return bitmap;
	}

	/**
	 * 获取植物信息列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PlantBean> getPlantInfo(int start,int end) {
		List<PlantBean> list = new ArrayList<PlantBean>();
		String sql = "select * from table_plants";
		cursor = database.rawQuery(sql, null);
		cursor.move(start);
		while (start<end) {
			PlantBean bean = new PlantBean();
			String str = cursor.getString(cursor.getColumnIndex("info"));
			String imagePath = cursor.getString(cursor.getColumnIndex("image"));
			Map<String, Object> info = JSONUtil.getPlantsInfo(str,imagePath);
			if (info.containsKey("attributes")) {
				Map<String, Object> attributes = (Map<String, Object>) info
						.get("attributes");
				bean.setBloom_color(attributes.get("BL").toString());
				bean.setLeaf_color(attributes.get("FO").toString());
				bean.setPlant_type(attributes.get("PT").toString());
				bean.setSpec_features(attributes.get("SF").toString());
				bean.setPlant_shape(attributes.get("SH").toString());
				bean.setBloom_time(attributes.get("SN").toString());
			}
			if (info.containsKey("image"))
				bean.setImage(getImg(info.get("image").toString()));
			if (info.containsKey("blooming"))
				bean.setBlooming(info.get("blooming").toString());
			if (info.containsKey("characteristics")) {
				Map<String, Object> characteristics = (Map<String, Object>) info
						.get("characteristics");
				bean.setFertilizer(characteristics.get("fertilizer").toString());
				bean.setSun(characteristics.get("sun").toString());
				bean.setTemperature_max_celsius(characteristics.get(
						"temperature_max_celsius").toString());
				bean.setTemperature_min_celsius(characteristics.get(
						"temperature_min_celsius").toString());
				bean.setWater(characteristics.get("water").toString());
			}
			if (info.containsKey("common_names"))
				bean.setCommon_name(((List<Map<String, Object>>) info
						.get("common_names")).get(0).get("common_name")
						.toString());
			if (info.containsKey("description"))

				bean.setDescription_source(((Map<String, Object>) info
						.get("description")).get("source").toString());
			if (info.containsKey("description"))
				bean.setDescription_text(((Map<String, Object>) info
						.get("description")).get("text").toString());
			if (info.containsKey("genus_name"))

				bean.setGenus_name(info.get("genus_name").toString());
			if (info.containsKey("growth"))
				bean.setGrowth(info.get("growth").toString());
			if (info.containsKey("hardiness_zone_max"))
				bean.setHardiness_zone_max(info.get("hardiness_zone_max")
						.toString());
			if (info.containsKey("hardiness_zone_max_value"))
				bean.setHardiness_zone_max_value(info.get(
						"hardiness_zone_max_value").toString());
			if (info.containsKey("hardiness_zone_min"))
				bean.setHardiness_zone_min(info.get("hardiness_zone_min")
						.toString());
			if (info.containsKey("hardiness_zone_min_value"))
				bean.setHardiness_zone_min_value(info.get(
						"hardiness_zone_min_value").toString());
			if (info.containsKey("heat_zone_max"))
				bean.setHeat_zone_max(info.get("heat_zone_max").toString());
			if (info.containsKey("heat_zone_max_value"))
				bean.setHeat_zone_max_value(info.get("heat_zone_max_value")
						.toString());
			if (info.containsKey("heat_zone_min"))
				bean.setHeat_zone_min(info.get("heat_zone_min").toString());
			if (info.containsKey("heat_zone_min_value"))
				bean.setHeat_zone_min_value(info.get("heat_zone_min_value")
						.toString());
			if (info.containsKey("subspecies_name"))
				bean.setSubspecies_name(info.get("subspecies_name").toString());
			if (info.containsKey("species_name"))
				bean.setSpecies_name(info.get("species_name").toString());
			if (info.containsKey("soil_irr"))
				bean.setSoil_irr(info.get("soil_irr").toString());
			if (info.containsKey("interesting"))
				bean.setInteresting(info.get("interesting").toString());
			if (info.containsKey("pruning"))
				bean.setPruning(info.get("pruning").toString());
			if (info.containsKey("pests"))
				bean.setPests(info.get("pests").toString());
			if (info.containsKey("planting"))
				bean.setPlanting(info.get("planting").toString());
			if (info.containsKey("spread_max"))
				bean.setSpread_max(info.get("spread_max").toString());
			if (info.containsKey("spread_min"))
				bean.setSpread_min(info.get("spread_min").toString());
			if (info.containsKey("height_min"))
				bean.setHeight_min(info.get("height_min").toString());
			if (info.containsKey("height_max"))
				bean.setHeight_max(info.get("height_max").toString());
			cursor.moveToNext();
			list.add(bean);
			start++;
		}
		return list;
	}
	public List<PlantBean> getPlantInfo() {
		List<PlantBean> list = new ArrayList<PlantBean>();
		String sql = "select * from table_plants";
		cursor = database.rawQuery(sql, null);
		cursor.moveToFirst();
		int i=0;
		while (!cursor.isLast()) {
			PlantBean bean = new PlantBean();
			String str = cursor.getString(cursor.getColumnIndex("info"));
			String imagePath = cursor.getString(cursor.getColumnIndex("image"));
			Map<String, Object> info = JSONUtil.getPlantsInfo(str,imagePath);
			if (info.containsKey("attributes")) {
				Map<String, Object> attributes = (Map<String, Object>) info
						.get("attributes");
				bean.setBloom_color(attributes.get("BL").toString());
				bean.setLeaf_color(attributes.get("FO").toString());
				bean.setPlant_type(attributes.get("PT").toString());
				bean.setSpec_features(attributes.get("SF").toString());
				bean.setPlant_shape(attributes.get("SH").toString());
				bean.setBloom_time(attributes.get("SN").toString());
			}
			if (info.containsKey("image"))
				bean.setImage(getImg(info.get("image").toString()));
			if (info.containsKey("blooming"))
				bean.setBlooming(info.get("blooming").toString());
			if (info.containsKey("characteristics")) {
				Map<String, Object> characteristics = (Map<String, Object>) info
						.get("characteristics");
				bean.setFertilizer(characteristics.get("fertilizer").toString());
				bean.setSun(characteristics.get("sun").toString());
				bean.setTemperature_max_celsius(characteristics.get(
						"temperature_max_celsius").toString());
				bean.setTemperature_min_celsius(characteristics.get(
						"temperature_min_celsius").toString());
				bean.setWater(characteristics.get("water").toString());
			}
			if (info.containsKey("common_names"))
				bean.setCommon_name(((List<Map<String, Object>>) info
						.get("common_names")).get(0).get("common_name")
						.toString());
			if (info.containsKey("description"))

				bean.setDescription_source(((Map<String, Object>) info
						.get("description")).get("source").toString());
			if (info.containsKey("description"))
				bean.setDescription_text(((Map<String, Object>) info
						.get("description")).get("text").toString());
			if (info.containsKey("genus_name"))

				bean.setGenus_name(info.get("genus_name").toString());
			if (info.containsKey("growth"))
				bean.setGrowth(info.get("growth").toString());
			if (info.containsKey("hardiness_zone_max"))
				bean.setHardiness_zone_max(info.get("hardiness_zone_max")
						.toString());
			if (info.containsKey("hardiness_zone_max_value"))
				bean.setHardiness_zone_max_value(info.get(
						"hardiness_zone_max_value").toString());
			if (info.containsKey("hardiness_zone_min"))
				bean.setHardiness_zone_min(info.get("hardiness_zone_min")
						.toString());
			if (info.containsKey("hardiness_zone_min_value"))
				bean.setHardiness_zone_min_value(info.get(
						"hardiness_zone_min_value").toString());
			if (info.containsKey("heat_zone_max"))
				bean.setHeat_zone_max(info.get("heat_zone_max").toString());
			if (info.containsKey("heat_zone_max_value"))
				bean.setHeat_zone_max_value(info.get("heat_zone_max_value")
						.toString());
			if (info.containsKey("heat_zone_min"))
				bean.setHeat_zone_min(info.get("heat_zone_min").toString());
			if (info.containsKey("heat_zone_min_value"))
				bean.setHeat_zone_min_value(info.get("heat_zone_min_value")
						.toString());
			if (info.containsKey("subspecies_name"))
				bean.setSubspecies_name(info.get("subspecies_name").toString());
			if (info.containsKey("species_name"))
				bean.setSpecies_name(info.get("species_name").toString());
			if (info.containsKey("soil_irr"))
				bean.setSoil_irr(info.get("soil_irr").toString());
			if (info.containsKey("interesting"))
				bean.setInteresting(info.get("interesting").toString());
			if (info.containsKey("pruning"))
				bean.setPruning(info.get("pruning").toString());
			if (info.containsKey("pests"))
				bean.setPests(info.get("pests").toString());
			if (info.containsKey("planting"))
				bean.setPlanting(info.get("planting").toString());
			if (info.containsKey("spread_max"))
				bean.setSpread_max(info.get("spread_max").toString());
			if (info.containsKey("spread_min"))
				bean.setSpread_min(info.get("spread_min").toString());
			if (info.containsKey("height_min"))
				bean.setHeight_min(info.get("height_min").toString());
			if (info.containsKey("height_max"))
				bean.setHeight_max(info.get("height_max").toString());
			cursor.moveToNext();
			list.add(bean);
			i++;
		}
		return list;
	}
}
