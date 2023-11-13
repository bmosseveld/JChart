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
 * The JScatterChart class, extends the JChart class.
 * @author Mees Mosseveld
 *
 */
public class JScatterChart extends JChart {
	private static final long serialVersionUID = 8319989111277698598L;
	
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
	 * Creates a scatter chart with the specified name.
	 * @param name The name of the chart.
	 */
	public JScatterChart(String name) {
		super(name);
		chartPlot = new JScatterChartPlot(name);
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
		return ((JScatterChartPlot) chartPlot).addDataPoint(dataSetName, x, y);
	}
	
	
	/**
	 * Set the line style of the specified data set.
	 * @param dataSetName The name of the data set.
	 * @param pointStyle The point style of the data set: JLineChart.POINT_STYLE_DOT, JLineChart.POINT_STYLE_SQUARE, or JLineChart.POINT_STYLE_DIAMOND
	 * @return False if the data set does not exist, otherwise true.
	 */
	public boolean chartSetPointStyle(String dataSetName, int pointStyle) {
		return ((JScatterChartPlot) chartPlot).setPointStyle(dataSetName, pointStyle);
	}
	
	
	/**
	 * Creates a predefined horizontal axis.
	 * @param minimum The minimum value on the axis.
	 * @param maximum The maximum value on the axis.
	 * @param step The step between the values on the axis.
	 * @param precision The number of digits shown behind the decimal point. When 0 the values are shown as integers.
	 */
	public void chartSetHorizontalAxis(double minimum, double maximum, double step, int precision) {
		((JScatterChartPlot) chartPlot).setHorizontalAxis(createAxis(minimum, maximum, step), precision);
	}
	
	
	/**
	 * Creates predefined horizontal axis.
	 * @param axis A list with the axis values.
	 * @param precision The number of digits shown behind the decimal point. When 0 the values are shown as integers.
	 */
	public void chartSetHorizontalAxis(List<Double> axis, int precision) {
		((JScatterChartPlot) chartPlot).setHorizontalAxis(axis, precision);
	}
	
	
	/**
	 * Sets the precision for the horizontal axis.
	 * @param precision The number of digits shown behind the decimal point. When 0 the values are shown as integers.
	 */
	public void chartSetHorizontalAxisPrecision(int precision) {
		((JScatterChartPlot) chartPlot).setHorizontalAxisPrecision(precision);
	}
	
	
	/**
	 * Sets the label of the horizontal axis.
	 * @param label The label.
	 */
	public void chartSetHorizontalAxisLabel(String label) {
		((JScatterChartPlot) chartPlot).setHorizontalAxisLabel(label);
	}
	
	
	/**
	 * Creates a predefined vertical axis.
	 * @param minimum The minimum value on the axis.
	 * @param maximum The maximum value on the axis.
	 * @param step The step between the values on the axis.
	 * @param precision The number of digits shown behind the decimal point. When 0 the values are shown as integers.
	 */
	public void chartSetVerticalAxis(double minimum, double maximum, double step, int precision) {
		((JScatterChartPlot) chartPlot).setVerticalAxis(createAxis(minimum, maximum, step), precision);
	}
	
	
	/**
	 * Creates predefined vertical axis.
	 * @param axis A list with the axis values.
	 * @param precision The number of digits shown behind the decimal point. When 0 the values are shown as integers.
	 */
	public void chartSetVerticalAxis(List<Double> axis, int precision) {
		((JScatterChartPlot) chartPlot).setVerticalAxis(axis, precision);
	}
	
	
	/**
	 * Sets the precision for the vertical axis.
	 * @param precision The number of digits shown behind the decimal point. When 0 the values are shown as integers.
	 */
	public void chartSetVerticalAxisPrecision(int precision) {
		((JScatterChartPlot) chartPlot).setVerticalAxisPrecision(precision);
	}
	
	
	/**
	 * Sets the label of the vertical axis.
	 * @param label The label.
	 */
	public void chartSetVerticalAxisLabel(String label) {
		((JScatterChartPlot) chartPlot).setVerticalAxisLabel(label);
	}
	
	
	/**
	 * Sets the color of the axes.
	 * @param color The color.
	 */
	public void chartSetAxisColor(Color color) {
		((JScatterChartPlot) chartPlot).setAxisColor(color);
	}
	
	
	/**
	 * Set the font of the axes.
	 * @param font The font.
	 */
	public void chartSetAxisFont(Font font) {
		((JScatterChartPlot) chartPlot).setAxisFont(font);
	}

}
