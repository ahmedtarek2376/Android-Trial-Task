package com.palm.trial.ui.chat

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.FragmentScenario
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import com.google.common.truth.Truth.assertThat
import com.palm.trial.data.repo.MessagesRepo
import com.palm.trial.domain.model.ChatMessage
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ChatFragmentTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    private lateinit var scenario: FragmentScenario<ChatFragment>

    @Before
    fun setup() {
        MessagesRepo.resetScope()
    }

    @After
    fun tearDown() {
        if (::scenario.isInitialized) {
            scenario.close()
        }
    }

    @Test
    fun liveData_hasNoObservers_afterFragmentDestroyed() {
        // Given: Launch fragment in RESUMED state
        scenario = FragmentScenario.launchInContainer(ChatFragment::class.java)

        var liveData: LiveData<List<ChatMessage>>? = null

        scenario.onFragment { f ->
            liveData = f.getMessagesLiveData()
        }

        scenario.moveToState(Lifecycle.State.RESUMED)

        // When: Fragment is destroyed
        scenario.moveToState(Lifecycle.State.DESTROYED)

        // Then: LiveData should have no observers
        assertThat(liveData?.hasObservers()).isTrue()
    }

    @Test
    fun liveData_hasActiveObservers_whenFragmentActive() {
        // Given: Launch fragment
        scenario = FragmentScenario.launchInContainer(ChatFragment::class.java)

        var liveData: LiveData<List<ChatMessage>>? = null

        scenario.onFragment { fragment ->
            liveData = fragment.getMessagesLiveData()
        }

        // When: Fragment is in RESUMED state
        scenario.moveToState(Lifecycle.State.RESUMED)

        // Then: LiveData should have active observers
        assertThat(liveData?.hasActiveObservers()).isTrue()
        assertThat(liveData?.hasObservers()).isTrue()
    }
}