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

package jchart.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import jchart.JBarChart;
import jchart.JBoxChart;
import jchart.JChart;
import jchart.JLineChart;
import jchart.JPieChart;
import jchart.JScatterChart;

public class Test {
	private static final int TEST_PRECISION = 1;
	private static final boolean SHOW_LEGEND = true;
	private static final boolean SHOW_TITLE = true;
	private static final int TITLE_ALIGNMENT = JChart.TITLE_ALIGNMENT_CENTER;
	private static final int TITLE_POSITION = JChart.TITLE_POSITION_TOP;
	
	
	
	private List<JChart> lineCharts() {
		List<JChart> charts = new ArrayList<JChart>();

		charts.add(lineChartFixedAxes());
		charts.add(lineChartFixedAxesPositiveOnly());
		charts.add(lineChartFixedxesCentered());
		charts.add(lineChartFreeAxes());

		return charts;
	}
	
	
	private List<JChart> stackedLineCharts() {
		List<JChart> charts = new ArrayList<JChart>();
		
		charts.add(lineChartTestStacked(false, false));
		charts.add(lineChartTestStacked(false, true));
		charts.add(lineChartTestStacked(true, false));
		charts.add(lineChartTestStacked(true, true));

		return charts;
	}
	
	
	private List<JChart> barChartsFixedAxis() {
		List<JChart> charts = new ArrayList<JChart>();

		charts.add(barChartFixedAxis(JBarChart.BAR_CHART_STYLE_VERTICAL));
		charts.add(barChartFixedAxis(JBarChart.BAR_CHART_STYLE_HORIZONTAL));
		charts.add(barChartFixedAxis(JBarChart.BAR_CHART_STYLE_STACKED_VERTICAL));
		charts.add(barChartFixedAxis(JBarChart.BAR_CHART_STYLE_STACKED_HORIZONTAL));
		charts.add(barChartFixedAxis(JBarChart.BAR_CHART_STYLE_3D_VERTICAL));
		charts.add(barChartFixedAxis(JBarChart.BAR_CHART_STYLE_3D_HORIZONTAL));
		charts.add(barChartFixedAxis(JBarChart.BAR_CHART_STYLE_3D_STACKED_VERTICAL));
		charts.add(barChartFixedAxis(JBarChart.BAR_CHART_STYLE_3D_STACKED_HORIZONTAL));

		return charts;
	}
	
	
	private List<JChart> barChartsFreeAxis() {
		List<JChart> charts = new ArrayList<JChart>();

		charts.add(barChartFreeAxis(JBarChart.BAR_CHART_STYLE_VERTICAL));
		charts.add(barChartFreeAxis(JBarChart.BAR_CHART_STYLE_HORIZONTAL));
		charts.add(barChartFreeAxis(JBarChart.BAR_CHART_STYLE_STACKED_VERTICAL));
		charts.add(barChartFreeAxis(JBarChart.BAR_CHART_STYLE_STACKED_HORIZONTAL));
		charts.add(barChartFreeAxis(JBarChart.BAR_CHART_STYLE_3D_VERTICAL));
		charts.add(barChartFreeAxis(JBarChart.BAR_CHART_STYLE_3D_HORIZONTAL));
		charts.add(barChartFreeAxis(JBarChart.BAR_CHART_STYLE_3D_STACKED_VERTICAL));
		charts.add(barChartFreeAxis(JBarChart.BAR_CHART_STYLE_3D_STACKED_HORIZONTAL));

		return charts;
	}
	
	
	private List<JChart> pieCharts() {
		List<JChart> charts = new ArrayList<JChart>();

		charts.add(pieChart1(JPieChart.PIE_CHART_STYLE_2D, null));
		charts.add(pieChart1(JPieChart.PIE_CHART_STYLE_3D, null));
		charts.add(pieChart1(JPieChart.PIE_CHART_STYLE_3D, 50));
		charts.add(pieChart2(JPieChart.PIE_CHART_STYLE_2D, null));
		charts.add(pieChart2(JPieChart.PIE_CHART_STYLE_3D, null));
		charts.add(pieChart2(JPieChart.PIE_CHART_STYLE_3D, 50));
		
		return charts;
	}
	
	
	private List<JChart> boxCharts() {
		List<JChart> charts = new ArrayList<JChart>();

		charts.add(boxChart1(true, JBoxChart.BOX_CHART_STYLE_VERTICAL));
		charts.add(boxChart1(false, JBoxChart.BOX_CHART_STYLE_VERTICAL));
		charts.add(boxChart1(true, JBoxChart.BOX_CHART_STYLE_HORIZONTAL));
		charts.add(boxChart1(false, JBoxChart.BOX_CHART_STYLE_HORIZONTAL));

		charts.add(boxChart2(true, JBoxChart.BOX_CHART_STYLE_VERTICAL, 50));
		charts.add(boxChart2(false, JBoxChart.BOX_CHART_STYLE_VERTICAL, 50));
		charts.add(boxChart2(true, JBoxChart.BOX_CHART_STYLE_HORIZONTAL, 50));
		charts.add(boxChart2(false, JBoxChart.BOX_CHART_STYLE_HORIZONTAL, 50));
		
		return charts;
	}
	
	
	private List<JChart> exampleLineCharts() {
		List<JChart> charts = new ArrayList<JChart>();
		
		charts.addAll(lineCharts());
		charts.addAll(stackedLineCharts());
		
		//charts.add(lineChartFixedAxes());
		//charts.add(lineChartTestStacked(true, true));

		charts.add(lineChartParabole());
		charts.add(lineChartGoniometric());
		
		return charts;
	}
	
	
	private List<JChart> exampleScatterCharts() {
		List<JChart> charts = new ArrayList<JChart>();
		
		charts.add(scatterChartFixedAxesPositiveOnly());
		charts.add(scatterChartFreeAxesPositiveOnly());
		
		return charts;
	}
	
	
	private List<JChart> exampleBarCharts() {
		List<JChart> charts = new ArrayList<JChart>();
		
		charts.add(barChartFreeAxis(JBarChart.BAR_CHART_STYLE_VERTICAL));
		charts.add(barChartFreeAxis(JBarChart.BAR_CHART_STYLE_HORIZONTAL));
		charts.add(barChartFreeAxis(JBarChart.BAR_CHART_STYLE_STACKED_VERTICAL));
		charts.add(barChartFreeAxis(JBarChart.BAR_CHART_STYLE_STACKED_HORIZONTAL));
		
		charts.add(barChartFreeAxis(JBarChart.BAR_CHART_STYLE_3D_VERTICAL));
		charts.add(barChartFreeAxis(JBarChart.BAR_CHART_STYLE_3D_HORIZONTAL));
		charts.add(barChartFreeAxis(JBarChart.BAR_CHART_STYLE_3D_STACKED_VERTICAL));
		charts.add(barChartFreeAxis(JBarChart.BAR_CHART_STYLE_3D_STACKED_HORIZONTAL));
		
		return charts;
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
	
	
	private class Modifier implements ActionListener {
		private JLineChart lineChart = null;
		private JBarChart barChart = null;
		private JPieChart pieChart = null;
		
		public Modifier(JLineChart lineChart) {
			this.lineChart = lineChart;
		}
		
		public Modifier(JBarChart barChart) {
			this.barChart = barChart;
		}
		
		public Modifier(JPieChart pieChart) {
			this.pieChart = pieChart;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (lineChart != null) {
				lineChart.chartClear();
				
				lineChart.repaint();
			}
			if (barChart != null) {
				barChart.chartClear();
				/*
				barChart.chartAddDataSet("Bar 3", Color.GREEN);
				barChart.chartAddDataPoint("Bar 3", "A", -6);
				barChart.chartAddDataPoint("Bar 3", "B", 5);
				barChart.chartAddDataPoint("Bar 3", "C", -3);
				barChart.chartAddDataPoint("Bar 3", "D", 7);
				barChart.chartAddDataPoint("Bar 3", "E", 12);
				*/
				barChart.repaint();
			}
			if (pieChart != null) {
				pieChart.chartClear();
				
				pieChart.repaint();
			}
		}
		
	}
	
	
	private JLineChart lineChartFixedAxes() {
		JLineChart lineChart = new JLineChart("LineChart Fixed Axes", JLineChart.LINE_CHART_STYLE_LINE);
		
		lineChart.chartAddDataSet("Line 1", Color.RED);
		lineChart.chartAddDataPoint("Line 1", 0, 10);
		lineChart.chartAddDataPoint("Line 1", 1, 12);
		lineChart.chartAddDataPoint("Line 1", 2, 5);
		lineChart.chartAddDataPoint("Line 1", 3, 14);
		lineChart.chartAddDataPoint("Line 1", 4, 8);
		
		lineChart.chartAddDataSet("Line 2", Color.BLUE);
		lineChart.chartAddDataPoint("Line 2", 0, 15);
		lineChart.chartAddDataPoint("Line 2", 1, 11);
		lineChart.chartAddDataPoint("Line 2", 2, -7);
		lineChart.chartAddDataPoint("Line 2", 3, 9);
		lineChart.chartAddDataPoint("Line 2", 4, 3);
		
		lineChart.chartSetHorizontalAxisPrecision(TEST_PRECISION);
		lineChart.chartSetVerticalAxisPrecision(TEST_PRECISION);
		
		lineChart.chartSetHorizontalAxis(0, 4, 1, 0);
		lineChart.chartSetVerticalAxis(-10, 20, 1, 0);
		
		lineChart.chartSetHorizontalAxisLabel("Unit X");
		lineChart.chartSetVerticalAxisLabel("Unit Y");
		
		if (SHOW_TITLE) {
			//lineChart.setTitleFont(new Font("Arial", Font.BOLD, 15));
			lineChart.chartSetTitle(lineChart.chartGetName(), TITLE_ALIGNMENT, TITLE_POSITION);
		}
		lineChart.chartSetLegendEnabled(SHOW_LEGEND);
		lineChart.chartSetlegendBorderColor(Color.GREEN);
		lineChart.chartSetlegendBackgroundColor(Color.YELLOW);
		
		return lineChart;
	}
	
	
	private JLineChart lineChartFixedAxesPositiveOnly() {
		JLineChart lineChart = new JLineChart("LineChart Fixed Axes Positive Only", JLineChart.LINE_CHART_STYLE_LINE);
		
		lineChart.chartAddDataSet("Line 1", Color.RED);
		lineChart.chartSetLineStyle("Line 1", JLineChart.POINT_STYLE_DOT);
		lineChart.chartAddDataPoint("Line 1", 0, 20);
		lineChart.chartAddDataPoint("Line 1", 1, 22);
		lineChart.chartAddDataPoint("Line 1", 2, 15);
		lineChart.chartAddDataPoint("Line 1", 3, 24);
		lineChart.chartAddDataPoint("Line 1", 4, 18);
		
		lineChart.chartAddDataSet("Line 2", Color.BLUE);
		lineChart.chartSetLineStyle("Line 2", JLineChart.POINT_STYLE_DIAMOND);
		lineChart.chartAddDataPoint("Line 2", 0, 25);
		lineChart.chartAddDataPoint("Line 2", 1, 21);
		lineChart.chartAddDataPoint("Line 2", 2, 3);
		lineChart.chartAddDataPoint("Line 2", 3, 19);
		lineChart.chartAddDataPoint("Line 2", 4, 13);
		
		lineChart.chartAddDataSet("Line 3", Color.GREEN);
		lineChart.chartSetLineStyle("Line 3", JLineChart.POINT_STYLE_SQUARE);
		lineChart.chartAddDataPoint("Line 3", 0, 12);
		lineChart.chartAddDataPoint("Line 3", 1, 23);
		lineChart.chartAddDataPoint("Line 3", 2, 10);
		lineChart.chartAddDataPoint("Line 3", 3, 11);
		lineChart.chartAddDataPoint("Line 3", 4, 20);
		
		lineChart.chartAddDataSet("Line 4", Color.YELLOW);
		lineChart.chartSetLineStyle("Line 4", JLineChart.POINT_STYLE_DOT);
		lineChart.chartAddDataPoint("Line 4", 0, 9);
		lineChart.chartAddDataPoint("Line 4", 1, 5);
		lineChart.chartAddDataPoint("Line 4", 2, 7);
		lineChart.chartAddDataPoint("Line 4", 3, 10);
		lineChart.chartAddDataPoint("Line 4", 4, 12);
		
		lineChart.chartAddDataSet("Line 5", Color.CYAN);
		lineChart.chartSetLineStyle("Line 5", JLineChart.POINT_STYLE_DIAMOND);
		lineChart.chartAddDataPoint("Line 5", 0, 21);
		lineChart.chartAddDataPoint("Line 5", 1, 20);
		lineChart.chartAddDataPoint("Line 5", 2, 23);
		lineChart.chartAddDataPoint("Line 5", 3, 25);
		lineChart.chartAddDataPoint("Line 5", 4, 24);
		
		lineChart.chartSetHorizontalAxisPrecision(TEST_PRECISION);
		lineChart.chartSetVerticalAxisPrecision(TEST_PRECISION);
		
		lineChart.chartSetHorizontalAxis(0, 4, 1, 0);
		lineChart.chartSetVerticalAxis(0, 30, 1, 0);
		
		lineChart.chartSetHorizontalAxisLabel("Unit X");
		lineChart.chartSetVerticalAxisLabel("Unit Y");
		
		if (SHOW_TITLE) {
			lineChart.chartSetTitle(lineChart.chartGetName(), TITLE_ALIGNMENT, TITLE_POSITION);
		}
		lineChart.chartSetLegendEnabled(SHOW_LEGEND);
		
		return lineChart;
	}
	
	
	private JLineChart lineChartFixedxesCentered() {
		JLineChart lineChart = new JLineChart("LineChart Fixed Axes Centered", JLineChart.LINE_CHART_STYLE_LINE);
		
		lineChart.chartAddDataSet("Line 1", Color.RED);
		lineChart.chartAddDataPoint("Line 1", -2, 10);
		lineChart.chartAddDataPoint("Line 1", -1, 12);
		lineChart.chartAddDataPoint("Line 1", 0, 5);
		lineChart.chartAddDataPoint("Line 1", 1, 14);
		lineChart.chartAddDataPoint("Line 1", 2, 8);
		
		lineChart.chartAddDataSet("Line 2", Color.BLUE);
		lineChart.chartAddDataPoint("Line 2", -2, 15);
		lineChart.chartAddDataPoint("Line 2", -1, 11);
		lineChart.chartAddDataPoint("Line 2", 0, -7);
		lineChart.chartAddDataPoint("Line 2", 1, 9);
		lineChart.chartAddDataPoint("Line 2", 2, 3);
		
		lineChart.chartSetHorizontalAxisPrecision(TEST_PRECISION);
		lineChart.chartSetVerticalAxisPrecision(TEST_PRECISION);
		
		lineChart.chartSetHorizontalAxis(-2, 2, 1, 0);
		lineChart.chartSetVerticalAxis(-10, 20, 1, 0);
		
		lineChart.chartSetHorizontalAxisLabel("Unit X");
		lineChart.chartSetVerticalAxisLabel("Unit Y");
		
		if (SHOW_TITLE) {
			lineChart.chartSetTitle(lineChart.chartGetName(), TITLE_ALIGNMENT, TITLE_POSITION);
		}
		lineChart.chartSetLegendEnabled(SHOW_LEGEND);
		
		return lineChart;
	}
	
	
	private JLineChart lineChartFreeAxes() {
		JLineChart lineChart = new JLineChart("LineChart Free Axes", JLineChart.LINE_CHART_STYLE_LINE);
		
		lineChart.chartAddDataSet("Line 1", Color.RED);
		lineChart.chartAddDataPoint("Line 1", 0, 10);
		lineChart.chartAddDataPoint("Line 1", 1, 12);
		lineChart.chartAddDataPoint("Line 1", 2, 5);
		lineChart.chartAddDataPoint("Line 1", 3, 14);
		lineChart.chartAddDataPoint("Line 1", 4, 8);
		
		lineChart.chartAddDataSet("Line 2", Color.BLUE);
		lineChart.chartAddDataPoint("Line 2", 0, 15);
		lineChart.chartAddDataPoint("Line 2", 1, 11);
		lineChart.chartAddDataPoint("Line 2", 2, -7);
		lineChart.chartAddDataPoint("Line 2", 3, 9);
		lineChart.chartAddDataPoint("Line 2", 4, 3);
		
		lineChart.chartSetHorizontalAxisPrecision(TEST_PRECISION);
		lineChart.chartSetVerticalAxisPrecision(TEST_PRECISION);
		
		//lineChart.setHorizontalAxis(0, 4, 1, 0);
		//lineChart.setVerticalAxis(-10, 20, 1, 0);
		
		lineChart.chartSetHorizontalAxisLabel("Unit X");
		lineChart.chartSetVerticalAxisLabel("Unit Y");
		
		if (SHOW_TITLE) {
			lineChart.chartSetTitle(lineChart.chartGetName(), TITLE_ALIGNMENT, TITLE_POSITION);
		}
		lineChart.chartSetLegendEnabled(SHOW_LEGEND);
		
		return lineChart;
	}
	
	
	private JLineChart lineChartTestStacked(boolean fixedAxis, boolean stacked) {
		JLineChart lineChart = new JLineChart("LineChart " + (fixedAxis ? "Fixed" : "Free") + " Axes" + (stacked ? " Stacked" : ""), stacked ? JLineChart.LINE_CHART_STYLE_STACKED : JLineChart.LINE_CHART_STYLE_LINE);
		
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
		
		if (fixedAxis) {
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
	
	
	private JBarChart barChartFixedAxis(int style) {
		String name = "BarChart Fixed Axis";
		name += ((style == JBarChart.BAR_CHART_STYLE_3D_HORIZONTAL) || (style == JBarChart.BAR_CHART_STYLE_3D_VERTICAL) || (style == JBarChart.BAR_CHART_STYLE_3D_STACKED_HORIZONTAL) || (style == JBarChart.BAR_CHART_STYLE_3D_STACKED_VERTICAL)) ? " 3D" : "";
		name += ((style == JBarChart.BAR_CHART_STYLE_STACKED_HORIZONTAL) || (style == JBarChart.BAR_CHART_STYLE_3D_STACKED_HORIZONTAL) || (style == JBarChart.BAR_CHART_STYLE_STACKED_VERTICAL) || (style == JBarChart.BAR_CHART_STYLE_3D_STACKED_VERTICAL)) ? " Stacked" : "";
		name += ((style == JBarChart.BAR_CHART_STYLE_HORIZONTAL) || (style == JBarChart.BAR_CHART_STYLE_3D_HORIZONTAL) || (style == JBarChart.BAR_CHART_STYLE_STACKED_HORIZONTAL) || (style == JBarChart.BAR_CHART_STYLE_3D_STACKED_HORIZONTAL)) ? " Horizontal" : " Vertical";
		JBarChart barChart = new JBarChart(name, style);
		
		barChart.chartAddDataSet("Bar 1", Color.RED);
		barChart.chartAddDataPoint("Bar 1", "A", 10);
		barChart.chartAddDataPoint("Bar 1", "B", 12);
		barChart.chartAddDataPoint("Bar 1", "C", 5);
		barChart.chartAddDataPoint("Bar 1", "D", 14);
		barChart.chartAddDataPoint("Bar 1", "E", 8);
		
		barChart.chartAddDataSet("Bar 2", Color.BLUE);
		barChart.chartAddDataPoint("Bar 2", "A", 15);
		barChart.chartAddDataPoint("Bar 2", "B", 0);
		//barChart.chartAddDataPoint("Bar 2", "C", -7);
		barChart.chartAddDataPoint("Bar 2", "C", 7);
		barChart.chartAddDataPoint("Bar 2", "D", 9);
		barChart.chartAddDataPoint("Bar 2", "E", 3);
		
		List<String> hAxis = new ArrayList<String>();
		hAxis.add("B");
		hAxis.add("D");
		hAxis.add("A");
		hAxis.add("E");
		hAxis.add("C");
		barChart.chartSetBucketAxis(hAxis);
		//barChart.chartSetValueAxis(-10, 20, 1, TEST_PRECISION);
		barChart.chartSetValueAxis(0, 25, 1, TEST_PRECISION);
		
		barChart.chartSetBucketAxisLabel("Unit X");
		barChart.chartSetValueAxisLabel("Unit Y");
		
		if (SHOW_TITLE) {
			barChart.chartSetTitle(barChart.chartGetName(), TITLE_ALIGNMENT, TITLE_POSITION);
		}
		barChart.chartSetLegendEnabled(SHOW_LEGEND);
		
		return barChart;
	}
	
	
	private JBarChart barChartFreeAxis(int style) {
		String name = "BarChart Free Axis";
		name += ((style == JBarChart.BAR_CHART_STYLE_3D_HORIZONTAL) || (style == JBarChart.BAR_CHART_STYLE_3D_VERTICAL) || (style == JBarChart.BAR_CHART_STYLE_3D_STACKED_HORIZONTAL) || (style == JBarChart.BAR_CHART_STYLE_3D_STACKED_VERTICAL)) ? " 3D" : "";
		name += ((style == JBarChart.BAR_CHART_STYLE_STACKED_HORIZONTAL) || (style == JBarChart.BAR_CHART_STYLE_3D_STACKED_HORIZONTAL) || (style == JBarChart.BAR_CHART_STYLE_STACKED_VERTICAL) || (style == JBarChart.BAR_CHART_STYLE_3D_STACKED_VERTICAL)) ? " Stacked" : "";
		name += ((style == JBarChart.BAR_CHART_STYLE_HORIZONTAL) || (style == JBarChart.BAR_CHART_STYLE_3D_HORIZONTAL) || (style == JBarChart.BAR_CHART_STYLE_STACKED_HORIZONTAL) || (style == JBarChart.BAR_CHART_STYLE_3D_STACKED_HORIZONTAL)) ? " Horizontal" : " Vertical";
		JBarChart barChart = new JBarChart(name, style);
		
		barChart.chartAddDataSet("Bar 1", Color.RED);
		barChart.chartAddDataPoint("Bar 1", "A", 10);
		barChart.chartAddDataPoint("Bar 1", "B", 12);
		barChart.chartAddDataPoint("Bar 1", "C", 5);
		barChart.chartAddDataPoint("Bar 1", "D", 14);
		barChart.chartAddDataPoint("Bar 1", "E", 8);
		
		barChart.chartAddDataSet("Bar 2", Color.BLUE);
		barChart.chartAddDataPoint("Bar 2", "A", 15);
		barChart.chartAddDataPoint("Bar 2", "B", 0);
		barChart.chartAddDataPoint("Bar 2", "C", 7);
		barChart.chartAddDataPoint("Bar 2", "D", 9);
		barChart.chartAddDataPoint("Bar 2", "E", 3);
		
		barChart.chartSetBucketAxisLabel("Unit X");
		barChart.chartSetValueAxisLabel("Unit Y");
		
		if (SHOW_TITLE) {
			barChart.chartSetTitle(barChart.chartGetName(), TITLE_ALIGNMENT, TITLE_POSITION);
		}
		barChart.chartSetLegendEnabled(SHOW_LEGEND);
		
		return barChart;
	}
	
	
	private JPieChart pieChart1(int style, Integer maxHeight) {
		JPieChart pieChart = new JPieChart((style == JPieChart.PIE_CHART_STYLE_2D ? "2D" : "3D") + " PieChart" + (maxHeight == null ? "" : (" max " + maxHeight + "px")), style);
		
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
	
	
	private JPieChart pieChart2(int style, Integer maxHeight) {
		JPieChart pieChart = new JPieChart((style == JPieChart.PIE_CHART_STYLE_2D ? "2D" : "3D") + " PieChart Months" + (maxHeight == null ? "" : (" max " + maxHeight + "px")), style);
		
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
	
	
	private JBoxChart boxChart1(boolean fixedAxis, int style) {
		JBoxChart boxChart = new JBoxChart((style == JBoxChart.BOX_CHART_STYLE_VERTICAL ? "Vertical" : "Horozontal") + " BoxChart", style);
		
		boxChart.chartAddDataSet("Blue", Color.BLUE);
		
		boxChart.chartAddDataPoint("Blue",27);
		boxChart.chartAddDataPoint("Blue",43);
		boxChart.chartAddDataPoint("Blue",17);
		boxChart.chartAddDataPoint("Blue",41);
		boxChart.chartAddDataPoint("Blue",22);
		boxChart.chartAddDataPoint("Blue",42);
		boxChart.chartAddDataPoint("Blue",5);
		boxChart.chartAddDataPoint("Blue",43);
		boxChart.chartAddDataPoint("Blue",13);
		boxChart.chartAddDataPoint("Blue",9);
		boxChart.chartAddDataPoint("Blue",27);
		boxChart.chartAddDataPoint("Blue",32);
		boxChart.chartAddDataPoint("Blue",32);
		boxChart.chartAddDataPoint("Blue",28);
		boxChart.chartAddDataPoint("Blue",25);
		boxChart.chartAddDataPoint("Blue",43);
		boxChart.chartAddDataPoint("Blue",13);
		boxChart.chartAddDataPoint("Blue",13);
		boxChart.chartAddDataPoint("Blue",12);
		boxChart.chartAddDataPoint("Blue",25);
		boxChart.chartAddDataPoint("Blue",29);
		boxChart.chartAddDataPoint("Blue",37);
		boxChart.chartAddDataPoint("Blue",49);
		boxChart.chartAddDataPoint("Blue",50);
		boxChart.chartAddDataPoint("Blue",21);
		boxChart.chartAddDataPoint("Blue",5);
		boxChart.chartAddDataPoint("Blue",5);
		boxChart.chartAddDataPoint("Blue",7);
		boxChart.chartAddDataPoint("Blue",11);
		boxChart.chartAddDataPoint("Blue",24);
		boxChart.chartAddDataPoint("Blue",21);
		boxChart.chartAddDataPoint("Blue",24);
		boxChart.chartAddDataPoint("Blue",35);
		boxChart.chartAddDataPoint("Blue",38);
		boxChart.chartAddDataPoint("Blue",20);
		boxChart.chartAddDataPoint("Blue",31);
		boxChart.chartAddDataPoint("Blue",49);
		boxChart.chartAddDataPoint("Blue",44);
		boxChart.chartAddDataPoint("Blue",19);
		boxChart.chartAddDataPoint("Blue",20);
		boxChart.chartAddDataPoint("Blue",29);
		boxChart.chartAddDataPoint("Blue",47);
		boxChart.chartAddDataPoint("Blue",46);
		boxChart.chartAddDataPoint("Blue",29);
		boxChart.chartAddDataPoint("Blue",19);
		boxChart.chartAddDataPoint("Blue",40);
		boxChart.chartAddDataPoint("Blue",20);
		boxChart.chartAddDataPoint("Blue",30);
		boxChart.chartAddDataPoint("Blue",14);
		boxChart.chartAddDataPoint("Blue",5);
		boxChart.chartAddDataPoint("Blue",48);
		boxChart.chartAddDataPoint("Blue",9);
		boxChart.chartAddDataPoint("Blue",46);
		boxChart.chartAddDataPoint("Blue",6);
		boxChart.chartAddDataPoint("Blue",9);
		boxChart.chartAddDataPoint("Blue",15);
		boxChart.chartAddDataPoint("Blue",36);
		boxChart.chartAddDataPoint("Blue",35);
		boxChart.chartAddDataPoint("Blue",8);
		boxChart.chartAddDataPoint("Blue",23);
		boxChart.chartAddDataPoint("Blue",16);
		boxChart.chartAddDataPoint("Blue",48);
		boxChart.chartAddDataPoint("Blue",17);
		boxChart.chartAddDataPoint("Blue",6);
		boxChart.chartAddDataPoint("Blue",10);
		boxChart.chartAddDataPoint("Blue",5);
		boxChart.chartAddDataPoint("Blue",11);
		boxChart.chartAddDataPoint("Blue",39);
		boxChart.chartAddDataPoint("Blue",42);
		boxChart.chartAddDataPoint("Blue",44);
		boxChart.chartAddDataPoint("Blue",45);
		boxChart.chartAddDataPoint("Blue",35);
		boxChart.chartAddDataPoint("Blue",6);
		boxChart.chartAddDataPoint("Blue",45);
		boxChart.chartAddDataPoint("Blue",44);
		boxChart.chartAddDataPoint("Blue",13);
		boxChart.chartAddDataPoint("Blue",44);
		boxChart.chartAddDataPoint("Blue",45);
		boxChart.chartAddDataPoint("Blue",34);
		boxChart.chartAddDataPoint("Blue",27);
		boxChart.chartAddDataPoint("Blue",12);
		boxChart.chartAddDataPoint("Blue",11);
		boxChart.chartAddDataPoint("Blue",44);
		boxChart.chartAddDataPoint("Blue",7);
		boxChart.chartAddDataPoint("Blue",39);
		boxChart.chartAddDataPoint("Blue",45);
		boxChart.chartAddDataPoint("Blue",50);
		boxChart.chartAddDataPoint("Blue",8);
		boxChart.chartAddDataPoint("Blue",15);
		boxChart.chartAddDataPoint("Blue",40);
		boxChart.chartAddDataPoint("Blue",13);
		boxChart.chartAddDataPoint("Blue",48);
		boxChart.chartAddDataPoint("Blue",18);
		boxChart.chartAddDataPoint("Blue",30);
		boxChart.chartAddDataPoint("Blue",23);
		boxChart.chartAddDataPoint("Blue",9);
		boxChart.chartAddDataPoint("Blue",13);
		boxChart.chartAddDataPoint("Blue",33);
		boxChart.chartAddDataPoint("Blue",15);
		boxChart.chartAddDataPoint("Blue",44);
		boxChart.chartAddDataPoint("Blue",6);
		
		boxChart.chartAddDataSet("Red", Color.RED);
		
		boxChart.chartAddDataPoint("Red",30);
		boxChart.chartAddDataPoint("Red",31);
		boxChart.chartAddDataPoint("Red",27);
		boxChart.chartAddDataPoint("Red",25);
		boxChart.chartAddDataPoint("Red",14);
		boxChart.chartAddDataPoint("Red",40);
		boxChart.chartAddDataPoint("Red",24);
		boxChart.chartAddDataPoint("Red",27);
		boxChart.chartAddDataPoint("Red",27);
		boxChart.chartAddDataPoint("Red",28);
		boxChart.chartAddDataPoint("Red",32);
		boxChart.chartAddDataPoint("Red",13);
		boxChart.chartAddDataPoint("Red",24);
		boxChart.chartAddDataPoint("Red",21);
		boxChart.chartAddDataPoint("Red",11);
		boxChart.chartAddDataPoint("Red",35);
		boxChart.chartAddDataPoint("Red",21);
		boxChart.chartAddDataPoint("Red",16);
		boxChart.chartAddDataPoint("Red",13);
		boxChart.chartAddDataPoint("Red",30);
		boxChart.chartAddDataPoint("Red",17);
		boxChart.chartAddDataPoint("Red",26);
		boxChart.chartAddDataPoint("Red",21);
		boxChart.chartAddDataPoint("Red",23);
		boxChart.chartAddDataPoint("Red",15);
		boxChart.chartAddDataPoint("Red",24);
		boxChart.chartAddDataPoint("Red",34);
		boxChart.chartAddDataPoint("Red",11);
		boxChart.chartAddDataPoint("Red",14);
		boxChart.chartAddDataPoint("Red",33);
		boxChart.chartAddDataPoint("Red",23);
		boxChart.chartAddDataPoint("Red",18);
		boxChart.chartAddDataPoint("Red",17);
		boxChart.chartAddDataPoint("Red",30);
		boxChart.chartAddDataPoint("Red",31);
		boxChart.chartAddDataPoint("Red",35);
		boxChart.chartAddDataPoint("Red",18);
		boxChart.chartAddDataPoint("Red",32);
		boxChart.chartAddDataPoint("Red",15);
		boxChart.chartAddDataPoint("Red",18);
		boxChart.chartAddDataPoint("Red",31);
		boxChart.chartAddDataPoint("Red",35);
		boxChart.chartAddDataPoint("Red",29);
		boxChart.chartAddDataPoint("Red",38);
		boxChart.chartAddDataPoint("Red",34);
		boxChart.chartAddDataPoint("Red",24);
		boxChart.chartAddDataPoint("Red",22);
		boxChart.chartAddDataPoint("Red",23);
		boxChart.chartAddDataPoint("Red",14);
		boxChart.chartAddDataPoint("Red",35);
		boxChart.chartAddDataPoint("Red",20);
		boxChart.chartAddDataPoint("Red",13);
		boxChart.chartAddDataPoint("Red",25);
		boxChart.chartAddDataPoint("Red",39);
		boxChart.chartAddDataPoint("Red",31);
		boxChart.chartAddDataPoint("Red",13);
		boxChart.chartAddDataPoint("Red",23);
		boxChart.chartAddDataPoint("Red",32);
		boxChart.chartAddDataPoint("Red",31);
		boxChart.chartAddDataPoint("Red",10);
		boxChart.chartAddDataPoint("Red",39);
		boxChart.chartAddDataPoint("Red",36);
		boxChart.chartAddDataPoint("Red",20);
		boxChart.chartAddDataPoint("Red",32);
		boxChart.chartAddDataPoint("Red",15);
		boxChart.chartAddDataPoint("Red",30);
		boxChart.chartAddDataPoint("Red",28);
		boxChart.chartAddDataPoint("Red",27);
		boxChart.chartAddDataPoint("Red",23);
		boxChart.chartAddDataPoint("Red",28);
		boxChart.chartAddDataPoint("Red",26);
		boxChart.chartAddDataPoint("Red",35);
		boxChart.chartAddDataPoint("Red",40);
		boxChart.chartAddDataPoint("Red",21);
		boxChart.chartAddDataPoint("Red",18);
		boxChart.chartAddDataPoint("Red",40);
		boxChart.chartAddDataPoint("Red",12);
		boxChart.chartAddDataPoint("Red",32);
		boxChart.chartAddDataPoint("Red",33);
		boxChart.chartAddDataPoint("Red",22);
		boxChart.chartAddDataPoint("Red",31);
		boxChart.chartAddDataPoint("Red",25);
		boxChart.chartAddDataPoint("Red",21);
		boxChart.chartAddDataPoint("Red",40);
		boxChart.chartAddDataPoint("Red",40);
		boxChart.chartAddDataPoint("Red",31);
		boxChart.chartAddDataPoint("Red",33);
		boxChart.chartAddDataPoint("Red",19);
		boxChart.chartAddDataPoint("Red",35);
		boxChart.chartAddDataPoint("Red",16);
		boxChart.chartAddDataPoint("Red",39);
		boxChart.chartAddDataPoint("Red",19);
		boxChart.chartAddDataPoint("Red",31);
		boxChart.chartAddDataPoint("Red",23);
		boxChart.chartAddDataPoint("Red",36);
		boxChart.chartAddDataPoint("Red",13);
		boxChart.chartAddDataPoint("Red",36);
		boxChart.chartAddDataPoint("Red",27);
		boxChart.chartAddDataPoint("Red",31);
		boxChart.chartAddDataPoint("Red",26);
		
		boxChart.chartSetValueAxisLabel(style == JBoxChart.BOX_CHART_STYLE_VERTICAL ? "Unit Y" : "Unit X");
		
		if (fixedAxis) {
			boxChart.chartSetValueAxis(0, 55, 5, 0);
		}
		
		return boxChart;
	}
	
	
	private JBoxChart boxChart2(boolean fixedAxis, int style, Integer maxBoxWidth) {
		JBoxChart boxChart = new JBoxChart((style == JBoxChart.BOX_CHART_STYLE_VERTICAL ? "Vertical" : "Horozontal") + " BoxChart" + (maxBoxWidth == null ? "" : (" max " + maxBoxWidth + "px")), style);
		
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
		
		if (fixedAxis) {
			boxChart.chartSetValueAxis(-30, 25, 5, 0);
		}
		
		if (maxBoxWidth != null) {
			boxChart.setMaxBoxWidth(maxBoxWidth);
		}
		
		return boxChart;
	}
	
	
	private JScatterChart scatterChartFixedAxesPositiveOnly() {
		JScatterChart scatterChart = new JScatterChart("ScatterChart Fixed Axes Positive Only");
		
		scatterChart.chartAddDataSet("Line 1", Color.RED);
		scatterChart.chartSetPointStyle("Line 1", JScatterChart.POINT_STYLE_DOT);
		scatterChart.chartAddDataPoint("Line 1", 0, 20);
		scatterChart.chartAddDataPoint("Line 1", 1, 22);
		scatterChart.chartAddDataPoint("Line 1", 2, 15);
		scatterChart.chartAddDataPoint("Line 1", 3, 24);
		scatterChart.chartAddDataPoint("Line 1", 4, 18);
		
		scatterChart.chartAddDataSet("Line 2", Color.BLUE);
		scatterChart.chartSetPointStyle("Line 2", JScatterChart.POINT_STYLE_DIAMOND);
		scatterChart.chartAddDataPoint("Line 2", 0, 25);
		scatterChart.chartAddDataPoint("Line 2", 1, 21);
		scatterChart.chartAddDataPoint("Line 2", 2, 3);
		scatterChart.chartAddDataPoint("Line 2", 3, 19);
		scatterChart.chartAddDataPoint("Line 2", 4, 13);
		
		scatterChart.chartAddDataSet("Line 3", Color.GREEN);
		scatterChart.chartSetPointStyle("Line 3", JScatterChart.POINT_STYLE_SQUARE);
		scatterChart.chartAddDataPoint("Line 3", 0, 12);
		scatterChart.chartAddDataPoint("Line 3", 1, 23);
		scatterChart.chartAddDataPoint("Line 3", 2, 10);
		scatterChart.chartAddDataPoint("Line 3", 3, 11);
		scatterChart.chartAddDataPoint("Line 3", 4, 20);
		
		scatterChart.chartAddDataSet("Line 4", Color.YELLOW);
		scatterChart.chartSetPointStyle("Line 4", JScatterChart.POINT_STYLE_DOT);
		scatterChart.chartAddDataPoint("Line 4", 0, 9);
		scatterChart.chartAddDataPoint("Line 4", 1, 5);
		scatterChart.chartAddDataPoint("Line 4", 2, 7);
		scatterChart.chartAddDataPoint("Line 4", 3, 10);
		scatterChart.chartAddDataPoint("Line 4", 4, 12);
		
		scatterChart.chartAddDataSet("Line 5", Color.CYAN);
		scatterChart.chartSetPointStyle("Line 5", JScatterChart.POINT_STYLE_DIAMOND);
		scatterChart.chartAddDataPoint("Line 5", 0, 21);
		scatterChart.chartAddDataPoint("Line 5", 1, 20);
		scatterChart.chartAddDataPoint("Line 5", 2, 23);
		scatterChart.chartAddDataPoint("Line 5", 3, 25);
		scatterChart.chartAddDataPoint("Line 5", 4, 24);
		
		scatterChart.chartSetHorizontalAxisPrecision(TEST_PRECISION);
		scatterChart.chartSetVerticalAxisPrecision(TEST_PRECISION);
		
		scatterChart.chartSetHorizontalAxis(0, 4, 1, 0);
		scatterChart.chartSetVerticalAxis(0, 30, 1, 0);
		
		scatterChart.chartSetHorizontalAxisLabel("Unit X");
		scatterChart.chartSetVerticalAxisLabel("Unit Y");
		
		if (SHOW_TITLE) {
			scatterChart.chartSetTitle(scatterChart.chartGetName(), TITLE_ALIGNMENT, TITLE_POSITION);
		}
		scatterChart.chartSetLegendEnabled(SHOW_LEGEND);
		
		return scatterChart;
	}
	
	
	private JScatterChart scatterChartFreeAxesPositiveOnly() {
		JScatterChart scatterChart = new JScatterChart("ScatterChart Free Axes Positive Only");
		
		scatterChart.chartAddDataSet("Line 1", Color.RED);
		scatterChart.chartSetPointStyle("Line 1", JScatterChart.POINT_STYLE_DOT);
		scatterChart.chartAddDataPoint("Line 1", 0, 20);
		scatterChart.chartAddDataPoint("Line 1", 1, 22);
		scatterChart.chartAddDataPoint("Line 1", 2, 15);
		scatterChart.chartAddDataPoint("Line 1", 3, 24);
		scatterChart.chartAddDataPoint("Line 1", 4, 18);
		
		scatterChart.chartAddDataSet("Line 2", Color.BLUE);
		scatterChart.chartSetPointStyle("Line 2", JScatterChart.POINT_STYLE_DIAMOND);
		scatterChart.chartAddDataPoint("Line 2", 0, 25);
		scatterChart.chartAddDataPoint("Line 2", 1, 21);
		scatterChart.chartAddDataPoint("Line 2", 2, 3);
		scatterChart.chartAddDataPoint("Line 2", 3, 19);
		scatterChart.chartAddDataPoint("Line 2", 4, 13);
		
		scatterChart.chartAddDataSet("Line 3", Color.GREEN);
		scatterChart.chartSetPointStyle("Line 3", JScatterChart.POINT_STYLE_SQUARE);
		scatterChart.chartAddDataPoint("Line 3", 0, 12);
		scatterChart.chartAddDataPoint("Line 3", 1, 23);
		scatterChart.chartAddDataPoint("Line 3", 2, 10);
		scatterChart.chartAddDataPoint("Line 3", 3, 11);
		scatterChart.chartAddDataPoint("Line 3", 4, 20);
		
		scatterChart.chartAddDataSet("Line 4", Color.YELLOW);
		scatterChart.chartSetPointStyle("Line 4", JScatterChart.POINT_STYLE_DOT);
		scatterChart.chartAddDataPoint("Line 4", 0, 9);
		scatterChart.chartAddDataPoint("Line 4", 1, 5);
		scatterChart.chartAddDataPoint("Line 4", 2, 7);
		scatterChart.chartAddDataPoint("Line 4", 3, 10);
		scatterChart.chartAddDataPoint("Line 4", 4, 12);
		
		scatterChart.chartAddDataSet("Line 5", Color.CYAN);
		scatterChart.chartSetPointStyle("Line 5", JScatterChart.POINT_STYLE_DIAMOND);
		scatterChart.chartAddDataPoint("Line 5", 0, 21);
		scatterChart.chartAddDataPoint("Line 5", 1, 20);
		scatterChart.chartAddDataPoint("Line 5", 2, 23);
		scatterChart.chartAddDataPoint("Line 5", 3, 25);
		scatterChart.chartAddDataPoint("Line 5", 4, 24);
		
		scatterChart.chartSetHorizontalAxisPrecision(TEST_PRECISION);
		scatterChart.chartSetVerticalAxisPrecision(TEST_PRECISION);
		
		//scatterChart.chartSetHorizontalAxis(0, 4, 1, 0);
		//scatterChart.chartSetVerticalAxis(0, 30, 1, 0);
		
		scatterChart.chartSetHorizontalAxisLabel("Unit X");
		scatterChart.chartSetVerticalAxisLabel("Unit Y");
		
		if (SHOW_TITLE) {
			scatterChart.chartSetTitle(scatterChart.chartGetName(), TITLE_ALIGNMENT, TITLE_POSITION);
		}
		scatterChart.chartSetLegendEnabled(SHOW_LEGEND);
		
		return scatterChart;
	}
	

	public static void main(String[] args) {
		Test t = new Test();
		
		JChart dummyChart = t.lineChartFixedAxes();
		System.out.println(dummyChart.chartGetVersion());
		System.out.println(dummyChart.chartGetLicense());
		
		//t.showCharts("Bar Charts Fixed Axis", t.barChartsFixedAxis());
		//t.showCharts("Bar Charts Free Axis", t.barChartsFreeAxis());
		//t.showCharts("Pie Charts", t.pieCharts());
		t.showCharts("Box Charts", t.boxCharts());
		//t.showCharts("Example Line Charts", t.exampleLineCharts());
		//t.showCharts("Example Bar Charts", t.exampleBarCharts());
		//t.showCharts("Example Scatter Charts", t.exampleScatterCharts());
	}

}
