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

/*********************************************************************************
 * JChart library                                                                *
 *                                                                               *
 * Author : Mees Mosseveld                                                       *
 * Version: 1.2.0                                                                *
 *                                                                               *
 * The library consists of four classes:                                         *
 *                                                                               *
 * JChart extends JPanel and is the abstract main class of the library.          *
 * JLineChart extends JChart is the class for creating line charts.              *
 * JBarChart extends JChart is the class for creating bar charts.                *
 * JPieChart extends JChart is the class for creating pie charts.                *
 * JScatterChart extends JChart is the class for creating scatter charts.        *
 * JBoxChart extends JChart is the class for creating box charts.                *
 *                                                                               *
 *********************************************************************************/
package jchart;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

/**
 * This the main abstract class of the JChart library and is an extension
 * of the JPanel class.
 * @author Mees  Mosseveld
 *
 */
public abstract class JChart extends JPanel {
	private static final long serialVersionUID = -3612624162463792308L;
	
	
	private static final String VERSION = "1.0.0";
	
	
	public static final int TITLE_ALIGNMENT_LEFT   = 0;
	public static final int TITLE_ALIGNMENT_CENTER = 1;
	public static final int TITLE_ALIGNMENT_RIGHT  = 2;
	
	public static final int TITLE_POSITION_TOP     = 0;
	public static final int TITLE_POSITION_BOTTOM  = 1;

	
	protected String name = null;
	protected JChartPlot chartPlot = null;
	
	
	JChart(String name) {
		super();
		
		setLayout(new BorderLayout());
		
		this.name = (name == null ? "<NO NAME>" : name);
	}
	
	
	/**
	 * Get the version of the JChart library.
	 * @return The version string.
	 */
	public String chartGetVersion() {
		return "JChart-v" + VERSION;
	}
	
	
	/**
	 * Get the license of the JChart library.
	 * @return The license text.
	 */
	public String chartGetLicense() {
		String license = "Copyright (C) 2022 Mees Mosseveld, Ouderkerk aan den IJssel, The Netherlands.\n\n";
		license       += "The JChart library is free software.\n";
		license       += "The JChart library is distributed in the hope that it will be useful, but\n";
		license       += "WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY\n";
		license       += "or FITNESS FOR A PARTICULAR PURPOSE.";
		return license;
	}
	
	
	/**
	 * Get the name of the chart.
	 * @return The name of the chart.
	 */
	public String chartGetName() {
		return name;
	}
	
	
	/**
	 * Set the title of the chart.
	 * @param title The title of the chart. When null no title is shown.
	 * @param alignment The alignment of the title: JChart.TITLE_ALIGNMENT_LEFT, JChart.TITLE_ALIGNMENT_CENTER or JChart.TITLE_ALIGNMENT_RIGHT
	 * @param position The position of the title: JChart.TITLE_POSITION_TOP or JChart.TITLE_POSITION_BOTTOM 
	 */
	public void chartSetTitle(String title, int alignment, int position) {
		chartPlot.setTitle(title, alignment, position);
	}
	
	
	/**
	 * Get the title of the chart.
	 * @return The title of the chart.
	 */
	public String chartGetTitle() {
		return chartPlot.getTitle();
	}
	
	
	/**
	 * Set the font of the title.
	 * @param font The font.
	 */
	public void chartSetTitleFont(Font font) {
		chartPlot.setTitleFont(font);
	}
	
	
	/**
	 * Set the color of the title.
	 * @param color The color.
	 */
	public void chartSetTitleColor(Color color) {
		chartPlot.setTitleColor(color);
	}
	
	
	/**
	 * Enable or disable the legend of the chart.
	 * @param enabled When true, the legend will be shown. When false, the legend will not be shown.
	 */
	public void chartSetLegendEnabled(boolean enabled) {
		chartPlot.setLegendEnabled(enabled);
	}
	
	
	/**
	 * Set the font of the legend.
	 * @param font The font.
	 */
	public void chartSetLegendFont(Font font) {
		chartPlot.setLegendFont(font);
	}
	
	
	/**
	 * Set the text color of the legend.
	 * @param color The color.
	 */
	public void chartSetlegendFontColor(Color color) {
		chartPlot.setLegendFontColor(color);
	}
	
	
	/**
	 * Set the background color of the legend.
	 * @param color The color.
	 */
	public void chartSetlegendBackgroundColor(Color color) {
		chartPlot.setLegendBackgroundColor(color);
	}
	
	
	/**
	 * Set the border color of the legend.
	 * @param color The color.
	 */
	public void chartSetlegendBorderColor(Color  color) {
		chartPlot.setLegendBorderColor(color);
	}
	
	
	/**
	 * Set the background color of the chart.
	 * @param color The color.
	 */
	public void chartSetBackgroundColor(Color color) {
		chartPlot.setBackground(color);
	}
	
	
	/**
	 * Add a data set to the chart. A data set is a set of points or values that represent the same entity.
	 * @param dataSetName The name of the data set
	 * @param color The color used in the chart for the data set.
	 * @return False if there is already a data set with the specified name, false otherwise.
	 */
	public boolean chartAddDataSet(String dataSetName, Color color) {
		return chartPlot.addDataSet(dataSetName, color);
	}
	
	
	/**
	 * Specifies if the data sets should be ordered by name.
	 * Default is the order by which they are added to the chart.
	 * @param ordered True or False.
	 */
	public void chartSetOrderedDataSets(Boolean ordered) {
		chartPlot.setOrderedDataSets(ordered);
	}
	
	
	/**
	 * Clears the data of the chart.
	 */
	public void chartClear() {
		chartPlot.clear();
	}
	
	
	protected List<Double> createAxis(double minimum, double maximum, double step) {
		List<Double> axis = new ArrayList<Double>();
		
		double value = minimum;
		while (value < maximum) {
			axis.add(value);
			value += step;
		}
		axis.add(maximum);
		
		return axis;
	}
}
