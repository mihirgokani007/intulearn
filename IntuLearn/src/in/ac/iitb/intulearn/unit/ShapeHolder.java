package in.ac.iitb.intulearn.unit;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;

/**
 * A data structure that holds a Shape and various properties that can be used
 * to define how the shapeDrawable is drawn.
 */
public class ShapeHolder {
    public enum LabelAlignment {
        TOP, MIDDLE, BOTTOM, TOP_OUTSIDE, BOTTOM_OUTSIDE
    }

    final private ShapeDrawable shapeDrawable, shapeDrawableStroke;
    final private Rect drawLabelBounds = new Rect();

    private float x = 0, y = 0;
    private String label;
    private Paint labelPaint = new Paint();
    private Paint labelBackground = null;
    private LabelAlignment labelAlignment = LabelAlignment.MIDDLE;

    /**********************************
     ** Bounds (x, y, width, height) **
     **********************************/

    public void setX(float value) {
        x = value;
    }

    public float getX() {
        return x;
    }

    public void setY(float value) {
        y = value;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        Shape shape = shapeDrawable.getShape();
        return shape.getWidth();
    }

    public void setWidth(float width) {
        Shape shape = shapeDrawable.getShape();
        shape.resize(width, shape.getHeight());
        Shape shapeStroke = shapeDrawableStroke.getShape();
        shapeStroke.resize(width, shapeStroke.getHeight());
    }

    public float getHeight() {
        Shape shape = shapeDrawable.getShape();
        return shape.getHeight();
    }

    public void setHeight(float height) {
        Shape shape = shapeDrawable.getShape();
        shape.resize(shape.getWidth(), height);
        Shape shapeStroke = shapeDrawableStroke.getShape();
        shapeStroke.resize(shapeStroke.getWidth(), height);
    }

    /********************************
     ** Misc (paint, color, alpha) **
     ********************************/

    public Paint getPaint() {
        return shapeDrawable.getPaint();
    }

    public void setPaint(Paint value) {
        shapeDrawable.getPaint().set(value);
    }

    public int getColor() {
        Paint paint = shapeDrawable.getPaint();
        return paint.getColor();
    }

    public void setColor(int value) {
        Paint paint = shapeDrawable.getPaint();
        paint.setColor(value);
    }

    public int getAlpha() {
        Paint paint = shapeDrawable.getPaint();
        return paint.getAlpha();
    }

    public void setAlpha(int alpha) {
        Paint paint = shapeDrawable.getPaint();
        paint.setAlpha(alpha);
    }

    /************
     ** Stroke **
     ************/

    public Paint getStrokePaint() {
        return shapeDrawableStroke.getPaint();
    }

    public void setStrokePaint(Paint value) {
        shapeDrawableStroke.getPaint().set(value);
    }

    public int getStrokeColor() {
        Paint labelPaint = shapeDrawableStroke.getPaint();
        return labelPaint.getColor();
    }

    public void setStrokeColor(int value) {
        Paint labelPaint = shapeDrawableStroke.getPaint();
        labelPaint.setColor(value);
    }

    public int getStrokeAlpha() {
        Paint labelPaint = shapeDrawableStroke.getPaint();
        return labelPaint.getAlpha();
    }

    public void setStrokeAlpha(int alpha) {
        Paint labelPaint = shapeDrawableStroke.getPaint();
        labelPaint.setAlpha(alpha);
    }

    /***********
     ** Label **
     ***********/

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabelPaint(Paint value) {
        labelPaint.set(value);
    }

    public Paint getLabelPaint() {
        return labelPaint;
    }

    public void setLabelColor(int value) {
        labelPaint.setColor(value);
    }

    public int getLabelColor() {
        return labelPaint.getColor();
    }

    public void setLabelAlpha(int alpha) {
        labelPaint.setAlpha(alpha);
    }

    public int getLabelAlpha() {
        return labelPaint.getAlpha();
    }

    public void setLabelBackground(Paint value) {
        if (labelBackground == null) labelBackground = new Paint(value);
        else labelBackground.set(value);
    }

    public Paint getLabelBackground() {
        return labelBackground;
    }

    public void setLabelAlign(Paint.Align align) {
        labelPaint.setTextAlign(align);
    }

    public Paint.Align getLabelAlign() {
        return labelPaint.getTextAlign();
    }

    public void setLabelVerticalAlign(LabelAlignment align) {
        labelAlignment = align;
    }

    public LabelAlignment getLabelVerticalAlign() {
        return labelAlignment;
    }

    /******************
     ** Constructors **
     ******************/

    public ShapeHolder(Shape s, Shape sStroke, String label) {
        shapeDrawable = new ShapeDrawable(s);
        shapeDrawableStroke = new ShapeDrawable(sStroke);
        this.label = label;
    }

    public void draw(Canvas canvas) {
        float x = getX(), y = getY();
        float width = getWidth(), height = getHeight();

        String label = getLabel();
        Paint labelPaint = getLabelPaint();

        labelPaint.getTextBounds(label, 0, label.length(), drawLabelBounds);

        float vCenterOffset, hOffset;
        switch (labelAlignment) {
            case TOP:
                vCenterOffset = (0 - drawLabelBounds.top);
                break;
            default:
            case MIDDLE:
                vCenterOffset = (height + (drawLabelBounds.bottom - drawLabelBounds.top)) / 2;
                break;
            case BOTTOM:
                vCenterOffset = (height - (drawLabelBounds.bottom - drawLabelBounds.top));
                break;
            case TOP_OUTSIDE:
                vCenterOffset = (0 - (drawLabelBounds.bottom - drawLabelBounds.top));
                break;
            case BOTTOM_OUTSIDE:
                vCenterOffset = (height - drawLabelBounds.top);
                break;
        }

        switch (labelPaint.getTextAlign()) {
            case LEFT:
                hOffset = 0;
                break;
            default:
            case CENTER:
                hOffset = width / 2;
                break;
            case RIGHT:
                hOffset = width;
                break;
        }

        canvas.save();
        canvas.translate(x - width / 2, y - height / 2);
        shapeDrawable.draw(canvas);
        shapeDrawableStroke.draw(canvas);
        if (labelBackground != null) {
            RectF labelBackgroundBounds = new RectF(hOffset - drawLabelBounds.width(), vCenterOffset - 1.5f * drawLabelBounds.height(), hOffset
                    + drawLabelBounds.width(), vCenterOffset + 0.5f * drawLabelBounds.height());
            canvas.drawRoundRect(labelBackgroundBounds, 5, 5, labelBackground);
        }
        canvas.drawText(label, hOffset, vCenterOffset, labelPaint);
        canvas.restore();
    }

    public RectF getBounds() {
        float w2 = getWidth() / 2;
        float h2 = getHeight() / 2;
        return new RectF(x - w2, y - h2, x + w2, y + h2);
    }

}
