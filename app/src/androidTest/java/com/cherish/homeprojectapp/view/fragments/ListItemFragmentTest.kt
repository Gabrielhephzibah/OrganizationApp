package com.cherish.homeprojectapp.view.fragments

import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.cherish.homeprojectapp.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@HiltAndroidTest
class ListItemFragmentTest{

    private lateinit var navController: NavController

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp(){
        hiltRule.inject()
        navController = Mockito.mock(NavController::class.java)

    }

    @Test
    fun launchFragment(){
        launchFragmentInHiltContainer<ListItemFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }
    }


}