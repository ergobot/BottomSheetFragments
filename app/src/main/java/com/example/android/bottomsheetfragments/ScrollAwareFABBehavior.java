package com.example.android.bottomsheetfragments;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;

/*
 * Copyright 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class ScrollAwareFABBehavior extends FloatingActionButton.Behavior {

    float offset;

    public BottomSheetBehavior bottomSheetBehavior;

    public ScrollAwareFABBehavior(Context context, AttributeSet attrs) {
        super();
        offset = 0;
    }

//    @Override
//    public boolean onStartNestedScroll(final CoordinatorLayout coordinatorLayout, final FloatingActionButton child,
//                                       final View directTargetChild, final View target, final int nestedScrollAxes) {
//        // Ensure we react to vertical scrolling
//        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
//                || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
//    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        return dependency instanceof NestedScrollView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {


        // not always catching event.... misses...
        // guess we'll handle it somewhere else...
        // the bottomsheet == state_collapsed not always caught unless you drag the nestedscrollview all the way down.
//        bottomSheetBehavior = BottomSheetBehavior.from(dependency);
//
//        if(bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED){
//            child.show();
//        }else{
//            child.hide();
//        }

//        if (offset == 0)
//            setOffsetValue(parent);
//
//        if (dependency.getY() <=0)
//            return false;
//
//        if (child.getY() <= (offset + child.getHeight()) && child.getVisibility() == View.VISIBLE)
//            child.hide();
//        else if (child.getY() > offset && child.getVisibility() != View.VISIBLE)
//            child.show();
//
        return false;
    }

    private void setOffsetValue(CoordinatorLayout coordinatorLayout) {

        for (int i = 0; i < coordinatorLayout.getChildCount(); i++) {
            View child = coordinatorLayout.getChildAt(i);

            if (child instanceof AppBarLayout) {

                if (child.getTag() != null &&
                        child.getTag().toString().contentEquals("modal-appbar") ) {
                    offset = child.getY()+child.getHeight();
                    break;
                }
            }
        }
    }
}