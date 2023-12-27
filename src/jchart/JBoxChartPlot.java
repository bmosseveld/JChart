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

class JBoxChartPlot extends JChartPlot {
	private static final long serialVersionUID = 2866745074890022093L;
	
	private static final int DATASET_MARGIN = 10;

	private Map<String, List<Double>> dataSet = null;
	
	private int style = JBoxChart.BOX_CHART_STYLE_VERTICAL;
	private List<String> dataSetAxis = null;
	private String dataSetAxisLabel = null;
	
	private List<Double> valueAxis = null;
	private int valueAxisPrecision = STANDARD_PRECISION;
	private String valueAxisLabel = null;
	
	private Color axisColor = Color.BLACK;
	private Font axisFont = new Font("Arial", Font.PLAIN, 10);
	
	private int maxBoxWidth = Integer.MAX_VALUE;

	
	public JBoxChartPlot(String name, int boxChartStyle) {
		super(name);
		if ((boxChartStyle >= JBoxChart.BOX_CHART_STYLE_VERTICAL) && (boxChartStyle <= JBoxChart.BOX_CHART_STYLE_HORIZONTAL)) {
			style = boxChartStyle;
		}
	}
	
	
	public boolean addDataPoint(String dataSetName, Double value) {
		boolean ok = false;

		if (dataSets.containsKey(dataSetName)) {
			ok = true;
			if (dataSet == null) {
				dataSet = new HashMap<String, List<Double>>();
			}
			List<Double> valueList = dataSet.get(dataSetName);
			if (valueList == null) {
				valueList = new ArrayList<Double>();
				dataSet.put(dataSetName, valueList);
			}
			valueList.add(value);
		}
		else {
			JOptionPane.showMessageDialog(null, "Unknown data set '" + dataSetName + ".", "LineChart '" + name + "' Data Set Error", JOptionPane.ERROR_MESSAGE);
		}
		
		return ok;
	}
	
	
	public void setDataSetAxis(List<String> axis) {
		dataSetAxis = axis;
	}
	
	
	public void setDataSetAxisLabel(String label) {
		dataSetAxisLabel = label;
	}
	
	
	public void setValueAxis(List<Double> axis, int precision) {
		valueAxis = axis;
		valueAxisPrecision = precision;
	}
	
	
	public void setValueAxisPrecision(int precision) {
		valueAxisPrecision = precision;
	}
	
	
	public void setValueAxisLabel(String label) {
		valueAxisLabel = label;
	}
	
	
	public void setAxisColor(Color color) {
		axisColor = color;
	}
	
	
	public void setAxisFont(Font font) {
		axisFont = font;
	}
	
	
	public void setMaxBoxWidth(int maxBoxWidth) {
		this.maxBoxWidth = maxBoxWidth;
	}
	
	
	@Override
	void draw(Graphics graphics) {
		if (style == JBoxChart.BOX_CHART_STYLE_VERTICAL) {
			drawVertical(graphics);
		}
		else if (style == JBoxChart.BOX_CHART_STYLE_HORIZONTAL) {
			drawHorizontal(graphics);
		}
	}
	
	
	private void drawVertical(Graphics graphics) {
		//Set background color
		graphics.setColor(chartBackgroundColor);
		graphics.fillRect(0, 0, getWidth(), getHeight());

		if (dataSet != null) {
			FontMetrics fontMetrics = graphics.getFontMetrics(axisFont);
			
			for (String dataSetName : dataSet.keySet()) {
				Collections.sort(dataSet.get(dataSetName));
			}

			int maxXWidth = 0;
			
			Double minY = null;
			Double maxY = null;
			int maxYWidth = 0;
			
			if (dataSetAxis != null) {
				for (String dataSetName : dataSetAxis) {
					maxXWidth = Math.max(maxYWidth, fontMetrics.stringWidth(dataSetName));
				}
			}
			
			if (valueAxis != null) {
				for (double y : valueAxis) {
					minY = minY == null ? y : Math.min(minY, y);
					maxY = maxY == null ? y : Math.max(maxY, y);
					maxYWidth = Math.max(maxYWidth, fontMetrics.stringWidth(formatValue(y, valueAxisPrecision)));
				}
			}

			if ((dataSetAxis == null) || (valueAxis == null)) {
				for (String dataSetName : dataSet.keySet()) {
					maxXWidth = Math.max(maxXWidth, fontMetrics.stringWidth(dataSetName));
					
					if (valueAxis == null) {
						minY = minY == null ? dataSet.get(dataSetName).get(0) : Math.min(minY, dataSet.get(dataSetName).get(0));
						maxY = maxY == null ? dataSet.get(dataSetName).get(dataSet.get(dataSetName).size() - 1) : Math.max(maxY, dataSet.get(dataSetName).get(dataSet.get(dataSetName).size() - 1));
						maxYWidth = Math.max(maxYWidth, fontMetrics.stringWidth(formatValue(minY, valueAxisPrecision)));
						maxYWidth = Math.max(maxYWidth, fontMetrics.stringWidth(formatValue(maxY, valueAxisPrecision)));
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
			
			if (dataSetAxisLabel != null) {
				leftBottomY = leftBottomY - fontMetrics.getHeight() - AXIS_VALUE_GAP;
				height = height - fontMetrics.getHeight() - AXIS_VALUE_GAP;
			}
			
			if (valueAxisLabel != null) {
				height = height - fontMetrics.getHeight() - AXIS_VALUE_GAP;
			}
			
			int xShift = Math.max(maxYWidth + AXIS_VALUE_GAP, (maxXWidth + 1) / 2);
			leftBottomX = leftBottomX + xShift;
			width = width - xShift;
			int verticalAxisX = leftBottomX;
			
			int horizontalAxisY = leftBottomY;

			leftBottomY = leftBottomY - fontMetrics.getHeight() - AXIS_VALUE_GAP;
			height = height - fontMetrics.getHeight() - AXIS_VALUE_GAP;
			horizontalAxisY = leftBottomY;

			int dataSetWidth = (int) Math.round(((double) width - DATASET_MARGIN) / (double) dataSetList.size());
			int barWidth = dataSetWidth - DATASET_MARGIN;
			List<Double> vAxis = verticalAxis(valueAxis, minY, maxY, leftBottomY, leftBottomY - height, fontMetrics, valueAxisPrecision);
			
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
				System.out.println("leftBottomX      = " + Integer.toString(leftBottomX));
				System.out.println("leftBottomY      = " + Integer.toString(leftBottomY));
				System.out.println("width            = " + Integer.toString(width));
				System.out.println("height           = " + Integer.toString(height));
				System.out.println("verticalAxisX    = " + Integer.toString(verticalAxisX));
				System.out.println("horizontalAxisY  = " + Integer.toString(horizontalAxisY));
				System.out.println("dataSetWidth     = " + Integer.toString(dataSetWidth));
				System.out.println("barWidth         = " + Integer.toString(barWidth));
				System.out.println();
			}
			
			if (barWidth < 1) {
				showError(graphics, "<html>BoxChart '" + name + "'<br>Data set error:<br>Too many data sets.</html>"); 
			}
			else {
				int finalBarWidth = Math.min(barWidth, maxBoxWidth);
				
				List<String> orderedDataSets = new ArrayList<String>();
				orderedDataSets.addAll(dataSetList);
				if (orderDataSets) {
					Collections.sort(orderedDataSets);
				}
				
				// Set axis color
				graphics.setColor(axisColor);
				
				//Set axis font
				graphics.setFont(axisFont);
				
				// Draw horizontal axis
				for (int dataSetNr = 0; dataSetNr < orderedDataSets.size(); dataSetNr++) {
					String xAxisLabel = orderedDataSets.get(dataSetNr);
					int xDataSetCenter = leftBottomX + DATASET_MARGIN + (dataSetNr * (barWidth + DATASET_MARGIN)) + (barWidth / 2);
					graphics.drawString(xAxisLabel, xDataSetCenter - (fontMetrics.stringWidth(xAxisLabel) / 2), horizontalAxisY + AXIS_VALUE_GAP + fontMetrics.getHeight());
				}
				if (dataSetAxisLabel != null) {
					graphics.drawString(dataSetAxisLabel, leftBottomX + width - fontMetrics.stringWidth(dataSetAxisLabel), horizontalAxisY + (2 * fontMetrics.getHeight() + AXIS_VALUE_GAP));
				}
				
				// Draw vertical axis
				graphics.drawLine(verticalAxisX, leftBottomY, verticalAxisX, leftBottomY - height);
				for (double y : vAxis) {
					String yString = formatValue(y, valueAxisPrecision);
					int yPos = valueToPosition(y, leftBottomY, leftBottomY - height, vAxis.get(0), vAxis.get(vAxis.size() - 1));
					if ((verticalAxisX == leftBottomX) || (yPos > (horizontalAxisY + fontMetrics.getHeight() + 2)) || (yPos < (horizontalAxisY - fontMetrics.getHeight() - 2))) {
						graphics.setColor(Color.LIGHT_GRAY);
						graphics.drawLine(verticalAxisX, yPos, leftBottomX + width, yPos);
						graphics.setColor(axisColor);
						graphics.drawLine(verticalAxisX - 2, yPos, verticalAxisX, yPos);
						graphics.drawString(yString, verticalAxisX - AXIS_VALUE_GAP - fontMetrics.stringWidth(yString), yPos + (fontMetrics.getHeight() / 2));
					}
				}
				if (valueAxisLabel != null) {
					graphics.drawString(valueAxisLabel, Math.max(MARGIN, verticalAxisX - (fontMetrics.stringWidth(valueAxisLabel) / 2)), leftBottomY - height - AXIS_VALUE_GAP);
				}
				
				// Draw boxes
				for (int dataSetNr = 0; dataSetNr < orderedDataSets.size(); dataSetNr++) {
					String dataSetName = orderedDataSets.get(dataSetNr);
					Color dataSetColor = dataSets.get(dataSetName); 
					graphics.setColor(dataSetColor);
					
					Double minimum = dataSet.get(dataSetName).get(0);
					Double maximum = dataSet.get(dataSetName).get(dataSet.get(dataSetName).size() - 1);
					Double median = null;
					Double p25 = null;
					Double p75 = null;
					String medianIndexString = null;
					String p25IndexString = null;
					String p75IndexString = null;
					
					if ((dataSet.get(dataSetName).size() % 2) == 0) { // Even number of values
						Integer medianLowIndex = (dataSet.get(dataSetName).size() / 2);
						Integer medianHighIndex = medianLowIndex + 1;
						Double medianLow  = dataSet.get(dataSetName).get(medianLowIndex - 1);
						Double medianHigh = dataSet.get(dataSetName).get(medianHighIndex - 1);
						median = (medianLow + medianHigh) / 2.0;
						
						if (JChartPlot.DEBUG) {
							medianIndexString = medianLowIndex + " + " + medianHighIndex + " -> " + Integer.toString(medianLowIndex - 1) + " + " + Integer.toString(medianHighIndex - 1);
						}
						
						if ((medianLowIndex % 2) == 0) { // Even number of values
							Integer p25LowIndex = medianLowIndex / 2;
							Integer p25HighIndex = p25LowIndex + 1;
							Double p25Low  = dataSet.get(dataSetName).get(p25LowIndex - 1);
							Double p25High = dataSet.get(dataSetName).get(p25HighIndex - 1);
							p25 = (p25Low + p25High) / 2.0;
							
							Integer p75LowIndex = medianLowIndex + p25LowIndex;
							Integer p75HighIndex = p75LowIndex + 1;
							Double p75Low  = dataSet.get(dataSetName).get(p75LowIndex - 1);
							Double p75High  = dataSet.get(dataSetName).get(p75HighIndex - 1);
							p75 = (p75Low + p75High) / 2.0;
							
							if (JChartPlot.DEBUG) {
								p25IndexString = p25LowIndex + " + " + p25HighIndex + " -> " + Integer.toString(p25LowIndex - 1) + " + " + Integer.toString(p25HighIndex - 1);
								p75IndexString = p75LowIndex + " + " + p75HighIndex + " -> " + Integer.toString(p75LowIndex - 1) + " + " + Integer.toString(p75HighIndex - 1);
							}
						}
						else {
							Integer p25Index = medianHighIndex / 2;
							p25 = dataSet.get(dataSetName).get(p25Index - 1);
							
							Integer p75Index = medianLowIndex + p25Index;
							p75 = dataSet.get(dataSetName).get(p75Index - 1);
							
							if (JChartPlot.DEBUG) {
								p25IndexString = p25Index + " -> " + Integer.toString(p25Index - 1);
								p75IndexString = p75Index + " -> " + Integer.toString(p75Index - 1);
							}
						}
					}
					else {
						Integer medianIndex = (dataSet.get(dataSetName).size() + 1) / 2;
						median = dataSet.get(dataSetName).get(medianIndex - 1);
						
						if (JChartPlot.DEBUG) {
							medianIndexString = medianIndex + " -> " + Integer.toString(medianIndex - 1);
						}
						
						if (((medianIndex - 1) % 2) == 0) { // Even number of values
							Integer p25LowIndex = (medianIndex - 1) / 2;
							Integer p25HighIndex = p25LowIndex + 1;
							Double p25Low  = dataSet.get(dataSetName).get(p25LowIndex - 1);
							Double p25High = dataSet.get(dataSetName).get(p25HighIndex - 1);
							p25 = (p25Low + p25High) / 2.0;
							
							Integer p75LowIndex = medianIndex + p25LowIndex;
							Integer p75HighIndex = p75LowIndex + 1;
							Double p75Low  = dataSet.get(dataSetName).get(p75LowIndex - 1);
							Double p75High  = dataSet.get(dataSetName).get(p75HighIndex - 1);
							p75 = (p75Low + p75High) / 2.0;
							
							if (JChartPlot.DEBUG) {
								p25IndexString = p25LowIndex + " + " + p25HighIndex + " -> " + Integer.toString(p25LowIndex - 1) + " + " + Integer.toString(p25HighIndex - 1);
								p75IndexString = p75LowIndex + " + " + p75HighIndex + " -> " + Integer.toString(p75LowIndex - 1) + " + " + Integer.toString(p75HighIndex - 1);
							}
						}
						else {
							Integer p25Index = medianIndex / 2;
							p25 = dataSet.get(dataSetName).get(p25Index - 1);
							
							Integer p75Index = medianIndex + p25Index;
							p75 = dataSet.get(dataSetName).get(p75Index - 1);
							
							if (JChartPlot.DEBUG) {
								p25IndexString = p25Index + " -> " + Integer.toString(p25Index - 1);
								p75IndexString = p75Index + " -> " + Integer.toString(p75Index - 1);
							}
						}
					}
					
					if (JChartPlot.DEBUG) {
						System.out.println(dataSetName + " (" + Integer.toString(dataSet.get(dataSetName).size()) + ")");
						System.out.println("  minimum        = " + minimum + " (1 -> 0)");
						System.out.println("  p25            = " + p25 + " (" + p25IndexString + ")");
						System.out.println("  median         = " + median + " (" + medianIndexString + ")");
						System.out.println("  p75            = " + p75 + " (" + p75IndexString + ")");
						System.out.println("  maximum        = " + maximum + " (" + Integer.toString(dataSet.get(dataSetName).size()) + " -> " + Integer.toString(dataSet.get(dataSetName).size() - 1) + ")");
						System.out.println();
					}
					
					int medianY = valueToPosition(median, leftBottomY, leftBottomY - height, vAxis.get(0), vAxis.get(vAxis.size() - 1));
					int p25Y = valueToPosition(p25, leftBottomY, leftBottomY - height, vAxis.get(0), vAxis.get(vAxis.size() - 1));
					int p75Y = valueToPosition(p75, leftBottomY, leftBottomY - height, vAxis.get(0), vAxis.get(vAxis.size() - 1));
					int minimumY = valueToPosition(minimum, leftBottomY, leftBottomY - height, vAxis.get(0), vAxis.get(vAxis.size() - 1));
					int maximumY = valueToPosition(maximum, leftBottomY, leftBottomY - height, vAxis.get(0), vAxis.get(vAxis.size() - 1));

					int barX = leftBottomX + DATASET_MARGIN + (dataSetNr * (barWidth + DATASET_MARGIN)) + (barWidth / 2) - (finalBarWidth / 2);
					int whiskerX = barX + (finalBarWidth / 2);

					graphics.fillRect(barX, p75Y, finalBarWidth, Math.abs(p75Y - p25Y));
					graphics.drawLine(whiskerX, minimumY, whiskerX, p25Y);
					graphics.drawLine(barX, minimumY, barX + finalBarWidth - 1, minimumY);
					graphics.drawLine(whiskerX, p75Y, whiskerX, maximumY);
					graphics.drawLine(barX, maximumY, barX + finalBarWidth - 1, maximumY);
					
					// luminance: sqrt( 0.299*R^2 + 0.587*G^2 + 0.114*B^2 )
					double rgbLuminance = Math.sqrt((0.299 * Math.pow(dataSetColor.getRed(),2.0)) + (0.587 * Math.pow(dataSetColor.getGreen(),2.0)) + (0.114 * Math.pow(dataSetColor.getBlue(),2.0)));
					if (rgbLuminance < 100.0) {
						graphics.setColor(Color.WHITE);
					}
					else {
						graphics.setColor(Color.BLACK);
					}
					graphics.drawLine(barX, medianY, barX + finalBarWidth - 1, medianY);
				}
			}
		}
	}
	
	
	private void drawHorizontal(Graphics graphics) {
		//Set background color
		graphics.setColor(chartBackgroundColor);
		graphics.fillRect(0, 0, getWidth(), getHeight());

		if (dataSet != null) {
			FontMetrics fontMetrics = graphics.getFontMetrics(axisFont);
			
			for (String dataSetName : dataSet.keySet()) {
				Collections.sort(dataSet.get(dataSetName));
			}
			
			Double minX = null;
			Double maxX = null;
			int maxXWidth = 0;

			int maxYWidth = 0;
			
			if (dataSetAxis != null) {
				for (String dataSet : dataSetAxis) {
					maxYWidth = Math.max(maxYWidth, fontMetrics.stringWidth(dataSet));
				}
			}
			
			if (valueAxis != null) {
				for (double x : valueAxis) {
					minX = minX == null ? x : Math.min(minX, x);
					maxX = maxX == null ? x : Math.max(maxX, x);
					maxXWidth = Math.max(maxXWidth, fontMetrics.stringWidth(formatValue(x, valueAxisPrecision)));
				}
			}

			if ((dataSetAxis == null) || (valueAxis == null)) {
				for (String dataSetName : dataSet.keySet()) {
					maxYWidth = Math.max(maxYWidth, fontMetrics.stringWidth(dataSetName));
					
					if (valueAxis == null) {
						minX = minX == null ? dataSet.get(dataSetName).get(0) : Math.min(minX, dataSet.get(dataSetName).get(0));
						maxX = maxX == null ? dataSet.get(dataSetName).get(dataSet.get(dataSetName).size() - 1) : Math.max(maxX, dataSet.get(dataSetName).get(dataSet.get(dataSetName).size() - 1));
						maxXWidth = Math.max(maxYWidth, fontMetrics.stringWidth(formatValue(minX, valueAxisPrecision)));
						maxXWidth = Math.max(maxYWidth, fontMetrics.stringWidth(formatValue(maxX, valueAxisPrecision)));
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

			if (valueAxisLabel != null) {
				leftBottomY = leftBottomY - fontMetrics.getHeight() - AXIS_VALUE_GAP;
				height = height - fontMetrics.getHeight() - AXIS_VALUE_GAP;
			}

			if (dataSetAxisLabel != null) {
				height = height - fontMetrics.getHeight() + AXIS_VALUE_GAP;
			}
			
			width = width - ((maxXWidth + 1) / 2);
			
			if ((minX < 0.0) && (maxX > 0.0)) {
				leftBottomX = leftBottomX + Math.max((maxXWidth + 1) / 2, maxYWidth);
				width = width - Math.max((maxXWidth + 1) / 2, maxYWidth);
			}
			else {
				int xShift = Math.max(maxYWidth + AXIS_VALUE_GAP, (maxXWidth + 1) / 2);
				leftBottomX = leftBottomX + xShift;
				width = width - xShift;
			}
			
			leftBottomY = leftBottomY - fontMetrics.getHeight() - AXIS_VALUE_GAP;
			height = height - fontMetrics.getHeight() - AXIS_VALUE_GAP;
			int horizontalAxisY = leftBottomY;
			
			List<Double> hAxis = horizontalAxis(valueAxis, minX, maxX, leftBottomX, leftBottomX + width, fontMetrics, valueAxisPrecision);
			int dataSetHeight = (int) Math.round(((double) height - DATASET_MARGIN) / (double) dataSetList.size());
			int barHeight = dataSetHeight - DATASET_MARGIN;
			
			if (JChartPlot.DEBUG) {
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
				System.out.println("leftBottomX      = " + Integer.toString(leftBottomX));
				System.out.println("leftBottomY      = " + Integer.toString(leftBottomY));
				System.out.println("width            = " + Integer.toString(width));
				System.out.println("height           = " + Integer.toString(height));
				System.out.println("horizontalAxisY  = " + Integer.toString(horizontalAxisY));
				System.out.println("dataSetHeight     = " + Integer.toString(dataSetHeight));
				System.out.println("barHeight        = " + Integer.toString(barHeight));
				System.out.println();
			}
			
			if (barHeight < 1) {
				showError(graphics, "<html>BoxChart '" + name + "'<br>Data set error:<br>Too many data sets.</html>");
			}
			else {
				int finalBarHeight = Math.min(barHeight, maxBoxWidth);
				
				List<String> orderedDataSets = new ArrayList<String>();
				orderedDataSets.addAll(dataSetList);
				if (orderDataSets) {
					Collections.sort(orderedDataSets);
				}
				
				// Set axis color
				graphics.setColor(axisColor);
				
				//Set axis font
				graphics.setFont(axisFont);
				
				// Draw horizontal axis
				graphics.drawLine(leftBottomX, horizontalAxisY, leftBottomX + width, horizontalAxisY);
				for (double x : hAxis) {
					String xString = formatValue(x, valueAxisPrecision);
					Integer xPos = valueToPosition(x, leftBottomX, leftBottomX + width, hAxis.get(0), hAxis.get(hAxis.size() - 1));
					int halfXStringWidth = (fontMetrics.stringWidth(xString) / 2);
					graphics.setColor(Color.LIGHT_GRAY);
					graphics.drawLine(xPos, leftBottomY, xPos, leftBottomY - height);
					graphics.setColor(axisColor);
					graphics.drawLine(xPos, leftBottomY + 2, xPos, leftBottomY);
					graphics.drawString(xString, xPos - halfXStringWidth, leftBottomY + 4 + fontMetrics.getHeight());
				}
				if (valueAxisLabel != null) {
					graphics.drawString(valueAxisLabel, leftBottomX + width + ((maxXWidth + 1) / 2) - fontMetrics.stringWidth(valueAxisLabel), horizontalAxisY + (2 * fontMetrics.getHeight() + AXIS_VALUE_GAP));
				}
				
				// Draw vertical axis
				for (int dataSetNr = 0; dataSetNr < orderedDataSets.size(); dataSetNr++) {
					String yAxisLabel = orderedDataSets.get(dataSetNr);
					int yDataSetCenter = leftBottomY - height + (dataSetNr * dataSetHeight) + (dataSetHeight / 2);
					graphics.drawString(yAxisLabel, leftBottomX - AXIS_VALUE_GAP - fontMetrics.stringWidth(yAxisLabel), yDataSetCenter + (fontMetrics.getHeight() / 2));
				}
				if (dataSetAxisLabel != null) {
					graphics.drawString(dataSetAxisLabel, Math.max(MARGIN, leftBottomX - (fontMetrics.stringWidth(dataSetAxisLabel) / 2)), leftBottomY - height - AXIS_VALUE_GAP);
				}

				// Draw bars
				for (int dataSetNr = 0; dataSetNr < orderedDataSets.size(); dataSetNr++) {
					String dataSetName = orderedDataSets.get(dataSetNr);
					Color dataSetColor = dataSets.get(dataSetName); 
					graphics.setColor(dataSetColor);
					
					Double minimum = dataSet.get(dataSetName).get(0);
					Double maximum = dataSet.get(dataSetName).get(dataSet.get(dataSetName).size() - 1);
					Double median = null;
					Double p25 = null;
					Double p75 = null;
					String medianIndexString = null;
					String p25IndexString = null;
					String p75IndexString = null;
					
					if ((dataSet.get(dataSetName).size() % 2) == 0) { // Even number of values
						Integer medianLowIndex = (dataSet.get(dataSetName).size() / 2);
						Integer medianHighIndex = medianLowIndex + 1;
						Double medianLow  = dataSet.get(dataSetName).get(medianLowIndex - 1);
						Double medianHigh = dataSet.get(dataSetName).get(medianHighIndex - 1);
						median = (medianLow + medianHigh) / 2.0;
						
						if (JChartPlot.DEBUG) {
							medianIndexString = medianLowIndex + " + " + medianHighIndex + " -> " + Integer.toString(medianLowIndex - 1) + " + " + Integer.toString(medianHighIndex - 1);
						}
						
						if ((medianLowIndex % 2) == 0) { // Even number of values
							Integer p25LowIndex = medianLowIndex / 2;
							Integer p25HighIndex = p25LowIndex + 1;
							Double p25Low  = dataSet.get(dataSetName).get(p25LowIndex - 1);
							Double p25High = dataSet.get(dataSetName).get(p25HighIndex - 1);
							p25 = (p25Low + p25High) / 2.0;
							
							Integer p75LowIndex = medianLowIndex + p25LowIndex;
							Integer p75HighIndex = p75LowIndex + 1;
							Double p75Low  = dataSet.get(dataSetName).get(p75LowIndex - 1);
							Double p75High  = dataSet.get(dataSetName).get(p75HighIndex - 1);
							p75 = (p75Low + p75High) / 2.0;
							
							if (JChartPlot.DEBUG) {
								p25IndexString = p25LowIndex + " + " + p25HighIndex + " -> " + Integer.toString(p25LowIndex - 1) + " + " + Integer.toString(p25HighIndex - 1);
								p75IndexString = p75LowIndex + " + " + p75HighIndex + " -> " + Integer.toString(p75LowIndex - 1) + " + " + Integer.toString(p75HighIndex - 1);
							}
						}
						else {
							Integer p25Index = medianHighIndex / 2;
							p25 = dataSet.get(dataSetName).get(p25Index - 1);
							
							Integer p75Index = medianLowIndex + p25Index;
							p75 = dataSet.get(dataSetName).get(p75Index - 1);
							
							if (JChartPlot.DEBUG) {
								p25IndexString = p25Index + " -> " + Integer.toString(p25Index - 1);
								p75IndexString = p75Index + " -> " + Integer.toString(p75Index - 1);
							}
						}
					}
					else {
						Integer medianIndex = (dataSet.get(dataSetName).size() + 1) / 2;
						median = dataSet.get(dataSetName).get(medianIndex - 1);
						
						if (JChartPlot.DEBUG) {
							medianIndexString = medianIndex + " -> " + Integer.toString(medianIndex - 1);
						}
						
						if (((medianIndex - 1) % 2) == 0) { // Even number of values
							Integer p25LowIndex = (medianIndex - 1) / 2;
							Integer p25HighIndex = p25LowIndex + 1;
							Double p25Low  = dataSet.get(dataSetName).get(p25LowIndex - 1);
							Double p25High = dataSet.get(dataSetName).get(p25HighIndex - 1);
							p25 = (p25Low + p25High) / 2.0;
							
							Integer p75LowIndex = medianIndex + p25LowIndex;
							Integer p75HighIndex = p75LowIndex + 1;
							Double p75Low  = dataSet.get(dataSetName).get(p75LowIndex - 1);
							Double p75High  = dataSet.get(dataSetName).get(p75HighIndex - 1);
							p75 = (p75Low + p75High) / 2.0;
							
							if (JChartPlot.DEBUG) {
								p25IndexString = p25LowIndex + " + " + p25HighIndex + " -> " + Integer.toString(p25LowIndex - 1) + " + " + Integer.toString(p25HighIndex - 1);
								p75IndexString = p75LowIndex + " + " + p75HighIndex + " -> " + Integer.toString(p75LowIndex - 1) + " + " + Integer.toString(p75HighIndex - 1);
							}
						}
						else {
							Integer p25Index = medianIndex / 2;
							p25 = dataSet.get(dataSetName).get(p25Index - 1);
							
							Integer p75Index = medianIndex + p25Index;
							p75 = dataSet.get(dataSetName).get(p75Index - 1);
							
							if (JChartPlot.DEBUG) {
								p25IndexString = p25Index + " -> " + Integer.toString(p25Index - 1);
								p75IndexString = p75Index + " -> " + Integer.toString(p75Index - 1);
							}
						}
					}
					
					if (JChartPlot.DEBUG) {
						System.out.println(dataSetName + " (" + Integer.toString(dataSet.get(dataSetName).size()) + ")");
						System.out.println("  minimum        = " + minimum + " (1 -> 0)");
						System.out.println("  p25            = " + p25 + " (" + p25IndexString + ")");
						System.out.println("  median         = " + median + " (" + medianIndexString + ")");
						System.out.println("  p75            = " + p75 + " (" + p75IndexString + ")");
						System.out.println("  maximum        = " + maximum + " (" + Integer.toString(dataSet.get(dataSetName).size()) + " -> " + Integer.toString(dataSet.get(dataSetName).size() - 1) + ")");
						System.out.println();
					}
					
					int medianX = valueToPosition(median, leftBottomX, leftBottomX + width, hAxis.get(0), hAxis.get(hAxis.size() - 1));
					int p25X = valueToPosition(p25, leftBottomX, leftBottomX + width, hAxis.get(0), hAxis.get(hAxis.size() - 1));
					int p75X = valueToPosition(p75, leftBottomX, leftBottomX + width, hAxis.get(0), hAxis.get(hAxis.size() - 1));
					int minimumX = valueToPosition(minimum, leftBottomX, leftBottomX + width, hAxis.get(0), hAxis.get(hAxis.size() - 1));
					int maximumX = valueToPosition(maximum, leftBottomX, leftBottomX + width, hAxis.get(0), hAxis.get(hAxis.size() - 1));

					int barY = leftBottomY - height + (dataSetNr * dataSetHeight) + DATASET_MARGIN + (barHeight / 2) - (finalBarHeight / 2);
					int whiskerY = barY + (finalBarHeight / 2);

					graphics.fillRect(p25X, barY, Math.abs(p75X - p25X), finalBarHeight);
					graphics.drawLine(minimumX, whiskerY, p25X, whiskerY);
					graphics.drawLine(minimumX, barY, minimumX, barY + finalBarHeight - 1);
					graphics.drawLine(p75X, whiskerY, maximumX, whiskerY);
					graphics.drawLine(maximumX, barY, maximumX, barY + finalBarHeight - 1);
					
					// luminance: sqrt( 0.299*R^2 + 0.587*G^2 + 0.114*B^2 )
					double rgbLuminance = Math.sqrt((0.299 * Math.pow(dataSetColor.getRed(),2.0)) + (0.587 * Math.pow(dataSetColor.getGreen(),2.0)) + (0.114 * Math.pow(dataSetColor.getBlue(),2.0)));
					if (rgbLuminance < 100.0) {
						graphics.setColor(Color.WHITE);
					}
					else {
						graphics.setColor(Color.BLACK);
					}
					graphics.drawLine(medianX, barY, medianX, barY + finalBarHeight - 1);
				}
			}
		}
	}


	@Override
	public void clear() {
		super.clear();
		dataSets = null;
		dataSet = null;
		dataSetAxis = null;
		dataSetAxisLabel = null;
		valueAxis = null;
		valueAxisLabel = null;
	}

}
