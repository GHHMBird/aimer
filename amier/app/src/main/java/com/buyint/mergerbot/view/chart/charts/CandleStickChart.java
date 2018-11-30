
package com.buyint.mergerbot.view.chart.charts;

import android.content.Context;
import android.util.AttributeSet;

import com.buyint.mergerbot.view.chart.interfaces.dataprovider.CandleDataProvider;
import com.buyint.mergerbot.view.chart.renderer.CandleStickChartRenderer;
import com.buyint.mergerbot.view.chart.charts.BarLineChartBase;
import com.buyint.mergerbot.view.chart.data.CandleData;

/**
 * Financial chart type that draws candle-sticks (OHCL chart).
 * 
 * @author Philipp Jahoda
 */
public class CandleStickChart extends BarLineChartBase<CandleData> implements CandleDataProvider {

    public CandleStickChart(Context context) {
        super(context);
    }

    public CandleStickChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CandleStickChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init() {
        super.init();

        mRenderer = new CandleStickChartRenderer(this, mAnimator, mViewPortHandler);
        mXAxis.mAxisMinimum = -0.5f;
    }

    @Override
    protected void calcMinMax() {
        super.calcMinMax();

        mXAxis.mAxisMaximum += 0.5f;
        mXAxis.mAxisRange = Math.abs(mXAxis.mAxisMaximum - mXAxis.mAxisMinimum);
    }

    @Override
    public CandleData getCandleData() {
        return mData;
    }
}
