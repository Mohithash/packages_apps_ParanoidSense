package com.bestrom.faceunlock.camera.callables

import com.bestrom.faceunlock.camera.listeners.CameraListener
import java.lang.Exception

class SetDisplayOrientationCallback(private val mAngle: Int, listener: CameraListener?) :
    CameraCallable(listener) {

    override fun call(): CallableReturn {
        val camera = cameraData.mCamera
            ?: return CallableReturn(Exception("Camera isn't opened"))
        camera.setDisplayOrientation(mAngle)
        return CallableReturn(null)
    }

    override val tag: String?
        get() = SetDisplayOrientationCallback::class.java.simpleName
}