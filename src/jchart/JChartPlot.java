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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;

abstract class JChartPlot extends JPanel {
	private static final long serialVersionUID = -1438448602003395056L;
	
	protected static final boolean DEBUG = true;
	
	protected static final int MARGIN             = 5;
	protected static final int LABEL_AXIS_GAP     = 4;
	protected static final int STANDARD_PRECISION = 1;
	protected static final int AXIS_VALUE_GAP     = 4;
	
	protected static final int FLOODFILL_STYLE_4_WAY = 0;
	protected static final int FLOODFILL_STYLE_8_WAY = 1;

	protected String name = null;
	protected String title = null;
	protected Color titleColor = Color.BLACK;
	protected Font titleFont = new Font("Arial", Font.BOLD, 15);
	protected int titleAlignment = JChart.TITLE_ALIGNMENT_CENTER;
	protected int titlePosition  = JChart.TITLE_POSITION_TOP;
	protected Color chartBackgroundColor = Color.WHITE;
	protected boolean legendEnabled = true;
	protected Font legendFont = new Font("Arial", Font.PLAIN, 10);
	protected Color legendFontColor = titleColor;
	protected Color legendBackGroundColor = chartBackgroundColor;
	protected Color legendBorderColor = titleColor;
	protected Boolean orderDataSets = false;

	protected Map<String, Color> dataSets = null;
	protected List<String> dataSetList = new ArrayList<String>();
	protected Map<String, Integer> pointStyles = null;
	
	private JPopupMenu popUpMenu;
	
	
	public JChartPlot(String name) {
		super();
		
		this.name = name;
		JPanel panel = this;
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder());
		popUpMenu = createPopUpMenu();
		addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				//right mouse click event
				if (SwingUtilities.isRightMouseButton(e) && e.getClickCount() == 1){
					popUpMenu.show(panel , e.getX(), e.getY());
				}
				
			}
		});
		clear();
	}
	
	public void setTitle(String title, int alignment, int position) {
		this.title = title;
		titleAlignment = alignment;
		titlePosition= position;
	}
	
	
	public String getTitle() {
		return title;
	}
	
	
	public void setTitleFont(Font font) {
		titleFont = font;
	}
	
	
	public void setTitleColor(Color color) {
		titleColor = color;
	}
	
	
	public void setBackgroundColor(Color color) {
		if (color != null) {
			chartBackgroundColor = color;
		}
	}
	
	
	public void setLegendEnabled(boolean enabled) {
		legendEnabled = enabled;
	}
	
	
	public void setLegendFont(Font font) {
		legendFont = font;
	}
	
	
	public void setLegendFontColor(Color color) {
		legendFontColor = color;
	}
	
	
	public void setLegendBackgroundColor(Color color) {
		legendBackGroundColor = color;
	}
	
	
	public void setLegendBorderColor(Color  color) {
		legendBorderColor = color;
	}
	
	
	public void setOrderedDataSets(Boolean ordered) {
		orderDataSets = ordered;
	}
	
	
	public void clear() {
		dataSets = null;
		pointStyles = null;
	}
	
	
	public boolean addDataSet(String dataSetName, Color color) {
		boolean ok = false;
		
		if (dataSets == null) {
			dataSets = new HashMap<String, Color>();
		}
		if (!dataSets.containsKey(dataSetName)) {
			dataSets.put(dataSetName, color);
			dataSetList.add(dataSetName);
			ok = true;
		}
		else {
			JOptionPane.showMessageDialog(null, "Data set '" + dataSetName + " already exists.", "Chart '" + name + "' Data Set Error", JOptionPane.ERROR_MESSAGE);
		}
		
		return ok;
	}
	
	
	protected String formatValue(double value, int precision) {
		String formattedValue = Double.toString(round(value, precision));
		if ((precision == 0) && formattedValue.contains(".")) {
			formattedValue = formattedValue.substring(0, formattedValue.indexOf("."));
		}
		return formattedValue;
	}
	
	
	protected double round(double value, int precision) {
		double factor = Math.pow(10, precision);
		return (((double) Math.round(value * factor)) / factor);
	}
	
	
	protected Integer valueToPosition(double value, int basePosition, int endPosition, double baseValue, double endValue) {
		return basePosition +
			   (int) Math.round(
					   //(((double) endPosition - (double) basePosition) / Math.abs((double) endPosition - (double) basePosition)) *
					   //(((double) endValue - (double) baseValue) / Math.abs((double) endValue - (double) baseValue)) *
					   ((value - baseValue) / (endValue - baseValue)) * ((double) endPosition - (double) basePosition)
					   );
				
	}
	
	
	protected List<Double> horizontalAxis(List<Double> predefinedAxis, double minValue, double maxValue, int minPosition, int maxPosition, FontMetrics fontMetrics, int precision) {
		List<Double> axis = new ArrayList<Double>();
		if (predefinedAxis != null) {
			int maxWidth = 0;
			for (double value : predefinedAxis) {
				maxWidth = Math.max(maxWidth, fontMetrics.stringWidth(formatValue(value, precision)));
			}
			
			Set<String> xValues = new HashSet<String>();
			int nextPossibleX = minPosition;
			for (int valueNr = 0; valueNr < (predefinedAxis.size() - 1); valueNr++) {
				int valueX = valueNr == 0 ? minPosition : valueToPosition(predefinedAxis.get(valueNr), minPosition, maxPosition, predefinedAxis.get(0), predefinedAxis.get(predefinedAxis.size() - 1));
				if ((valueX >= nextPossibleX) && (valueX <= (Math.max(minPosition, maxPosition) - maxWidth - AXIS_VALUE_GAP)) && xValues.add(formatValue(predefinedAxis.get(valueNr), precision))) {
					axis.add(predefinedAxis.get(valueNr));
					nextPossibleX = valueX + maxWidth + AXIS_VALUE_GAP;
				}	
			}
			if (xValues.add(formatValue(predefinedAxis.get(predefinedAxis.size() - 1), precision))) {
				axis.add(predefinedAxis.get(predefinedAxis.size() - 1));
			}
			else {
				axis.set(axis.size() - 1, predefinedAxis.get(predefinedAxis.size() - 1));
			}
		}
		else {
			int maxWidth = Math.max(fontMetrics.stringWidth(formatValue(minValue, precision)), fontMetrics.stringWidth(formatValue(maxValue, precision)));
			double step = ((double) (maxValue - minValue) / ((double) Math.abs(maxPosition - minPosition) / (double) (maxWidth + AXIS_VALUE_GAP)));
			axis = horizontalAxis(createAxis(minValue, maxValue, step), minValue, maxValue, minPosition, maxPosition, fontMetrics, precision);
		}
		return axis;
	}
	
	
	protected List<Double> verticalAxis(List<Double> predefinedAxis, double minValue, double maxValue, int minPosition, int maxPosition, FontMetrics fontMetrics, int precision) {
		List<Double> axis = new ArrayList<Double>();
		if (predefinedAxis != null) {
			Set<String> yValues = new HashSet<String>();
			int nextPossibleY = minPosition;
			for (int valueNr = 0; valueNr < (predefinedAxis.size() - 1); valueNr++) {
				int valueY = valueNr == 0 ? minPosition : valueToPosition(predefinedAxis.get(valueNr), minPosition, maxPosition, predefinedAxis.get(0), predefinedAxis.get(predefinedAxis.size() - 1));
				if ((valueY <= nextPossibleY) && (valueY >= (maxPosition + fontMetrics.getHeight() + AXIS_VALUE_GAP)) && yValues.add(formatValue(predefinedAxis.get(valueNr), precision))) {
					axis.add(predefinedAxis.get(valueNr));
					nextPossibleY = valueY - fontMetrics.getHeight() - AXIS_VALUE_GAP;
				}	
			}
			if (yValues.add(formatValue(predefinedAxis.get(predefinedAxis.size() - 1), precision))) {
				axis.add(predefinedAxis.get(predefinedAxis.size() - 1));
			}
			else {
				axis.set(axis.size() - 1, predefinedAxis.get(predefinedAxis.size() - 1));
			}
		}
		else {
			double step = ((double) (maxValue - minValue) / ((double) Math.abs(maxPosition - minPosition) / (double) (fontMetrics.getHeight() + AXIS_VALUE_GAP)));
			axis = verticalAxis(createAxis(minValue, maxValue, step), minValue, maxValue, minPosition, maxPosition, fontMetrics, precision);
		}
		return axis;
	}
	
	
	protected List<Double> createAxis(double min, double max, double step) {
		List<Double> axis = new ArrayList<Double>();
		Double point = min;
		while (point <= max) {
			axis.add(point);
			point += step;
		}
		if (!axis.contains(max)) {
			axis.add(max);
		}
		return axis;
	}
	
	
	protected Dimension showTitle(Graphics graphics, int leftBottomX, int leftBottomY, int width, int height) {
		// Returns a dimension with the new leftBottomX and the new height.
		//   width  = the new leftBottomY
		//   height = the new height
		final int EXTRA_VERTICAL_SPACE = 4;
		
		if (title != null) {
			FontMetrics fontMetrics = graphics.getFontMetrics(titleFont);

			int x = leftBottomX; // Left alignment
			if (titleAlignment == JChart.TITLE_ALIGNMENT_RIGHT) {
				x += width - fontMetrics.stringWidth(title);
			}
			else if (titleAlignment == JChart.TITLE_ALIGNMENT_CENTER) {
				x += (width / 2) - (fontMetrics.stringWidth(title) / 2);
			}
			int y = leftBottomY;
			if (titlePosition == JChart.TITLE_POSITION_TOP) {
				y = y - height + fontMetrics.getHeight();
			}
			else { // titlePosition == JChart.TITLE_POSITION_BOTTOM
				leftBottomY = leftBottomY - fontMetrics.getHeight() - EXTRA_VERTICAL_SPACE;
			}
			graphics.setFont(titleFont);
			graphics.setColor(titleColor);
			graphics.drawString(title, x, y);
			height = height - fontMetrics.getHeight() - MARGIN - EXTRA_VERTICAL_SPACE;
		}
		
		return new Dimension(leftBottomY, height);
	}
	
	
	protected int showLegendRightTop(Graphics graphics, int rightTopX, int rightTopY) {
		final int LEGEND_MARGIN =  2;
		final int ROW_GAP       =  4;
		
		// Returns the width taken by the legend.
		int width = 0;
		
		if (legendEnabled) {
			FontMetrics fontMetrics = graphics.getFontMetrics(legendFont);
			int maxLegendWidth = fontMetrics.stringWidth("Legend:");
			List<String> orderedDataSets = new ArrayList<String>();
			orderedDataSets.addAll(dataSetList);
			if (orderDataSets) {
				Collections.sort(orderedDataSets);
			}
			for (String dataSetName : orderedDataSets) {
				maxLegendWidth = Math.max(maxLegendWidth, fontMetrics.getHeight() + fontMetrics.stringWidth(" = " + dataSetName));
			}
			width = fontMetrics.getHeight() + maxLegendWidth + (2 * LEGEND_MARGIN);
			int height = (orderedDataSets.size() * (fontMetrics.getHeight() + ROW_GAP)) + fontMetrics.getHeight() + (2 * LEGEND_MARGIN);
			
			// Fill background
			graphics.setColor(legendBackGroundColor);
			graphics.fillRect(rightTopX - width, rightTopY, width, height);
			
			// Draw border
			graphics.setColor(legendBorderColor);
			graphics.drawLine(rightTopX - width, rightTopY, rightTopX, rightTopY);
			graphics.drawLine(rightTopX, rightTopY, rightTopX, rightTopY + height);
			graphics.drawLine(rightTopX, rightTopY + height, rightTopX - width, rightTopY + height);
			graphics.drawLine(rightTopX - width, rightTopY + height, rightTopX - width, rightTopY);
			
			graphics.setFont(legendFont);
			graphics.setColor(legendFontColor);
			int legendX = rightTopX - width + LEGEND_MARGIN;
			int legendY = rightTopY + LEGEND_MARGIN + fontMetrics.getHeight();
			graphics.drawString("Legend:", legendX, legendY);
			legendY += ROW_GAP + fontMetrics.getHeight();
			for (String dataSetName : orderedDataSets) {
				graphics.setColor(dataSets.get(dataSetName));
				graphics.fillRect(legendX, legendY - fontMetrics.getHeight() + 2, fontMetrics.getHeight(), fontMetrics.getHeight());
				graphics.setColor(Color.BLACK);
				graphics.drawRect(legendX, legendY - fontMetrics.getHeight() + 2, fontMetrics.getHeight(), fontMetrics.getHeight());
				graphics.setColor(legendFontColor);
				graphics.drawString(" = " + dataSetName, legendX + fontMetrics.getHeight(), legendY);
				legendY += ROW_GAP + fontMetrics.getHeight();
			}
			// Add margin between the legend and the plot
			width = width + MARGIN;
		}
		
		return width;
	}
	
	
	protected int showLegendBottom(Graphics graphics, int leftBottomX, int leftBottomY, int width, boolean showBlock) {
		// Returns the height taken by the legend.
		final int LEGEND_MARGIN =  2;
		final int COLUMN_GAP    =  8;
		final int ROW_GAP       =  4;
		final int LINE_LENGTH   = 11; // Should be odd
		
		int height = 0;
		if (legendEnabled) {
			FontMetrics fontMetrics = graphics.getFontMetrics(legendFont);
			int maxLegendWidth = 0;
			List<String> orderedDataSets = new ArrayList<String>();
			orderedDataSets.addAll(dataSetList);
			if (orderDataSets) {
				Collections.sort(orderedDataSets);
			}
			for (String dataSetName : orderedDataSets) {
				maxLegendWidth = Math.max(maxLegendWidth, fontMetrics.stringWidth(" = " + dataSetName));
			}
			// Add line length
			maxLegendWidth += LINE_LENGTH;
			String legendLabel = "Legend:";
			int legendLabelWidth = fontMetrics.stringWidth(legendLabel);
			int legendWidth = width - 6 - (2 * LEGEND_MARGIN) - legendLabelWidth - COLUMN_GAP;
			int columns = legendWidth / (maxLegendWidth + COLUMN_GAP);
			int rows = (int) Math.ceil((double) orderedDataSets.size() / (double) columns);
			height = 2 + (2 * LEGEND_MARGIN) + (rows * (ROW_GAP + fontMetrics.getHeight()));
			
			// Fill background
			graphics.setColor(legendBackGroundColor);
			graphics.fillRect(leftBottomX, leftBottomY - (height - ROW_GAP), width, (height - ROW_GAP));
			
			// Draw border
			graphics.setColor(legendBorderColor);
			graphics.drawLine(leftBottomX + 2, leftBottomY - 2, leftBottomX + 2, leftBottomY - height + ROW_GAP);
			graphics.drawLine(leftBottomX + 2, leftBottomY - height + ROW_GAP, leftBottomX + width - 2, leftBottomY - height + ROW_GAP);
			graphics.drawLine(leftBottomX + width - 2, leftBottomY - height + ROW_GAP, leftBottomX + width - 2, leftBottomY - 2);
			graphics.drawLine(leftBottomX + width - 2, leftBottomY - 2, leftBottomX + 2, leftBottomY - 2);
			
			// Write legend
			int x = leftBottomX + 3 + LEGEND_MARGIN;
			int y = leftBottomY - 4 - LEGEND_MARGIN - ((rows - 1) * (ROW_GAP + fontMetrics.getHeight()));
			graphics.setFont(legendFont);
			graphics.setColor(legendFontColor);
			graphics.drawString(legendLabel, x, y);
			x = x + legendLabelWidth + COLUMN_GAP;
			int columnNr = 0;
			for (String dataSetName : orderedDataSets) {
				graphics.setColor(dataSets.get(dataSetName));
				if (showBlock) {
					graphics.fillRect(x, y - fontMetrics.getHeight() + 2, LINE_LENGTH, fontMetrics.getHeight());
					graphics.setColor(Color.BLACK);
					graphics.drawRect(x, y - fontMetrics.getHeight() + 2, LINE_LENGTH, fontMetrics.getHeight());
				}
				else {
					graphics.drawLine(x, y - (fontMetrics.getHeight() / 2) + 2, x + LINE_LENGTH, y - (fontMetrics.getHeight() / 2) + 2);
					drawPoint(graphics, x + ((LINE_LENGTH - 1) / 2) + 1, y - (fontMetrics.getHeight() / 2) + 2, getLineStyle(dataSetName));
				}
				graphics.setColor(legendFontColor);
				graphics.drawString(" = " + dataSetName, x + LINE_LENGTH, y);
				columnNr++;
				if (columnNr == columns) {
					x = leftBottomX + 3 + LEGEND_MARGIN + legendLabelWidth + COLUMN_GAP;
					y = y + ROW_GAP + fontMetrics.getHeight();
					columnNr = 0;
				}
				else {
					x = x + maxLegendWidth + COLUMN_GAP;
				}
			}
		}
		
		return height;
	}
	
	
	protected void drawPoint(Graphics graphics, int x, int y, int style) {
		if (style != JLineChart.POINT_STYLE_NONE) {
			if (style == JLineChart.POINT_STYLE_DOT) {
				graphics.drawLine(x - 1, y - 2, x + 1, y - 2);
				graphics.drawLine(x - 2, y - 1, x + 2, y - 1);
				graphics.drawLine(x - 2, y, x + 2, y);
				graphics.drawLine(x - 2, y + 1, x + 2, y + 1);
				graphics.drawLine(x - 1, y + 2, x + 1, y + 2);
			}
			else if (style == JLineChart.POINT_STYLE_SQUARE) {
				graphics.fillRect(x - 2, y - 2, 5, 5);
			}
			else if (style == JLineChart.POINT_STYLE_DIAMOND) {
				graphics.drawLine(x, y - 2, x, y - 2);
				graphics.drawLine(x - 1, y - 1, x + 1, y - 1);
				graphics.drawLine(x - 2, y, x + 1, y);
				graphics.drawLine(x - 1, y + 1, x + 1, y + 1);
				graphics.drawLine(x, y + 2, x, y + 2);
			}
		}
	}
	
	
	protected int getLineStyle(String dataSetName) {
		Integer lineStyle = pointStyles == null ? null : pointStyles.get(dataSetName);
		return lineStyle == null ? JLineChart.POINT_STYLE_NONE : lineStyle;
	}
	
	
	protected void showError(Graphics graphics, String error) {
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, getWidth(), getHeight());
		graphics.setColor(Color.RED);
		int fontSize = 20;
		Font errorFont = new Font("Arial", Font.PLAIN, fontSize);
		FontMetrics fontMetrics = graphics.getFontMetrics(errorFont);
		while ((fontSize > 6) && ((fontMetrics.stringWidth("ERROR") > (getWidth() - (2 * MARGIN))) || (fontMetrics.getHeight() > (getHeight() - (2 * MARGIN))))) {
			fontSize--;
			errorFont = new Font("Arial", Font.PLAIN, fontSize);
			fontMetrics = graphics.getFontMetrics(errorFont);
		}
		graphics.setFont(errorFont);
		graphics.drawString("ERROR", (getWidth() / 2) - (fontMetrics.stringWidth("ERROR") / 2), (getHeight()/ 2) - (fontMetrics.getHeight() / 2));
		setToolTipText(error);
	}
	
	
	protected void floodFill(JPanel panel, BufferedImage panelImage, int seedX, int seedY, Color color, Color boundaryColor, int style) {
		final int X = 0;
		final int Y = 1;

		int boundaryRGB = boundaryColor.getRGB();

		// Set color
		panel.getGraphics().setColor(color);

		if (panelImage.getRGB(seedX, seedY) != boundaryRGB) {
			// Create stack
			List<int[]> stack = new ArrayList<int[]>();
			// Add seed point to stack
			stack.add(new int[] { seedX, seedY });
			
			// Loop until stack is empty
			while (stack.size() > 0) {
				int[] point = stack.get(0);
				panel.getGraphics().drawLine(point[X], point[Y], point[X], point[Y]);
				stack.remove(0);
				// style == FLOODFILL_STYLE_4_WAY or FLOODFILL_STYLE_8_WAY
				if (panelImage.getRGB(point[X] - 1, point[Y]    ) != boundaryRGB) stack.add(new int[] { point[X] - 1, point[Y]     });
				if (panelImage.getRGB(point[X] + 1, point[Y]    ) != boundaryRGB) stack.add(new int[] { point[X] + 1, point[Y]     });
				if (panelImage.getRGB(point[X]    , point[Y] - 1) != boundaryRGB) stack.add(new int[] { point[X]    , point[Y] - 1 });
				if (panelImage.getRGB(point[X    ], point[Y] - 1) != boundaryRGB) stack.add(new int[] { point[X]    , point[Y] - 1 });
				if (style == FLOODFILL_STYLE_8_WAY) {
					if (panelImage.getRGB(point[X] - 1, point[Y] - 1) != boundaryRGB) stack.add(new int[] { point[X] - 1, point[Y] - 1 });
					if (panelImage.getRGB(point[X] + 1, point[Y] - 1) != boundaryRGB) stack.add(new int[] { point[X] + 1, point[Y] - 1 });
					if (panelImage.getRGB(point[X] - 1, point[Y] + 1) != boundaryRGB) stack.add(new int[] { point[X] - 1, point[Y] + 1 });
					if (panelImage.getRGB(point[X] + 1, point[Y] + 1) != boundaryRGB) stack.add(new int[] { point[X] + 1, point[Y] + 1 });
				}
			}
		}
		
	}
	
	
	abstract void draw(Graphics graphics);
	
	
	public void paint(Graphics graphics) {
		super.paint(graphics);
		setToolTipText(null);
		if ((getWidth() > 20) && (getHeight() > 20)) {
			draw(graphics);
		}
	}
	
	
	private JPopupMenu createPopUpMenu() {
		JPopupMenu popUpMenu = new JPopupMenu();
		
		JMenuItem saveAsMenuItem = new JMenuItem("Save As...");
		saveAsMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				saveAsImage();
			}
		});
		popUpMenu.add(saveAsMenuItem);
		
		return popUpMenu;
	}
	
	
	private void saveAsImage() {
		JFileChooser pngChooser = new JFileChooser();
		pngChooser.setDialogTitle("Save " + name);
		pngChooser.addChoosableFileFilter(new PNGFileFilter());
		pngChooser.setAcceptAllFileFilterUsed(false);
		int result = pngChooser.showSaveDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			String pngFileName = pngChooser.getSelectedFile().getAbsolutePath();
			if (!pngFileName.substring(pngFileName.length() - 4).toLowerCase().equals(".png")) {
				pngFileName += ".png";
			}
			BufferedImage image = new BufferedImage(this.getWidth()	, this.getHeight(), BufferedImage.TYPE_INT_ARGB);
			paint(image.getGraphics());
			try {
				ImageIO.write(image, "PNG", new File(pngFileName));
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Error writing imagefile.", "Chart '" + name + "' Save Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	
	public class PNGFileFilter extends FileFilter {

		@Override
		public boolean accept(File file) {
			return (file.isDirectory() || file.getAbsolutePath().toLowerCase().endsWith(".png"));
		}

		@Override
		public String getDescription() {
			return "Portable Network Graphics (.png)";
		}
		
	}
}
