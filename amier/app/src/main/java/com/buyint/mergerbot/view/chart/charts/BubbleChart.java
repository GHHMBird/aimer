
package com.buyint.mergerbot.view.chart.charts;

import android.content.Context;
import android.util.AttributeSet;

import com.buyint.mergerbot.view.chart.data.BubbleData;
import com.buyint.mergerbot.view.chart.interfaces.dataprovider.BubbleDataProvider;
import com.buyint.mergerbot.view.chart.interfaces.datasets.IBubbleDataSet;
import com.buyint.mergerbot.view.chart.renderer.BubbleChartRenderer;

/**
 * The BubbleChart. Draws bubbles. Bubble chart implementation: Copyright 2015
 * Pierre-Marc Airoldi Licensed under Apache License 2.0. In the BubbleChart, it
 * is the area of the bubble, not the radius or diameter of the bubble that
 * conveys the data.
 *
 * @author Philipp Jahoda
 */
public class BubbleChart extends BarLineChartBase<BubbleData> implements BubbleDataProvider {

    public BubbleChart(Context context) {
        super(context);
    }

    public BubbleChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BubbleChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init() {
        super.init();

        mRenderer = new BubbleChartRenderer(this, mAnimator, mViewPortHandler);
    }

    @Override
    protected void calcMinMax() {
        super.calcMinMax();

        if (mXAxis.mAxisRange == 0 && mData.getYValCount() > 0)
            mXAxis.mAxisRange = 1;

        mXAxis.mAxisMinimum = -0.5f;
        mXAxis.mAxisMaximum = (float) mData.getXValCount() - 0.5f;

        if (mRenderer != null) {
            for (IBubbleDataSet set : mData.getDataSets()) {

                final float xmin = set.getXMin();
                final float xmax = set.getXMax();

                if (xmin < mXAxis.mAxisMinimum)
                    mXAxis.mAxisMinimum = xmin;

                if (xmax > mXAxis.mAxisMaximum)
                    mXAxis.mAxisMaximum = xmax;
            }
        }

        mXAxis.mAxisRange = Math.abs(mXAxis.mAxisMaximum - mXAxis.mAxisMinimum);
    }

    public BubbleData getBubbleData() {
        return mData;
    }
}
