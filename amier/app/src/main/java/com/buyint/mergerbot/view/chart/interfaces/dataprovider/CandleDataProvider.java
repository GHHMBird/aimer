package com.buyint.mergerbot.view.chart.interfaces.dataprovider;

import com.buyint.mergerbot.view.chart.data.CandleData;

public interface CandleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    CandleData getCandleData();
}
