/**
 ***** BEGIN GPL LICENSE BLOCK *****
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, write to:
 * 
 * the Free Software Foundation Inc. 51 Franklin Street, Fifth Floor Boston, MA
 * 02110-1301, USA
 * 
 * or go online at: http://www.gnu.org/licenses/ to view license options.
 * 
 * Original Author: Mihir Gokani (http://mihirgokani.in)
 * 
 ***** END GPL LICENCE BLOCK *****
 */
package in.ac.iitb.intulearn.unit.binarysearch;

import in.ac.iitb.intulearn.unit.ExperimentView;
import in.ac.iitb.intulearn.unit.ShapeHolder;
import in.ac.iitb.intulearn.unit.Unit;
import in.ac.iitb.intulearn.util.Common;

import java.util.ArrayList;
import java.util.Arrays;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.shapes.PathShape;
import android.view.animation.LinearInterpolator;

public class BinarySearchExperiment extends ExperimentView {

    final static private int ELEMENT_ANIMATION_REL_LENGTH_MOVE = 1; // Animate
    final static private int ELEMENT_ANIMATION_REL_LENGTH_MATCH = 2; // Match

    private static final int ELEMENT_FILL_COLOR_FOUND = 0xff88ff88;
    private static final int ELEMENT_FILL_COLOR_NOT_FOUND = 0xffff8888;

    final static private TimeInterpolator linearInterpolator = new LinearInterpolator();
    final static private ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    private final AnimatorUpdateListener viewInvalidator = new Common.ViewInvalidator(this);

    private ArrayList<ShapeHolder> inputElements = new ArrayList<ShapeHolder>();
    private ShapeHolder searchElement = null, lowElement = null, highElement = null;

    public BinarySearchExperiment(Context context) {
        super(context);

        int[] originalElementValues = Arrays.copyOf(Unit.INITIAL_VALUES_ARRAY, Unit.INITIAL_VALUES_ARRAY.length);
        int originalSearchElementValue = originalElementValues[originalElementValues.length * 3 / 4];

        Arrays.sort(originalElementValues);

        ShapeHolder inputElement;
        for (int i = 0; i < originalElementValues.length; i++) {
            inputElement = newElement(i, originalElementValues[i]);
            inputElements.add(inputElement);
        }

        searchElement = newElement((inputElements.size() - 1) / 2, originalSearchElementValue);
        lowElement = newElementT("L");
        highElement = newElementT("H");

        initExperiment();
    }

    @Override
    public void initExperiment() {
        for (int i = 0; i < inputElements.size(); i++) {
            resetElement(inputElements.get(i), i);
        }

        resetElement(searchElement, (inputElements.size() - 1) / 2);
        searchElement.setY(densityManager.pixelsToDIP(GLOBAL_OFFSET_Y + (ELEMENT_HEIGHT + ELEMENT_GAP_Y)));

        lowElement.setX(densityManager.pixelsToDIP(GLOBAL_OFFSET_X));
        highElement.setX(densityManager.pixelsToDIP(GLOBAL_OFFSET_X + (ELEMENT_WIDTH + ELEMENT_GAP_X) * (inputElements.size() - 1)));
    }

    private ShapeHolder newElementT(String str) {
        float x = GLOBAL_OFFSET_X;
        float y = GLOBAL_OFFSET_Y + (ELEMENT_HEIGHT + ELEMENT_GAP_Y);

        Path path = new Path();
        path.moveTo(densityManager.pixelsToDIP(0.5f * ELEMENT_WIDTH), densityManager.pixelsToDIP(0 * ELEMENT_HEIGHT));
        path.lineTo(densityManager.pixelsToDIP(0.8f * ELEMENT_WIDTH), densityManager.pixelsToDIP(0.5f * ELEMENT_HEIGHT));
        path.lineTo(densityManager.pixelsToDIP(0.6f * ELEMENT_WIDTH), densityManager.pixelsToDIP(0.5f * ELEMENT_HEIGHT));
        path.lineTo(densityManager.pixelsToDIP(0.6f * ELEMENT_WIDTH), densityManager.pixelsToDIP(1.5f * ELEMENT_HEIGHT));

        path.lineTo(densityManager.pixelsToDIP(0.4f * ELEMENT_WIDTH), densityManager.pixelsToDIP(1.5f * ELEMENT_HEIGHT));
        path.lineTo(densityManager.pixelsToDIP(0.4f * ELEMENT_WIDTH), densityManager.pixelsToDIP(0.5f * ELEMENT_HEIGHT));
        path.lineTo(densityManager.pixelsToDIP(0.2f * ELEMENT_WIDTH), densityManager.pixelsToDIP(0.5f * ELEMENT_HEIGHT));
        path.lineTo(densityManager.pixelsToDIP(0.5f * ELEMENT_WIDTH), densityManager.pixelsToDIP(0 * ELEMENT_HEIGHT));
        path.close();

        PathShape pathShape = new PathShape(path, densityManager.pixelsToDIP(ELEMENT_WIDTH), densityManager.pixelsToDIP(1.5f * ELEMENT_HEIGHT));
        pathShape.resize(densityManager.pixelsToDIP(ELEMENT_WIDTH), densityManager.pixelsToDIP(1.5f * ELEMENT_HEIGHT));

        PathShape pathShapeStroke = null;
        try {
            pathShapeStroke = pathShape.clone();
        } catch (CloneNotSupportedException e) {}

        ShapeHolder shapeHolder = new ShapeHolder(pathShape, pathShapeStroke, str);
        shapeHolder.setX(densityManager.pixelsToDIP(x));
        shapeHolder.setY(densityManager.pixelsToDIP(y));
        shapeHolder.setPaint(shapePaint);
        shapeHolder.setStrokePaint(shapeStrokePaint);
        shapeHolder.setLabelPaint(labelPaint);
        shapeHolder.setLabelVerticalAlign(ShapeHolder.LabelAlignment.BOTTOM_OUTSIDE);

        Paint p = new Paint();
        p.setColor(ELEMENT_FILL_COLOR);
        p.setAlpha(200);
        shapeHolder.setLabelBackground(p);

        return shapeHolder;
    }

    @Override
    protected ArrayList<Animator> newAnimation() {
        ArrayList<Animator> fullAnimation = new ArrayList<Animator>();
        ArrayList<Animator> stepAnimation;

        int searchValue = Integer.parseInt(searchElement.getLabel());

        int low = 0;
        int high = inputElements.size() - 1;
        int mid = -1, oldmid;
        while (low <= high) {
            oldmid = mid;
            mid = (low + high) / 2;

            stepAnimation = new ArrayList<Animator>();
            int midValue = Integer.parseInt(inputElements.get(mid).getLabel());

            if (midValue == searchValue) {
                stepAnimation.add(getMatchFoundAnimation(mid));
                AnimatorSet stepAnimatorSet = new AnimatorSet();
                stepAnimatorSet.playSequentially(stepAnimation);
                fullAnimation.add(stepAnimatorSet);
                break;

            } else {
                stepAnimation.add(getMatchNotFoundAnimation(mid));
                if (midValue > searchValue) high = mid - 1;
                else low = mid + 1;

                int num = Math.abs(mid - oldmid);
                stepAnimation.add(getMoveAnimation((low + high) / 2, low, high, num));
                AnimatorSet stepAnimatorSet = new AnimatorSet();
                stepAnimatorSet.playSequentially(stepAnimation);
                fullAnimation.add(stepAnimatorSet);
            }
        }

        return fullAnimation;
    }

    private Animator getMoveAnimation(int newMid, int newLow, int newHigh, int num) {
        float newX1 = densityManager.pixelsToDIP(GLOBAL_OFFSET_X + newMid * (ELEMENT_WIDTH + ELEMENT_GAP_X));
        float newX2 = densityManager.pixelsToDIP(GLOBAL_OFFSET_X + newLow * (ELEMENT_WIDTH + ELEMENT_GAP_X));
        float newX3 = densityManager.pixelsToDIP(GLOBAL_OFFSET_X + newHigh * (ELEMENT_WIDTH + ELEMENT_GAP_X));

        ObjectAnimator a1 = ObjectAnimator.ofFloat(searchElement, "x", newX1);
        ObjectAnimator a2 = ObjectAnimator.ofFloat(lowElement, "x", newX2);
        ObjectAnimator a3 = ObjectAnimator.ofFloat(highElement, "x", newX3);

        a1.addUpdateListener(viewInvalidator);
        a2.addUpdateListener(viewInvalidator);
        a3.addUpdateListener(viewInvalidator);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(a1, a2, a3);
        animatorSet.setDuration(Math.abs(num) * getAnimationDurationBase(ELEMENT_ANIMATION_REL_LENGTH_MOVE));
        animatorSet.setInterpolator(linearInterpolator);
        return animatorSet;
    }

    private Animator getMatchFoundAnimation(int midIndex) {
        ShapeHolder midElement = inputElements.get(midIndex);

        ObjectAnimator a1 = ObjectAnimator.ofInt(midElement, "color", ELEMENT_FILL_COLOR_FOUND);
        ObjectAnimator a2 = ObjectAnimator.ofInt(searchElement, "color", ELEMENT_FILL_COLOR_FOUND);

        a1.addUpdateListener(viewInvalidator);
        a2.addUpdateListener(viewInvalidator);

        a1.setEvaluator(argbEvaluator);
        a2.setEvaluator(argbEvaluator);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(a1, a2);
        animatorSet.setDuration(getAnimationDurationBase(ELEMENT_ANIMATION_REL_LENGTH_MATCH));
        animatorSet.setInterpolator(linearInterpolator);
        return animatorSet;
    }

    private Animator getMatchNotFoundAnimation(int midIndex) {
        ShapeHolder midElement = inputElements.get(midIndex);

        ObjectAnimator a1 = ObjectAnimator.ofInt(midElement, "color", ELEMENT_FILL_COLOR_NOT_FOUND);
        ObjectAnimator a2 = ObjectAnimator.ofInt(searchElement, "color", ELEMENT_FILL_COLOR_NOT_FOUND);
        ObjectAnimator a3 = ObjectAnimator.ofInt(searchElement, "color", ELEMENT_FILL_COLOR);

        a1.addUpdateListener(viewInvalidator);
        a2.addUpdateListener(viewInvalidator);

        a1.setEvaluator(argbEvaluator);
        a2.setEvaluator(argbEvaluator);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(a1).with(a2).before(a3);
        animatorSet.setDuration(getAnimationDurationBase(ELEMENT_ANIMATION_REL_LENGTH_MATCH));
        animatorSet.setInterpolator(linearInterpolator);
        return animatorSet;
    }

    @Override
    protected ArrayList<ShapeHolder> getShapeHolders() {
        ArrayList<ShapeHolder> shapeHolders = new ArrayList<ShapeHolder>(inputElements);
        shapeHolders.add(searchElement);
        return shapeHolders;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (ShapeHolder element : inputElements)
            element.draw(canvas);
        lowElement.draw(canvas);
        highElement.draw(canvas);
        searchElement.draw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = densityManager.pixelsToDIP(GLOBAL_OFFSET_X + inputElements.size() * (ELEMENT_WIDTH + ELEMENT_GAP_X));
        int measuredHeight = densityManager.pixelsToDIP(GLOBAL_OFFSET_Y + (ELEMENT_HEIGHT + ELEMENT_GAP_Y) + (1.5f * ELEMENT_HEIGHT + ELEMENT_GAP_Y));
        setMeasuredDimension(resolveSize(measuredWidth, widthMeasureSpec), resolveSize(measuredHeight, heightMeasureSpec));
    }
}
