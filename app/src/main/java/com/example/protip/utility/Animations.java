package com.example.protip.utility;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;

/**
 * Static utility class for different kind of View animations used across the app
 */
public class Animations {

    /**
     * Btn animation.
     *
     * @param view {@link View} the view for simple small scale increase animation
     */
    public static void btnAnimation(final View view) {
        if (view == null) throw new NullPointerException("View must not be null");

        Animator scale = ObjectAnimator.ofPropertyValuesHolder(
                view,
                PropertyValuesHolder.ofFloat(View.SCALE_X, 1, 1.12f, 1),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 1, 1.12f, 1)
        );
        scale.setDuration(300);
        scale.start();
    }
}
