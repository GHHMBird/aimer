package com.buyint.mergerbot.view.chart.renderer.scatter;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.buyint.mergerbot.view.chart.buffer.ScatterBuffer;
import com.buyint.mergerbot.view.chart.interfaces.datasets.IScatterDataSet;
import com.buyint.mergerbot.view.chart.utils.ViewPortHandler;

/**
 * Created by wajdic on 15/06/2016.
 * Created at Time 09:07
 */
public interface ShapeRenderer {

    /**
     * Renders the provided ScatterDataSet with a shape.
     *
     * @param c
     * @param dataSet
     * @param viewPortHandler
     * @param buffer
     * @param renderPaint
     * @param shapeSize
     */
    void renderShape(Canvas c, IScatterDataSet dataSet, ViewPortHandler viewPortHandler, ScatterBuffer buffer, Paint
            renderPaint, final float shapeSize);
}
