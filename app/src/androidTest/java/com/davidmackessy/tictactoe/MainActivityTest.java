package com.davidmackessy.tictactoe;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.PositionAssertions.isBelow;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

//    @Before
//    public void stubAllExternalIntents(){
//
//        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
//
//    }

    @Test
    public void click2PlayerGame(){
        //find view & click 2 player game
        onView(withId(R.id.txt_two_player)).perform(click());

        //check if the game screen has opened
        onView(withId(R.id.tile_9)).check(matches(isDisplayed()));

        //check if tile 9 is below tile 6
        onView(withId(R.id.tile_9)).check(isBelow((withId(R.id.tile_6))));

    }

    @Test
    public void checkIntentDataPassedIsCorrect(){
        Intents.init();

        //find view & click 2 player game
        onView(withId(R.id.txt_two_player)).perform(click());

        //check intent was sent & has correct data
        intended(allOf(
                hasExtra("gameType", 2)
        ));

        Intents.release();
    }
}
