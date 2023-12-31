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
 * Use this class to create a line plot.
 * @author Mees Mosseveld
 *
 */
public class JLineChart extends JChart {
	private static final long serialVersionUID = -7368689193402892710L;
	
	/**
	 * Point style dot.
	 */
	public static final int POINT_STYLE_DOT     = 0;
	
	/**
	 * Point style square.
	 */
	public static final int POINT_STYLE_SQUARE  = 1;
	
	/**
	 * Point style diamond.
	 */
	public static final int POINT_STYLE_DIAMOND = 2;
	
	/**
	 * Point style none.
	 */
	public static final int POINT_STYLE_NONE    = 3;
	
	/**
	 * Line style line.
	 */
	public static final int LINE_CHART_STYLE_LINE    = 0;
	
	/**
	 * Line style stacked area.
	 */
	public static final int LINE_CHART_STYLE_STACKED = 1;
	
	
	
	/**
	 * Creates a line chart with the specified name.
	 * @param name The name of the chart.
	 * @param lineChartStyle The style of the line chart: JLineChart.LINE_CHART_STYLE_LINE, or LINE_CHART_STYLE_STACKED. JLineChart.LINE_CHART_STYLE_STACKED forces line style to JLineChart.LINE_STYLE_NONE.
	 */
	public JLineChart(String name, int lineChartStyle) {
		super(name);
		chartPlot = new JLineChartPlot(name, lineChartStyle);
		add(chartPlot, BorderLayout.CENTER);
	}
	
	
	/**
	 * Add a data point to the specified data set.
	 * @param dataSetName The name of the data set.
	 * @param x The x value of the data point.
	 * @param y The y value of the data point.
	 * @return False if the data set does not exist, otherwise true.
	 */
	public boolean chartAddDataPoint(String dataSetName, double x, double y) {
		return ((JLineChartPlot) chartPlot).addDataPoint(dataSetName, x, y);
	}
	
	
	/**
	 * Set the line style of the specified data set.
	 * @param dataSetName The name of the data set.
	 * @param pointStyle The line style of the data set: JLineChart.POINT_STYLE_DOT, JLineChart.POINT_STYLE_SQUARE, JLineChart.POINT_STYLE_DIAMOND, or JLineChart.POINT_STYLE_NONE
	 * @return False if the data set does not exist, otherwise true.
	 */
	public boolean chartSetLineStyle(String dataSetName, int pointStyle) {
		return ((JLineChartPlot) chartPlot).setLineStyle(dataSetName, pointStyle);
	}
	
	
	/**
	 * Creates a predefined horizontal axis.
	 * @param minimum The minimum value on the axis.
	 * @param maximum The maximum value on the axis.
	 * @param step The step between the values on the axis.
	 * @param precision The number of digits shown behind the decimal point. When 0 the values are shown as integers.
	 */
	public void chartSetHorizontalAxis(double minimum, double maximum, double step, int precision) {
		((JLineChartPlot) chartPlot).setHorizontalAxis(createAxis(minimum, maximum, step), precision);
	}
	
	
	/**
	 * Creates predefined horizontal axis.
	 * @param axis A list with the axis values.
	 * @param precision The number of digits shown behind the decimal point. When 0 the values are shown as integers.
	 */
	public void chartSetHorizontalAxis(List<Double> axis, int precision) {
		((JLineChartPlot) chartPlot).setHorizontalAxis(axis, precision);
	}
	
	
	/**
	 * Sets the precision for the horizontal axis.
	 * @param precision The number of digits shown behind the decimal point. When 0 the values are shown as integers.
	 */
	public void chartSetHorizontalAxisPrecision(int precision) {
		((JLineChartPlot) chartPlot).setHorizontalAxisPrecision(precision);
	}
	
	
	/**
	 * Sets the label of the horizontal axis.
	 * @param label The label.
	 */
	public void chartSetHorizontalAxisLabel(String label) {
		((JLineChartPlot) chartPlot).setHorizontalAxisLabel(label);
	}
	
	
	/**
	 * Creates a predefined vertical axis.
	 * @param minimum The minimum value on the axis.
	 * @param maximum The maximum value on the axis.
	 * @param step The step between the values on the axis.
	 * @param precision The number of digits shown behind the decimal point. When 0 the values are shown as integers.
	 */
	public void chartSetVerticalAxis(double minimum, double maximum, double step, int precision) {
		((JLineChartPlot) chartPlot).setVerticalAxis(createAxis(minimum, maximum, step), precision);
	}
	
	
	/**
	 * Creates predefined vertical axis.
	 * @param axis A list with the axis values.
	 * @param precision The number of digits shown behind the decimal point. When 0 the values are shown as integers.
	 */
	public void chartSetVerticalAxis(List<Double> axis, int precision) {
		((JLineChartPlot) chartPlot).setVerticalAxis(axis, precision);
	}
	
	
	/**
	 * Sets the precision for the vertical axis.
	 * @param precision The number of digits shown behind the decimal point. When 0 the values are shown as integers.
	 */
	public void chartSetVerticalAxisPrecision(int precision) {
		((JLineChartPlot) chartPlot).setVerticalAxisPrecision(precision);
	}
	
	
	/**
	 * Sets the label of the vertical axis.
	 * @param label The label.
	 */
	public void chartSetVerticalAxisLabel(String label) {
		((JLineChartPlot) chartPlot).setVerticalAxisLabel(label);
	}
	
	
	/**
	 * Sets the color of the axes.
	 * @param color The color.
	 */
	public void chartSetAxisColor(Color color) {
		((JLineChartPlot) chartPlot).setAxisColor(color);
	}
	
	
	/**
	 * Set the font of the axes.
	 * @param font The font.
	 */
	public void chartSetAxisFont(Font font) {
		((JLineChartPlot) chartPlot).setAxisFont(font);
	}

}
