package com.example.simpleblog.setup

import org.mockito.Mockito

/**
 * Created by mskwon on 5/6/24.
 */
object MockitoHelper {

    fun <T> anyObject(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> uninitialized(): T = null as T
}