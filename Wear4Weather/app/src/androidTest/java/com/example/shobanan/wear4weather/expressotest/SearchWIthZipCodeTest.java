package com.example.shobanan.wear4weather.expressotest;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.shobanan.wear4weather.R;
import com.example.shobanan.wear4weather.WeatherActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class SearchWIthZipCodeTest {
    private String zipCode;

    @Rule
    public ActivityTestRule<WeatherActivity> mActivityRule = new ActivityTestRule<>(
            WeatherActivity.class);

    @Before
    public void init() {
        // Specify a valid string.
        zipCode = "97005"
        ;
    }

    @Test
    public void changeText_sameActivity() {
        // Type text and then press the button.
        onView(withId(R.id.edtT_zipcode))
                .perform(typeText(zipCode), closeSoftKeyboard());
        onView(withId(R.id.btn_search)).perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
