package app.beacon.core

import android.R.attr.data
import androidx.annotation.DrawableRes
import app.beacon.R.drawable

class Device {
    interface IntoDevice {
        fun getIcon() : Int
        fun deserialize(data: Int) : IntoDevice
        fun serialize(): Int
    }

    enum class DeviceType: IntoDevice {
        Laptop,
        Desktop,
        Mobile,
        Tv,
        Tablet,
        Unknown;

        override fun getIcon() : Int {
            return when(this) {
                Laptop -> {
                    drawable.laptop_minimal
                }
                Desktop -> {
                    drawable.monitor
                }
                Mobile -> {
                    drawable.smartphone
                }
                Tv -> {
                    drawable.tv
                }
                Tablet -> {
                    drawable.tablet
                }
                Unknown -> {
                    drawable.circle_question
                }
            }
        }

        override fun deserialize(data: Int): IntoDevice {
            return when(data) {
                1 -> Laptop
                2 -> Desktop
                3 -> Mobile
                4 -> Tv
                5 -> Tablet
                else -> Unknown
            }
        }

        override fun serialize(): Int {
            return when(this) {
                Laptop -> 1
                Desktop -> 2
                Mobile -> 3
                Tv -> 4
                Tablet -> 5
                Unknown -> 6
            }
        }
    }
}