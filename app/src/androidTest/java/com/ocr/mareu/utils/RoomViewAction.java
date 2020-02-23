package com.ocr.mareu.utils;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import com.ocr.mareu.R;

import org.hamcrest.Matcher;

/**
 * Created by Florence LE BOURNOT on 23/02/2020
 */
public class RoomViewAction implements ViewAction {

        @Override
        public Matcher<View> getConstraints() {
            return null;
        }

        @Override
        public String getDescription() {
            return "Click on a child view with specified id.";
        }

        @Override
        public void perform(UiController uiController, View view) {
            View v = view.findViewById(R.id.room_item);
            v.performClick();
        }
 }
