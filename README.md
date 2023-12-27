<!--
 /********************************************************************************
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
 ********************************************************************************/
 -->

# JChart
This is a Java chart library that provides a JChart class that extends JPanel.<br>
It supports the following types of charts:

- Line charts (sub class JLineChart)
- Bar charts (sub class JBarChart)
- Pie charts (sub class JPieChart)
- Scatter charts (sub class JScatterChart)
- Box charts (subclass JBoxChart)

The basic principle is that you first create an instance of the preferred chart class.<br>
After that you add the data sets with the method chartAddDataSet and then the data points with chartAddDataPoint.<br>
There are also several methods to set the style and colors of the chart.<br>
By right clicking on a chart you can save it as a .png image.<br>
The JavaDocs are included in the doc folder in the .jar file.<br>
<br>
<br>
**Examples**

<img src="JChart Examples.png" alt="JChart Examples" title="JChart Examples" />

The library also contains a JChartDemo class. When you create an instance of this class
it creates a number of windows with all kinds of examples.