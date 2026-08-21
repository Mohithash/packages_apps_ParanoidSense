package com.voltageos.faceunlock.camera.callables

import com.voltageos.faceunlock.camera.listeners.CameraListener
import java.lang.Exception

class StopPreviewCallable(listener: CameraListener?) : CameraCallable(listener) {

    override fun call(): CallableReturn {
        val camera = cameraData.mCamera
            ?: return CallableReturn(Exception("Camera isn't opened"))
        camera.stopPreview()
        return CallableReturn(null)
    }

    override val tag: String?
        get() = StopPreviewCallable::class.java.simpleName
}