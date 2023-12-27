package jchart;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This is a demo class.
 * When you create an instance of this class it creates a number of windows with all kinds of examples.
 * @author Mees Mosseveld
 *
 */
public class JChartDemo {
	
	private static final int TEST_PRECISION = 1;
	private static final boolean SHOW_LEGEND = true;
	private static final boolean SHOW_TITLE = true;
	private static final int TITLE_ALIGNMENT = JChart.TITLE_ALIGNMENT_CENTER;
	private static final int TITLE_POSITION = JChart.TITLE_POSITION_TOP;

	
	public JChartDemo() {
		
		JChart dummyChart = new JLineChart("Dummy", JLineChart.LINE_CHART_STYLE_LINE);
		System.out.println(dummyChart.chartGetVersion());
		System.out.println(dummyChart.chartGetLicense());

		showCharts("Line Charts", lineCharts());
		showCharts("Bar Charts Fixed Axis", barCharts(true, null));
		showCharts("Bar Charts Fixed Axis Max Width", barCharts(true, 20));
		showCharts("Bar Charts Dynamic Axis", barCharts(false, null));
		showCharts("Bar Charts Dynamic Axis Max Width", barCharts(false, 20));
		showCharts("Pie Charts", pieCharts());
		showCharts("Box Charts", boxCharts());
		showCharts("Scatter Charts", scatterCharts());
	}
	
	
	
	public JChartDemo(boolean github) {
		showCharts("Examples", githubExamples());
	}
	
	
	private void showCharts(String title, List<JChart> charts) {
		JFrame frame;
		
		frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		Dimension frameSize = new Dimension(charts.size() < 3 ? 465 : (charts.size() < 5 ? 930 : (charts.size() < 7 ? 1395 : 1860)), 900);
		frame.setSize(frameSize);
		frame.setMinimumSize(frameSize);
		frame.setPreferredSize(frameSize);
		
		JPanel graphicsPanel = new JPanel(new GridLayout(0, charts.size() < 3 ? 1 : (charts.size() < 5 ? 2 : (charts.size() < 7 ? 3 : (charts.size() < 9 ? 4 : 5)))));
		for (JChart chart : charts) {
			chart.setBorder(BorderFactory.createTitledBorder(chart.chartGetName()));
			graphicsPanel.add(chart);
		}

		frame.add(graphicsPanel, BorderLayout.CENTER);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	
	
	private List<JChart> lineCharts() {
		List<JChart> charts = new ArrayList<JChart>();
		
		charts.add(lineChart(false, false));
		charts.add(lineChart(true, false));
		charts.add(lineChartParabole());
		charts.add(lineChart(false, true));
		charts.add(lineChart(true, true));
		charts.add(lineChartGoniometric());

		return charts;
	}
	
	
	private List<JChart> barCharts(boolean fixedAxis, Integer maxBarWidth) {
		List<JChart> charts = new ArrayList<JChart>();

		charts.add(barChart(JBarChart.BAR_CHART_STYLE_VERTICAL, fixedAxis, maxBarWidth));
		charts.add(barChart(JBarChart.BAR_CHART_STYLE_HORIZONTAL, fixedAxis, maxBarWidth));
		charts.add(barChart(JBarChart.BAR_CHART_STYLE_STACKED_VERTICAL, fixedAxis, maxBarWidth));
		charts.add(barChart(JBarChart.BAR_CHART_STYLE_STACKED_HORIZONTAL, fixedAxis, maxBarWidth));
		charts.add(barChart(JBarChart.BAR_CHART_STYLE_3D_VERTICAL, fixedAxis, maxBarWidth));
		charts.add(barChart(JBarChart.BAR_CHART_STYLE_3D_HORIZONTAL, fixedAxis, maxBarWidth));
		charts.add(barChart(JBarChart.BAR_CHART_STYLE_3D_STACKED_VERTICAL, fixedAxis, maxBarWidth));
		charts.add(barChart(JBarChart.BAR_CHART_STYLE_3D_STACKED_HORIZONTAL, fixedAxis, maxBarWidth));

		return charts;
	}
	
	
	private List<JChart> pieCharts() {
		List<JChart> charts = new ArrayList<JChart>();

		charts.add(pieChart(JPieChart.PIE_CHART_STYLE_2D, null));
		charts.add(pieChart(JPieChart.PIE_CHART_STYLE_3D, null));
		charts.add(pieChart(JPieChart.PIE_CHART_STYLE_3D, 50));
		charts.add(pieChartMonths(JPieChart.PIE_CHART_STYLE_2D, null));
		charts.add(pieChartMonths(JPieChart.PIE_CHART_STYLE_3D, null));
		charts.add(pieChartMonths(JPieChart.PIE_CHART_STYLE_3D, 50));
		
		return charts;
	}
	
	
	private List<JChart> boxCharts() {
		List<JChart> charts = new ArrayList<JChart>();

		charts.add(boxChart(true, JBoxChart.BOX_CHART_STYLE_VERTICAL, null));
		charts.add(boxChart(false, JBoxChart.BOX_CHART_STYLE_VERTICAL, null));
		charts.add(boxChart(true, JBoxChart.BOX_CHART_STYLE_HORIZONTAL, null));
		charts.add(boxChart(false, JBoxChart.BOX_CHART_STYLE_HORIZONTAL, null));

		charts.add(boxChart(true, JBoxChart.BOX_CHART_STYLE_VERTICAL, 50));
		charts.add(boxChart(false, JBoxChart.BOX_CHART_STYLE_VERTICAL, 50));
		charts.add(boxChart(true, JBoxChart.BOX_CHART_STYLE_HORIZONTAL, 50));
		charts.add(boxChart(false, JBoxChart.BOX_CHART_STYLE_HORIZONTAL, 50));
		
		return charts;
	}
	
	
	private List<JChart> scatterCharts() {
		List<JChart> charts = new ArrayList<JChart>();
		
		charts.add(scatterChart(true, true));
		charts.add(scatterChart(false, true));
		charts.add(scatterChart(true, false));
		charts.add(scatterChart(false, false));
		
		return charts;
	}
	
	
	private List<JChart> githubExamples() {
		List<JChart> charts = new ArrayList<JChart>();

		charts.add(lineChart(true, false));
		charts.add(lineChart(true, true));
		charts.add(barChart(JBarChart.BAR_CHART_STYLE_3D_VERTICAL, false, null));
		charts.add(barChart(JBarChart.BAR_CHART_STYLE_3D_STACKED_HORIZONTAL, true, 20));
		charts.add(pieChart(JPieChart.PIE_CHART_STYLE_3D, 50));
		charts.add(boxChart(true, JBoxChart.BOX_CHART_STYLE_VERTICAL, 50));
		charts.add(boxChart(false, JBoxChart.BOX_CHART_STYLE_HORIZONTAL, null));
		charts.add(scatterChart(false, true));
		
		return charts;
	}
	
	
	private JLineChart lineChart(boolean fixedAxes, boolean stacked) {
		String name = "LineChart";
		name += (fixedAxes ? " Fixed" : " Dynamic") + " Axes";
		name += (stacked ? " Stacked" : "");
				
		JLineChart lineChart = new JLineChart(name, stacked ? JLineChart.LINE_CHART_STYLE_STACKED : JLineChart.LINE_CHART_STYLE_LINE);
		
		lineChart.chartAddDataSet("Line 1", Color.RED);
		lineChart.chartSetLineStyle("Line 1", JLineChart.POINT_STYLE_DOT);
		lineChart.chartAddDataPoint("Line 1", 0, 2);
		lineChart.chartAddDataPoint("Line 1", 1, 3);
		lineChart.chartAddDataPoint("Line 1", 2, 1);
		lineChart.chartAddDataPoint("Line 1", 3, 5);
		lineChart.chartAddDataPoint("Line 1", 4, 4);
		
		lineChart.chartAddDataSet("Line 2", Color.BLUE);
		lineChart.chartSetLineStyle("Line 2", JLineChart.POINT_STYLE_DIAMOND);
		lineChart.chartAddDataPoint("Line 2", 0, 1);
		lineChart.chartAddDataPoint("Line 2", 1, 2);
		lineChart.chartAddDataPoint("Line 2", 2, 3);
		lineChart.chartAddDataPoint("Line 2", 3, 4);
		lineChart.chartAddDataPoint("Line 2", 4, 5);
		
		lineChart.chartAddDataSet("Line 3", Color.GREEN);
		lineChart.chartSetLineStyle("Line 3", JLineChart.POINT_STYLE_SQUARE);
		lineChart.chartAddDataPoint("Line 3", 0, 3);
		lineChart.chartAddDataPoint("Line 3", 1, 1);
		lineChart.chartAddDataPoint("Line 3", 2, 2);
		lineChart.chartAddDataPoint("Line 3", 3, 4);
		lineChart.chartAddDataPoint("Line 3", 4, 5);
		
		lineChart.chartAddDataSet("Line 4", Color.YELLOW);
		lineChart.chartSetLineStyle("Line 4", JLineChart.POINT_STYLE_DOT);
		lineChart.chartAddDataPoint("Line 4", 0, 5);
		lineChart.chartAddDataPoint("Line 4", 1, 4);
		lineChart.chartAddDataPoint("Line 4", 2, 3);
		lineChart.chartAddDataPoint("Line 4", 3, 2);
		lineChart.chartAddDataPoint("Line 4", 4, 1);
		
		lineChart.chartAddDataSet("Line 5", Color.CYAN);
		lineChart.chartSetLineStyle("Line 5", JLineChart.POINT_STYLE_DIAMOND);
		lineChart.chartAddDataPoint("Line 5", 0, 4);
		lineChart.chartAddDataPoint("Line 5", 1, 2);
		lineChart.chartAddDataPoint("Line 5", 2, 5);
		lineChart.chartAddDataPoint("Line 5", 3, 3);
		lineChart.chartAddDataPoint("Line 5", 4, 1);
		
		lineChart.chartSetHorizontalAxisPrecision(0);
		lineChart.chartSetVerticalAxisPrecision(0);
		
		if (fixedAxes) {
			lineChart.chartSetHorizontalAxis(0, 4, 1, 0);
			lineChart.chartSetVerticalAxis(0, 20, 1, 0);
		}
		
		lineChart.chartSetHorizontalAxisLabel("Unit X");
		lineChart.chartSetVerticalAxisLabel("Unit Y");
		
		if (SHOW_TITLE) {
			//lineChart.setTitleFont(new Font("Arial", Font.BOLD, 15));
			lineChart.chartSetTitle(lineChart.chartGetName(), TITLE_ALIGNMENT, TITLE_POSITION);
		}
		lineChart.chartSetLegendEnabled(SHOW_LEGEND);
		lineChart.chartSetlegendBorderColor(Color.WHITE);
		lineChart.chartSetlegendBackgroundColor(Color.WHITE);
		
		return lineChart;
	}
	
	
	private JLineChart lineChartParabole() {
		// y = 5 + x^2
		JLineChart lineChart = new JLineChart("Parabole y = 5 + x^2", JLineChart.LINE_CHART_STYLE_LINE);
		
		lineChart.chartAddDataSet("y = 5 + x^2", Color.BLUE);
		for (double x = -10.0; x <= 10.0; x += 0.1) {
			lineChart.chartAddDataPoint("y = 5 + x^2", x, 5.0 + Math.pow(x, 2));
		}
		
		lineChart.chartSetHorizontalAxis(-10.0, 10, 1, 0);
		lineChart.chartSetVerticalAxis(0, 110, 10, 0);
		
		lineChart.chartSetHorizontalAxisLabel("Unit X");
		lineChart.chartSetVerticalAxisLabel("Unit Y");
		
		if (SHOW_TITLE) {
			lineChart.chartSetTitle(lineChart.chartGetName(), TITLE_ALIGNMENT, TITLE_POSITION);
		}
		lineChart.chartSetLegendEnabled(SHOW_LEGEND);
		
		return lineChart;
	}
	
	
	private JLineChart lineChartGoniometric() {
		// y = 5 + x^2
		JLineChart lineChart = new JLineChart("Sine / Cosine", JLineChart.LINE_CHART_STYLE_LINE);
		
		lineChart.chartAddDataSet("Sine", Color.BLUE);
		for (double x = 0.0; x <= 360.0; x += 1.0) {
			lineChart.chartAddDataPoint("Sine", x, Math.sin((x / 360.0) * 2 * Math.PI));
		}
		
		lineChart.chartAddDataSet("Cosine", Color.RED);
		for (double x = 0.0; x <= 360.0; x += 1.0) {
			lineChart.chartAddDataPoint("Cosine", x, Math.cos((x / 360.0) * 2 * Math.PI));
		}
		
		lineChart.chartSetHorizontalAxis(0.0, 360.0, 45, 0);
		lineChart.chartSetVerticalAxis(-1.0, 1.0, 0.5, 1);
		
		lineChart.chartSetHorizontalAxisLabel("Degrees");
		lineChart.chartSetVerticalAxisLabel("Y");
		
		if (SHOW_TITLE) {
			lineChart.chartSetTitle(lineChart.chartGetName(), TITLE_ALIGNMENT, TITLE_POSITION);
		}
		lineChart.chartSetLegendEnabled(SHOW_LEGEND);
		
		return lineChart;
	}
	
	
	private JBarChart barChart(int style, boolean fixedAxis, Integer maxBarWidth) {
		String name = "BarChart";
		name += (fixedAxis ? " Fixed" : " Dynamic") + " Axis";
		name += ((style == JBarChart.BAR_CHART_STYLE_3D_HORIZONTAL) || (style == JBarChart.BAR_CHART_STYLE_3D_VERTICAL) || (style == JBarChart.BAR_CHART_STYLE_3D_STACKED_HORIZONTAL) || (style == JBarChart.BAR_CHART_STYLE_3D_STACKED_VERTICAL)) ? " 3D" : "";
		name += ((style == JBarChart.BAR_CHART_STYLE_STACKED_HORIZONTAL) || (style == JBarChart.BAR_CHART_STYLE_3D_STACKED_HORIZONTAL) || (style == JBarChart.BAR_CHART_STYLE_STACKED_VERTICAL) || (style == JBarChart.BAR_CHART_STYLE_3D_STACKED_VERTICAL)) ? " Stacked" : "";
		name += ((style == JBarChart.BAR_CHART_STYLE_HORIZONTAL) || (style == JBarChart.BAR_CHART_STYLE_3D_HORIZONTAL) || (style == JBarChart.BAR_CHART_STYLE_STACKED_HORIZONTAL) || (style == JBarChart.BAR_CHART_STYLE_3D_STACKED_HORIZONTAL)) ? " Horizontal" : " Vertical";
		if (maxBarWidth != null) name += " Max Bar Width " + maxBarWidth + "px";
		
		JBarChart barChart = new JBarChart(name, style);
		
		barChart.chartAddDataSet("Bar 1", Color.RED);
		barChart.chartAddDataPoint("Bar 1", "A", 10);
		barChart.chartAddDataPoint("Bar 1", "B", 12);
		barChart.chartAddDataPoint("Bar 1", "C", 5);
		
		barChart.chartAddDataSet("Bar 2", Color.BLUE);
		barChart.chartAddDataPoint("Bar 2", "A", 15);
		barChart.chartAddDataPoint("Bar 2", "B", 0);
		barChart.chartAddDataPoint("Bar 2", "C", 7);
		
		List<String> hAxis = new ArrayList<String>();
		hAxis.add("B");
		hAxis.add("A");
		hAxis.add("C");
		barChart.chartSetBucketAxis(hAxis);
		if (fixedAxis) barChart.chartSetValueAxis(0, 25, 1, TEST_PRECISION);
		
		barChart.chartSetBucketAxisLabel("Unit X");
		barChart.chartSetValueAxisLabel("Unit Y");
		
		if (SHOW_TITLE) {
			barChart.chartSetTitle(barChart.chartGetName(), TITLE_ALIGNMENT, TITLE_POSITION);
		}
		barChart.chartSetLegendEnabled(SHOW_LEGEND);
		
		if (maxBarWidth != null) {
			barChart.setMaxBarWidth(maxBarWidth);
		}
		
		return barChart;
	}
	
	
	private JPieChart pieChart(int style, Integer maxHeight) {
		String name = (style == JPieChart.PIE_CHART_STYLE_2D ? "2D" : "3D") + " PieChart";
		name += (maxHeight == null ? "" : (" Max Pie Height " + maxHeight + "px"));
				
		JPieChart pieChart = new JPieChart(name, style);
		
		pieChart.chartAddDataSet("Pie 3", Color.GREEN);
		pieChart.chartAddDataSet("Pie 2", Color.BLUE);
		pieChart.chartAddDataSet("Pie 4", Color.YELLOW);
		pieChart.chartAddDataSet("Pie 1", Color.RED);
		pieChart.chartSetOrderedDataSets(true);
		
		pieChart.chartAddDataPoint("Pie 1", 10);
		pieChart.chartAddDataPoint("Pie 2", 25);
		pieChart.chartAddDataPoint("Pie 3", 45);
		pieChart.chartAddDataPoint("Pie 4", 20);
		
		if (SHOW_TITLE) {
			pieChart.chartSetTitle(pieChart.chartGetName(), TITLE_ALIGNMENT, TITLE_POSITION);
		}
		pieChart.chartSetLegendEnabled(SHOW_LEGEND);
		pieChart.chartSetlegendBorderColor(Color.GREEN);
		pieChart.chartSetlegendBackgroundColor(Color.ORANGE);
		pieChart.chartSetUnitDescription("%");
		if (maxHeight != null) {
			pieChart.setMax3DPieChartHeight(maxHeight);
		}
		
		return pieChart;
	}
	
	
	private JPieChart pieChartMonths(int style, Integer maxHeight) {
		String name = (style == JPieChart.PIE_CHART_STYLE_2D ? "2D" : "3D") + " PieChart Months";
		name += (maxHeight == null ? "" : (" Max Pie Height " + maxHeight + "px"));
				
		JPieChart pieChart = new JPieChart(name, style);
		
		pieChart.chartAddDataSet("January", Color.RED);
		pieChart.chartAddDataPoint("January", 31);
		
		pieChart.chartAddDataSet("February", Color.BLUE);
		pieChart.chartAddDataPoint("February", 28);
		
		pieChart.chartAddDataSet("March", Color.GREEN);
		pieChart.chartAddDataPoint("March", 31);
		
		pieChart.chartAddDataSet("April", Color.YELLOW);
		pieChart.chartAddDataPoint("April", 30);
		
		pieChart.chartAddDataSet("May", Color.CYAN);
		pieChart.chartAddDataPoint("May", 31);
		
		pieChart.chartAddDataSet("June", Color.DARK_GRAY);
		pieChart.chartAddDataPoint("June", 30);
		
		pieChart.chartAddDataSet("July", Color.MAGENTA);
		pieChart.chartAddDataPoint("July", 31);
		
		pieChart.chartAddDataSet("August", Color.GRAY);
		pieChart.chartAddDataPoint("August", 31);
		
		pieChart.chartAddDataSet("September", Color.PINK);
		pieChart.chartAddDataPoint("September", 30);
		
		pieChart.chartAddDataSet("October", Color.LIGHT_GRAY);
		pieChart.chartAddDataPoint("October", 31);
		
		pieChart.chartAddDataSet("November", Color.ORANGE);
		pieChart.chartAddDataPoint("November", 30);
		
		pieChart.chartAddDataSet("December", Color.WHITE);
		pieChart.chartAddDataPoint("December", 31);
		
		if (SHOW_TITLE) {
			pieChart.chartSetTitle(pieChart.chartGetName(), TITLE_ALIGNMENT, TITLE_POSITION);
		}
		pieChart.chartSetLegendEnabled(SHOW_LEGEND);
		pieChart.chartSetlegendBorderColor(Color.GREEN);
		pieChart.chartSetlegendBackgroundColor(Color.ORANGE);
		pieChart.chartSetUnitDescription("days");
		if (maxHeight != null) {
			pieChart.setMax3DPieChartHeight(maxHeight);
		}
		
		return pieChart;
	}
	
	
	private JBoxChart boxChart(boolean fixedAxis, int style, Integer maxBoxWidth) {
		String name = (style == JBoxChart.BOX_CHART_STYLE_VERTICAL ? "Vertical" : "Horizontal") + " BoxChart";
		name += (fixedAxis ? " Fixed" : " Dynamic") + " Axis";
		name += (maxBoxWidth == null ? "" : (" Max Box Width " + maxBoxWidth + "px"));
		
		JBoxChart boxChart = new JBoxChart(name, style);
		
		boxChart.chartAddDataSet("Blue", Color.BLUE);
		
		boxChart.chartAddDataPoint("Blue",-3);
		boxChart.chartAddDataPoint("Blue",13);
		boxChart.chartAddDataPoint("Blue",-13);
		boxChart.chartAddDataPoint("Blue",11);
		boxChart.chartAddDataPoint("Blue",-8);
		boxChart.chartAddDataPoint("Blue",12);
		boxChart.chartAddDataPoint("Blue",-25);
		boxChart.chartAddDataPoint("Blue",13);
		boxChart.chartAddDataPoint("Blue",-17);
		boxChart.chartAddDataPoint("Blue",-21);
		boxChart.chartAddDataPoint("Blue",-3);
		boxChart.chartAddDataPoint("Blue",2);
		boxChart.chartAddDataPoint("Blue",2);
		boxChart.chartAddDataPoint("Blue",-2);
		boxChart.chartAddDataPoint("Blue",-5);
		boxChart.chartAddDataPoint("Blue",13);
		boxChart.chartAddDataPoint("Blue",-17);
		boxChart.chartAddDataPoint("Blue",-17);
		boxChart.chartAddDataPoint("Blue",-18);
		boxChart.chartAddDataPoint("Blue",-5);
		boxChart.chartAddDataPoint("Blue",-1);
		boxChart.chartAddDataPoint("Blue",7);
		boxChart.chartAddDataPoint("Blue",19);
		boxChart.chartAddDataPoint("Blue",20);
		boxChart.chartAddDataPoint("Blue",-9);
		boxChart.chartAddDataPoint("Blue",-25);
		boxChart.chartAddDataPoint("Blue",-25);
		boxChart.chartAddDataPoint("Blue",-23);
		boxChart.chartAddDataPoint("Blue",-19);
		boxChart.chartAddDataPoint("Blue",-6);
		boxChart.chartAddDataPoint("Blue",-9);
		boxChart.chartAddDataPoint("Blue",-6);
		boxChart.chartAddDataPoint("Blue",5);
		boxChart.chartAddDataPoint("Blue",8);
		boxChart.chartAddDataPoint("Blue",-10);
		boxChart.chartAddDataPoint("Blue",1);
		boxChart.chartAddDataPoint("Blue",19);
		boxChart.chartAddDataPoint("Blue",14);
		boxChart.chartAddDataPoint("Blue",-11);
		boxChart.chartAddDataPoint("Blue",-10);
		boxChart.chartAddDataPoint("Blue",-1);
		boxChart.chartAddDataPoint("Blue",17);
		boxChart.chartAddDataPoint("Blue",16);
		boxChart.chartAddDataPoint("Blue",-1);
		boxChart.chartAddDataPoint("Blue",-11);
		boxChart.chartAddDataPoint("Blue",10);
		boxChart.chartAddDataPoint("Blue",-10);
		boxChart.chartAddDataPoint("Blue",0);
		boxChart.chartAddDataPoint("Blue",-16);
		boxChart.chartAddDataPoint("Blue",-25);
		boxChart.chartAddDataPoint("Blue",18);
		boxChart.chartAddDataPoint("Blue",-21);
		boxChart.chartAddDataPoint("Blue",16);
		boxChart.chartAddDataPoint("Blue",-24);
		boxChart.chartAddDataPoint("Blue",-21);
		boxChart.chartAddDataPoint("Blue",-15);
		boxChart.chartAddDataPoint("Blue",6);
		boxChart.chartAddDataPoint("Blue",5);
		boxChart.chartAddDataPoint("Blue",-22);
		boxChart.chartAddDataPoint("Blue",-7);
		boxChart.chartAddDataPoint("Blue",-14);
		boxChart.chartAddDataPoint("Blue",18);
		boxChart.chartAddDataPoint("Blue",-13);
		boxChart.chartAddDataPoint("Blue",-24);
		boxChart.chartAddDataPoint("Blue",-20);
		boxChart.chartAddDataPoint("Blue",-25);
		boxChart.chartAddDataPoint("Blue",-19);
		boxChart.chartAddDataPoint("Blue",9);
		boxChart.chartAddDataPoint("Blue",12);
		boxChart.chartAddDataPoint("Blue",14);
		boxChart.chartAddDataPoint("Blue",15);
		boxChart.chartAddDataPoint("Blue",5);
		boxChart.chartAddDataPoint("Blue",-24);
		boxChart.chartAddDataPoint("Blue",15);
		boxChart.chartAddDataPoint("Blue",14);
		boxChart.chartAddDataPoint("Blue",-17);
		boxChart.chartAddDataPoint("Blue",14);
		boxChart.chartAddDataPoint("Blue",15);
		boxChart.chartAddDataPoint("Blue",4);
		boxChart.chartAddDataPoint("Blue",-3);
		boxChart.chartAddDataPoint("Blue",-18);
		boxChart.chartAddDataPoint("Blue",-19);
		boxChart.chartAddDataPoint("Blue",14);
		boxChart.chartAddDataPoint("Blue",-23);
		boxChart.chartAddDataPoint("Blue",9);
		boxChart.chartAddDataPoint("Blue",15);
		boxChart.chartAddDataPoint("Blue",20);
		boxChart.chartAddDataPoint("Blue",-22);
		boxChart.chartAddDataPoint("Blue",-15);
		boxChart.chartAddDataPoint("Blue",10);
		boxChart.chartAddDataPoint("Blue",-17);
		boxChart.chartAddDataPoint("Blue",18);
		boxChart.chartAddDataPoint("Blue",-12);
		boxChart.chartAddDataPoint("Blue",0);
		boxChart.chartAddDataPoint("Blue",-7);
		boxChart.chartAddDataPoint("Blue",-21);
		boxChart.chartAddDataPoint("Blue",-17);
		boxChart.chartAddDataPoint("Blue",3);
		boxChart.chartAddDataPoint("Blue",-15);
		
		boxChart.chartAddDataSet("Red", Color.RED);
		
		boxChart.chartAddDataPoint("Red",0);
		boxChart.chartAddDataPoint("Red",1);
		boxChart.chartAddDataPoint("Red",-3);
		boxChart.chartAddDataPoint("Red",-5);
		boxChart.chartAddDataPoint("Red",-16);
		boxChart.chartAddDataPoint("Red",10);
		boxChart.chartAddDataPoint("Red",-6);
		boxChart.chartAddDataPoint("Red",-3);
		boxChart.chartAddDataPoint("Red",-3);
		boxChart.chartAddDataPoint("Red",-2);
		boxChart.chartAddDataPoint("Red",2);
		boxChart.chartAddDataPoint("Red",-17);
		boxChart.chartAddDataPoint("Red",-6);
		boxChart.chartAddDataPoint("Red",-9);
		boxChart.chartAddDataPoint("Red",-19);
		boxChart.chartAddDataPoint("Red",5);
		boxChart.chartAddDataPoint("Red",-9);
		boxChart.chartAddDataPoint("Red",-14);
		boxChart.chartAddDataPoint("Red",-17);
		boxChart.chartAddDataPoint("Red",0);
		boxChart.chartAddDataPoint("Red",-13);
		boxChart.chartAddDataPoint("Red",-4);
		boxChart.chartAddDataPoint("Red",-9);
		boxChart.chartAddDataPoint("Red",-7);
		boxChart.chartAddDataPoint("Red",-15);
		boxChart.chartAddDataPoint("Red",-6);
		boxChart.chartAddDataPoint("Red",4);
		boxChart.chartAddDataPoint("Red",-19);
		boxChart.chartAddDataPoint("Red",-16);
		boxChart.chartAddDataPoint("Red",3);
		boxChart.chartAddDataPoint("Red",-7);
		boxChart.chartAddDataPoint("Red",-12);
		boxChart.chartAddDataPoint("Red",-13);
		boxChart.chartAddDataPoint("Red",0);
		boxChart.chartAddDataPoint("Red",1);
		boxChart.chartAddDataPoint("Red",5);
		boxChart.chartAddDataPoint("Red",-12);
		boxChart.chartAddDataPoint("Red",2);
		boxChart.chartAddDataPoint("Red",-15);
		boxChart.chartAddDataPoint("Red",-12);
		boxChart.chartAddDataPoint("Red",1);
		boxChart.chartAddDataPoint("Red",5);
		boxChart.chartAddDataPoint("Red",-1);
		boxChart.chartAddDataPoint("Red",8);
		boxChart.chartAddDataPoint("Red",4);
		boxChart.chartAddDataPoint("Red",-6);
		boxChart.chartAddDataPoint("Red",-8);
		boxChart.chartAddDataPoint("Red",-7);
		boxChart.chartAddDataPoint("Red",-16);
		boxChart.chartAddDataPoint("Red",5);
		boxChart.chartAddDataPoint("Red",-10);
		boxChart.chartAddDataPoint("Red",-17);
		boxChart.chartAddDataPoint("Red",-5);
		boxChart.chartAddDataPoint("Red",9);
		boxChart.chartAddDataPoint("Red",1);
		boxChart.chartAddDataPoint("Red",-17);
		boxChart.chartAddDataPoint("Red",-7);
		boxChart.chartAddDataPoint("Red",2);
		boxChart.chartAddDataPoint("Red",1);
		boxChart.chartAddDataPoint("Red",-20);
		boxChart.chartAddDataPoint("Red",9);
		boxChart.chartAddDataPoint("Red",6);
		boxChart.chartAddDataPoint("Red",-10);
		boxChart.chartAddDataPoint("Red",2);
		boxChart.chartAddDataPoint("Red",-15);
		boxChart.chartAddDataPoint("Red",0);
		boxChart.chartAddDataPoint("Red",-2);
		boxChart.chartAddDataPoint("Red",-3);
		boxChart.chartAddDataPoint("Red",-7);
		boxChart.chartAddDataPoint("Red",-2);
		boxChart.chartAddDataPoint("Red",-4);
		boxChart.chartAddDataPoint("Red",5);
		boxChart.chartAddDataPoint("Red",10);
		boxChart.chartAddDataPoint("Red",-9);
		boxChart.chartAddDataPoint("Red",-12);
		boxChart.chartAddDataPoint("Red",10);
		boxChart.chartAddDataPoint("Red",-18);
		boxChart.chartAddDataPoint("Red",2);
		boxChart.chartAddDataPoint("Red",3);
		boxChart.chartAddDataPoint("Red",-8);
		boxChart.chartAddDataPoint("Red",1);
		boxChart.chartAddDataPoint("Red",-5);
		boxChart.chartAddDataPoint("Red",-9);
		boxChart.chartAddDataPoint("Red",10);
		boxChart.chartAddDataPoint("Red",10);
		boxChart.chartAddDataPoint("Red",1);
		boxChart.chartAddDataPoint("Red",3);
		boxChart.chartAddDataPoint("Red",-11);
		boxChart.chartAddDataPoint("Red",5);
		boxChart.chartAddDataPoint("Red",-14);
		boxChart.chartAddDataPoint("Red",9);
		boxChart.chartAddDataPoint("Red",-11);
		boxChart.chartAddDataPoint("Red",1);
		boxChart.chartAddDataPoint("Red",-7);
		boxChart.chartAddDataPoint("Red",6);
		boxChart.chartAddDataPoint("Red",-17);
		boxChart.chartAddDataPoint("Red",6);
		boxChart.chartAddDataPoint("Red",-3);
		
		boxChart.chartSetValueAxisLabel(style == JBoxChart.BOX_CHART_STYLE_VERTICAL ? "Unit Y" : "Unit X");
		
		if (SHOW_TITLE) {
			boxChart.chartSetTitle(boxChart.chartGetName(), TITLE_ALIGNMENT, TITLE_POSITION);
		}
		
		if (fixedAxis) {
			boxChart.chartSetValueAxis(-30, 25, 5, 0);
		}
		
		if (maxBoxWidth != null) {
			boxChart.setMaxBoxWidth(maxBoxWidth);
		}
		
		return boxChart;
	}
	
	
	private JScatterChart scatterChart(boolean positiveOnly, boolean fixedAxes) {
		JScatterChart scatterChart = new JScatterChart("ScatterChart" + (fixedAxes ? " Fixed Axes" : " Dynamic Axes"));
		
		int offset = positiveOnly ? 0 : -15;
		
		scatterChart.chartAddDataSet("Line 1", Color.RED);
		scatterChart.chartSetPointStyle("Line 1", JScatterChart.POINT_STYLE_DOT);
		scatterChart.chartAddDataPoint("Line 1", 0, 20 + offset);
		scatterChart.chartAddDataPoint("Line 1", 1, 22 + offset);
		scatterChart.chartAddDataPoint("Line 1", 2, 15 + offset);
		scatterChart.chartAddDataPoint("Line 1", 3, 24 + offset);
		scatterChart.chartAddDataPoint("Line 1", 4, 18 + offset);
		
		scatterChart.chartAddDataSet("Line 2", Color.BLUE);
		scatterChart.chartSetPointStyle("Line 2", JScatterChart.POINT_STYLE_DIAMOND);
		scatterChart.chartAddDataPoint("Line 2", 0, 25 + offset);
		scatterChart.chartAddDataPoint("Line 2", 1, 21 + offset);
		scatterChart.chartAddDataPoint("Line 2", 2, 3 + offset);
		scatterChart.chartAddDataPoint("Line 2", 3, 19 + offset);
		scatterChart.chartAddDataPoint("Line 2", 4, 13 + offset);
		
		scatterChart.chartAddDataSet("Line 3", Color.GREEN);
		scatterChart.chartSetPointStyle("Line 3", JScatterChart.POINT_STYLE_SQUARE);
		scatterChart.chartAddDataPoint("Line 3", 0, 12 + offset);
		scatterChart.chartAddDataPoint("Line 3", 1, 23 + offset);
		scatterChart.chartAddDataPoint("Line 3", 2, 10 + offset);
		scatterChart.chartAddDataPoint("Line 3", 3, 11 + offset);
		scatterChart.chartAddDataPoint("Line 3", 4, 20 + offset);
		
		scatterChart.chartAddDataSet("Line 4", Color.YELLOW);
		scatterChart.chartSetPointStyle("Line 4", JScatterChart.POINT_STYLE_DOT);
		scatterChart.chartAddDataPoint("Line 4", 0, 9 + offset);
		scatterChart.chartAddDataPoint("Line 4", 1, 5 + offset);
		scatterChart.chartAddDataPoint("Line 4", 2, 7 + offset);
		scatterChart.chartAddDataPoint("Line 4", 3, 10 + offset);
		scatterChart.chartAddDataPoint("Line 4", 4, 12 + offset);
		
		scatterChart.chartAddDataSet("Line 5", Color.CYAN);
		scatterChart.chartSetPointStyle("Line 5", JScatterChart.POINT_STYLE_DIAMOND);
		scatterChart.chartAddDataPoint("Line 5", 0, 21 + offset);
		scatterChart.chartAddDataPoint("Line 5", 1, 20 + offset);
		scatterChart.chartAddDataPoint("Line 5", 2, 23 + offset);
		scatterChart.chartAddDataPoint("Line 5", 3, 25 + offset);
		scatterChart.chartAddDataPoint("Line 5", 4, 24 + offset);
		
		scatterChart.chartSetHorizontalAxisPrecision(TEST_PRECISION);
		scatterChart.chartSetVerticalAxisPrecision(TEST_PRECISION);
		
		if (fixedAxes) {
			scatterChart.chartSetHorizontalAxis(0, 4, 1, 0);
			scatterChart.chartSetVerticalAxis(0 + offset, 30 + offset, 1, 0);
		}
		
		scatterChart.chartSetHorizontalAxisLabel("Unit X");
		scatterChart.chartSetVerticalAxisLabel("Unit Y");
		
		if (SHOW_TITLE) {
			scatterChart.chartSetTitle(scatterChart.chartGetName(), TITLE_ALIGNMENT, TITLE_POSITION);
		}
		scatterChart.chartSetLegendEnabled(SHOW_LEGEND);
		
		return scatterChart;
	}
	

	public static void main(String[] args) {
		if (args.length > 0) {
			new JChartDemo(true);
		}
		else {
			new JChartDemo();
		}
	}
}
