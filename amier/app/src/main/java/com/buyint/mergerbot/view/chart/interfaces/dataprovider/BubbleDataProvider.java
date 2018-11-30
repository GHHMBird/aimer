package com.buyint.mergerbot.view.chart.interfaces.dataprovider;

import com.buyint.mergerbot.view.chart.data.BubbleData;

public interface BubbleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    BubbleData getBubbleData();
}
