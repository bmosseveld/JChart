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

class JScatterChartPlot extends JChartPlot {
	private static final long serialVersionUID = -7356620474405945915L;
	
	private Map<String, Map<Double, Double>> dataSet = null;
	
	private List<Double> horizontalAxis = null;
	private int horizontalAxisPrecision = STANDARD_PRECISION;
	private String horizontalAxisLabel = null;
	
	private List<Double> verticalAxis = null;
	private int verticalAxisPrecision = STANDARD_PRECISION;
	private String verticalAxisLabel = null;
	
	private Color axisColor = Color.BLACK;
	private Font axisFont = new Font("Arial", Font.PLAIN, 10);
	

	public JScatterChartPlot(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	
	public boolean addDataPoint(String dataSetName, Double x, Double y) {
		boolean ok = false;

		if (dataSets.containsKey(dataSetName)) {
			ok = true;
			if (dataSet == null) {
				dataSet = new HashMap<String, Map<Double, Double>>();
			}
			Map<Double, Double> valueMap = dataSet.get(dataSetName);
			if (valueMap == null) {
				valueMap = new HashMap<Double, Double>();
				dataSet.put(dataSetName, valueMap);
			}
			valueMap.put(x, y);
		}
		else {
			JOptionPane.showMessageDialog(null, "Unknown data set '" + dataSetName + ".", "LineChart '" + name + "' Data Set Error", JOptionPane.ERROR_MESSAGE);
		}
		
		return ok;
	}
	
	
	public boolean setPointStyle(String dataSetName, int pointStyle) {
		boolean ok = false;

		if (dataSets.containsKey(dataSetName)) {
			if ((pointStyle >= 0) && (pointStyle <= JScatterChart.POINT_STYLE_DIAMOND)) {
				if (pointStyles == null) {
					pointStyles = new HashMap<String, Integer>();
				}
				pointStyles.put(dataSetName, pointStyle);
			}
			else {
				JOptionPane.showMessageDialog(null, "Unknown point style.", "ScatterChart '" + name + "' Point Style Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "Unknown data set '" + dataSetName + ".", "LineChart '" + name + "' Data Set Error", JOptionPane.ERROR_MESSAGE);
		}
		
		return ok;
	}
	
	
	public void setHorizontalAxis(List<Double> axis, int precision) {
		horizontalAxis = axis;
		horizontalAxisPrecision = precision;
	}
	
	
	public void setHorizontalAxisPrecision(int precision) {
		horizontalAxisPrecision = precision;
	}
	
	
	public void setHorizontalAxisLabel(String label) {
		horizontalAxisLabel = label;
	}
	
	
	public void setVerticalAxis(List<Double> axis, int precision) {
		verticalAxis = axis;
		verticalAxisPrecision = precision;
	}
	
	
	public void setVerticalAxisPrecision(int precision) {
		verticalAxisPrecision = precision;
	}
	
	
	public void setVerticalAxisLabel(String label) {
		verticalAxisLabel = label;
	}
	
	
	public void setAxisColor(Color color) {
		axisColor = color;
	}
	
	
	public void setAxisFont(Font font) {
		axisFont = font;
	}

	@Override
	void draw(Graphics graphics) {
		//Set background color
		graphics.setColor(chartBackgroundColor);
		graphics.fillRect(0, 0, getWidth(), getHeight());

		if (dataSet != null) {
			FontMetrics fontMetrics = graphics.getFontMetrics(axisFont);

			Double minX = null;
			Double maxX = null;
			int maxXWidth = 0;
			
			Double minY = null;
			Double maxY = null;
			int maxYWidth = 0;
			
			if (horizontalAxis != null) {
				for (double x : horizontalAxis) {
					minX = minX == null ? x : Math.min(minX, x);
					maxX = maxX == null ? x : Math.max(maxX, x);
					maxXWidth = Math.max(maxYWidth, fontMetrics.stringWidth(formatValue(x, horizontalAxisPrecision)));
				}
			}
			
			if (verticalAxis != null) {
				for (double y : verticalAxis) {
					minY = minY == null ? y : Math.min(minY, y);
					maxY = maxY == null ? y : Math.max(maxY, y);
					maxYWidth = Math.max(maxYWidth, fontMetrics.stringWidth(formatValue(y, verticalAxisPrecision)));
				}
			}
			
			if ((horizontalAxis == null) || (verticalAxis == null)) {
				for (String dataSetName : dataSet.keySet()) {
					for (double x : dataSet.get(dataSetName).keySet()) {
						if (horizontalAxis == null) {
							minX = minX == null ? x : Math.min(minX, x);
							maxX = maxX == null ? x : Math.max(maxX, x);
							maxXWidth = Math.max(maxYWidth, fontMetrics.stringWidth(formatValue(x, horizontalAxisPrecision)));
						}
						
						if (verticalAxis == null) {
							double y = dataSet.get(dataSetName).get(x);
							minY = minY == null ? y : Math.min(minY, y);
							maxY = maxY == null ? y : Math.max(maxY, y);
							maxYWidth = Math.max(maxYWidth, fontMetrics.stringWidth(formatValue(y, verticalAxisPrecision)));
						}
					}
				}
			}
			
			int leftBottomX = MARGIN;
			int leftBottomY = getHeight() - MARGIN;
			int width = getWidth() - (2 * MARGIN);
			int height = getHeight() - (2 * MARGIN);
			
			Dimension changesYAndHeihght = showTitle(graphics, leftBottomX, leftBottomY, width, height);
			leftBottomY = changesYAndHeihght.width;
			height = changesYAndHeihght.height;
			
			int legendHeight = showLegendBottom(graphics, leftBottomX, leftBottomY, width, false);
			leftBottomY = leftBottomY - legendHeight;
			height = height - legendHeight;
			
			if (horizontalAxisLabel != null) {
				if (!((minY < 0.0) && (maxY > 0.0))) {
					leftBottomY = leftBottomY - fontMetrics.getHeight() - AXIS_VALUE_GAP;
					height = height - fontMetrics.getHeight() - AXIS_VALUE_GAP;
				}
			}
			
			width = width - ((maxXWidth + 1) / 2);
			 
			if (verticalAxisLabel != null) {
				height = height - fontMetrics.getHeight() - AXIS_VALUE_GAP;
			}
			
			int verticalAxisX = leftBottomX;
			if ((minX < 0.0) && (maxX > 0.0)) {
				leftBottomX = leftBottomX + ((maxXWidth + 1) / 2);
				width = width - ((maxXWidth + 1) / 2);
				verticalAxisX = valueToPosition(0.0, leftBottomX, leftBottomX + width, minX, maxX);
				if ((verticalAxisX - (maxYWidth + AXIS_VALUE_GAP)) < leftBottomX) {
					leftBottomX = leftBottomX - verticalAxisX + maxYWidth + AXIS_VALUE_GAP;
					width = width - ((maxYWidth + AXIS_VALUE_GAP) - verticalAxisX);
					verticalAxisX = valueToPosition(0.0, leftBottomX, leftBottomX + width, minX, maxX);
				}
			}
			else {
				int xShift = Math.max(maxYWidth + AXIS_VALUE_GAP, (maxXWidth + 1) / 2);
				leftBottomX = leftBottomX + xShift;
				width = width - xShift;
				verticalAxisX = leftBottomX;
			}
			
			int horizontalAxisY = leftBottomY;
			if ((minY < 0.0) && (maxY > 0.0)) {
				leftBottomY = leftBottomY - ((fontMetrics.getHeight() + 1) / 2);
				height = height - ((fontMetrics.getHeight() + 1) / 2);
				horizontalAxisY = valueToPosition(0.0, leftBottomY, leftBottomY - height, minY, maxY);
			}
			else {
				leftBottomY = leftBottomY - fontMetrics.getHeight() - AXIS_VALUE_GAP;
				height = height - fontMetrics.getHeight() - AXIS_VALUE_GAP;
				horizontalAxisY = leftBottomY;
			}
			
			List<Double> hAxis = horizontalAxis(horizontalAxis, minX, maxX, leftBottomX, leftBottomX + width, fontMetrics, horizontalAxisPrecision);
			List<Double> vAxis = verticalAxis(verticalAxis, minY, maxY, leftBottomY, leftBottomY - height, fontMetrics, verticalAxisPrecision);
			
			if (JChartPlot.DEBUG) {
				System.out.println();
				System.out.println(name);
				System.out.println("PanelWidth       = " + Integer.toString(getWidth()));
				System.out.println("PanelHeight      = " + Integer.toString(getHeight()));
				System.out.println("MARGIN           = " + Integer.toString(MARGIN));
				System.out.println("AXIS_VALUE_GAP   = " + Integer.toString(AXIS_VALUE_GAP));
				System.out.println("FontHeight       = " + Integer.toString(graphics.getFontMetrics(axisFont).getHeight()));
				System.out.println("maxXWidth        = " + Integer.toString(maxXWidth));
				System.out.println("maxYWidth        = " + Integer.toString(maxYWidth));
				System.out.println("leftBottomyTitle = " + Integer.toString((int) changesYAndHeihght.getWidth()));
				System.out.println("heightTitle      = " + Integer.toString((int) changesYAndHeihght.getHeight()));
				System.out.println("legendHeight     = " + Integer.toString(legendHeight));
				System.out.println("leftBottomX      = " + Integer.toString(leftBottomX));
				System.out.println("leftBottomY      = " + Integer.toString(leftBottomY));
				System.out.println("width            = " + Integer.toString(width));
				System.out.println("height           = " + Integer.toString(height));
				System.out.println("verticalAxisX    = " + Integer.toString(verticalAxisX));
				System.out.println("horizontalAxisY  = " + Integer.toString(horizontalAxisY));
				System.out.println();
			}
			
			// Draw data 
			for (String dataSetName : dataSets.keySet()) {
				graphics.setColor(dataSets.get(dataSetName));
				List<Double> xValues = new ArrayList<Double>();
				xValues.addAll(dataSet.get(dataSetName).keySet());
				Collections.sort(xValues);
				for (Double x : xValues) {
					Integer xPos = valueToPosition(x, leftBottomX, leftBottomX + width, hAxis.get(0), hAxis.get(hAxis.size() - 1));
					Integer yPos = null;
					Double yValue = dataSet.get(dataSetName).get(x);
					if (yValue != null) {
						yPos = valueToPosition(yValue, leftBottomY, leftBottomY - height, vAxis.get(0), vAxis.get(vAxis.size() - 1));
						drawPoint(graphics, xPos, yPos, getLineStyle(dataSetName));
					}
				}
			}
			
			// Set axis color
			graphics.setColor(axisColor);
			
			//Set axis font
			graphics.setFont(axisFont);
			
			// Draw horizontal axis
			graphics.drawLine(leftBottomX, horizontalAxisY, leftBottomX + width, horizontalAxisY);
			for (double x : hAxis) {
				String xString = formatValue(x, horizontalAxisPrecision);
				Integer xPos = valueToPosition(x, leftBottomX, leftBottomX + width, hAxis.get(0), hAxis.get(hAxis.size() - 1));
				int halfXStringWidth = (fontMetrics.stringWidth(xString) / 2);
				if ((horizontalAxisY == leftBottomY) || (xPos < (verticalAxisX - halfXStringWidth - 2)) || (xPos > (verticalAxisX + halfXStringWidth + 2))) {
					graphics.drawLine(xPos, horizontalAxisY + 2, xPos, horizontalAxisY);
					graphics.drawString(xString, xPos - halfXStringWidth, horizontalAxisY + AXIS_VALUE_GAP + fontMetrics.getHeight());
				}
			}
			if (horizontalAxisLabel != null) {
				graphics.drawString(horizontalAxisLabel, leftBottomX + width + ((maxXWidth + 1) / 2) - fontMetrics.stringWidth(horizontalAxisLabel), horizontalAxisY + (2 * fontMetrics.getHeight() + AXIS_VALUE_GAP));
			}
			
			// Draw vertical axis
			graphics.drawLine(verticalAxisX, leftBottomY, verticalAxisX, leftBottomY - height);
			for (double y : vAxis) {
				String yString = formatValue(y, verticalAxisPrecision);
				int yPos = valueToPosition(y, leftBottomY, leftBottomY - height, vAxis.get(0), vAxis.get(vAxis.size() - 1));
				if ((verticalAxisX == leftBottomX) || (yPos > (horizontalAxisY + fontMetrics.getHeight() + 2)) || (yPos < (horizontalAxisY - fontMetrics.getHeight() - 2))) {
					graphics.drawLine(verticalAxisX - 2, yPos, verticalAxisX, yPos);
					graphics.drawString(yString, verticalAxisX - AXIS_VALUE_GAP - fontMetrics.stringWidth(yString), yPos + (fontMetrics.getHeight() / 2));
				}
			}
			if (verticalAxisLabel != null) {
				graphics.drawString(verticalAxisLabel, Math.max(MARGIN, verticalAxisX - (fontMetrics.stringWidth(verticalAxisLabel) / 2)), leftBottomY - height - AXIS_VALUE_GAP);
			}
		}
	}


	@Override
	public void clear() {
		super.clear();
		dataSet = null;
		pointStyles = null;
		horizontalAxis = null;
		horizontalAxisLabel = null;
		verticalAxis = null;
		verticalAxisLabel = null;
	}

}
