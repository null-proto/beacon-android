package app.beacon.core.routes

import android.content.Context
import app.beacon.core.PairBox

interface Path {
    val name : String
    fun work(context: Context, data: PairBox)
}