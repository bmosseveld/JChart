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
 * Use this class to create a box plot.
 * @author Mees Mosseveld
 *
 */
public class JBoxChart extends JChart {
	private static final long serialVersionUID = 5138129585655295705L;
	
	/**
	 * Vertical box chart.
	 */
	public static final int BOX_CHART_STYLE_VERTICAL   = 0;
	
	/**
	 * Horizontal box chart.
	 */
	public static final int BOX_CHART_STYLE_HORIZONTAL = 1;

	/**
	 * Create JBoxChart.
	 * @param name The name of the box chart.
	 * @param boxChartStyle The style of the box chart, BOX_CHART_STYLE_VERTICAL or BOX_CHART_STYLE_HORIZONTAL.
	 */
	public JBoxChart(String name, int boxChartStyle) {
		super(name);
		chartPlot = new JBoxChartPlot(name, boxChartStyle);
		add(chartPlot, BorderLayout.CENTER);
	}
	
	
	/**
	 * Add a data point to the specified data set.
	 * @param dataSetName The name of the data set.
	 * @param value A value in the data set.
	 * @return False if the data set does not exist, otherwise true.
	 */
	public boolean chartAddDataPoint(String dataSetName, double value) {
		return ((JBoxChartPlot) chartPlot).addDataPoint(dataSetName, value);
	}
	
	
	/**
	 * Creates predefined bucket axis.
	 * @param axis A list with the bucket names.
	 */
	public void chartSetDataSetAxis(List<String> axis) {
		((JBoxChartPlot) chartPlot).setDataSetAxis(axis);
	}
	
	
	/**
	 * Sets the label of the bucket axis.
	 * @param label The label.
	 */
	public void chartSetDataSetAxisLabel(String label) {
		((JBoxChartPlot) chartPlot).setDataSetAxisLabel(label);
	}
	
	
	/**
	 * Creates a predefined value axis.
	 * @param minimum The minimum value on the axis.
	 * @param maximum The maximum value on the axis.
	 * @param step The step between the values on the axis.
	 * @param precision The number of digits shown behind the decimal point. When 0 the values are shown as integers.
	 */
	public void chartSetValueAxis(double minimum, double maximum, double step, int precision) {
		((JBoxChartPlot) chartPlot).setValueAxis(createAxis(minimum, maximum, step), precision);
	}
	
	
	/**
	 * Creates predefined value axis.
	 * @param axis A list with the axis values.
	 * @param precision The number of digits shown behind the decimal point. When 0 the values are shown as integers.
	 */
	public void chartSetValueAxis(List<Double> axis, int precision) {
		((JBoxChartPlot) chartPlot).setValueAxis(axis, precision);
	}
	
	
	/**
	 * Sets the precision for the value axis.
	 * @param precision The number of digits shown behind the decimal point. When 0 the values are shown as integers.
	 */
	public void chartSetValueAxisPrecision(int precision) {
		((JBoxChartPlot) chartPlot).setValueAxisPrecision(precision);
	}
	
	
	/**
	 * Sets the label of the value axis.
	 * @param label The label.
	 */
	public void chartSetValueAxisLabel(String label) {
		((JBoxChartPlot) chartPlot).setValueAxisLabel(label);
	}
	
	
	/**
	 * Sets the color of the axes.
	 * @param color The color.
	 */
	public void chartSetAxisColor(Color color) {
		((JBoxChartPlot) chartPlot).setAxisColor(color);
	}
	
	
	/**
	 * Set the font of the axes.
	 * @param font The font.
	 */
	public void chartSetAxisFont(Font font) {
		((JBoxChartPlot) chartPlot).setAxisFont(font);
	}
	
	
	/**
	 * Set the maximum width in pixels of the boxes in the box chart.
	 * @param maxBoxWidth The maximum width (thickness) of the boxes in pixels.
	 */
	public void setMaxBoxWidth(int maxBoxWidth) {
		((JBoxChartPlot) chartPlot).setMaxBoxWidth(maxBoxWidth);
	}

}
