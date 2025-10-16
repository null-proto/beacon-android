package app.beacon.core.routes

import android.content.Context
import app.beacon.core.PairBox

interface Task {
    fun work(context: Context, data: PairBox)
}