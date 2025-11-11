package app.beacon.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import app.beacon.core.net.types.Kv
import app.beacon.core.request.C
import app.beacon.core.request.Request
import app.beacon.state.PairNg

class Pairing: Service() {
    override fun onBind(intent: Intent?): IBinder? = null

    @OptIn(ExperimentalUnsignedTypes::class)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.getStringExtra(C.ID)?.let { id ->
            PairNg.code.get(id)?.let { ip ->

                when (intent.action) {
                    C.M_PAIR_MODE_1 -> {
                        Request(ip , 4300).sendKv(
                            Kv().apply {
                                put(C.ROUTE,C.M_PAIR_MODE_1)
                            }
                        )
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