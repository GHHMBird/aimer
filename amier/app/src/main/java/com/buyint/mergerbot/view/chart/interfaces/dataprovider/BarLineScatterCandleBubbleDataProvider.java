package com.buyint.mergerbot.view.chart.interfaces.dataprovider;

import com.buyint.mergerbot.view.chart.components.YAxis.AxisDependency;
import com.buyint.mergerbot.view.chart.data.BarLineScatterCandleBubbleData;
import com.buyint.mergerbot.view.chart.utils.Transformer;

public interface BarLineScatterCandleBubbleDataProvider extends ChartInterface {

    Transformer getTransformer(AxisDependency axis);
    int getMaxVisibleCount();
    boolean isInverted(AxisDependency axis);
    
    int getLowestVisibleXIndex();
    int getHighestVisibleXIndex();

    BarLineScatterCandleBubbleData getData();
}
