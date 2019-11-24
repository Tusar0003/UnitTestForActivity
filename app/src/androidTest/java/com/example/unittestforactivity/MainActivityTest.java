package com.example.unittestforactivity;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class MainActivityTest {

    /**
     * to test the activity launch we need rules
     *
     * this rule specifies that MainActivity is launched
     */
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    /**
     * reference to the main activity
     */
    private MainActivity mActivity = null;

    /**
     * getInstrumentation() needs add manual import
     *
     * in addMonitor() first parameter is which activity we want to monitor
     */
    Instrumentation.ActivityMonitor monitor = getInstrumentation()
            .addMonitor(SecondActivity.class.getName(), null, false);

    /**
     * setUp() will be called before executing the test case
     *
     * whatever preconditions we need for the testLaunch(), we will be doing it here
     */
    @Before
    public void setUp() throws Exception {
        /**
         * when the activity is launched this test rule will five the context
         */
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch() {
        View view = mActivity.findViewById(R.id.text_view_hello);

        /**
         * if the view is found and it's not null, that means activity launch is successful
         */
        assertNotNull(view);

        /**
         * if the second activity is created, getInstrumentation() will give the instance
         * of that activity
         *
         * timeOut is within 5000ms I have to click the button
         */
        Activity secondActivity = getInstrumentation()
                .waitForMonitorWithTimeout(monitor, 5000);

        /**
         * if it passed then it means that monitor has returned the second activity
         * which is launched when I clicked the button
         */
        assertNotNull(secondActivity);
    }

    @Test
    public void testLaunchOfSecondActivityOnButtonClicked() {
        // asserts that button is not null
        assertNotNull(mActivity.findViewById(R.id.button_second));

        /**
         * make sure that espresso is added in the gradle build
         *
         * to use it first write onView(), then click alt+enter and select
         * "Import static method 'android.support.test.espresso.Espresso.onView' "
         *
         * withId() and perform() also need to import manually using the same method
         */
        onView(withId(R.id.button_second)).perform(click());
    }

    /**
     * tearDown() will ber called after executing the test case
     *
     * whatever cleanup we need to do after the testLaunch(), we will be doing it here
     */
    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}