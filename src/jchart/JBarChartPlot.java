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

class JBarChartPlot extends JChartPlot {
	private static final long serialVersionUID = 5180036871714686032L;
	
	private static final int BUCKET_MARGIN = 10;
	private static final int BAR_DEPTH_X = 18;
	private static final int BAR_DEPTH_Y = 13;
	private static final int BAR_SHIFT_X = 13;
	private static final int BAR_SHIFT_Y = 11;
	private static final int SHADOW_RIGHT_RGB_SHIFT = -80;
	private static final int SHADOW_TOP_RGB_SHIFT   = -40;
	
	private Map<String, Map<String, Double>> dataSet = null;
	
	private int style = JBarChart.BAR_CHART_STYLE_VERTICAL;
	private List<String> bucketAxis = null;
	private String bucketAxisLabel = null;
	
	private List<Double> valueAxis = null;
	private int valueAxisPrecision = STANDARD_PRECISION;
	private String valueAxisLabel = null;
	
	private Color axisColor = Color.BLACK;
	private Font axisFont = new Font("Arial", Font.PLAIN, 10);
	

	public JBarChartPlot(String name, int barChartStyle) {
		super(name);
		if ((barChartStyle >= JBarChart.BAR_CHART_STYLE_HORIZONTAL) && (barChartStyle <= JBarChart.BAR_CHART_STYLE_3D_STACKED_HORIZONTAL)) {
			style = barChartStyle;
		}
	}
	
	
	public boolean addDataPoint(String dataSetName, String bucket, Double value) {
		boolean ok = false;

		if (dataSets.containsKey(dataSetName)) {
			ok = true;
			if (dataSet == null) {
				dataSet = new HashMap<String, Map<String, Double>>();
			}
			Map<String, Double> valueMap = dataSet.get(dataSetName);
			if (valueMap == null) {
				valueMap = new HashMap<String, Double>();
				dataSet.put(dataSetName, valueMap);
			}
			valueMap.put(bucket, value);
		}
		else {
			JOptionPane.showMessageDialog(null, "Unknown data set '" + dataSetName + ".", "BarChart '" + name + "' Data Set Error", JOptionPane.ERROR_MESSAGE);
		}
		
		return ok;
	}
	
	
	public void setBucketAxis(List<String> axis) {
		bucketAxis = axis;
	}
	
	
	public void setBucketAxisLabel(String label) {
		bucketAxisLabel = label;
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
	
	
	@Override
	public void draw(Graphics graphics) {
		if (style == JBarChart.BAR_CHART_STYLE_VERTICAL) {
			drawVertical(graphics);
		}
		else if (style == JBarChart.BAR_CHART_STYLE_HORIZONTAL) {
			drawHorizontal(graphics);
		}
		else if (style == JBarChart.BAR_CHART_STYLE_STACKED_VERTICAL) {
			drawStackedVertical(graphics);
		}
		else if (style == JBarChart.BAR_CHART_STYLE_STACKED_HORIZONTAL) {
			drawStackedHorizontal(graphics);
		}
		else if (style == JBarChart.BAR_CHART_STYLE_3D_VERTICAL) {
			draw3DVertical(graphics);
		}
		else if (style == JBarChart.BAR_CHART_STYLE_3D_HORIZONTAL) {
			draw3DHorizontal(graphics);
		}
		else if (style == JBarChart.BAR_CHART_STYLE_3D_STACKED_VERTICAL) {
			draw3DStackedVertical(graphics);
		}
		else if (style == JBarChart.BAR_CHART_STYLE_3D_STACKED_HORIZONTAL) {
			draw3DStackedHorizontal(graphics);
		}
	}
	
	
	private void drawVertical(Graphics graphics) {
		//Set background color
		graphics.setColor(chartBackgroundColor);
		graphics.fillRect(0, 0, getWidth(), getHeight());

		if (dataSet != null) {
			FontMetrics fontMetrics = graphics.getFontMetrics(axisFont);

			int maxXWidth = 0;
			
			Double minY = null;
			Double maxY = null;
			int maxYWidth = 0;
			
			List<String> buckets = new ArrayList<String>();
			if (bucketAxis != null) {
				for (String bucket : bucketAxis) {
					if (!buckets.contains(bucket)) {
						buckets.add(bucket);
					}
					maxXWidth = Math.max(maxYWidth, fontMetrics.stringWidth(bucket));
				}
			}
			
			if (valueAxis != null) {
				for (double y : valueAxis) {
					minY = minY == null ? y : Math.min(minY, y);
					maxY = maxY == null ? y : Math.max(maxY, y);
					maxYWidth = Math.max(maxYWidth, fontMetrics.stringWidth(formatValue(y, valueAxisPrecision)));
				}
			}

			if ((bucketAxis == null) || (valueAxis == null)) {
				for (String dataSetName : dataSet.keySet()) {
					for (String bucket : dataSet.get(dataSetName).keySet()) {
						maxXWidth = Math.max(maxYWidth, fontMetrics.stringWidth(bucket));
						if (bucketAxis == null) {
							if (!buckets.contains(bucket)) {
								buckets.add(bucket);
							}
						}
						
						if (valueAxis == null) {
							double y = dataSet.get(dataSetName).get(bucket);
							minY = minY == null ? y : Math.min(minY, y);
							maxY = maxY == null ? y : Math.max(maxY, y);
							maxYWidth = Math.max(maxYWidth, fontMetrics.stringWidth(formatValue(y, valueAxisPrecision)));
						}
					}
				}
			}
			
			if (bucketAxis == null) {
				Collections.sort(buckets);
			}
			
			int leftBottomX = MARGIN;
			int leftBottomY = getHeight() - MARGIN;
			int width = getWidth() - (2 * MARGIN);
			int height = getHeight() - (2 * MARGIN);
			
			Dimension changesYAndHeihght = showTitle(graphics, leftBottomX, leftBottomY, width, height);
			leftBottomY = changesYAndHeihght.width;
			height = changesYAndHeihght.height;
			
			int legendHeight = showLegendBottom(graphics, leftBottomX, leftBottomY, width, true);
			leftBottomY = leftBottomY - legendHeight;
			height = height - legendHeight;
			
			if (bucketAxisLabel != null) {
				if (!((minY < 0.0) && (maxY > 0.0))) {
					leftBottomY = leftBottomY - fontMetrics.getHeight() - AXIS_VALUE_GAP;
					height = height - fontMetrics.getHeight() - AXIS_VALUE_GAP;
				}
			}
			
			if (valueAxisLabel != null) {
				height = height - fontMetrics.getHeight() - AXIS_VALUE_GAP;
			}
			
			int xShift = Math.max(maxYWidth + AXIS_VALUE_GAP, (maxXWidth + 1) / 2);
			leftBottomX = leftBottomX + xShift;
			width = width - xShift;
			int verticalAxisX = leftBottomX;
			
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

			int bucketWidth = (int) Math.round((double) width / (double) buckets.size());
			int barWidth = (int) Math.round(((double) bucketWidth - (2 * BUCKET_MARGIN)) / (double) dataSets.keySet().size());
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
				System.out.println("legendHeight     = " + Integer.toString(legendHeight));
				System.out.println("leftBottomX      = " + Integer.toString(leftBottomX));
				System.out.println("leftBottomY      = " + Integer.toString(leftBottomY));
				System.out.println("width            = " + Integer.toString(width));
				System.out.println("height           = " + Integer.toString(height));
				System.out.println("verticalAxisX    = " + Integer.toString(verticalAxisX));
				System.out.println("horizontalAxisY  = " + Integer.toString(horizontalAxisY));
				System.out.println("bucketWidth      = " + Integer.toString(bucketWidth));
				System.out.println("barWidth         = " + Integer.toString(barWidth));
				System.out.println();
			}
			
			if (barWidth < 1) {
				showError(graphics, "<html>BarChart '" + name + "'<br>Data set error:<br>Too many buckets and/or data sets.</html>"); 
			}
			else {
				// Draw bars
				List<String> orderedDataSets = new ArrayList<String>();
				orderedDataSets.addAll(dataSetList);
				if (orderDataSets) {
					Collections.sort(orderedDataSets);
				}
				for (int dataSetNr = 0; dataSetNr < orderedDataSets.size(); dataSetNr++) {
					String dataSetName = orderedDataSets.get(dataSetNr);
					graphics.setColor(dataSets.get(dataSetName));
					for (int bucketNr = 0; bucketNr < buckets.size(); bucketNr++) {
						String bucket = buckets.get(bucketNr);
						Double y = dataSet.get(dataSetName).get(bucket);
						if (y != null) {
							int barX = leftBottomX + (bucketNr * bucketWidth) + BUCKET_MARGIN + (dataSetNr * barWidth);
							int barY = valueToPosition(y, leftBottomY, leftBottomY - height, vAxis.get(0), vAxis.get(vAxis.size() - 1));
							if (Math.abs(barY - horizontalAxisY) > 0) {
								graphics.fillRect(barX, Math.min(horizontalAxisY, barY), barWidth, Math.abs(barY - horizontalAxisY));
							}
						}
					}
				}
				
				// Set axis color
				graphics.setColor(axisColor);
				
				//Set axis font
				graphics.setFont(axisFont);
				
				// Draw horizontal axis
				graphics.drawLine(leftBottomX, horizontalAxisY, leftBottomX + width, horizontalAxisY);
				for (int bucketNr = 0; bucketNr < buckets.size(); bucketNr++) {
					String xAxisLabel = buckets.get(bucketNr);
					int xBucketCenter = leftBottomX + (bucketNr * bucketWidth) + (bucketWidth / 2);
					graphics.drawString(xAxisLabel, xBucketCenter - (fontMetrics.stringWidth(xAxisLabel) / 2), horizontalAxisY + AXIS_VALUE_GAP + fontMetrics.getHeight());
				}
				if (bucketAxisLabel != null) {
					graphics.drawString(bucketAxisLabel, leftBottomX + width - fontMetrics.stringWidth(bucketAxisLabel), horizontalAxisY + (2 * fontMetrics.getHeight() + AXIS_VALUE_GAP));
				}
				
				// Draw vertical axis
				graphics.drawLine(verticalAxisX, leftBottomY, verticalAxisX, leftBottomY - height);
				for (double y : vAxis) {
					String yString = formatValue(y, valueAxisPrecision);
					int yPos = valueToPosition(y, leftBottomY, leftBottomY - height, vAxis.get(0), vAxis.get(vAxis.size() - 1));
					if ((verticalAxisX == leftBottomX) || (yPos > (horizontalAxisY + fontMetrics.getHeight() + 2)) || (yPos < (horizontalAxisY - fontMetrics.getHeight() - 2))) {
						graphics.drawLine(verticalAxisX - 2, yPos, verticalAxisX, yPos);
						graphics.drawString(yString, verticalAxisX - AXIS_VALUE_GAP - fontMetrics.stringWidth(yString), yPos + (fontMetrics.getHeight() / 2));
					}
				}
				if (valueAxisLabel != null) {
					graphics.drawString(valueAxisLabel, Math.max(MARGIN, verticalAxisX - (fontMetrics.stringWidth(valueAxisLabel) / 2)), leftBottomY - height - AXIS_VALUE_GAP);
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
			
			Double minX = null;
			Double maxX = null;
			int maxXWidth = 0;

			int maxYWidth = 0;
			
			List<String> buckets = new ArrayList<String>();
			if (bucketAxis != null) {
				for (String bucket : bucketAxis) {
					if (!buckets.contains(bucket)) {
						buckets.add(bucket);
					}
					maxYWidth = Math.max(maxYWidth, fontMetrics.stringWidth(bucket));
				}
			}
			
			if (valueAxis != null) {
				for (double x : valueAxis) {
					minX = minX == null ? x : Math.min(minX, x);
					maxX = maxX == null ? x : Math.max(maxX, x);
					maxXWidth = Math.max(maxXWidth, fontMetrics.stringWidth(formatValue(x, valueAxisPrecision)));
				}
			}

			if ((bucketAxis == null) || (valueAxis == null)) {
				for (String dataSetName : dataSet.keySet()) {
					for (String bucket : dataSet.get(dataSetName).keySet()) {
						maxYWidth = Math.max(maxYWidth, fontMetrics.stringWidth(bucket));
						if (bucketAxis == null) {
							if (!buckets.contains(bucket)) {
								buckets.add(bucket);
							}
						}
						
						if (valueAxis == null) {
							double x = dataSet.get(dataSetName).get(bucket);
							minX = minX == null ? x : Math.min(minX, x);
							maxX = maxX == null ? x : Math.max(maxX, x);
							maxXWidth = Math.max(maxXWidth, fontMetrics.stringWidth(formatValue(x, valueAxisPrecision)));
						}
					}
				}
			}
			
			if (bucketAxis == null) {
				Collections.sort(buckets);
			}
			
			int leftBottomX = MARGIN;
			int leftBottomY = getHeight() - MARGIN;
			int width = getWidth() - (2 * MARGIN);
			int height = getHeight() - (2 * MARGIN);
			
			Dimension changesYAndHeihght = showTitle(graphics, leftBottomX, leftBottomY, width, height);
			leftBottomY = changesYAndHeihght.width;
			height = changesYAndHeihght.height;
			
			int legendHeight = showLegendBottom(graphics, leftBottomX, leftBottomY, width, true);
			leftBottomY = leftBottomY - legendHeight;
			height = height - legendHeight;

			if (valueAxisLabel != null) {
				leftBottomY = leftBottomY - fontMetrics.getHeight() - AXIS_VALUE_GAP;
				height = height - fontMetrics.getHeight() - AXIS_VALUE_GAP;
			}

			if (bucketAxisLabel != null) {
				height = height - fontMetrics.getHeight() + AXIS_VALUE_GAP;
			}
			
			width = width - ((maxXWidth + 1) / 2);
			
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
			
			leftBottomY = leftBottomY - fontMetrics.getHeight() - AXIS_VALUE_GAP;
			height = height - fontMetrics.getHeight() - AXIS_VALUE_GAP;
			int horizontalAxisY = leftBottomY;
			
			List<Double> hAxis = horizontalAxis(valueAxis, minX, maxX, leftBottomX, leftBottomX + width, fontMetrics, valueAxisPrecision);
			int bucketHeight = (int) Math.round((double) height / (double) buckets.size());
			int barHeight = (int) Math.round(((double) bucketHeight - (2 * BUCKET_MARGIN)) / (double) dataSets.keySet().size());
			
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
				System.out.println("bucketHeight     = " + Integer.toString(bucketHeight));
				System.out.println("barHeight        = " + Integer.toString(barHeight));
				System.out.println();
			}
			
			if (barHeight < 1) {
				showError(graphics, "<html>BarChart '" + name + "'<br>Data set error:<br>Too many buckets and/or data sets.</html>");
			}
			else {
				// Draw bars
				List<String> orderedDataSets = new ArrayList<String>();
				orderedDataSets.addAll(dataSetList);
				if (orderDataSets) {
					Collections.sort(orderedDataSets);
				}
				for (int dataSetNr = 0; dataSetNr < orderedDataSets.size(); dataSetNr++) {
					String dataSetName = orderedDataSets.get(dataSetNr);
					graphics.setColor(dataSets.get(dataSetName));
					for (int bucketNr = 0; bucketNr < buckets.size(); bucketNr++) {
						String bucket = buckets.get(bucketNr);
						Double x = dataSet.get(dataSetName).get(bucket);
						if (x != null) {
							int barX = valueToPosition(x, leftBottomX, leftBottomX + width, hAxis.get(0), hAxis.get(hAxis.size() - 1));
							int barY = leftBottomY - height + (bucketNr * bucketHeight) + BUCKET_MARGIN + (dataSetNr * barHeight);
							if (Math.abs(barX - verticalAxisX) > 0) {
								graphics.fillRect(Math.min(verticalAxisX, barX), barY, Math.abs(barX - verticalAxisX), barHeight);
							}
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
					String xString = formatValue(x, valueAxisPrecision);
					Integer xPos = valueToPosition(x, leftBottomX, leftBottomX + width, hAxis.get(0), hAxis.get(hAxis.size() - 1));
					int halfXStringWidth = (fontMetrics.stringWidth(xString) / 2);
					if ((horizontalAxisY == leftBottomY) || (xPos < (verticalAxisX - halfXStringWidth - 2)) || (xPos > (verticalAxisX + halfXStringWidth + 2))) {
						graphics.drawLine(xPos, horizontalAxisY + 2, xPos, horizontalAxisY);
						graphics.drawString(xString, xPos - halfXStringWidth, horizontalAxisY + 4 + fontMetrics.getHeight());
					}
				}
				if (valueAxisLabel != null) {
					graphics.drawString(valueAxisLabel, leftBottomX + width + ((maxXWidth + 1) / 2) - fontMetrics.stringWidth(valueAxisLabel), horizontalAxisY + (2 * fontMetrics.getHeight() + AXIS_VALUE_GAP));
				}
				
				// Draw vertical axis
				graphics.drawLine(verticalAxisX, leftBottomY, verticalAxisX, leftBottomY - height);
				for (int bucketNr = 0; bucketNr < buckets.size(); bucketNr++) {
					String yAxisLabel = buckets.get(bucketNr);
					int yBucketCenter = leftBottomY - height + (bucketNr * bucketHeight) + (bucketHeight / 2);
					graphics.drawString(yAxisLabel, verticalAxisX - AXIS_VALUE_GAP - fontMetrics.stringWidth(yAxisLabel), yBucketCenter + (fontMetrics.getHeight() / 2));
				}
				if (bucketAxisLabel != null) {
					graphics.drawString(bucketAxisLabel, Math.max(MARGIN, verticalAxisX - (fontMetrics.stringWidth(bucketAxisLabel) / 2)), leftBottomY - height - AXIS_VALUE_GAP);
				}
			}
		}
	}
	
	
	private void drawStackedVertical(Graphics graphics) {
		//Set background color
		graphics.setColor(chartBackgroundColor);
		graphics.fillRect(0, 0, getWidth(), getHeight());

		if (dataSet != null) {
			FontMetrics fontMetrics = graphics.getFontMetrics(axisFont);

			int maxXWidth = 0;
			
			Double minY = null;
			Double maxY = null;
			int maxYWidth = 0;
			
			List<String> buckets = new ArrayList<String>();
			if (bucketAxis != null) {
				for (String bucket : bucketAxis) {
					if (!buckets.contains(bucket)) {
						buckets.add(bucket);
					}
					maxXWidth = Math.max(maxYWidth, fontMetrics.stringWidth(bucket));
				}
			}
			
			if (valueAxis != null) {
				for (double y : valueAxis) {
					minY = minY == null ? y : Math.min(minY, y);
					maxY = maxY == null ? y : Math.max(maxY, y);
					maxYWidth = Math.max(maxYWidth, fontMetrics.stringWidth(formatValue(y, valueAxisPrecision)));
				}
			}
			else {
				maxY = 0.0;
			}

			if ((bucketAxis == null) || (valueAxis == null)) {
				Map<String, Double> bucketMaxY = new HashMap<String, Double>();
				for (String dataSetName : dataSet.keySet()) {
					for (String bucket : dataSet.get(dataSetName).keySet()) {
						maxXWidth = Math.max(maxYWidth, fontMetrics.stringWidth(bucket));
						if (bucketAxis == null) {
							if (!buckets.contains(bucket)) {
								buckets.add(bucket);
							}
						}
						
						if (valueAxis == null) {
							double y = dataSet.get(dataSetName).get(bucket);
							minY = minY == null ? y : Math.min(minY, y);
							bucketMaxY.put(bucket, bucketMaxY.get(bucket) == null ? y : (bucketMaxY.get(bucket) + y));
						}
					}
				}
				for (String bucket : bucketMaxY.keySet()) {
					maxY = maxY == null ? bucketMaxY.get(bucket) : Math.max(maxY, bucketMaxY.get(bucket));
					maxYWidth = Math.max(maxYWidth, fontMetrics.stringWidth(formatValue(maxY, valueAxisPrecision)));
				}
			}
			
			if (minY < 0.0) {
				showError(graphics, "<html>BarChart '" + name + "'<br>Data set error:<br>Negative value(s) in data sets.</html>");
			}
			else {
				minY = 0.0;
				
				if (bucketAxis == null) {
					Collections.sort(buckets);
				}
				
				int leftBottomX = MARGIN;
				int leftBottomY = getHeight() - MARGIN;
				int width = getWidth() - (2 * MARGIN);
				int height = getHeight() - (2 * MARGIN);
				
				Dimension changesYAndHeihght = showTitle(graphics, leftBottomX, leftBottomY, width, height);
				leftBottomY = changesYAndHeihght.width;
				height = changesYAndHeihght.height;
				
				int legendHeight = showLegendBottom(graphics, leftBottomX, leftBottomY, width, true);
				leftBottomY = leftBottomY - legendHeight;
				height = height - legendHeight;
				
				if (bucketAxisLabel != null) {
					if (!((minY < 0.0) && (maxY > 0.0))) {
						leftBottomY = leftBottomY - fontMetrics.getHeight() - AXIS_VALUE_GAP;
						height = height - fontMetrics.getHeight() - AXIS_VALUE_GAP;
					}
				}
				
				if (valueAxisLabel != null) {
					height = height - fontMetrics.getHeight() - AXIS_VALUE_GAP;
				}
				
				int xShift = Math.max(maxYWidth + AXIS_VALUE_GAP, (maxXWidth + 1) / 2);
				leftBottomX = leftBottomX + xShift;
				width = width - xShift;
				int verticalAxisX = leftBottomX;
				
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

				int bucketWidth = (int) Math.round((double) width / (double) buckets.size());
				int barWidth = (int) Math.round((double) bucketWidth - (2 * BUCKET_MARGIN));
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
					System.out.println("legendHeight     = " + Integer.toString(legendHeight));
					System.out.println("leftBottomX      = " + Integer.toString(leftBottomX));
					System.out.println("leftBottomY      = " + Integer.toString(leftBottomY));
					System.out.println("width            = " + Integer.toString(width));
					System.out.println("height           = " + Integer.toString(height));
					System.out.println("verticalAxisX    = " + Integer.toString(verticalAxisX));
					System.out.println("horizontalAxisY  = " + Integer.toString(horizontalAxisY));
					System.out.println("bucketWidth      = " + Integer.toString(bucketWidth));
					System.out.println("barWidth         = " + Integer.toString(barWidth));
					System.out.println();
				}
				
				if (barWidth < 1) {
					showError(graphics, "<html>BarChart '" + name + "'<br>Data set error:<br>Too many buckets and/or data sets.</html>"); 
				}
				else {
					// Draw bars
					List<String> orderedDataSets = new ArrayList<String>();
					orderedDataSets.addAll(dataSetList);
					if (orderDataSets) {
						Collections.sort(orderedDataSets);
					}
					for (int bucketNr = 0; bucketNr < buckets.size(); bucketNr++) {
						Double lastY = 0.0;
						int lastBarY = horizontalAxisY; 
						String bucket = buckets.get(bucketNr);
						int barX = leftBottomX + (bucketNr * bucketWidth) + BUCKET_MARGIN;
						for (int dataSetNr = 0; dataSetNr < orderedDataSets.size(); dataSetNr++) {
							String dataSetName = orderedDataSets.get(dataSetNr);
							graphics.setColor(dataSets.get(dataSetName));
							Double y = dataSet.get(dataSetName).get(bucket);
							if (y != null) {
								Double newY = lastY + y;
								int barY = valueToPosition(newY, leftBottomY, leftBottomY - height, vAxis.get(0), vAxis.get(vAxis.size() - 1));
								if (Math.abs(barY - horizontalAxisY) > 0) {
									graphics.fillRect(barX, barY, barWidth, lastBarY - barY);
								}
								lastY = newY;
								lastBarY = barY;
							}
						}
					}
					
					// Set axis color
					graphics.setColor(axisColor);
					
					//Set axis font
					graphics.setFont(axisFont);
					
					// Draw horizontal axis
					graphics.drawLine(leftBottomX, horizontalAxisY, leftBottomX + width, horizontalAxisY);
					for (int bucketNr = 0; bucketNr < buckets.size(); bucketNr++) {
						String xAxisLabel = buckets.get(bucketNr);
						int xBucketCenter = leftBottomX + (bucketNr * bucketWidth) + (bucketWidth / 2);
						graphics.drawString(xAxisLabel, xBucketCenter - (fontMetrics.stringWidth(xAxisLabel) / 2), horizontalAxisY + AXIS_VALUE_GAP + fontMetrics.getHeight());
					}
					if (bucketAxisLabel != null) {
						graphics.drawString(bucketAxisLabel, leftBottomX + width - fontMetrics.stringWidth(bucketAxisLabel), horizontalAxisY + (2 * fontMetrics.getHeight() + AXIS_VALUE_GAP));
					}
					
					// Draw vertical axis
					graphics.drawLine(verticalAxisX, leftBottomY, verticalAxisX, leftBottomY - height);
					for (double y : vAxis) {
						String yString = formatValue(y, valueAxisPrecision);
						int yPos = valueToPosition(y, leftBottomY, leftBottomY - height, vAxis.get(0), vAxis.get(vAxis.size() - 1));
						if ((verticalAxisX == leftBottomX) || (yPos > (horizontalAxisY + fontMetrics.getHeight() + 2)) || (yPos < (horizontalAxisY - fontMetrics.getHeight() - 2))) {
							graphics.drawLine(verticalAxisX - 2, yPos, verticalAxisX, yPos);
							graphics.drawString(yString, verticalAxisX - AXIS_VALUE_GAP - fontMetrics.stringWidth(yString), yPos + (fontMetrics.getHeight() / 2));
						}
					}
					if (valueAxisLabel != null) {
						graphics.drawString(valueAxisLabel, Math.max(MARGIN, verticalAxisX - (fontMetrics.stringWidth(valueAxisLabel) / 2)), leftBottomY - height - AXIS_VALUE_GAP);
					}
				}
			}
		}
	}
	
	
	private void drawStackedHorizontal(Graphics graphics) {
		//Set background color
		graphics.setColor(chartBackgroundColor);
		graphics.fillRect(0, 0, getWidth(), getHeight());

		if (dataSet != null) {
			FontMetrics fontMetrics = graphics.getFontMetrics(axisFont);
			
			Double minX = null;
			Double maxX = null;
			int maxXWidth = 0;

			int maxYWidth = 0;
			
			List<String> buckets = new ArrayList<String>();
			if (bucketAxis != null) {
				for (String bucket : bucketAxis) {
					if (!buckets.contains(bucket)) {
						buckets.add(bucket);
					}
					maxYWidth = Math.max(maxYWidth, fontMetrics.stringWidth(bucket));
				}
			}
			
			if (valueAxis != null) {
				for (double x : valueAxis) {
					minX = minX == null ? x : Math.min(minX, x);
					maxX = maxX == null ? x : Math.max(maxX, x);
					maxXWidth = Math.max(maxXWidth, fontMetrics.stringWidth(formatValue(x, valueAxisPrecision)));
				}
			}
			else {
				maxX = 0.0;
			}

			if ((bucketAxis == null) || (valueAxis == null)) {
				Map<String, Double> bucketMaxX = new HashMap<String, Double>();
				for (String dataSetName : dataSet.keySet()) {
					for (String bucket : dataSet.get(dataSetName).keySet()) {
						maxYWidth = Math.max(maxYWidth, fontMetrics.stringWidth(bucket));
						if (bucketAxis == null) {
							if (!buckets.contains(bucket)) {
								buckets.add(bucket);
							}
						}
						
						if (valueAxis == null) {
							double x = dataSet.get(dataSetName).get(bucket);
							minX = minX == null ? x : Math.min(minX, x);
							bucketMaxX.put(bucket, bucketMaxX.get(bucket) == null ? x : (bucketMaxX.get(bucket) + x));
						}
					}
				}
				for (String bucket : bucketMaxX.keySet()) {
					maxX = maxX == null ? bucketMaxX.get(bucket) : Math.max(maxX, bucketMaxX.get(bucket));
					maxXWidth = Math.max(maxXWidth, fontMetrics.stringWidth(formatValue(maxX, valueAxisPrecision)));
				}
			}
			
			if (minX < 0.0) {
				showError(graphics, "<html>BarChart '" + name + "'<br>Data set error:<br>Negative value(s) in data sets.</html>");
			}
			else {
				minX = 0.0;
				
				if (bucketAxis == null) {
					Collections.sort(buckets);
				}
				
				int leftBottomX = MARGIN;
				int leftBottomY = getHeight() - MARGIN;
				int width = getWidth() - (2 * MARGIN);
				int height = getHeight() - (2 * MARGIN);
				
				Dimension changesYAndHeihght = showTitle(graphics, leftBottomX, leftBottomY, width, height);
				leftBottomY = changesYAndHeihght.width;
				height = changesYAndHeihght.height;
				
				int legendHeight = showLegendBottom(graphics, leftBottomX, leftBottomY, width, true);
				leftBottomY = leftBottomY - legendHeight;
				height = height - legendHeight;

				if (valueAxisLabel != null) {
					leftBottomY = leftBottomY - fontMetrics.getHeight() - AXIS_VALUE_GAP;
					height = height - fontMetrics.getHeight() - AXIS_VALUE_GAP;
				}

				if (bucketAxisLabel != null) {
					height = height - fontMetrics.getHeight() + AXIS_VALUE_GAP;
				}
				
				width = width - ((maxXWidth + 1) / 2);
				
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
				
				leftBottomY = leftBottomY - fontMetrics.getHeight() - AXIS_VALUE_GAP;
				height = height - fontMetrics.getHeight() - AXIS_VALUE_GAP;
				int horizontalAxisY = leftBottomY;
				
				List<Double> hAxis = horizontalAxis(valueAxis, minX, maxX, leftBottomX, leftBottomX + width, fontMetrics, valueAxisPrecision);
				int bucketHeight = (int) Math.round((double) height / (double) buckets.size());
				int barHeight = (int) Math.round((double) bucketHeight - (2 * BUCKET_MARGIN));
				
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
					System.out.println("bucketHeight     = " + Integer.toString(bucketHeight));
					System.out.println("barHeight        = " + Integer.toString(barHeight));
					System.out.println();
				}
				
				if (barHeight < 1) {
					showError(graphics, "<html>BarChart '" + name + "'<br>Data set error:<br>Too many buckets and/or data sets.</html>");
				}
				else {
					// Draw bars
					List<String> orderedDataSets = new ArrayList<String>();
					orderedDataSets.addAll(dataSetList);
					if (orderDataSets) {
						Collections.sort(orderedDataSets);
					}
					for (int bucketNr = 0; bucketNr < buckets.size(); bucketNr++) {
						String bucket = buckets.get(bucketNr);
						Double lastX = 0.0;
						int lastBarX = verticalAxisX;
						for (int dataSetNr = 0; dataSetNr < orderedDataSets.size(); dataSetNr++) {
							String dataSetName = orderedDataSets.get(dataSetNr);
							graphics.setColor(dataSets.get(dataSetName));
							Double x = dataSet.get(dataSetName).get(bucket);
							if (x != null) {
								Double newX = lastX + x;
								int barX = valueToPosition(newX, leftBottomX, leftBottomX + width, hAxis.get(0), hAxis.get(hAxis.size() - 1));
								int barY = leftBottomY - height + (bucketNr * bucketHeight) + BUCKET_MARGIN;
								if (Math.abs(barX - lastX) > 0) {
									graphics.fillRect(lastBarX, barY, barX - lastBarX, barHeight);
								}
								lastX = newX;
								lastBarX = barX;
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
						String xString = formatValue(x, valueAxisPrecision);
						Integer xPos = valueToPosition(x, leftBottomX, leftBottomX + width, hAxis.get(0), hAxis.get(hAxis.size() - 1));
						int halfXStringWidth = (fontMetrics.stringWidth(xString) / 2);
						if ((horizontalAxisY == leftBottomY) || (xPos < (verticalAxisX - halfXStringWidth - 2)) || (xPos > (verticalAxisX + halfXStringWidth + 2))) {
							graphics.drawLine(xPos, horizontalAxisY + 2, xPos, horizontalAxisY);
							graphics.drawString(xString, xPos - halfXStringWidth, horizontalAxisY + 4 + fontMetrics.getHeight());
						}
					}
					if (valueAxisLabel != null) {
						graphics.drawString(valueAxisLabel, leftBottomX + width + ((maxXWidth + 1) / 2) - fontMetrics.stringWidth(valueAxisLabel), horizontalAxisY + (2 * fontMetrics.getHeight() + AXIS_VALUE_GAP));
					}
					
					// Draw vertical axis
					graphics.drawLine(verticalAxisX, leftBottomY, verticalAxisX, leftBottomY - height);
					for (int bucketNr = 0; bucketNr < buckets.size(); bucketNr++) {
						String yAxisLabel = buckets.get(bucketNr);
						int yBucketCenter = leftBottomY - height + (bucketNr * bucketHeight) + (bucketHeight / 2);
						graphics.drawString(yAxisLabel, verticalAxisX - AXIS_VALUE_GAP - fontMetrics.stringWidth(yAxisLabel), yBucketCenter + (fontMetrics.getHeight() / 2));
					}
					if (bucketAxisLabel != null) {
						graphics.drawString(bucketAxisLabel, Math.max(MARGIN, verticalAxisX - (fontMetrics.stringWidth(bucketAxisLabel) / 2)), leftBottomY - height - AXIS_VALUE_GAP);
					}
				}
			}
		}
	}
	
	
	private void draw3DVertical(Graphics graphics) {
		//Set background color
		graphics.setColor(chartBackgroundColor);
		graphics.fillRect(0, 0, getWidth(), getHeight());

		if (dataSet != null) {
			FontMetrics fontMetrics = graphics.getFontMetrics(axisFont);

			int maxXWidth = 0;
			
			Double minY = null;
			Double maxY = null;
			int maxYWidth = 0;
			
			List<String> buckets = new ArrayList<String>();
			if (bucketAxis != null) {
				for (String bucket : bucketAxis) {
					if (!buckets.contains(bucket)) {
						buckets.add(bucket);
					}
					maxXWidth = Math.max(maxYWidth, fontMetrics.stringWidth(bucket));
				}
			}
			
			if (valueAxis != null) {
				for (double y : valueAxis) {
					minY = minY == null ? y : Math.min(minY, y);
					maxY = maxY == null ? y : Math.max(maxY, y);
					maxYWidth = Math.max(maxYWidth, fontMetrics.stringWidth(formatValue(y, valueAxisPrecision)));
				}
			}

			if ((bucketAxis == null) || (valueAxis == null)) {
				for (String dataSetName : dataSet.keySet()) {
					for (String bucket : dataSet.get(dataSetName).keySet()) {
						maxXWidth = Math.max(maxYWidth, fontMetrics.stringWidth(bucket));
						if (bucketAxis == null) {
							if (!buckets.contains(bucket)) {
								buckets.add(bucket);
							}
						}
						
						if (valueAxis == null) {
							double y = dataSet.get(dataSetName).get(bucket);
							minY = minY == null ? y : Math.min(minY, y);
							maxY = maxY == null ? y : Math.max(maxY, y);
							maxYWidth = Math.max(maxYWidth, fontMetrics.stringWidth(formatValue(y, valueAxisPrecision)));
						}
					}
				}
			}
			
			if (bucketAxis == null) {
				Collections.sort(buckets);
			}
			
			int leftBottomX = MARGIN;
			int leftBottomY = getHeight() - MARGIN;
			int width = getWidth() - (2 * MARGIN);
			int height = getHeight() - (2 * MARGIN);
			
			Dimension changesYAndHeihght = showTitle(graphics, leftBottomX, leftBottomY, width, height);
			leftBottomY = changesYAndHeihght.width;
			height = changesYAndHeihght.height;
			
			int legendHeight = showLegendBottom(graphics, leftBottomX, leftBottomY, width, true);
			leftBottomY = leftBottomY - legendHeight;
			height = height - legendHeight;
			
			if (bucketAxisLabel != null) {
				if (!((minY < 0.0) && (maxY > 0.0))) {
					leftBottomY = leftBottomY - fontMetrics.getHeight() - AXIS_VALUE_GAP;
					height = height - fontMetrics.getHeight() - AXIS_VALUE_GAP;
				}
			}
			
			if (valueAxisLabel != null) {
				height = height - fontMetrics.getHeight() - AXIS_VALUE_GAP;
			}
			
			int xShift = Math.max(maxYWidth + AXIS_VALUE_GAP, (maxXWidth + 1) / 2);
			leftBottomX = leftBottomX + xShift;
			width = width - xShift;
			int verticalAxisX = leftBottomX;
			
			int horizontalAxisY = leftBottomY;
			if ((minY < 0.0) && (maxY > 0.0)) {
				leftBottomY = leftBottomY - ((fontMetrics.getHeight() + 1) / 2);
				height = height - ((fontMetrics.getHeight() + 1) / 2);
				horizontalAxisY = valueToPosition(0.0, leftBottomY, leftBottomY - height + (2 * BAR_SHIFT_Y) + BAR_DEPTH_Y, minY, maxY);
			}
			else {
				leftBottomY = leftBottomY - fontMetrics.getHeight() - AXIS_VALUE_GAP;
				height = height - fontMetrics.getHeight() - AXIS_VALUE_GAP;
				horizontalAxisY = leftBottomY;
			}

			int bucketWidth = (int) Math.round((double) (width - leftBottomX - BAR_SHIFT_X - BAR_DEPTH_X) / (double) buckets.size());
			int barWidth = (int) Math.round(((double) bucketWidth - (2 * BUCKET_MARGIN)) / (double) dataSets.keySet().size());
			List<Double> vAxis = verticalAxis(valueAxis, minY, maxY, leftBottomY, leftBottomY - height, fontMetrics, valueAxisPrecision);
			
			if (JChartPlot.DEBUG) {
				System.out.println();
				System.out.println(name);
				System.out.println("PanelWidth       = " + Integer.toString(getWidth()));
				System.out.println("PanelHeight      = " + Integer.toString(getHeight()));
				System.out.println("MARGIN           = " + Integer.toString(MARGIN));
				System.out.println("AXIS_VALUE_GAP   = " + Integer.toString(AXIS_VALUE_GAP));
				System.out.println("BAR_SHIFT_X      = " + Integer.toString(BAR_SHIFT_X));
				System.out.println("BAR_SHIFT_Y      = " + Integer.toString(BAR_SHIFT_Y));
				System.out.println("BAR_DEPTH_X      = " + Integer.toString(BAR_DEPTH_X));
				System.out.println("BAR_DEPTH_Y      = " + Integer.toString(BAR_DEPTH_Y));
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
				System.out.println("bucketWidth      = " + Integer.toString(bucketWidth));
				System.out.println("barWidth         = " + Integer.toString(barWidth));
				System.out.println();
			}
			
			if (barWidth < 1) {
				showError(graphics, "<html>BarChart '" + name + "'<br>Data set error:<br>Too many buckets and/or data sets.</html>");
			}
			else {
				// Set axis color
				graphics.setColor(axisColor);
				
				//Set axis font
				graphics.setFont(axisFont);

				// Draw vertical axis
				for (double y : vAxis) {
					String yString = formatValue(y, valueAxisPrecision);
					int yPos = valueToPosition(y, leftBottomY, leftBottomY - height + (2* BAR_SHIFT_Y) + BAR_DEPTH_Y, vAxis.get(0), vAxis.get(vAxis.size() - 1));
					if ((verticalAxisX == leftBottomX) || (yPos > (horizontalAxisY + fontMetrics.getHeight() + 2)) || (yPos < (horizontalAxisY - fontMetrics.getHeight() - 2))) {
						graphics.setColor(axisColor);
						graphics.drawLine(verticalAxisX - 2, yPos, verticalAxisX, yPos);
						graphics.drawString(yString, verticalAxisX - AXIS_VALUE_GAP - fontMetrics.stringWidth(yString), yPos + (fontMetrics.getHeight() / 2));
						
						graphics.setColor(Color.LIGHT_GRAY);
						graphics.drawLine(verticalAxisX, yPos, verticalAxisX + (2 * BAR_SHIFT_X) + BAR_DEPTH_X, yPos - (2 * BAR_SHIFT_Y) - BAR_DEPTH_Y);
						graphics.drawLine(verticalAxisX + (2 * BAR_SHIFT_X) + BAR_DEPTH_X, yPos - (2 * BAR_SHIFT_Y) - BAR_DEPTH_Y, width, yPos - (2 * BAR_SHIFT_Y) - BAR_DEPTH_Y);
					}
				}
				if (valueAxisLabel != null) {
					graphics.setColor(axisColor);
					graphics.drawString(valueAxisLabel, Math.max(MARGIN, verticalAxisX - (fontMetrics.stringWidth(valueAxisLabel) / 2)), leftBottomY - height - AXIS_VALUE_GAP);
				}

				// Draw bars
				List<String> orderedDataSets = new ArrayList<String>();
				orderedDataSets.addAll(dataSetList);
				if (orderDataSets) {
					Collections.sort(orderedDataSets);
				}
				for (int dataSetNr = 0; dataSetNr < orderedDataSets.size(); dataSetNr++) {
					String dataSetName = orderedDataSets.get(dataSetNr);
					Color color = dataSets.get(dataSetName);
					Color shadowColorRight = new Color(Math.max(0, color.getRed() + SHADOW_RIGHT_RGB_SHIFT), Math.max(0, color.getGreen() + SHADOW_RIGHT_RGB_SHIFT), Math.max(0, color.getBlue() + SHADOW_RIGHT_RGB_SHIFT));
					Color shadowColorTop = new Color(Math.max(0, color.getRed() + SHADOW_TOP_RGB_SHIFT), Math.max(0, color.getGreen() + SHADOW_TOP_RGB_SHIFT), Math.max(0, color.getBlue() + SHADOW_TOP_RGB_SHIFT));
					for (int bucketNr = 0; bucketNr < buckets.size(); bucketNr++) {
						String bucket = buckets.get(bucketNr);
						Double y = dataSet.get(dataSetName).get(bucket);
						if (y != null) {
							int barX = leftBottomX + (bucketNr * bucketWidth) + BUCKET_MARGIN + (dataSetNr * barWidth) + BAR_SHIFT_X;
							int barY = valueToPosition(y, leftBottomY, leftBottomY - height + (2 * BAR_SHIFT_Y) + BAR_DEPTH_Y, vAxis.get(0), vAxis.get(vAxis.size() - 1));
							if (Math.abs(barY - horizontalAxisY) > 0) {
								graphics.setColor(color);
								graphics.fillRect(barX, Math.min(horizontalAxisY, barY) - BAR_SHIFT_Y, barWidth, Math.abs(barY - horizontalAxisY));
								
								int frontRightX = barX + barWidth;
								int frontTopY = Math.min(horizontalAxisY, barY) - BAR_SHIFT_Y;
								int frontBottomY = frontTopY + Math.abs(barY - horizontalAxisY);
								int rearRightX = frontRightX + BAR_DEPTH_X;
								int rearBottomY = frontBottomY - BAR_DEPTH_Y;
								int rearTopY = frontTopY - BAR_DEPTH_Y;
								int rearLeftTopX = barX + BAR_DEPTH_X;
								graphics.setColor(shadowColorRight);
								graphics.fillPolygon(new int[] { frontRightX, rearRightX, rearRightX, frontRightX }, new int[] { frontBottomY, rearBottomY, rearTopY, frontTopY }, 4);
								graphics.setColor(shadowColorTop);
								graphics.fillPolygon(new int[] { barX, frontRightX, rearRightX, rearLeftTopX }, new int[] { frontTopY, frontTopY, rearTopY, rearTopY }, 4);
							}
						}
					}
				}

				// Set axis color
				graphics.setColor(axisColor);
				
				//Set axis font
				graphics.setFont(axisFont);

				// Draw horizontal axis
				graphics.drawLine(leftBottomX + BAR_SHIFT_X, horizontalAxisY - BAR_SHIFT_Y, leftBottomX + width - (2 * BAR_SHIFT_X) - BAR_DEPTH_X, horizontalAxisY - BAR_SHIFT_Y);
				for (int bucketNr = 0; bucketNr < buckets.size(); bucketNr++) {
					String xAxisLabel = buckets.get(bucketNr);
					int xBucketCenter = leftBottomX + BAR_SHIFT_X + (bucketNr * bucketWidth) + (bucketWidth / 2);
					graphics.drawString(xAxisLabel, xBucketCenter - (fontMetrics.stringWidth(xAxisLabel) / 2), horizontalAxisY - BAR_SHIFT_Y + AXIS_VALUE_GAP + fontMetrics.getHeight());
				}
				if (bucketAxisLabel != null) {
					graphics.drawString(bucketAxisLabel, leftBottomX + width - fontMetrics.stringWidth(bucketAxisLabel), horizontalAxisY + (2 * fontMetrics.getHeight() + AXIS_VALUE_GAP));
				}
			}
		}
	}
	
	
	private void draw3DHorizontal(Graphics graphics) {
		//Set background color
		graphics.setColor(chartBackgroundColor);
		graphics.fillRect(0, 0, getWidth(), getHeight());

		if (dataSet != null) {
			FontMetrics fontMetrics = graphics.getFontMetrics(axisFont);
			
			Double minX = null;
			Double maxX = null;
			int maxXWidth = 0;

			int maxYWidth = 0;
			
			List<String> buckets = new ArrayList<String>();
			if (bucketAxis != null) {
				for (String bucket : bucketAxis) {
					if (!buckets.contains(bucket)) {
						buckets.add(bucket);
					}
					maxYWidth = Math.max(maxYWidth, fontMetrics.stringWidth(bucket));
				}
			}
			
			if (valueAxis != null) {
				for (double x : valueAxis) {
					minX = minX == null ? x : Math.min(minX, x);
					maxX = maxX == null ? x : Math.max(maxX, x);
					maxXWidth = Math.max(maxXWidth, fontMetrics.stringWidth(formatValue(x, valueAxisPrecision)));
				}
			}

			if ((bucketAxis == null) || (valueAxis == null)) {
				for (String dataSetName : dataSet.keySet()) {
					for (String bucket : dataSet.get(dataSetName).keySet()) {
						maxYWidth = Math.max(maxYWidth, fontMetrics.stringWidth(bucket));
						if (bucketAxis == null) {
							if (!buckets.contains(bucket)) {
								buckets.add(bucket);
							}
						}
						
						if (valueAxis == null) {
							double x = dataSet.get(dataSetName).get(bucket);
							minX = minX == null ? x : Math.min(minX, x);
							maxX = maxX == null ? x : Math.max(maxX, x);
							maxXWidth = Math.max(maxXWidth, fontMetrics.stringWidth(formatValue(x, valueAxisPrecision)));
						}
					}
				}
			}
			
			if (bucketAxis == null) {
				Collections.sort(buckets);
			}
			
			int leftBottomX = MARGIN;
			int leftBottomY = getHeight() - MARGIN;
			int width = getWidth() - (2 * MARGIN);
			int height = getHeight() - (2 * MARGIN);
			
			Dimension changesYAndHeihght = showTitle(graphics, leftBottomX, leftBottomY, width, height);
			leftBottomY = changesYAndHeihght.width;
			height = changesYAndHeihght.height;
			
			int legendHeight = showLegendBottom(graphics, leftBottomX, leftBottomY, width, true);
			leftBottomY = leftBottomY - legendHeight;
			height = height - legendHeight;

			if (valueAxisLabel != null) {
				leftBottomY = leftBottomY - fontMetrics.getHeight() - AXIS_VALUE_GAP;
				height = height - fontMetrics.getHeight() - AXIS_VALUE_GAP;
			}

			if (bucketAxisLabel != null) {
				height = height - fontMetrics.getHeight() - AXIS_VALUE_GAP;
			}
			
			width = width - ((maxXWidth + 1) / 2);
			
			int xShift = Math.max(maxYWidth + AXIS_VALUE_GAP, ((maxXWidth + 1) / 2));
			int verticalAxisX = leftBottomX + xShift;
			if ((minX < 0.0) && (maxX > 0.0)) {
				verticalAxisX = valueToPosition(0.0, leftBottomX + xShift, leftBottomX + width - xShift - (2 * BAR_SHIFT_X) - BAR_DEPTH_X, minX, maxX);
				if ((verticalAxisX - xShift) < leftBottomX) {
					leftBottomX = leftBottomX + xShift + (leftBottomX - (verticalAxisX - xShift));
					width = width - xShift - (leftBottomX - (verticalAxisX - xShift));
				}
				else {
					leftBottomX = leftBottomX + xShift;
					width = width - xShift;
				}
				verticalAxisX = valueToPosition(0.0, leftBottomX, leftBottomX + width - (2 * BAR_SHIFT_X) - BAR_DEPTH_X, minX, maxX);
			}
			else {
				leftBottomX = leftBottomX + xShift;
				width = width - xShift;
				verticalAxisX = leftBottomX;
			}
			
			leftBottomY = leftBottomY - fontMetrics.getHeight() - AXIS_VALUE_GAP;
			height = height - fontMetrics.getHeight() - AXIS_VALUE_GAP;
			int horizontalAxisY = leftBottomY;
			
			List<Double> hAxis = horizontalAxis(valueAxis, minX, maxX, leftBottomX, leftBottomX + width - BAR_SHIFT_X, fontMetrics, valueAxisPrecision);
			int bucketHeight = (int) Math.round((double) (height - (2 * BAR_SHIFT_Y) - BAR_DEPTH_Y) / (double) buckets.size());
			int barHeight = (int) Math.round(((double) bucketHeight - (2 * BUCKET_MARGIN)) / (double) dataSets.keySet().size());
			
			if (JChartPlot.DEBUG) {
				System.out.println();
				System.out.println(name);
				System.out.println("PanelWidth       = " + Integer.toString(getWidth()));
				System.out.println("PanelHeight      = " + Integer.toString(getHeight()));
				System.out.println("MARGIN           = " + Integer.toString(MARGIN));
				System.out.println("AXIS_VALUE_GAP   = " + Integer.toString(AXIS_VALUE_GAP));
				System.out.println("BAR_SHIFT_X      = " + Integer.toString(BAR_SHIFT_X));
				System.out.println("BAR_SHIFT_Y      = " + Integer.toString(BAR_SHIFT_Y));
				System.out.println("BAR_DEPTH_X      = " + Integer.toString(BAR_DEPTH_X));
				System.out.println("BAR_DEPTH_Y      = " + Integer.toString(BAR_DEPTH_Y));
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
				System.out.println("bucketHeight     = " + Integer.toString(bucketHeight));
				System.out.println("barHeight        = " + Integer.toString(barHeight));
				System.out.println();
			}
			
			if (barHeight < 1) {
				showError(graphics, "<html>BarChart '" + name + "'<br>Data set error:<br>Too many buckets and/or data sets.</html>");
			}
			else {
				// Set axis color
				graphics.setColor(axisColor);
				
				//Set axis font
				graphics.setFont(axisFont);
				
				// Draw horizontal axis
				for (double x : hAxis) {
					String xString = formatValue(x, valueAxisPrecision);
					Integer xPos = valueToPosition(x, leftBottomX, leftBottomX + width - (2 * BAR_SHIFT_X) - BAR_DEPTH_X, hAxis.get(0), hAxis.get(hAxis.size() - 1));
					int halfXStringWidth = (fontMetrics.stringWidth(xString) / 2);
					if ((horizontalAxisY == leftBottomY) || (xPos < (verticalAxisX - halfXStringWidth - 2)) || (xPos > (verticalAxisX + halfXStringWidth + 2))) {
						graphics.setColor(axisColor);
						graphics.drawLine(xPos, horizontalAxisY + 2, xPos, horizontalAxisY);
						graphics.drawString(xString, xPos - halfXStringWidth, horizontalAxisY + 4 + fontMetrics.getHeight());
					}
					graphics.setColor(Color.LIGHT_GRAY);
					graphics.drawLine(xPos, horizontalAxisY, xPos + (2 * BAR_SHIFT_X) + BAR_DEPTH_X, horizontalAxisY - (2 * BAR_SHIFT_Y) - BAR_DEPTH_Y);
					graphics.drawLine(xPos + (2 * BAR_SHIFT_X) + BAR_DEPTH_X, horizontalAxisY - (2 * BAR_SHIFT_Y) - BAR_DEPTH_Y, xPos + (2 * BAR_SHIFT_X) + BAR_DEPTH_X, leftBottomY - height);
				}
				if (valueAxisLabel != null) {
					graphics.setColor(axisColor);
					graphics.drawString(valueAxisLabel, leftBottomX + width + ((maxXWidth + 1) / 2) - fontMetrics.stringWidth(valueAxisLabel), horizontalAxisY + (2 * fontMetrics.getHeight() + AXIS_VALUE_GAP));
				}
				
				// Draw bars
				List<String> orderedDataSets = new ArrayList<String>();
				orderedDataSets.addAll(dataSetList);
				if (orderDataSets) {
					Collections.sort(orderedDataSets);
				}
				for (int dataSetNr = orderedDataSets.size() - 1; dataSetNr >= 0; dataSetNr--) {
					String dataSetName = orderedDataSets.get(dataSetNr);
					Color color = dataSets.get(dataSetName);
					Color shadowColorRight = new Color(Math.max(0, color.getRed() + SHADOW_RIGHT_RGB_SHIFT), Math.max(0, color.getGreen() + SHADOW_RIGHT_RGB_SHIFT), Math.max(0, color.getBlue() + SHADOW_RIGHT_RGB_SHIFT));
					Color shadowColorTop = new Color(Math.max(0, color.getRed() + SHADOW_TOP_RGB_SHIFT), Math.max(0, color.getGreen() + SHADOW_TOP_RGB_SHIFT), Math.max(0, color.getBlue() + SHADOW_TOP_RGB_SHIFT));
					for (int bucketNr = 0; bucketNr < buckets.size(); bucketNr++) {
						String bucket = buckets.get(bucketNr);
						Double x = dataSet.get(dataSetName).get(bucket);
						if (x != null) {
							int barX = valueToPosition(x, leftBottomX, leftBottomX + width - (2 * BAR_SHIFT_X) - BAR_DEPTH_X, hAxis.get(0), hAxis.get(hAxis.size() - 1));
							int barY = horizontalAxisY - height + (2 * BAR_SHIFT_Y) + BAR_DEPTH_Y + (bucketNr * bucketHeight) + BUCKET_MARGIN + (dataSetNr * barHeight);
							if (Math.abs(barX - verticalAxisX) > 0) {
								graphics.setColor(color);
								graphics.fillRect(Math.min(verticalAxisX, barX) + BAR_SHIFT_X, barY - BAR_SHIFT_Y, Math.abs(barX - verticalAxisX), barHeight);
								
								int leftFrontX = Math.min(verticalAxisX, barX) + BAR_SHIFT_X;
								int leftRearX = leftFrontX + BAR_DEPTH_X;
								int rightFrontX = leftFrontX + Math.abs(barX - verticalAxisX);
								int rightRearX = rightFrontX + BAR_DEPTH_X;
								int frontTopY = barY - BAR_SHIFT_Y;
								int rearTopY = frontTopY - BAR_DEPTH_Y;
								int frontBottomY = frontTopY + barHeight;
								int rearBottomY = frontBottomY - BAR_DEPTH_Y;
								graphics.setColor(shadowColorTop);
								graphics.fillPolygon(new int[] { leftFrontX, rightFrontX, rightRearX, leftRearX }, new int[] { frontTopY, frontTopY, rearTopY, rearTopY }, 4);
								graphics.setColor(shadowColorRight);
								graphics.fillPolygon(new int[] { rightFrontX, rightRearX, rightRearX, rightFrontX }, new int[] { frontBottomY, rearBottomY, rearTopY, frontTopY }, 4);
							}
						}
					}
				}

				// Set axis color
				graphics.setColor(axisColor);
				
				//Set axis font
				graphics.setFont(axisFont);

				// Draw vertical axis
				graphics.drawLine(verticalAxisX + BAR_SHIFT_X, horizontalAxisY - BAR_SHIFT_Y, verticalAxisX + BAR_SHIFT_X, leftBottomY - height);
				for (int bucketNr = 0; bucketNr < buckets.size(); bucketNr++) {
					String yAxisLabel = buckets.get(bucketNr);
					graphics.setColor(axisColor);
					int yBucketCenter = leftBottomY - height + (bucketNr * bucketHeight) + (bucketHeight / 2) + BAR_SHIFT_Y + BAR_DEPTH_Y;
					graphics.drawString(yAxisLabel, verticalAxisX - AXIS_VALUE_GAP - fontMetrics.stringWidth(yAxisLabel), yBucketCenter + (fontMetrics.getHeight() / 2));
				}
				if (bucketAxisLabel != null) {
					graphics.setColor(axisColor);
					graphics.drawString(bucketAxisLabel, Math.max(MARGIN, verticalAxisX - (fontMetrics.stringWidth(bucketAxisLabel) / 2)), leftBottomY - height - AXIS_VALUE_GAP);
				}
			}
		}
	}
	
	
	private void draw3DStackedVertical(Graphics graphics) {
		//Set background color
		graphics.setColor(chartBackgroundColor);
		graphics.fillRect(0, 0, getWidth(), getHeight());

		if (dataSet != null) {
			FontMetrics fontMetrics = graphics.getFontMetrics(axisFont);

			int maxXWidth = 0;
			
			Double minY = null;
			Double maxY = null;
			int maxYWidth = 0;
			
			List<String> buckets = new ArrayList<String>();
			if (bucketAxis != null) {
				for (String bucket : bucketAxis) {
					if (!buckets.contains(bucket)) {
						buckets.add(bucket);
					}
					maxXWidth = Math.max(maxYWidth, fontMetrics.stringWidth(bucket));
				}
			}
			
			if (valueAxis != null) {
				for (double y : valueAxis) {
					minY = minY == null ? y : Math.min(minY, y);
					maxY = maxY == null ? y : Math.max(maxY, y);
					maxYWidth = Math.max(maxYWidth, fontMetrics.stringWidth(formatValue(y, valueAxisPrecision)));
				}
			}
			else {
				maxY = 0.0;
			}

			if ((bucketAxis == null) || (valueAxis == null)) {
				Map<String, Double> bucketMaxY = new HashMap<String, Double>();
				for (String dataSetName : dataSet.keySet()) {
					for (String bucket : dataSet.get(dataSetName).keySet()) {
						maxXWidth = Math.max(maxYWidth, fontMetrics.stringWidth(bucket));
						if (bucketAxis == null) {
							if (!buckets.contains(bucket)) {
								buckets.add(bucket);
							}
						}
						
						if (valueAxis == null) {
							double y = dataSet.get(dataSetName).get(bucket);
							minY = minY == null ? y : Math.min(minY, y);
							bucketMaxY.put(bucket, bucketMaxY.get(bucket) == null ? y : (bucketMaxY.get(bucket) + y));
						}
					}
				}
				for (String bucket : bucketMaxY.keySet()) {
					maxY = maxY == null ? bucketMaxY.get(bucket) : Math.max(maxY, bucketMaxY.get(bucket));
					maxYWidth = Math.max(maxYWidth, fontMetrics.stringWidth(formatValue(maxY, valueAxisPrecision)));
				}
			}
			
			if (minY < 0.0) {
				showError(graphics, "<html>BarChart '" + name + "'<br>Data set error:<br>Negative value(s) in data sets.</html>");
			}
			else {
				minY = 0.0;
				
				if (bucketAxis == null) {
					Collections.sort(buckets);
				}
				
				int leftBottomX = MARGIN;
				int leftBottomY = getHeight() - MARGIN;
				int width = getWidth() - (2 * MARGIN);
				int height = getHeight() - (2 * MARGIN);
				
				Dimension changesYAndHeihght = showTitle(graphics, leftBottomX, leftBottomY, width, height);
				leftBottomY = changesYAndHeihght.width;
				height = changesYAndHeihght.height;
				
				int legendHeight = showLegendBottom(graphics, leftBottomX, leftBottomY, width, true);
				leftBottomY = leftBottomY - legendHeight;
				height = height - legendHeight;
				
				if (bucketAxisLabel != null) {
					if (!((minY < 0.0) && (maxY > 0.0))) {
						leftBottomY = leftBottomY - fontMetrics.getHeight() - AXIS_VALUE_GAP;
						height = height - fontMetrics.getHeight() - AXIS_VALUE_GAP;
					}
				}
				
				if (valueAxisLabel != null) {
					height = height - fontMetrics.getHeight() - AXIS_VALUE_GAP;
				}
				
				int xShift = Math.max(maxYWidth + AXIS_VALUE_GAP, (maxXWidth + 1) / 2);
				leftBottomX = leftBottomX + xShift;
				width = width - xShift;
				int verticalAxisX = leftBottomX;
				
				int horizontalAxisY = leftBottomY;
				if ((minY < 0.0) && (maxY > 0.0)) {
					leftBottomY = leftBottomY - ((fontMetrics.getHeight() + 1) / 2);
					height = height - ((fontMetrics.getHeight() + 1) / 2);
					horizontalAxisY = valueToPosition(0.0, leftBottomY, leftBottomY - height + (2 * BAR_SHIFT_Y) + BAR_DEPTH_Y, minY, maxY);
				}
				else {
					leftBottomY = leftBottomY - fontMetrics.getHeight() - AXIS_VALUE_GAP;
					height = height - fontMetrics.getHeight() - AXIS_VALUE_GAP;
					horizontalAxisY = leftBottomY;
				}

				int bucketWidth = (int) Math.round((double) (width - leftBottomX - BAR_SHIFT_X - BAR_DEPTH_X) / (double) buckets.size());
				int barWidth = (int) Math.round((double) bucketWidth - (2 * BUCKET_MARGIN));
				List<Double> vAxis = verticalAxis(valueAxis, minY, maxY, leftBottomY, leftBottomY - height, fontMetrics, valueAxisPrecision);
				
				if (JChartPlot.DEBUG) {
					System.out.println();
					System.out.println(name);
					System.out.println("PanelWidth       = " + Integer.toString(getWidth()));
					System.out.println("PanelHeight      = " + Integer.toString(getHeight()));
					System.out.println("MARGIN           = " + Integer.toString(MARGIN));
					System.out.println("AXIS_VALUE_GAP   = " + Integer.toString(AXIS_VALUE_GAP));
					System.out.println("BAR_SHIFT_X      = " + Integer.toString(BAR_SHIFT_X));
					System.out.println("BAR_SHIFT_Y      = " + Integer.toString(BAR_SHIFT_Y));
					System.out.println("BAR_DEPTH_X      = " + Integer.toString(BAR_DEPTH_X));
					System.out.println("BAR_DEPTH_Y      = " + Integer.toString(BAR_DEPTH_Y));
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
					System.out.println("bucketWidth      = " + Integer.toString(bucketWidth));
					System.out.println("barWidth         = " + Integer.toString(barWidth));
					System.out.println();
				}
				
				if (barWidth < 1) {
					showError(graphics, "<html>BarChart '" + name + "'<br>Data set error:<br>Too many buckets and/or data sets.</html>");
				}
				else {
					// Set axis color
					graphics.setColor(axisColor);
					
					//Set axis font
					graphics.setFont(axisFont);

					// Draw vertical axis
					for (double y : vAxis) {
						String yString = formatValue(y, valueAxisPrecision);
						int yPos = valueToPosition(y, leftBottomY, leftBottomY - height + (2* BAR_SHIFT_Y) + BAR_DEPTH_Y, vAxis.get(0), vAxis.get(vAxis.size() - 1));
						if ((verticalAxisX == leftBottomX) || (yPos > (horizontalAxisY + fontMetrics.getHeight() + 2)) || (yPos < (horizontalAxisY - fontMetrics.getHeight() - 2))) {
							graphics.setColor(axisColor);
							graphics.drawLine(verticalAxisX - 2, yPos, verticalAxisX, yPos);
							graphics.drawString(yString, verticalAxisX - AXIS_VALUE_GAP - fontMetrics.stringWidth(yString), yPos + (fontMetrics.getHeight() / 2));
							
							graphics.setColor(Color.LIGHT_GRAY);
							graphics.drawLine(verticalAxisX, yPos, verticalAxisX + (2 * BAR_SHIFT_X) + BAR_DEPTH_X, yPos - (2 * BAR_SHIFT_Y) - BAR_DEPTH_Y);
							graphics.drawLine(verticalAxisX + (2 * BAR_SHIFT_X) + BAR_DEPTH_X, yPos - (2 * BAR_SHIFT_Y) - BAR_DEPTH_Y, width, yPos - (2 * BAR_SHIFT_Y) - BAR_DEPTH_Y);
						}
					}
					if (valueAxisLabel != null) {
						graphics.setColor(axisColor);
						graphics.drawString(valueAxisLabel, Math.max(MARGIN, verticalAxisX - (fontMetrics.stringWidth(valueAxisLabel) / 2)), leftBottomY - height - AXIS_VALUE_GAP);
					}

					// Draw bars
					List<String> orderedDataSets = new ArrayList<String>();
					orderedDataSets.addAll(dataSetList);
					if (orderDataSets) {
						Collections.sort(orderedDataSets);
					}
					for (int bucketNr = 0; bucketNr < buckets.size(); bucketNr++) {
						String bucket = buckets.get(bucketNr);
						Double lastY = 0.0;
						int lastBarY = horizontalAxisY;
						for (int dataSetNr = 0; dataSetNr < orderedDataSets.size(); dataSetNr++) {
							String dataSetName = orderedDataSets.get(dataSetNr);
							Color color = dataSets.get(dataSetName);
							Color shadowColorRight = new Color(Math.max(0, color.getRed() + SHADOW_RIGHT_RGB_SHIFT), Math.max(0, color.getGreen() + SHADOW_RIGHT_RGB_SHIFT), Math.max(0, color.getBlue() + SHADOW_RIGHT_RGB_SHIFT));
							Color shadowColorTop = new Color(Math.max(0, color.getRed() + SHADOW_TOP_RGB_SHIFT), Math.max(0, color.getGreen() + SHADOW_TOP_RGB_SHIFT), Math.max(0, color.getBlue() + SHADOW_TOP_RGB_SHIFT));
							Double y = dataSet.get(dataSetName).get(bucket);
							if (y != null) {
								Double newY = lastY + y;
								int barX = leftBottomX + (bucketNr * bucketWidth) + BUCKET_MARGIN + BAR_SHIFT_X;
								int barY = valueToPosition(newY, leftBottomY, leftBottomY - height + (2 * BAR_SHIFT_Y) + BAR_DEPTH_Y, vAxis.get(0), vAxis.get(vAxis.size() - 1));
								if ((lastBarY - barY) > 0) {
									graphics.setColor(color);
									graphics.fillRect(barX, barY - BAR_SHIFT_Y, barWidth, lastBarY - barY);
									
									int frontRightX = barX + barWidth;
									int frontTopY = barY - BAR_SHIFT_Y;
									int frontBottomY = frontTopY + lastBarY - barY;
									int rearRightX = frontRightX + BAR_DEPTH_X;
									int rearBottomY = frontBottomY - BAR_DEPTH_Y;
									int rearTopY = frontTopY - BAR_DEPTH_Y;
									int rearLeftTopX = barX + BAR_DEPTH_X;
									graphics.setColor(shadowColorRight);
									graphics.fillPolygon(new int[] { frontRightX, rearRightX, rearRightX, frontRightX }, new int[] { frontBottomY, rearBottomY, rearTopY, frontTopY }, 4);
									graphics.setColor(shadowColorTop);
									graphics.fillPolygon(new int[] { barX, frontRightX, rearRightX, rearLeftTopX }, new int[] { frontTopY, frontTopY, rearTopY, rearTopY }, 4);
								}
								lastY = newY;
								lastBarY = barY;
							}
						}
					}

					// Set axis color
					graphics.setColor(axisColor);
					
					//Set axis font
					graphics.setFont(axisFont);

					// Draw horizontal axis
					graphics.drawLine(leftBottomX + BAR_SHIFT_X, horizontalAxisY - BAR_SHIFT_Y, leftBottomX + width - (2 * BAR_SHIFT_X) - BAR_DEPTH_X, horizontalAxisY - BAR_SHIFT_Y);
					for (int bucketNr = 0; bucketNr < buckets.size(); bucketNr++) {
						String xAxisLabel = buckets.get(bucketNr);
						int xBucketCenter = leftBottomX + BAR_SHIFT_X + (bucketNr * bucketWidth) + (bucketWidth / 2);
						graphics.drawString(xAxisLabel, xBucketCenter - (fontMetrics.stringWidth(xAxisLabel) / 2), horizontalAxisY - BAR_SHIFT_Y + AXIS_VALUE_GAP + fontMetrics.getHeight());
					}
					if (bucketAxisLabel != null) {
						graphics.drawString(bucketAxisLabel, leftBottomX + width - fontMetrics.stringWidth(bucketAxisLabel), horizontalAxisY + (2 * fontMetrics.getHeight() + AXIS_VALUE_GAP));
					}
				}
			}
		}
	}
	
	
	private void draw3DStackedHorizontal(Graphics graphics) {
		//Set background color
		graphics.setColor(chartBackgroundColor);
		graphics.fillRect(0, 0, getWidth(), getHeight());

		if (dataSet != null) {
			FontMetrics fontMetrics = graphics.getFontMetrics(axisFont);
			
			Double minX = null;
			Double maxX = null;
			int maxXWidth = 0;

			int maxYWidth = 0;
			
			List<String> buckets = new ArrayList<String>();
			if (bucketAxis != null) {
				for (String bucket : bucketAxis) {
					if (!buckets.contains(bucket)) {
						buckets.add(bucket);
					}
					maxYWidth = Math.max(maxYWidth, fontMetrics.stringWidth(bucket));
				}
			}
			
			if (valueAxis != null) {
				for (double x : valueAxis) {
					minX = minX == null ? x : Math.min(minX, x);
					maxX = maxX == null ? x : Math.max(maxX, x);
					maxXWidth = Math.max(maxXWidth, fontMetrics.stringWidth(formatValue(x, valueAxisPrecision)));
				}
			}
			else {
				maxX = 0.0;
			}

			if ((bucketAxis == null) || (valueAxis == null)) {
				Map<String, Double> bucketMaxX = new HashMap<String, Double>();
				for (String dataSetName : dataSet.keySet()) {
					for (String bucket : dataSet.get(dataSetName).keySet()) {
						maxYWidth = Math.max(maxYWidth, fontMetrics.stringWidth(bucket));
						if (bucketAxis == null) {
							if (!buckets.contains(bucket)) {
								buckets.add(bucket);
							}
						}
						
						if (valueAxis == null) {
							double x = dataSet.get(dataSetName).get(bucket);
							minX = minX == null ? x : Math.min(minX, x);
							bucketMaxX.put(bucket, bucketMaxX.get(bucket) == null ? x : (bucketMaxX.get(bucket) + x));
						}
					}
				}
				for (String bucket : bucketMaxX.keySet()) {
					maxX = maxX == null ? bucketMaxX.get(bucket) : Math.max(maxX, bucketMaxX.get(bucket));
					maxXWidth = Math.max(maxXWidth, fontMetrics.stringWidth(formatValue(maxX, valueAxisPrecision)));
				}
			}
			
			if (minX < 0.0) {
				showError(graphics, "<html>BarChart '" + name + "'<br>Data set error:<br>Negative value(s) in data sets.</html>");
			}
			else {
				minX = 0.0;
				
				if (bucketAxis == null) {
					Collections.sort(buckets);
				}
				
				int leftBottomX = MARGIN;
				int leftBottomY = getHeight() - MARGIN;
				int width = getWidth() - (2 * MARGIN);
				int height = getHeight() - (2 * MARGIN);
				
				Dimension changesYAndHeihght = showTitle(graphics, leftBottomX, leftBottomY, width, height);
				leftBottomY = changesYAndHeihght.width;
				height = changesYAndHeihght.height;
				
				int legendHeight = showLegendBottom(graphics, leftBottomX, leftBottomY, width, true);
				leftBottomY = leftBottomY - legendHeight;
				height = height - legendHeight;

				if (valueAxisLabel != null) {
					leftBottomY = leftBottomY - fontMetrics.getHeight() - AXIS_VALUE_GAP;
					height = height - fontMetrics.getHeight() - AXIS_VALUE_GAP;
				}

				if (bucketAxisLabel != null) {
					height = height - fontMetrics.getHeight() - AXIS_VALUE_GAP;
				}
				
				width = width - ((maxXWidth + 1) / 2);
				
				int xShift = Math.max(maxYWidth + AXIS_VALUE_GAP, ((maxXWidth + 1) / 2));
				int verticalAxisX = leftBottomX + xShift;
				if ((minX < 0.0) && (maxX > 0.0)) {
					verticalAxisX = valueToPosition(0.0, leftBottomX + xShift, leftBottomX + width - xShift - (2 * BAR_SHIFT_X) - BAR_DEPTH_X, minX, maxX);
					if ((verticalAxisX - xShift) < leftBottomX) {
						leftBottomX = leftBottomX + xShift + (leftBottomX - (verticalAxisX - xShift));
						width = width - xShift - (leftBottomX - (verticalAxisX - xShift));
					}
					else {
						leftBottomX = leftBottomX + xShift;
						width = width - xShift;
					}
					verticalAxisX = valueToPosition(0.0, leftBottomX, leftBottomX + width - (2 * BAR_SHIFT_X) - BAR_DEPTH_X, minX, maxX);
				}
				else {
					leftBottomX = leftBottomX + xShift;
					width = width - xShift;
					verticalAxisX = leftBottomX;
				}
				
				leftBottomY = leftBottomY - fontMetrics.getHeight() - AXIS_VALUE_GAP;
				height = height - fontMetrics.getHeight() - AXIS_VALUE_GAP;
				int horizontalAxisY = leftBottomY;
				
				List<Double> hAxis = horizontalAxis(valueAxis, minX, maxX, leftBottomX, leftBottomX + width - BAR_SHIFT_X, fontMetrics, valueAxisPrecision);
				int bucketHeight = (int) Math.round((double) (height - (2 * BAR_SHIFT_Y) - BAR_DEPTH_Y) / (double) buckets.size());
				int barHeight = (int) Math.round((double) bucketHeight - (2 * BUCKET_MARGIN));
				
				if (JChartPlot.DEBUG) {
					System.out.println();
					System.out.println(name);
					System.out.println("PanelWidth       = " + Integer.toString(getWidth()));
					System.out.println("PanelHeight      = " + Integer.toString(getHeight()));
					System.out.println("MARGIN           = " + Integer.toString(MARGIN));
					System.out.println("AXIS_VALUE_GAP   = " + Integer.toString(AXIS_VALUE_GAP));
					System.out.println("BAR_SHIFT_X      = " + Integer.toString(BAR_SHIFT_X));
					System.out.println("BAR_SHIFT_Y      = " + Integer.toString(BAR_SHIFT_Y));
					System.out.println("BAR_DEPTH_X      = " + Integer.toString(BAR_DEPTH_X));
					System.out.println("BAR_DEPTH_Y      = " + Integer.toString(BAR_DEPTH_Y));
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
					System.out.println("bucketHeight     = " + Integer.toString(bucketHeight));
					System.out.println("barHeight        = " + Integer.toString(barHeight));
					System.out.println();
				}
				
				if (barHeight < 1) {
					showError(graphics, "<html>BarChart '" + name + "'<br>Data set error:<br>Too many buckets and/or data sets.</html>");
				}
				else {
					// Set axis color
					graphics.setColor(axisColor);
					
					//Set axis font
					graphics.setFont(axisFont);
					
					// Draw horizontal axis
					for (double x : hAxis) {
						String xString = formatValue(x, valueAxisPrecision);
						Integer xPos = valueToPosition(x, leftBottomX, leftBottomX + width - (2 * BAR_SHIFT_X) - BAR_DEPTH_X, hAxis.get(0), hAxis.get(hAxis.size() - 1));
						int halfXStringWidth = (fontMetrics.stringWidth(xString) / 2);
						if ((horizontalAxisY == leftBottomY) || (xPos < (verticalAxisX - halfXStringWidth - 2)) || (xPos > (verticalAxisX + halfXStringWidth + 2))) {
							graphics.setColor(axisColor);
							graphics.drawLine(xPos, horizontalAxisY + 2, xPos, horizontalAxisY);
							graphics.drawString(xString, xPos - halfXStringWidth, horizontalAxisY + 4 + fontMetrics.getHeight());
						}
						graphics.setColor(Color.LIGHT_GRAY);
						graphics.drawLine(xPos, horizontalAxisY, xPos + (2 * BAR_SHIFT_X) + BAR_DEPTH_X, horizontalAxisY - (2 * BAR_SHIFT_Y) - BAR_DEPTH_Y);
						graphics.drawLine(xPos + (2 * BAR_SHIFT_X) + BAR_DEPTH_X, horizontalAxisY - (2 * BAR_SHIFT_Y) - BAR_DEPTH_Y, xPos + (2 * BAR_SHIFT_X) + BAR_DEPTH_X, leftBottomY - height);
					}
					if (valueAxisLabel != null) {
						graphics.setColor(axisColor);
						graphics.drawString(valueAxisLabel, leftBottomX + width + ((maxXWidth + 1) / 2) - fontMetrics.stringWidth(valueAxisLabel), horizontalAxisY + (2 * fontMetrics.getHeight() + AXIS_VALUE_GAP));
					}
					
					// Draw bars
					List<String> orderedDataSets = new ArrayList<String>();
					orderedDataSets.addAll(dataSetList);
					if (orderDataSets) {
						Collections.sort(orderedDataSets);
					}
					for (int bucketNr = 0; bucketNr < buckets.size(); bucketNr++) {
						String bucket = buckets.get(bucketNr);
						Double lastX = 0.0;
						int lastBarX = verticalAxisX;
						for (int dataSetNr = 0; dataSetNr < orderedDataSets.size(); dataSetNr++) {
							String dataSetName = orderedDataSets.get(dataSetNr);
							Color color = dataSets.get(dataSetName);
							Color shadowColorRight = new Color(Math.max(0, color.getRed() + SHADOW_RIGHT_RGB_SHIFT), Math.max(0, color.getGreen() + SHADOW_RIGHT_RGB_SHIFT), Math.max(0, color.getBlue() + SHADOW_RIGHT_RGB_SHIFT));
							Color shadowColorTop = new Color(Math.max(0, color.getRed() + SHADOW_TOP_RGB_SHIFT), Math.max(0, color.getGreen() + SHADOW_TOP_RGB_SHIFT), Math.max(0, color.getBlue() + SHADOW_TOP_RGB_SHIFT));
							Double x = dataSet.get(dataSetName).get(bucket);
							if (x != null) {
								Double newX = lastX + x;
								int barX = valueToPosition(newX, leftBottomX, leftBottomX + width - (2 * BAR_SHIFT_X) - BAR_DEPTH_X, hAxis.get(0), hAxis.get(hAxis.size() - 1));
								int barY = horizontalAxisY - height + (2 * BAR_SHIFT_Y) + BAR_DEPTH_Y + (bucketNr * bucketHeight) + BUCKET_MARGIN;
								if ((barX - lastBarX) > 0) {
									graphics.setColor(color);
									graphics.fillRect(lastBarX + BAR_SHIFT_X, barY - BAR_SHIFT_Y, barX - lastBarX, barHeight);
									
									int leftFrontX = lastBarX + BAR_SHIFT_X;
									int leftRearX = leftFrontX + BAR_DEPTH_X;
									int rightFrontX = leftFrontX + (barX - lastBarX);
									int rightRearX = rightFrontX + BAR_DEPTH_X;
									int frontTopY = barY - BAR_SHIFT_Y;
									int rearTopY = frontTopY - BAR_DEPTH_Y;
									int frontBottomY = frontTopY + barHeight;
									int rearBottomY = frontBottomY - BAR_DEPTH_Y;
									graphics.setColor(shadowColorTop);
									graphics.fillPolygon(new int[] { leftFrontX, rightFrontX, rightRearX, leftRearX }, new int[] { frontTopY, frontTopY, rearTopY, rearTopY }, 4);
									graphics.setColor(shadowColorRight);
									graphics.fillPolygon(new int[] { rightFrontX, rightRearX, rightRearX, rightFrontX }, new int[] { frontBottomY, rearBottomY, rearTopY, frontTopY }, 4);
								}
								lastX = newX;
								lastBarX = barX;
							}
						}
					}

					// Set axis color
					graphics.setColor(axisColor);
					
					//Set axis font
					graphics.setFont(axisFont);

					// Draw vertical axis
					graphics.drawLine(verticalAxisX + BAR_SHIFT_X, horizontalAxisY - BAR_SHIFT_Y, verticalAxisX + BAR_SHIFT_X, leftBottomY - height);
					for (int bucketNr = 0; bucketNr < buckets.size(); bucketNr++) {
						String yAxisLabel = buckets.get(bucketNr);
						graphics.setColor(axisColor);
						int yBucketCenter = leftBottomY - height + (bucketNr * bucketHeight) + (bucketHeight / 2) + BAR_SHIFT_Y + BAR_DEPTH_Y;
						graphics.drawString(yAxisLabel, verticalAxisX - AXIS_VALUE_GAP - fontMetrics.stringWidth(yAxisLabel), yBucketCenter + (fontMetrics.getHeight() / 2));
					}
					if (bucketAxisLabel != null) {
						graphics.setColor(axisColor);
						graphics.drawString(bucketAxisLabel, Math.max(MARGIN, verticalAxisX - (fontMetrics.stringWidth(bucketAxisLabel) / 2)), leftBottomY - height - AXIS_VALUE_GAP);
					}
				}
			}
		}
	}


	@Override
	public void clear() {
		super.clear();
		dataSet = null;
		bucketAxis = null;
		bucketAxisLabel = null;
		valueAxis = null;
		valueAxisLabel = null;
	}

}
