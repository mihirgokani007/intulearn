package in.ac.iitb.intulearn.unit;

import in.ac.iitb.intulearn.util.Common.DensityManager;

import java.util.ArrayList;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.shapes.RectShape;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * @author Mihir Gokani
 * @created 9-Feb-2013 00:49:38 AM
 */
public abstract class ExperimentView extends View {

    final static public float GLOBAL_OFFSET_X = 50f;
    final static public float GLOBAL_OFFSET_Y = 150f;

    final static public float ELEMENT_GAP_X = 10f;
    final static public float ELEMENT_GAP_Y = 20f;

    final static public float ELEMENT_WIDTH = 65f;
    final static public float ELEMENT_HEIGHT = 70f;
    final static public int ELEMENT_FILL_COLOR = 0xff8888ff;

    final static public float ELEMENT_STROKE_THICKNESS = 2f;
    final static public int ELEMENT_STROKE_COLOR = 0xff000000;

    final static public float ELEMENT_LABEL_SIZE = 28f;
    final static public int ELEMENT_LABEL_COLOR = 0xff000000;

    final protected Paint shapePaint, shapeStrokePaint, labelPaint;

    final public Events events = new Events();

    final protected DensityManager densityManager;
    final protected StepAnimatorListener stepAnimatorListener = new StepAnimatorListener();
    final protected GlobalAnimatorListener globalAnimatorListener = new GlobalAnimatorListener();

    private AnimatorSet mAnimatorSet = null;
    private boolean isAnimationRunning = false;
    private int currentStepIndex;

    public ExperimentView(Context context) {
        super(context);
        densityManager = DensityManager.getInstance(context);

        shapePaint = new Paint();
        shapeStrokePaint = new Paint();
        labelPaint = new Paint();

        shapePaint.reset();
        shapePaint.clearShadowLayer();
        shapePaint.setStyle(Paint.Style.FILL);
        shapePaint.setColor(ELEMENT_FILL_COLOR);

        shapeStrokePaint.reset();
        shapeStrokePaint.setStyle(Paint.Style.STROKE);
        shapeStrokePaint.setColor(ELEMENT_STROKE_COLOR);
        shapeStrokePaint.setStrokeWidth(densityManager.pixelsToDIP(ELEMENT_STROKE_THICKNESS));

        labelPaint.reset();
        labelPaint.setColor(ELEMENT_LABEL_COLOR);
        labelPaint.setTextSize(densityManager.pixelsToSP(ELEMENT_LABEL_SIZE));
        labelPaint.setTextAlign(Paint.Align.CENTER);

        LongTouchListener longTouchListener = new LongTouchListener();
        setOnTouchListener(longTouchListener);
        setOnLongClickListener(longTouchListener);
    }

    protected ShapeHolder newElement(int index, int value) {
        float x = GLOBAL_OFFSET_X + (ELEMENT_WIDTH + ELEMENT_GAP_X) * index;
        float y = GLOBAL_OFFSET_Y;

        RectShape rectShape = new RectShape();
        rectShape.resize(densityManager.pixelsToDIP(ELEMENT_WIDTH), densityManager.pixelsToDIP(ELEMENT_HEIGHT));

        RectShape rectShapeStroke = null;
        try {
            rectShapeStroke = rectShape.clone();
        } catch (CloneNotSupportedException e) {}

        ShapeHolder shapeHolder = new ShapeHolder(rectShape, rectShapeStroke, String.valueOf(value));
        shapeHolder.setX(densityManager.pixelsToDIP(x));
        shapeHolder.setY(densityManager.pixelsToDIP(y));
        shapeHolder.setPaint(shapePaint);
        shapeHolder.setStrokePaint(shapeStrokePaint);
        shapeHolder.setLabelPaint(labelPaint);

        return shapeHolder;
    }

    protected void resetElement(ShapeHolder shapeHolder, int index) {
        float x = GLOBAL_OFFSET_X + (ELEMENT_WIDTH + ELEMENT_GAP_X) * index;
        float y = GLOBAL_OFFSET_Y;

        shapeHolder.setX(densityManager.pixelsToDIP(x));
        shapeHolder.setY(densityManager.pixelsToDIP(y));
        shapeHolder.setPaint(shapePaint);
        shapeHolder.setStrokePaint(shapeStrokePaint);
        shapeHolder.setLabelPaint(labelPaint);
    }

    public void createAnimation() {
        if (mAnimatorSet != null) {
            mAnimatorSet.removeListener(globalAnimatorListener);
            for (Animator animator : mAnimatorSet.getChildAnimations())
                animator.removeListener(stepAnimatorListener);
            mAnimatorSet.cancel();
        }

        mAnimatorSet = new AnimatorSet();
        ArrayList<Animator> animatorList = newAnimation();
        for (Animator animator : animatorList)
            animator.addListener(stepAnimatorListener);
        mAnimatorSet.addListener(globalAnimatorListener);
        mAnimatorSet.playSequentially(animatorList);
        currentStepIndex = -1;
    }

    public boolean isAnimationRunning() {
        return isAnimationRunning;
    }

    public long getTotalDuration() {
        long totalDuration = 0;
        for (Animator step : mAnimatorSet.getChildAnimations()) {
            totalDuration += step.getStartDelay() + step.getDuration();
        }
        return totalDuration;
    }

    abstract protected ArrayList<Animator> newAnimation();

    @Override
    abstract protected void onDraw(Canvas canvas);

    @Override
    abstract protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec);

    abstract protected ArrayList<ShapeHolder> getShapeHolders();

    public abstract void initExperiment();

    /*******************************
     ** Animation related methods **
     *******************************/

    public void playAnimation() {
        mAnimatorSet.start();
    }

    public void pauseAnimation() {
        mAnimatorSet.cancel();
    }

    public void toggleAnimation() {
        if (isAnimationRunning) pauseAnimation();
        else playAnimation();
    }

    public void stopAnimation() {
        mAnimatorSet.end();
    }

    /**************************
     ** Step related methods **
     **************************/

    /**
     * Returns the current list of {@link Animator} objects registered for this
     * experiment. It is a copy of the internal list; modifications to the
     * returned list will not affect the experiment, although changes to the
     * underlying {@link Animator} objects will affect those objects being
     * managed by this experiment.
     * 
     * @return {@link ArrayList<Animator>} The list of steps of this experiment.
     */
    public ArrayList<Animator> getSteps() {
        return mAnimatorSet.getChildAnimations();
    }

    /**
     * Returns the number of {@link Animator} objects registered for this
     * experiment.
     * 
     * @return The number of steps of this experiment.
     */
    public int getStepsCount() {
        return mAnimatorSet.getChildAnimations().size();
    }

    /*********************************
     ** Step timing related methods **
     *********************************/

    /**
     * Finds and returns the index of the current step which is active for the
     * given time
     * 
     * @return Index of the current step
     */
    public int getCurrentStepIndex() {
        return currentStepIndex;
    }

    /**
     * Finds and returns the current step which is active for the given time
     * 
     * @return The current step
     */
    public Animator getCurrentStep() {

        int index = getCurrentStepIndex();
        try {
            return mAnimatorSet.getChildAnimations().get(index);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    /**********************************
     ** Event related helper methods **
     **********************************/

    final public class Events {

        private final ArrayList<ExperimentListener> experimentListeners = new ArrayList<ExperimentListener>();

        public void addExperimentListener(ExperimentListener listener) {
            experimentListeners.add(listener);
        }

        public void removeExperimentListener(ExperimentListener listener) {
            experimentListeners.remove(listener);
        }

        public void removeAllExperimentListeners() {
            experimentListeners.clear();
        }

        public ArrayList<ExperimentListener> getExperimentListeners() {
            return new ArrayList<ExperimentListener>(experimentListeners);
        }

        protected void fireOnCurrentStepChanged(int oldIndex, int newIndex) {
            for (ExperimentListener listener : experimentListeners)
                listener.onCurrentStepChanged(ExperimentView.this, oldIndex, newIndex);
        }

        protected void fireOnExperimentStart(boolean resume) {
            for (ExperimentListener listener : experimentListeners)
                listener.onExperimentStart(ExperimentView.this, resume);
        }

        protected void fireOnExperimentPause() {
            for (ExperimentListener listener : experimentListeners)
                listener.onExperimentPause(ExperimentView.this);
        }

        protected void fireOnExperimentEnd() {
            for (ExperimentListener listener : experimentListeners)
                listener.onExperimentEnd(ExperimentView.this);
        }

        protected void fireOnElementClicked(ShapeHolder element) {
            for (ExperimentListener listener : experimentListeners)
                listener.onElementClicked(ExperimentView.this, element);
        }
    }

    private class GlobalAnimatorListener implements Animator.AnimatorListener {

        @Override
        public void onAnimationStart(Animator animation) {
            isAnimationRunning = true;
            events.fireOnExperimentStart(false);
            invalidate();
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
            invalidate();
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            isAnimationRunning = false;
            events.fireOnExperimentPause();
            invalidate();
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            isAnimationRunning = false;
            // FIXME: Verify: currentStepIndex = -1;
            events.fireOnExperimentEnd();
            invalidate();
        }
    }

    private class StepAnimatorListener implements Animator.AnimatorListener {

        @Override
        public void onAnimationStart(Animator animation) {
            int oldIndex = currentStepIndex;
            int newIndex = mAnimatorSet.getChildAnimations().indexOf(animation);

            if (oldIndex != newIndex) {
                currentStepIndex = newIndex;
                events.fireOnCurrentStepChanged(oldIndex, newIndex);
                invalidate();
            }
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
            invalidate();
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            invalidate();
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            invalidate();
        }
    }

    protected AlertDialog.Builder showElementLabelChangeDialog(final ShapeHolder element, String title, String message) {
        final String oldLabel = element.getLabel();

        final EditText input = new EditText(getContext());
        input.setText(oldLabel);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);

        final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                boolean isValid = !value.isEmpty() && TextUtils.isDigitsOnly(value);
                if (isValid && value != oldLabel) {
                    element.setLabel(value);
                    ExperimentView.this.invalidate();
                }
            }
        });

        alert.setNegativeButton("Cancel", null);

        return alert;
    }

    private class LongTouchListener implements OnTouchListener, OnLongClickListener {

        private float recentTouchX = -1, recentTouchY = -1;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                this.recentTouchX = -1;
                this.recentTouchY = -1;
            } else {
                this.recentTouchX = event.getX();
                this.recentTouchY = event.getY();
            }
            return false;
        }

        @Override
        public boolean onLongClick(View v) {
            if (isAnimationRunning() || (recentTouchX < 0 && recentTouchY < 0)) return false;

            for (ShapeHolder element : getShapeHolders()) {
                if (element.getBounds().contains(recentTouchX, recentTouchY)) {
                    events.fireOnElementClicked(element);

                    showElementLabelChangeDialog(
                            element,
                            "Update value",
                            "Enter a new value and click 'OK' to change. Click 'Cancel' to keep current values as they are.\n"
                                    + "NOTE: Only integer values are allowed. You will have to restart the experiment if you change the value.").show();
                    return true;
                }
            }

            return false;
        }
    }

}
