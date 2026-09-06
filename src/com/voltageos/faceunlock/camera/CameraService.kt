package com.voltageos.faceunlock.camera

import android.graphics.SurfaceTexture
import android.hardware.Camera
import android.os.Handler
import android.os.Message
import android.view.SurfaceHolder
import com.voltageos.faceunlock.camera.callables.CameraCallable
import com.voltageos.faceunlock.camera.listeners.CameraEventListener
import com.voltageos.faceunlock.camera.listeners.CameraListener
import com.voltageos.faceunlock.camera.callables.OpenCameraCallable
import com.voltageos.faceunlock.camera.callables.CloseCameraCallable
import com.voltageos.faceunlock.camera.callables.ReadParamsCallable
import com.voltageos.faceunlock.camera.callables.WriteParamsCallable
import com.voltageos.faceunlock.camera.callables.StartPreviewCallable
import com.voltageos.faceunlock.camera.callables.StopPreviewCallable
import com.voltageos.faceunlock.camera.callables.AddCallbackBufferCallable
import com.voltageos.faceunlock.camera.callables.SetPreviewCallbackCallable
import com.voltageos.faceunlock.camera.callables.SetFaceDetectionCallback
import com.voltageos.faceunlock.camera.callables.SetDisplayOrientationCallback

class CameraService private constructor() {

    private val mServiceHandler: Handler
    private fun addCallable(cameraCallable: CameraCallable) {
        mServiceHandler.sendMessage(mServiceHandler.obtainMessage(DEFAULT_MSG_TYPE, cameraCallable))
    }

    private object LazyLoader {
        var field: CameraService = CameraService()
    }

    companion object {
        private const val DEFAULT_MSG_TYPE = 1
        fun openCamera(id: Int, errorListener: CameraEventListener?, listener: CameraListener?) {
            val instance: CameraService = LazyLoader.field
            instance.addCallable(OpenCameraCallable(id, errorListener, listener))
        }

        fun closeCamera(listener: CameraListener?) {
            val instance: CameraService = LazyLoader.field
            clearQueue()
            instance.addCallable(CloseCameraCallable(listener))
        }

        fun readParameters(eventListener: CameraEventListener?, listener: CameraListener?) {
            val instance: CameraService = LazyLoader.field
            instance.addCallable(ReadParamsCallable(eventListener, listener))
        }

        fun writeParameters(listener: CameraListener?) {
            val instance: CameraService = LazyLoader.field
            instance.addCallable(WriteParamsCallable(listener))
        }

        fun startPreview(surfaceTexture: SurfaceTexture?, listener: CameraListener?) {
            val instance: CameraService = LazyLoader.field
            instance.addCallable(StartPreviewCallable(surfaceTexture, listener))
        }

        fun startPreview(surfaceHolder: SurfaceHolder?, listener: CameraListener?) {
            val instance: CameraService = LazyLoader.field
            instance.addCallable(StartPreviewCallable(surfaceHolder, listener))
        }

        fun stopPreview(listener: CameraListener?) {
            val instance: CameraService = LazyLoader.field
            instance.addCallable(StopPreviewCallable(listener))
        }

        fun addCallbackBuffer(data: ByteArray?, listener: CameraListener?) {
            val instance: CameraService = LazyLoader.field
            instance.addCallable(AddCallbackBufferCallable(data, listener))
        }

        fun setPreviewCallback(
            eventListener: CameraEventListener?,
            withBuffer: Boolean,
            listener: CameraListener?
        ) {
            val instance: CameraService = LazyLoader.field
            instance.addCallable(
                SetPreviewCallbackCallable(
                    eventListener,
                    withBuffer,
                    listener
                )
            )
        }

        fun setFaceDetectionCallback(
            faceDetectionListener: Camera.FaceDetectionListener?,
            listener: CameraListener?
        ) {
            val instance: CameraService = LazyLoader.field
            instance.addCallable(
                SetFaceDetectionCallback(
                    faceDetectionListener,
                    listener
                )
            )
        }

        fun setDisplayOrientationCallback(angle: Int, listener: CameraListener?) {
            val instance: CameraService = LazyLoader.field
            instance.addCallable(SetDisplayOrientationCallback(angle, listener))
        }

        fun clearQueue() {
            val instance: CameraService = LazyLoader.field
            instance.mServiceHandler.removeMessages(DEFAULT_MSG_TYPE)
        }
    }

    init {
        val cameraHandlerThread = CameraHandlerThread()
        cameraHandlerThread.start()
        mServiceHandler = Handler(cameraHandlerThread.looper) { message: Message ->
            (message.obj as CameraCallable).run()
            true
        }
    }
}