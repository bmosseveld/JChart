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


/**
 * This is the class of the pie chart.
 * @author Mees Mosseveld
 *
 */
public class JPieChart extends JChart {
	private static final long serialVersionUID = 5259909585084828307L;


	/**
	 * Creates a pie chart with the specified name.
	 * @param name The name of the chart.
	 */
	public JPieChart(String name) {
		super(name);
		chartPlot = new JPieChartPlot(this.name);
		add(chartPlot, BorderLayout.CENTER);
	}
	
	
	/**
	 * Add a data point to the specified data set.
	 * @param dataSetName The name of the data set.
	 * @param value The value for the specified data set.
	 * @return False if the data set does not exist, otherwise true.
	 */
	public boolean chartAddDataPoint(String dataSetName, double value) {
		return ((JPieChartPlot) chartPlot).addDataPoint(dataSetName, value);
	}
	
	
	/**
	 * Sets the precision for the values.
	 * @param precision The number of digits shown behind the decimal point. When 0 the values are shown as integers.
	 */
	public void chartSetValuePrecision(int precision) {
		((JPieChartPlot) chartPlot).setValuePrecision(precision);
	}
	
	
	/**
	 * Sets the unit description for the values.
	 * @param unitDescription The unit description.
	 */
	public void chartSetUnitDescription(String unitDescription) {
		((JPieChartPlot) chartPlot).setUnitDescription(unitDescription);
	}
	
	
	/**
	 * Sets the color of the value labels.
	 * @param color The color.
	 */
	public void chartSetLabelColor(Color color) {
		((JPieChartPlot) chartPlot).setLabelColor(color);
	}
	
	
	/**
	 * Sets the font of the value labeles.
	 * @param font The font.
	 */
	public void chartSetLabelFont(Font font) {
		((JPieChartPlot) chartPlot).setLabelFont(font);
	}
	
	
	/**
	 * Sets the color of the outline of the pie chart.
	 * Default is black.
	 * @param color The color.
	 */
	public void chartSetOutlineColor(Color color) {
		((JPieChartPlot) chartPlot).setOutlineColor(color);
	}

}
