
package com.buyint.mergerbot.view.chart.charts;

import android.content.Context;
import android.util.AttributeSet;

import com.buyint.mergerbot.view.chart.data.BarData;
import com.buyint.mergerbot.view.chart.data.BubbleData;
import com.buyint.mergerbot.view.chart.data.CandleData;
import com.buyint.mergerbot.view.chart.data.CombinedData;
import com.buyint.mergerbot.view.chart.data.LineData;
import com.buyint.mergerbot.view.chart.data.ScatterData;
import com.buyint.mergerbot.view.chart.highlight.CombinedHighlighter;
import com.buyint.mergerbot.view.chart.interfaces.dataprovider.BarDataProvider;
import com.buyint.mergerbot.view.chart.interfaces.dataprovider.BubbleDataProvider;
import com.buyint.mergerbot.view.chart.interfaces.dataprovider.CandleDataProvider;
import com.buyint.mergerbot.view.chart.interfaces.dataprovider.LineDataProvider;
import com.buyint.mergerbot.view.chart.interfaces.dataprovider.ScatterDataProvider;
import com.buyint.mergerbot.view.chart.interfaces.datasets.IBubbleDataSet;
import com.buyint.mergerbot.view.chart.renderer.CombinedChartRenderer;
import com.buyint.mergerbot.view.chart.renderer.scatter.ShapeRenderer;

/**
 * This chart class allows the combination of lines, bars, scatter and candle
 * data all displayed in one chart area.
 *
 * @author Philipp Jahoda
 */
public class CombinedChart extends BarLineChartBase<CombinedData> implements LineDataProvider,
        BarDataProvider, ScatterDataProvider, CandleDataProvider, BubbleDataProvider {

    private com.buyint.mergerbot.view.chart.charts.ScatterChart.ShapeRendererHandler mShapeRendererHandler;

    /**
     * flag that enables or disables the highlighting arrow
     */
    private boolean mDrawHighlightArrow = false;

    /**
     * if set to true, all values are drawn above their bars, instead of below
     * their top
     */
    private boolean mDrawValueAboveBar = true;

    /**
     * if set to true, a grey area is drawn behind each bar that indicates the
     * maximum value
     */
    private boolean mDrawBarShadow = false;

    protected DrawOrder[] mDrawOrder = new DrawOrder[]{
            DrawOrder.BAR, DrawOrder.BUBBLE, DrawOrder.LINE, DrawOrder.CANDLE, DrawOrder.SCATTER
    };

    /**
     * enum that allows to specify the order in which the different data objects
     * for the combined-chart are drawn
     */
    public enum DrawOrder {
        BAR, BUBBLE, LINE, CANDLE, SCATTER
    }

    public CombinedChart(Context context) {
        super(context);
    }

    public CombinedChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CombinedChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init() {
        super.init();

        mShapeRendererHandler = new com.buyint.mergerbot.view.chart.charts.ScatterChart.ShapeRendererHandler();

        setHighlighter(new CombinedHighlighter(this));

        // Old default behaviour
        setHighlightFullBarEnabled(true);

        // mRenderer = new CombinedChartRenderer(this, mAnimator,
        // mViewPortHandler);
    }

    @Override
    protected void calcMinMax() {
        super.calcMinMax();

        if (getBarData() != null || getCandleData() != null || getBubbleData() != null) {
            mXAxis.mAxisMinimum = -0.5f;
            mXAxis.mAxisMaximum = mData.getXVals().size() - 0.5f;

            if (getBubbleData() != null) {

                for (IBubbleDataSet set : getBubbleData().getDataSets()) {

                    final float xmin = set.getXMin();
                    final float xmax = set.getXMax();

                    if (xmin < mXAxis.mAxisMinimum)
                        mXAxis.mAxisMinimum = xmin;

                    if (xmax > mXAxis.mAxisMaximum)
                        mXAxis.mAxisMaximum = xmax;
                }
            }
        }

        mXAxis.mAxisRange = Math.abs(mXAxis.mAxisMaximum - mXAxis.mAxisMinimum);

        if (mXAxis.mAxisRange == 0.f && getLineData() != null && getLineData().getYValCount() > 0) {
            mXAxis.mAxisRange = 1.f;
        }
    }

    @Override
    public void setData(CombinedData data) {
        mData = null;
        mRenderer = null;
        super.setData(data);
        mRenderer = new CombinedChartRenderer(this, mAnimator, mViewPortHandler);
        mRenderer.initBuffers();
    }

    @Override
    public LineData getLineData() {
        if (mData == null)
            return null;
        return mData.getLineData();
    }

    @Override
    public BarData getBarData() {
        if (mData == null)
            return null;
        return mData.getBarData();
    }

    @Override
    public ScatterData getScatterData() {
        if (mData == null)
            return null;
        return mData.getScatterData();
    }

    @Override
    public CandleData getCandleData() {
        if (mData == null)
            return null;
        return mData.getCandleData();
    }

    @Override
    public BubbleData getBubbleData() {
        if (mData == null)
            return null;
        return mData.getBubbleData();
    }

    @Override
    public void addShapeRenderer(ShapeRenderer shapeRenderer, String shapeIdentifier) {
        mShapeRendererHandler.addShapeRenderer(shapeRenderer, shapeIdentifier);
    }

    @Override
    public ShapeRenderer getShapeRenderer(String shapeIdentifier) {
        return mShapeRendererHandler.getShapeRenderer(shapeIdentifier);
    }

    @Override
    public boolean isDrawBarShadowEnabled() {
        return mDrawBarShadow;
    }

    @Override
    public boolean isDrawValueAboveBarEnabled() {
        return mDrawValueAboveBar;
    }

    @Override
    public boolean isDrawHighlightArrowEnabled() {
        return mDrawHighlightArrow;
    }

    /**
     * set this to true to draw the highlightning arrow
     *
     * @param enabled
     */
    public void setDrawHighlightArrow(boolean enabled) {
        mDrawHighlightArrow = enabled;
    }

    /**
     * If set to true, all values are drawn above their bars, instead of below
     * their top.
     *
     * @param enabled
     */
    public void setDrawValueAboveBar(boolean enabled) {
        mDrawValueAboveBar = enabled;
    }


    /**
     * If set to true, a grey area is drawn behind each bar that indicates the
     * maximum value. Enabling his will reduce performance by about 50%.
     *
     * @param enabled
     */
    public void setDrawBarShadow(boolean enabled) {
        mDrawBarShadow = enabled;
    }

    /**
     * Returns the currently set draw order.
     *
     * @return
     */
    public DrawOrder[] getDrawOrder() {
        return mDrawOrder;
    }

    /**
     * Sets the order in which the provided data objects should be drawn. The
     * earlier you place them in the provided array, the further they will be in
     * the background. e.g. if you provide new DrawOrer[] { DrawOrder.BAR,
     * DrawOrder.LINE }, the bars will be drawn behind the lines.
     *
     * @param order
     */
    public void setDrawOrder(DrawOrder[] order) {
        if (order == null || order.length <= 0)
            return;
        mDrawOrder = order;
    }
}
