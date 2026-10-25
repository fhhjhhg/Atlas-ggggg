package com.example.util

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.sqrt

/**
 * Detects device shake gestures via the accelerometer.
 *
 * Uses standard normalized g-force calculation:
 * gForce = sqrt(gx^2 + gy^2 + gz^2) where gx = ax / GRAVITY_EARTH.
 * Includes a debounce interval to prevent multiple triggers from a single continuous shake.
 */
class ShakeDetector(
    private val shakeThresholdGForce: Float = 2.7f,
    private val debounceMillis: Long = 800L,
    private val onShake: () -> Unit
) : SensorEventListener {

    private var sensorManager: SensorManager? = null
    private var accelerometer: Sensor? = null
    private var lastShakeTimestamp: Long = 0L

    /**
     * Registers the listener on the provided SensorManager.
     * @return true if accelerometer sensor was found and registered, false otherwise.
     */
    fun start(manager: SensorManager): Boolean {
        sensorManager = manager
        accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        return accelerometer?.let { sensor ->
            manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI)
        } ?: false
    }

    /**
     * Unregisters the listener.
     */
    fun stop() {
        sensorManager?.unregisterListener(this)
        sensorManager = null
        accelerometer = null
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null || event.sensor.type != Sensor.TYPE_ACCELEROMETER) return

        val gX = event.values[0] / SensorManager.GRAVITY_EARTH
        val gY = event.values[1] / SensorManager.GRAVITY_EARTH
        val gZ = event.values[2] / SensorManager.GRAVITY_EARTH

        // Net g-force vector magnitude
        val gForce = sqrt((gX * gX + gY * gY + gZ * gZ).toDouble()).toFloat()

        if (gForce >= shakeThresholdGForce) {
            val now = System.currentTimeMillis()
            if (now - lastShakeTimestamp >= debounceMillis) {
                lastShakeTimestamp = now
                onShake()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // No-op for shake detection
    }

    /**
     * Allows simulated shake dispatch for test environments and emulator UI actions.
     */
    fun simulateShake() {
        val now = System.currentTimeMillis()
        if (now - lastShakeTimestamp >= debounceMillis) {
            lastShakeTimestamp = now
            onShake()
        }
    }
}
