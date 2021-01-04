package com.yma.banks.utils

import io.reactivex.Scheduler

interface SchedulerProvider {
    fun ui() : Scheduler
    fun io() : Scheduler
}