
package com.buyint.mergerbot.view.chart.data;

import com.buyint.mergerbot.view.chart.charts.ScatterChart;
import com.buyint.mergerbot.view.chart.interfaces.datasets.IScatterDataSet;
import com.buyint.mergerbot.view.chart.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class ScatterDataSet extends LineScatterCandleRadarDataSet<Entry> implements IScatterDataSet {

    /**
     * the size the scattershape will have, in density pixels
     */
    private float mShapeSize = 15f;

    /**
     * the type of shape that is set to be drawn where the values are at,
     * default ScatterShape.SQUARE
     */
    private String mScatterShape = ScatterChart.ScatterShape.SQUARE.toString();

    /**
     * The radius of the hole in the shape (applies to Square, Circle and Triangle)
     * - default: 0.0
     */
    private float mScatterShapeHoleRadius = 0f;

    /**
     * Color for the hole in the shape.
     * Setting to `ColorTemplate.COLOR_NONE` will behave as transparent.
     * - default: ColorTemplate.COLOR_NONE
     */
    private int mScatterShapeHoleColor = ColorTemplate.COLOR_NONE;

    /**
     * Custom path object the user can provide that is drawn where the values
     * are at. This is used when ScatterShape.CUSTOM is set for a DataSet.
     */
    //private Path mCustomScatterPath = null;
    public ScatterDataSet(List<Entry> yVals, String label) {
        super(yVals, label);
    }

    @Override
    public DataSet<Entry> copy() {

        List<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < mYVals.size(); i++) {
            yVals.add(mYVals.get(i).copy());
        }

        ScatterDataSet copied = new ScatterDataSet(yVals, getLabel());
        copied.mColors = mColors;
        copied.mShapeSize = mShapeSize;
        copied.mScatterShape = mScatterShape;
        copied.mScatterShapeHoleRadius = mScatterShapeHoleRadius;
        copied.mScatterShapeHoleColor = mScatterShapeHoleColor;
        //copied.mCustomScatterPath = mCustomScatterPath;
        copied.mHighLightColor = mHighLightColor;

        return copied;
    }

    /**
     * Sets the size in density pixels the drawn scattershape will have. This
     * only applies for non custom shapes.
     *
     * @param size
     */
    public void setScatterShapeSize(float size) {
        mShapeSize = size;
    }

    @Override
    public float getScatterShapeSize() {
        return mShapeSize;
    }


    /**
     * Sets the shapeIdentifier that this DataSet should be drawn with.
     * Make sure the ScatterChart has a renderer capable of rendering the provided identifier.
     *
     * @param shape
     */
    public void setScatterShape(ScatterChart.ScatterShape shape) {
        mScatterShape = shape.toString();
    }

    /**
     * Sets the shapeIdentifier that this DataSet should be drawn with.
     * Make sure the ScatterChart has a renderer capable of rendering the provided identifier.
     *
     * @param shapeIdentifier
     */
    public void setScatterShape(String shapeIdentifier) {
        mScatterShape = shapeIdentifier;
    }

    @Override
    public String getScatterShape() {
        return mScatterShape;
    }

    /**
     * Sets the radius of the hole in the shape (applies to Square, Circle and Triangle)
     * Set this to <= 0 to remove holes.
     *
     * @param holeRadius
     */
    public void setScatterShapeHoleRadius(float holeRadius) {
        mScatterShapeHoleRadius = holeRadius;
    }

    @Override
    public float getScatterShapeHoleRadius() {
        return mScatterShapeHoleRadius;
    }

    /**
     * Sets the color for the hole in the shape.
     *
     * @param holeColor
     */
    public void setScatterShapeHoleColor(int holeColor) {
        mScatterShapeHoleColor = holeColor;
    }

    @Override
    public int getScatterShapeHoleColor() {
        return mScatterShapeHoleColor;
    }
}
