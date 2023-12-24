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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

class JPieChartPlot extends JChartPlot {
	private static final long serialVersionUID = 9125603413644579552L;
	
	private Map<String, Double> dataSet = null;
	
	private int style = JPieChart.PIE_CHART_STYLE_2D;
	private int valuePrecision = STANDARD_PRECISION;
	private String unitDescription = null;
	private Color valueColor = Color.BLACK;
	private Font valueFont = new Font("Arial", Font.PLAIN, 10);
	private Color outlineColor = Color.BLACK;
	private int max3DPieChartHeight = Integer.MAX_VALUE;

	
	public JPieChartPlot(String name, int pieChartStyle) {
		super(name);
		style = pieChartStyle;
	}
	
	
	public boolean addDataPoint(String dataSetName, double value) {
		boolean ok = false;

		if (dataSets.containsKey(dataSetName)) {
			ok = true;
			if (dataSet == null) {
				dataSet = new HashMap<String, Double>();
			}
			dataSet.put(dataSetName, value);
		}
		else {
			JOptionPane.showMessageDialog(null, "Unknown data set '" + dataSetName + ".", "BarChart '" + name + "' Data Set Error", JOptionPane.ERROR_MESSAGE);
		}
		
		return ok;
	}
	
	
	public void setValuePrecision(int precision) {
		valuePrecision = precision; 
	}
	
	
	public void setUnitDescription(String unitDescription) {
		this.unitDescription = unitDescription;
	}
	
	
	public void setLabelColor(Color color) {
		valueColor = color;
	}
	
	
	public void setLabelFont(Font font) {
		valueFont = font;
	}
	
	
	public void setOutlineColor(Color color) {
		outlineColor = color;
	}
	
	
	public void setMax3DPieChartHeight(int maxHeight) {
		max3DPieChartHeight = maxHeight;
	}

	
	@Override
	void draw(Graphics graphics) {
		if (style == JPieChart.PIE_CHART_STYLE_2D) {
			draw2D(graphics);
		}
		else if (style == JPieChart.PIE_CHART_STYLE_3D) {
			draw3D(graphics);
		}
	}
	
	

	private void draw2D(Graphics graphics) {
		final int VALUE_LINE_EXTRA_LENGTH = 6;
		final int VALUE_LINE_GAP = 2;
		
		//Set background color
		graphics.setColor(chartBackgroundColor);
		graphics.fillRect(0, 0, getWidth(), getHeight());
		
		if (dataSet != null) {
			int leftBottomX = MARGIN;
			int leftBottomY = getHeight() - MARGIN;
			int width = getWidth() - (2 * MARGIN);
			int height = getHeight() - (2 * MARGIN);

			List<String> orderedDataSets = new ArrayList<String>();
			orderedDataSets.addAll(dataSetList);
			if (orderDataSets) {
				Collections.sort(orderedDataSets);
			}
			
			Dimension changesYAndHeihght = showTitle(graphics, leftBottomX, leftBottomY, width, height);
			leftBottomY = changesYAndHeihght.width;
			height = changesYAndHeihght.height;
			
			int legendWidth = showLegendRightTop(graphics, leftBottomX + width, leftBottomY - height); 
			width = width - legendWidth;

			FontMetrics fontMetrics = graphics.getFontMetrics(valueFont);
			int maxValueWidth = 0;
			double total = 0;
			for (String dataSetName : orderedDataSets) {
				double value = dataSet.get(dataSetName);
				maxValueWidth = fontMetrics.stringWidth(formatValue(value, valuePrecision) + (unitDescription == null ? "" : (" " + unitDescription)));
				total += value;
			}
			
			// Draw data
			int pieSize = Math.min(width - (2 * (maxValueWidth + VALUE_LINE_GAP + VALUE_LINE_EXTRA_LENGTH)), height - (2 * (fontMetrics.getHeight() + VALUE_LINE_GAP)));
			int pieX = leftBottomX + ((width - pieSize ) /2);
			int pieY = leftBottomY  - height + ((height - pieSize) / 2);
			int pieCenterX = pieX + (pieSize /2);
			int pieCenterY = pieY + (pieSize /2);
			
			if (JChartPlot.DEBUG) {
				System.out.println();
				System.out.println(name);
				System.out.println("PanelWidth       = " + Integer.toString(getWidth()));
				System.out.println("PanelHeight      = " + Integer.toString(getHeight()));
				System.out.println("MARGIN           = " + Integer.toString(MARGIN));
				System.out.println("AXIS_VALUE_GAP   = " + Integer.toString(AXIS_VALUE_GAP));
				System.out.println("FontHeight       = " + Integer.toString(graphics.getFontMetrics(valueFont).getHeight()));
				System.out.println("pieSize          = " + Integer.toString(pieSize));
				System.out.println("pieX             = " + Integer.toString(pieX));
				System.out.println("pieY             = " + Integer.toString(pieY));
				System.out.println("pieCenterX       = " + Integer.toString(pieCenterX));
				System.out.println("pieCenterY       = " + Integer.toString(pieCenterY));
				System.out.println("leftBottomyTitle = " + Integer.toString((int) changesYAndHeihght.getWidth()));
				System.out.println("heightTitle      = " + Integer.toString((int) changesYAndHeihght.getHeight()));
				System.out.println("legendWidth      = " + Integer.toString(legendWidth));
				System.out.println("leftBottomX      = " + Integer.toString(leftBottomX));
				System.out.println("leftBottomY      = " + Integer.toString(leftBottomY));
				System.out.println("width            = " + Integer.toString(width));
				System.out.println("height           = " + Integer.toString(height));
				System.out.println();
			}
			
			int angle = 90;
			for (String dataSetName : orderedDataSets) {
				double value = dataSet.get(dataSetName);
				String valueString = formatValue(value, valuePrecision) + (unitDescription == null ? "" : (" " + unitDescription));
				int arcAngle = (int) round((value / total) * 360.0, 0);
				
				
				// Draw pie piece
				graphics.setColor(dataSets.get(dataSetName));
				graphics.fillArc(pieX, pieY, pieSize, pieSize, angle, -arcAngle);
				//System.out.println(name + ", " + dataSetName + ", " + Double.toString(value) + ", " + Double.toString(total) + ", " + Integer.toString(angle) + ", " + Double.toString((value / total) * 360.0) + ", " + Integer.toString(arcAngle));
				
				// Draw line and value
				int valueLineAngle = angle - (int) round(arcAngle / 2.0, 0);
				int valueLineStartX = pieCenterX + (int) round((pieSize / 4) * Math.cos(((valueLineAngle) / 360.0) * 2 * Math.PI), 0);
				int valueLineStartY = pieCenterY - (int) round((pieSize / 4) * Math.sin(((valueLineAngle) / 360.0) * 2 * Math.PI), 0);
				int valueLineEndX = pieCenterX + (int) round(((pieSize / 2) + VALUE_LINE_EXTRA_LENGTH) * Math.cos(((valueLineAngle) / 360.0) * 2 * Math.PI), 0);
				int valueLineEndY = pieCenterY - (int) round(((pieSize / 2) + VALUE_LINE_EXTRA_LENGTH) * Math.sin(((valueLineAngle) / 360.0) * 2 * Math.PI), 0);
				int valueX = pieCenterX + (int) round(((pieSize / 2) + VALUE_LINE_EXTRA_LENGTH + VALUE_LINE_GAP) * Math.cos(((valueLineAngle) / 360.0) * 2 * Math.PI), 0);
				if (valueX < pieCenterX) {
					valueX = valueX - fontMetrics.stringWidth(valueString);
				}
				int valueY = pieCenterY - (int) round(((pieSize / 2) + VALUE_LINE_EXTRA_LENGTH + VALUE_LINE_GAP) * Math.sin(((valueLineAngle) / 360.0) * 2 * Math.PI), 0) + (fontMetrics.getHeight() / 2);
				graphics.setColor(valueColor);
				graphics.setFont(valueFont);
				graphics.drawLine(valueLineStartX, valueLineStartY, valueLineEndX, valueLineEndY);
				graphics.drawString(valueString, valueX, valueY);
				
				angle -= arcAngle;
			}
			graphics.setColor(outlineColor);
			graphics.drawArc(pieX, pieY, pieSize, pieSize, 0, 360);
		}
	}
	
	

	private void draw3D(Graphics graphics) {
		final int VALUE_LINE_EXTRA_LENGTH = 6;
		final int VALUE_LINE_GAP = 2;
		
		//Set background color
		graphics.setColor(chartBackgroundColor);
		graphics.fillRect(0, 0, getWidth(), getHeight());
		
		if (dataSet != null) {
			int leftBottomX = MARGIN;
			int leftBottomY = getHeight() - MARGIN;
			int width = getWidth() - (2 * MARGIN);
			int height = getHeight() - (2 * MARGIN);

			List<String> orderedDataSets = new ArrayList<String>();
			orderedDataSets.addAll(dataSetList);
			if (orderDataSets) {
				Collections.sort(orderedDataSets);
			}
			
			Dimension changesYAndHeihght = showTitle(graphics, leftBottomX, leftBottomY, width, height);
			leftBottomY = changesYAndHeihght.width;
			height = changesYAndHeihght.height;
			
			int legendWidth = showLegendRightTop(graphics, leftBottomX + width, leftBottomY - height); 
			width = width - legendWidth;

			FontMetrics fontMetrics = graphics.getFontMetrics(valueFont);
			int maxValueWidth = 0;
			double total = 0;
			for (String dataSetName : orderedDataSets) {
				double value = dataSet.get(dataSetName);
				maxValueWidth = fontMetrics.stringWidth(formatValue(value, valuePrecision) + (unitDescription == null ? "" : (" " + unitDescription)));
				total += value;
			}
			
			// Draw data
			int pieWidth = width - (2 * (maxValueWidth + VALUE_LINE_GAP + VALUE_LINE_EXTRA_LENGTH));
			int pieHeight = Math.min(height - (2 * (fontMetrics.getHeight() + VALUE_LINE_GAP)) - (4 * MARGIN) - (pieWidth / 2), max3DPieChartHeight);
			int pieX = leftBottomX + ((width - pieWidth) / 2);
			int pieYTop = leftBottomY - height + ((height - pieHeight - (pieWidth / 2)) /2);
			int pieYBottom = leftBottomY - (2 * MARGIN);
			int pieCenterX = pieX + (pieWidth / 2);
			int pieCenterYTop = pieYTop + (pieWidth / 4);
			int pieCenterYBottom = pieCenterYTop + pieHeight;
			
			if (JChartPlot.DEBUG) {
				System.out.println();
				System.out.println(name);
				System.out.println("PanelWidth       = " + Integer.toString(getWidth()));
				System.out.println("PanelHeight      = " + Integer.toString(getHeight()));
				System.out.println("MARGIN           = " + Integer.toString(MARGIN));
				System.out.println("AXIS_VALUE_GAP   = " + Integer.toString(AXIS_VALUE_GAP));
				System.out.println("FontHeight       = " + Integer.toString(graphics.getFontMetrics(valueFont).getHeight()));
				System.out.println("pieSize          = " + Integer.toString(pieWidth));
				System.out.println("pieHeight        = " + Integer.toString(pieHeight));
				System.out.println("pieX             = " + Integer.toString(pieX));
				System.out.println("pieYTop          = " + Integer.toString(pieYTop));
				System.out.println("pieYBottom       = " + Integer.toString(pieYBottom));
				System.out.println("pieCenterX       = " + Integer.toString(pieCenterX));
				System.out.println("pieCenterYTop    = " + Integer.toString(pieCenterYTop));
				System.out.println("pieCenterYBottom = " + Integer.toString(pieCenterYBottom));
				System.out.println("leftBottomyTitle = " + Integer.toString((int) changesYAndHeihght.getWidth()));
				System.out.println("heightTitle      = " + Integer.toString((int) changesYAndHeihght.getHeight()));
				System.out.println("legendWidth      = " + Integer.toString(legendWidth));
				System.out.println("leftBottomX      = " + Integer.toString(leftBottomX));
				System.out.println("leftBottomY      = " + Integer.toString(leftBottomY));
				System.out.println("width            = " + Integer.toString(width));
				System.out.println("height           = " + Integer.toString(height));
				System.out.println();
			}
			
			// Draw pie
			int angle = 90;
			for (String dataSetName : orderedDataSets) {
				double value = dataSet.get(dataSetName);
				int arcAngle = (int) round((value / total) * 360, 0);
				
				// Draw pie piece
				int endAngle = angle - arcAngle;
				graphics.setColor(dataSets.get(dataSetName));
				if (((angle  < 0) && (angle > -180)) || ((endAngle  < 0) && (endAngle > -180))) {
					for (int y = pieYTop + pieHeight; y >= pieYTop; y--) {
						graphics.drawArc(pieX, y, pieWidth, pieWidth / 2, angle, -arcAngle);
					}
				}
				graphics.fillArc(pieX, pieYTop, pieWidth, pieWidth / 2, angle, -arcAngle);
				
				// Draw pie sides
				graphics.setColor(outlineColor);
				graphics.drawLine(pieCenterX - (pieWidth / 2), pieCenterYTop, pieCenterX - (pieWidth / 2), pieCenterYBottom);
				graphics.drawLine(pieCenterX + (pieWidth / 2), pieCenterYTop, pieCenterX + (pieWidth / 2), pieCenterYBottom);

				angle -= arcAngle;
			}
			
			// Draw top and bottom outline
			graphics.setColor(outlineColor);
			graphics.drawArc(pieX, pieYTop, pieWidth, pieWidth / 2, 0, 360);
			graphics.drawArc(pieX, pieYTop + pieHeight, pieWidth, pieWidth / 2, 0, -180);

			// Draw lines and values
			for (String dataSetName : orderedDataSets) {
				double value = dataSet.get(dataSetName);
				String valueString = formatValue(value, valuePrecision) + (unitDescription == null ? "" : (" " + unitDescription));
				int arcAngle = (int) round((value / total) * 360, 0);
				
				int valueLineAngle = angle - (int) round(arcAngle / 2.0, 0);
				int valueLineStartX = pieCenterX + (int) round((pieWidth / 4) * Math.cos(((valueLineAngle) / 360.0) * 2 * Math.PI), 0);
				int valueLineStartY = pieCenterYTop - (int) round((pieWidth / 8) * Math.sin(((valueLineAngle) / 360.0) * 2 * Math.PI), 0);
				int valueLineEndX = pieCenterX + (int) round(((pieWidth / 2) + VALUE_LINE_EXTRA_LENGTH) * Math.cos(((valueLineAngle) / 360.0) * 2 * Math.PI), 0);
				int valueLineEndY = pieCenterYTop - (int) round(((pieWidth / 4) + VALUE_LINE_EXTRA_LENGTH) * Math.sin(((valueLineAngle) / 360.0) * 2 * Math.PI), 0);
				int valueX = pieCenterX + (int) round(((pieWidth / 2) + VALUE_LINE_EXTRA_LENGTH + VALUE_LINE_GAP) * Math.cos(((valueLineAngle) / 360.0) * 2 * Math.PI), 0);
				if (valueX < pieCenterX) {
					valueX = valueX - fontMetrics.stringWidth(valueString);
				}
				int valueY = pieCenterYTop - (int) round(((pieWidth / 4) + VALUE_LINE_EXTRA_LENGTH + VALUE_LINE_GAP) * Math.sin(((valueLineAngle) / 360.0) * 2 * Math.PI), 0) + (fontMetrics.getHeight() / 2);

				graphics.setColor(valueColor);
				graphics.setFont(valueFont);
				graphics.drawLine(valueLineStartX, valueLineStartY, valueLineEndX, valueLineEndY);
				graphics.drawString(valueString, valueX, valueY);
				
				angle -= arcAngle;
			}
		}
	}
	
	
	public void clear() {
		super.clear();
		dataSet = null;
		unitDescription = null;
	}

}
