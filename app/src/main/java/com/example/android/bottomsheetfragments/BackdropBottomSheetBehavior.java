package com.example.android.bottomsheetfragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;

/**
 *
 */
public class BackdropBottomSheetBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {

    private float mImageHeight;
    private float mYmultiplier;
    private float mPreviousY;
    private View mChild;

    public BackdropBottomSheetBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof NestedScrollView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child,
                                          View dependency) {

        if (mYmultiplier == 0) {
            initValues(child, dependency);
            return true;
        }

        float dVerticalScroll = dependency.getY() - mPreviousY;
        mPreviousY = dependency.getY();

        //going up
        if (dVerticalScroll <= 0 && child.getY() <= 0) {
            child.setY(0);
            return true;
        }

        //going down
        if (dVerticalScroll >= 0 && dependency.getY() <= mImageHeight)
            return false;

        child.setY( (int)(child.getY() + (dVerticalScroll * mYmultiplier) ) );

        return true;
    }


    private void initValues(View child, View dependency) {

        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(dependency);
        int peekHeight = bottomSheetBehavior.getPeekHeight();

        ViewParent viewParent = dependency.getParent();
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) viewParent;
        float heightParent = coordinatorLayout.getHeight();

        mImageHeight = child.getHeight();

        float bottomSheetScroll_vertical_height = heightParent - mImageHeight - peekHeight;
        mYmultiplier = (mImageHeight / bottomSheetScroll_vertical_height) + 1;

        mPreviousY = dependency.getY();
        child.setY((int) mPreviousY);


        mChild = child;

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, @BottomSheetBehavior.State int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED ||
                        newState == BottomSheetBehavior.STATE_HIDDEN)
                    mChild.setY(bottomSheet.getY());
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }
}
