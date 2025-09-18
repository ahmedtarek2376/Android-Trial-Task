package com.palm.trial.ui.chat

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.NavHostFragment
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.google.common.truth.Truth.assertThat
import com.palm.trial.MainActivity
import com.palm.trial.R
import com.palm.trial.data.repo.MessagesRepo
import com.palm.trial.domain.model.ChatMessage
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ChatFragmentNavigationEspressoTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MessagesRepo.resetScope()
    }

    @Test
    fun chatFragment_observersCleanedUp_afterNavigationCycle() {
        var chatFragment: ChatFragment? = null
        var chatLiveData: LiveData<List<ChatMessage>>? = null

        // STEP 1: Navigate to ChatFragment from HomeFragment
        onView(withId(R.id.btn_navigate))
            .perform(click())

        Thread.sleep(500) // Allow navigation to complete

        activityRule.scenario.onActivity { activity ->
            val navHostFragment = activity.supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val currentFragment = navHostFragment.childFragmentManager.fragments[0]

            chatFragment = currentFragment as? ChatFragment
            assertThat(chatFragment).isNotNull()

            chatLiveData = chatFragment!!.getMessagesLiveData()

            // Verify LiveData has observers when ChatFragment is active
            assertThat(chatLiveData?.hasObservers()).isTrue()
        }

        // Verify ChatFragment UI is displayed
        onView(withId(R.id.chat_recycler_view))
            .check(matches(isDisplayed()))

        /////////////////////////////////////////////////////////////////////

        // STEP 2: Navigate back to HomeFragment
        Thread.sleep(1000) // To see the messages received
        pressBack()

        Thread.sleep(500) // Allow navigation to complete

        // Verify LiveData has no observers after navigating back from ChatFragment
        assertThat(chatLiveData?.hasObservers()).isFalse()

        ///////////////////////////////////////////////////////////////////////

        // STEP 3: Navigate to ChatFragment again
        onView(withId(R.id.btn_navigate))
            .perform(click())

        Thread.sleep(500)

        ///////////////////////////////////////////////////////////////////////

        // STEP 4: Verify new ChatFragment instance works correctly
        activityRule.scenario.onActivity { activity ->
            val navHostFragment = activity.supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val newChatFragment = navHostFragment.childFragmentManager.fragments[0] as ChatFragment

            val newChatLiveData = newChatFragment.getMessagesLiveData()

            // Verify livedata has active observers
            assertThat(newChatLiveData.hasObservers()).isTrue()
        }

        // Verify ChatFragment UI is displayed again
        onView(withId(R.id.chat_recycler_view))
            .check(matches(isDisplayed()))

        Thread.sleep(100) // To see the messages received
    }
}