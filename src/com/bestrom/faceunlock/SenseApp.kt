package com.bestrom.faceunlock

import android.app.Application
import android.content.ComponentName
import android.content.pm.PackageManager
import android.util.Log
import com.bestrom.faceunlock.activities.EnrollActivity
import com.bestrom.faceunlock.util.Util

class SenseApp : Application() {

    override fun onCreate() {
        if (Util.IS_DEBUG_LOGGING) Log.d(TAG, "onCreate")
        super.onCreate()
        app = this
        packageManager.setComponentEnabledSetting(
            ComponentName(this, EnrollActivity::class.java),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
        Util.setFaceUnlockAvailable(applicationContext)
    }

    override fun onTerminate() {
        if (Util.IS_DEBUG_LOGGING) {
            Log.d(TAG, "onTerminate")
        }
        super.onTerminate()
    }

    companion object {
        private const val TAG = "SenseApp"
        var app: SenseApp? = null
    }
}