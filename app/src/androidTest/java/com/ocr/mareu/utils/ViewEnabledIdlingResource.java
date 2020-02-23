package com.ocr.mareu.utils;
import android.view.View;

import androidx.test.espresso.IdlingResource;

/**
 * Created by Florence LE BOURNOT on 23/02/2020
 */

/**
 * IdlingResource waiting until the given view is not enabled any more.<br>
 * Can easily be adapted to check for different view properties, like "isVisible", "isSelected", "isChecked", etc.
 * <p>
 * Sample usage:
 * </p><pre> * ViewEnabledIdlingResource idlingResource = new ViewEnabledIdlingResource(mActivityRule.getActivity().findViewById(R.id.buttonApp));
 * registerIdlingResources(idlingResource);
 * Espresso.registerIdlingResources(idlingResource);
 * // .. do something. View is now guaranteed to be enabled.
 * Espresso.unregisterIdlingResources(idlingResource);
 * </pre>
 */
public class ViewEnabledIdlingResource implements IdlingResource {
    private final View view;
    private ResourceCallback resourceCallback;

    public ViewEnabledIdlingResource(View view) {
        this.view = view;
    }

    @Override
    public String getName() {
        return ViewEnabledIdlingResource.class.getName() + ":" + view.getId();
    }

    @Override
    public boolean isIdleNow() {
        boolean idle = view.isEnabled();
        if (idle) {
            resourceCallback.onTransitionToIdle();
        }
        return idle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
        this.resourceCallback = resourceCallback;
    }
}