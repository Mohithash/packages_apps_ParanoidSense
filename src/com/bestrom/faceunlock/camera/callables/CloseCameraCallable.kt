package com.bestrom.faceunlock.camera.callables

import android.util.Log
import com.bestrom.faceunlock.camera.listeners.CameraListener
import com.bestrom.faceunlock.util.Util
import java.lang.Exception

class CloseCameraCallable(listener: CameraListener?) : CameraCallable(listener) {

    override fun call(): CallableReturn {
        val cameraData = cameraData
        val camera = cameraData.mCamera
            ?: return CallableReturn(Exception("Camera isn't opened"))
        if (Util.IS_DEBUG_LOGGING) Log.d(tag, "releasing camera")
        camera.setErrorCallback(null)
        camera.release()
        cameraData.mCamera = null
        cameraData.mCameraId = -1
        cameraData.mParameters = null
        return CallableReturn(null)
    }

    override val tag: String?
        get() = CloseCameraCallable::class.java.simpleName
}