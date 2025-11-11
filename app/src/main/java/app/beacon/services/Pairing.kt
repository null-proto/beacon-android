package app.beacon.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import app.beacon.core.database.schema.SecreteStore
import app.beacon.core.net.types.Kv
import app.beacon.core.request.C
import app.beacon.core.request.Request
import app.beacon.state.Globals
import app.beacon.state.PairNg
import app.beacon.state.Session
import kotlin.uuid.ExperimentalUuidApi

class Pairing: Service() {
    override fun onBind(intent: Intent?): IBinder? = null

    @OptIn(ExperimentalUnsignedTypes::class)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.getStringExtra(C.ID)?.let { id ->
            PairNg.code.get(id)?.let { ip ->

                when (intent.action) {
                    C.M_PAIR_MODE_1 -> {
                        Request(ip , 4300).apply {
                            sendKv(
                                Kv().apply {
                                    put(C.ROUTE,C.M_PAIR_MODE_1)
                                    put(C.UUID , Globals.inf.uuid)
                                }
                            )

                            receive()?.getKv()?.get(C.RESULT)?.let {
                                if (it==C.OK) {
                                    Log.d("PairService" , "Transaction success")
                                }
                            }

                        }
                    }

                    C.M_PAIR_MODE_2 -> {}

                    C.M_PAIR_MODE_3 -> {}

                    else -> {}
                }
            }
        }


        return START_NOT_STICKY
    }

    override fun onCreate() {
        super.onCreate()
    }
}