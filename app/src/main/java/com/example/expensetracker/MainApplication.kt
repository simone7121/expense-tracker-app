package com.example.expensetracker

import android.app.Application
import com.example.expensetracker.data.Graph
import com.example.expensetracker.data.OnBoardingRepository

class MainApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        OnBoardingRepository.initialize(this)
        Graph.provide(this)
    }
}