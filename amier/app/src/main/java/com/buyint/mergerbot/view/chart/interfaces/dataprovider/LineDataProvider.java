package com.buyint.mergerbot.view.chart.interfaces.dataprovider;

import com.buyint.mergerbot.view.chart.components.YAxis;
import com.buyint.mergerbot.view.chart.data.LineData;

public interface LineDataProvider extends BarLineScatterCandleBubbleDataProvider {

    LineData getLineData();

    YAxis getAxis(YAxis.AxisDependency dependency);
}
