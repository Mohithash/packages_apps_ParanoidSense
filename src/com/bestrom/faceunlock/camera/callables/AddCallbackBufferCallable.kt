package com.bestrom.faceunlock.camera.callables

import com.bestrom.faceunlock.camera.listeners.CameraListener
import java.lang.Exception

class AddCallbackBufferCallable(private val mBuffer: ByteArray?, listener: CameraListener?) :
    CameraCallable(listener) {

    override fun call(): CallableReturn {
        val camera = cameraData.mCamera
            ?: return CallableReturn(Exception("Camera isn't opened"))
        camera.addCallbackBuffer(mBuffer)
        return CallableReturn(null)
    }

    override val tag: String?
        get() = AddCallbackBufferCallable::class.java.simpleName
}