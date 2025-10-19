package app.beacon.functions

import android.content.Context
import app.beacon.core.database.schema.Pop
import app.beacon.functions.MakeNotification.Args

class MakeNotification : Task<Args> {
    data class Args(
        val title : String,
        val message : String,
        val whois : Pop
    )

    override fun run(context: Context, arg: Args) {
        
    }
}