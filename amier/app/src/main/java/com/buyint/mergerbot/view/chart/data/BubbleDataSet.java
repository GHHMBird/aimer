
package com.buyint.mergerbot.view.chart.data;

import com.buyint.mergerbot.view.chart.interfaces.datasets.IBubbleDataSet;
import com.buyint.mergerbot.view.chart.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class BubbleDataSet extends BarLineScatterCandleBubbleDataSet<BubbleEntry> implements IBubbleDataSet {

    // NOTE: Do not initialize these, as the calculate is called by the super,
    // and the initializers are called after that and can reset the values
    protected float mXMax;
    protected float mXMin;
    protected float mMaxSize;
    protected boolean mNormalizeSize = true;

    private float mHighlightCircleWidth = 2.5f;

    public BubbleDataSet(List<BubbleEntry> yVals, String label) {
        super(yVals, label);
    }

    @Override
    public void setHighlightCircleWidth(float width) {
        mHighlightCircleWidth = Utils.convertDpToPixel(width);
    }

    @Override
    public float getHighlightCircleWidth() {
        return mHighlightCircleWidth;
    }

    @Override
    public void calcMinMax(int start, int end) {

        if (mYVals == null)
            return;

        if (mYVals.size() == 0)
            return;

        int endValue;

        if (end == 0 || end >= mYVals.size())
            endValue = mYVals.size() - 1;
        else
            endValue = end;

        mYMin = yMin(mYVals.get(start));
        mYMax = yMax(mYVals.get(start));

        // need chart width to guess this properly

        for (int i = start; i <= endValue; i++) {

            final BubbleEntry entry = mYVals.get(i);

            float ymin = yMin(entry);
            float ymax = yMax(entry);

            if (ymin < mYMin) {
                mYMin = ymin;
            }

            if (ymax > mYMax) {
                mYMax = ymax;
            }

            final float xmin = xMin(entry);
            final float xmax = xMax(entry);

            if (xmin < mXMin) {
                mXMin = xmin;
            }

            if (xmax > mXMax) {
                mXMax = xmax;
            }

            final float size = largestSize(entry);

            if (size > mMaxSize) {
                mMaxSize = size;
            }
        }
    }

    @Override
    public DataSet<BubbleEntry> copy() {

        List<BubbleEntry> yVals = new ArrayList<BubbleEntry>();

        for (int i = 0; i < mYVals.size(); i++) {
            yVals.add(mYVals.get(i).copy());
        }

        BubbleDataSet copied = new BubbleDataSet(yVals, getLabel());
        copied.mColors = mColors;
        copied.mHighLightColor = mHighLightColor;

        return copied;
    }

    @Override
    public float getXMax() {
        return mXMax;
    }

    @Override
    public float getXMin() {
        return mXMin;
    }

    @Override
    public float getMaxSize() {
        return mMaxSize;
    }

    @Override
    public boolean isNormalizeSizeEnabled() {
        return mNormalizeSize;
    }

    public void setNormalizeSizeEnabled(boolean normalizeSize) {
        mNormalizeSize = normalizeSize;
    }

    private float yMin(BubbleEntry entry) {
        return entry.getVal();
    }

    private float yMax(BubbleEntry entry) {
        return entry.getVal();
    }

    private float xMin(BubbleEntry entry) {
        return (float) entry.getXIndex();
    }

    private float xMax(BubbleEntry entry) {
        return (float) entry.getXIndex();
    }

    private float largestSize(BubbleEntry entry) {
        return entry.getSize();
    }
}
