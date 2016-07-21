package com.qlf.plants.graph;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.view.View;

public class GraphUtils {
	private static GraphUtils graph;
	private static ArrayList<HashMap<String, StudentGradeMessage>> stuGradeList = new ArrayList<HashMap<String, StudentGradeMessage>>();

	public static GraphUtils getInstance() {
		if (graph == null) {
			graph = new GraphUtils();
		}
		return graph;
	}

	

	/**
	 * 圆滑单曲线 
	 * @param context
	 * @param 
	 * @param tag
	 * @return
	 */
	public static View getLineChartView(Context context,
			List<HashMap<String, StudentGradeMessage>> studentGradeList,
			String tag) {

		stuGradeList = (ArrayList<HashMap<String, StudentGradeMessage>>) studentGradeList;
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.setBackgroundColor(Color.parseColor("#76bfd1"));
		renderer.setApplyBackgroundColor(true);
		
		renderer.setMarginsColor(Color.parseColor("#76bfd1"));
		renderer.setPanEnabled(false, false); 
		renderer.setLabelsTextSize(20f);
		//renderer.setMargins(new int[] {20, 55, 15,5}); 
		renderer.setYAxisMin(0);
		renderer.setXLabels(0);
		renderer.setShowGrid(false); 
	   // renderer.setGridColor(Color.parseColor("#eeeeee"));
		renderer.setPointSize(5f);
		Align align = renderer.getYAxisAlign(0);
		renderer.setYLabelsAlign(align);
		renderer.setYLabelsColor(0, Color.WHITE);
		renderer.setYLabels(6);
		renderer.setYAxisMin(10);
		renderer.setYAxisMax(100);
		renderer.setXAxisMin(0.1);
		renderer.setXAxisMax(10.5);
		renderer.setXLabelsColor(Color.WHITE);
		renderer.setAxesColor(Color.WHITE);
		renderer.setYLabelsAlign(Align.RIGHT);
		renderer.addYTextLabel(100, String.valueOf("100"));
		renderer.addYTextLabel(80, String.valueOf("80"));
		renderer.addYTextLabel(60, String.valueOf("60"));
		renderer.addYTextLabel(40,String.valueOf("40"));
		renderer.addYTextLabel(20,String.valueOf("20"));
		int j = 0;
		for (int i = 0; i < 10;i++) {
		
//			String name = map.get("name").getName().toString();
			renderer.addTextLabel(j, j+"");
			j++;
		}
		XYMultipleSeriesDataset dataset = getXYMultipleSeriesDataset(tag);
		XYSeriesRenderer xyRenderer = new XYSeriesRenderer();
		xyRenderer.setColor(Color.parseColor("#ffffff"));
		xyRenderer.setLineWidth(2f);
		xyRenderer.setDisplayChartValues(true);
		xyRenderer.setChartValuesTextSize(18f);
		xyRenderer.setDisplayChartValuesDistance(30);
		xyRenderer.setPointStyle(PointStyle.CIRCLE);
		xyRenderer.setFillBelowLine(true);
		xyRenderer.setFillBelowLineColor(Color.parseColor("#79e2e8"));
		xyRenderer.setFillPoints(true);
		
		renderer.addSeriesRenderer(xyRenderer);
		
		return ChartFactory.getCubeLineChartView(context, dataset, renderer,0.33f); 

	}

	public static XYMultipleSeriesDataset getXYMultipleSeriesDataset(String tag) {

		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		if ("A".equals(tag)) {
			XYSeries series = new XYSeries("A");
			for (int i = 1; i < stuGradeList.size() + 1; i++) {
				series.add(
						Double.valueOf(i + ""),
						Double.valueOf(stuGradeList.get(i - 1).get("name")
								.getChinese()));

			}
			dataset.addSeries(series);
		} else if ("B".equals(tag)) {
			XYSeries series = new XYSeries("B");
			for (int i = 1; i < stuGradeList.size() + 1; i++) {
				series.add(
						Double.valueOf(i + ""),
						Double.valueOf(stuGradeList.get(i - 1).get("name")
								.getMath()));
			}
			dataset.addSeries(series);
		} else if ("C".equals(tag)) {
			XYSeries series = new XYSeries("C");
			for (int i = 1; i < stuGradeList.size() + 1; i++) {
				series.add(
						Double.valueOf(i + ""),
						Double.valueOf(stuGradeList.get(i - 1).get("name")
								.getEnglish()));
			}
			dataset.addSeries(series);
		} else {
			XYSeries series = new XYSeries("D");
			for (int i = 1; i < stuGradeList.size() + 1; i++) {
				series.add(
						Double.valueOf(i + ""),
						Double.valueOf(stuGradeList.get(i - 1).get("name")
								.getTotal()));
			}
			dataset.addSeries(series);
		}
		return dataset;
	}

	
	
}
