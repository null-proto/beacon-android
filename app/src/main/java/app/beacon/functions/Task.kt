package app.beacon.functions

import android.content.Context

interface Task<T> {
    fun run(context: Context , arg : T)
}