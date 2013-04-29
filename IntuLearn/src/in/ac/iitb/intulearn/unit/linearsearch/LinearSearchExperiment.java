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
package in.ac.iitb.intulearn.unit.linearsearch;

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
import android.view.animation.LinearInterpolator;

public class LinearSearchExperiment extends ExperimentView {

    final static private long ELEMENT_ANIMATION_DURATION_MOVE = 1000; // Animate
    final static private long ELEMENT_ANIMATION_DURATION_HALT = 500; // Halt
    final static private long ELEMENT_ANIMATION_DURATION_MATCH = 2000; // Match

    private static final int ELEMENT_FILL_COLOR_FOUND = 0xff88ff88;
    private static final int ELEMENT_FILL_COLOR_NOT_FOUND = 0xffff8888;

    final static private TimeInterpolator linearInterpolator = new LinearInterpolator();
    final static private ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    private final AnimatorUpdateListener viewInvalidator = new Common.ViewInvalidator(this);

    private ArrayList<ShapeHolder> inputElements = new ArrayList<ShapeHolder>();
    private ShapeHolder searchElement = null;

    public LinearSearchExperiment(Context context) {
        super(context);

        int[] originalElementValues = Arrays.copyOf(Unit.INITIAL_VALUES_ARRAY, Unit.INITIAL_VALUES_ARRAY.length);
        int originalSearchElementValue = originalElementValues[originalElementValues.length * 3 / 4];

        ShapeHolder inputElement;
        for (int i = 0; i < originalElementValues.length; i++) {
            inputElement = newElement(i, originalElementValues[i]);
            inputElements.add(inputElement);
        }

        searchElement = newElement(0, originalSearchElementValue);

        initExperiment();
    }

    @Override
    public void initExperiment() {
        for (int i = 0; i < inputElements.size(); i++) {
            resetElement(inputElements.get(i), i);
        }
        resetElement(searchElement, 0);
        searchElement.setY(densityManager.pixelsToDIP(GLOBAL_OFFSET_Y + (ELEMENT_HEIGHT + ELEMENT_GAP_Y)));
    }

    @Override
    protected ArrayList<Animator> newAnimation() {

        ArrayList<Animator> fullAnimation = new ArrayList<Animator>();
        ArrayList<Animator> stepAnimation;

        int searchValue = Integer.parseInt(searchElement.getLabel());
        for (int current = 0; current < inputElements.size(); current++) {

            stepAnimation = new ArrayList<Animator>();
            int currentValue = Integer.parseInt(inputElements.get(current).getLabel());

            if (currentValue == searchValue) {
                fullAnimation.add(getMatchFoundAnimation(current));
                break;

            } else {

                stepAnimation.add(getMatchNotFoundAnimation(current));
                if (current < inputElements.size() - 1) {
                    stepAnimation.add(getMoveAnimation(current+1));
                }

                AnimatorSet stepAnimatorSet = new AnimatorSet();
                stepAnimatorSet.playSequentially(stepAnimation);
                fullAnimation.add(stepAnimatorSet);
            }
        }

        return fullAnimation;
    }

    private Animator getMoveAnimation(int newCurrent) {
        float newX = densityManager.pixelsToDIP(GLOBAL_OFFSET_X + newCurrent * (ELEMENT_WIDTH + ELEMENT_GAP_X));

        ObjectAnimator anim = ObjectAnimator.ofFloat(searchElement, "x", newX);
        anim.addUpdateListener(viewInvalidator);
        anim.setDuration(ELEMENT_ANIMATION_DURATION_MOVE);
        anim.setInterpolator(linearInterpolator);
        return anim;
    }

    private Animator getMatchFoundAnimation(int current) {
        ShapeHolder midElement = inputElements.get(current);

        ObjectAnimator a1 = ObjectAnimator.ofInt(midElement, "color", ELEMENT_FILL_COLOR_FOUND);
        ObjectAnimator a2 = ObjectAnimator.ofInt(searchElement, "color", ELEMENT_FILL_COLOR_FOUND);

        a1.addUpdateListener(viewInvalidator);
        a2.addUpdateListener(viewInvalidator);

        a1.setEvaluator(argbEvaluator);
        a2.setEvaluator(argbEvaluator);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(a1, a2);
        animatorSet.setDuration(ELEMENT_ANIMATION_DURATION_MATCH);
        animatorSet.setInterpolator(linearInterpolator);
        return animatorSet;
    }

    private Animator getMatchNotFoundAnimation(int current) {
        ShapeHolder midElement = inputElements.get(current);

        ObjectAnimator a1 = ObjectAnimator.ofInt(midElement, "color", ELEMENT_FILL_COLOR_NOT_FOUND);
        ObjectAnimator a2 = ObjectAnimator.ofInt(searchElement, "color", ELEMENT_FILL_COLOR_NOT_FOUND);
        ObjectAnimator a3 = ObjectAnimator.ofInt(searchElement, "color", ELEMENT_FILL_COLOR);

        a1.addUpdateListener(viewInvalidator);
        a2.addUpdateListener(viewInvalidator);

        a1.setEvaluator(argbEvaluator);
        a2.setEvaluator(argbEvaluator);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(a1).with(a2).before(a3);
        animatorSet.setDuration(ELEMENT_ANIMATION_DURATION_MATCH);
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
        searchElement.draw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = densityManager.pixelsToDIP(GLOBAL_OFFSET_X + inputElements.size() * (ELEMENT_WIDTH + ELEMENT_GAP_X));
        int measuredHeight = densityManager.pixelsToDIP(GLOBAL_OFFSET_Y + 2 * (ELEMENT_HEIGHT + ELEMENT_GAP_Y));
        setMeasuredDimension(resolveSize(measuredWidth, widthMeasureSpec), resolveSize(measuredHeight, heightMeasureSpec));
    }
}
