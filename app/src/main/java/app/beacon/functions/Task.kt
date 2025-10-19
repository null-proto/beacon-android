package app.beacon.functions

import android.content.Context
import app.beacon.core.database.schema.Pop

interface Task<T> {
    fun run(context: Context , arg : T)
}