package com.voltageos.faceunlock.camera.listeners

interface CameraEventListener {
    fun onEventCallback(data: Int, value: Any?)
}