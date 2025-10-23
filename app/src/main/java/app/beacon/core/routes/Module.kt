package app.beacon.core.routes

import android.content.Context
import app.beacon.core.PairBox

interface Module {
    val name : String
    suspend fun work(context: Context, data: PairBox)
}