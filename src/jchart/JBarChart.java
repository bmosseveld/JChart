/*********************************************************************************
 *                                                                               *
 * Copyright (C) 2022 Mees Mosseveld, Ouderkerk aan den IJssel, The Netherlands. *
 *                                                                               *
 * The JChart library is free software.                                          *
 * The JChart library is distributed in the hope that it will be useful, but     *
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY    *
 * or FITNESS FOR A PARTICULAR PURPOSE.                                          *
 *                                                                               *
 * This file is part of the JChart library.                                      *
 *                                                                               *
 *********************************************************************************/

package jchart;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.List;


/**
 * The JBarChart class, extends the JChart class.
 * @author Mees Mosseveld
 *
 */
public class JBarChart extends JChart {
	private static final long serialVersionUID = -3531649545408358060L;
	
	/**
	 * Vertical 2D bar chart.
	 */
	public static final int BAR_CHART_STYLE_VERTICAL              = 0;
	
	/**
	 * Horizontal 2D bar chart.
	 */
	public static final int BAR_CHART_STYLE_HORIZONTAL            = 1;
	
	/**
	 * Vertical 2D stacked bar chart.
	 */
	public static final int BAR_CHART_STYLE_STACKED_VERTICAL      = 2;
	
	/**
	 * Horizontal 2D stacked bar chart.
	 */
	public static final int BAR_CHART_STYLE_STACKED_HORIZONTAL    = 3;
	
	/**
	 * Vertical 3D bar chart.
	 */
	public static final int BAR_CHART_STYLE_3D_VERTICAL           = 4;
	
	/**
	 * Horizontal 3D bar chart.
	 */
	public static final int BAR_CHART_STYLE_3D_HORIZONTAL         = 5;
	
	/**
	 * Vertical 3D stacked bar chart.
	 */
	public static final int BAR_CHART_STYLE_3D_STACKED_VERTICAL   = 6;
	
	/**
	 * Horizontal 3D stacked bar chart.
	 */
	public static final int BAR_CHART_STYLE_3D_STACKED_HORIZONTAL = 7;


	/**
	 * Creates a bar chart with the specified name.
	 * @param name The name of the chart.
	 * @param barChartStyle The style of the bar chart: JBarChart.BAR_CHART_STYLE_VERTICAL, JBarChart.BAR_CHART_STYLE_HORIZONTAL, JBarChart.BAR_CHART_STYLE_STACKED_VERTICAL, JBarChart.BAR_CHART_STYLE_STACKED_HORIZONTAL, JBarChart.BAR_CHART_STYLE_3D_VERTICAL, JBarChart.BAR_CHART_STYLE_3D_HORIZONTAL, JBarChart.BAR_CHART_STYLE_3D_STACKED_VERTICAL, or JBarChart.BAR_CHART_STYLE_3D_STACKED_HORIZONTAL.
	 */
	public JBarChart(String name, int barChartStyle) {
		super(name);
		chartPlot = new JBarChartPlot(name, barChartStyle);
		add(chartPlot, BorderLayout.CENTER);
	}
	
	
	/**
	 * Add a data point to the bar chart.
	 * The values of the different data sets are grouped per bucket.
	 * @param dataSetName The name of the data set.
	 * @param bucket The name of bucket.
	 * @param bucketValue The value of the data set for the specified bucket.
	 * @return False if the data set does not exist, otherwise true.
	 */
	public boolean chartAddDataPoint(String dataSetName, String bucket, double bucketValue) {
		return ((JBarChartPlot) chartPlot).addDataPoint(dataSetName, bucket, bucketValue);
	}
	
	
	/**
	 * Creates predefined bucket axis.
	 * @param axis A list with the bucket names.
	 */
	public void chartSetBucketAxis(List<String> axis) {
		((JBarChartPlot) chartPlot).setBucketAxis(axis);
	}
	
	
	/**
	 * Sets the label of the bucket axis.
	 * @param label The label.
	 */
	public void chartSetBucketAxisLabel(String label) {
		((JBarChartPlot) chartPlot).setBucketAxisLabel(label);
	}
	
	
	/**
	 * Creates a predefined value axis.
	 * @param minimum The minimum value on the axis.
	 * @param maximum The maximum value on the axis.
	 * @param step The step between the values on the axis.
	 * @param precision The number of digits shown behind the decimal point. When 0 the values are shown as integers.
	 */
	public void chartSetValueAxis(double minimum, double maximum, double step, int precision) {
		((JBarChartPlot) chartPlot).setValueAxis(createAxis(minimum, maximum, step), precision);
	}
	
	
	/**
	 * Creates predefined value axis.
	 * @param axis A list with the axis values.
	 * @param precision The number of digits shown behind the decimal point. When 0 the values are shown as integers.
	 */
	public void chartSetValueAxis(List<Double> axis, int precision) {
		((JBarChartPlot) chartPlot).setValueAxis(axis, precision);
	}
	
	
	/**
	 * Sets the precision for the value axis.
	 * @param precision The number of digits shown behind the decimal point. When 0 the values are shown as integers.
	 */
	public void chartSetValueAxisPrecision(int precision) {
		((JBarChartPlot) chartPlot).setValueAxisPrecision(precision);
	}
	
	
	/**
	 * Sets the label of the value axis.
	 * @param label The label.
	 */
	public void chartSetValueAxisLabel(String label) {
		((JBarChartPlot) chartPlot).setValueAxisLabel(label);
	}
	
	
	/**
	 * Sets the color of the axes.
	 * @param color The color.
	 */
	public void chartSetAxisColor(Color color) {
		((JBarChartPlot) chartPlot).setAxisColor(color);
	}
	
	
	/**
	 * Set the font of the axes.
	 * @param font The font.
	 */
	public void chartSetAxisFont(Font font) {
		((JBarChartPlot) chartPlot).setAxisFont(font);
	}

}
