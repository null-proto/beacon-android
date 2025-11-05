package app.beacon.modules

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import app.beacon.R
import app.beacon.core.routes.Args
import app.beacon.core.routes.Module
import app.beacon.state.Globals
import kotlin.contracts.contract

object Ring : Module{
    override val name: String = "play-sound"

    override suspend fun work(args: Args): Module.Result {

        if (ActivityCompat.checkSelfPermission(
                args.state.context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
            val ringtone = RingtoneManager.getRingtone(args.state.context,uri)
            ringtone.isLooping = true

            val vibrator = args.state.context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            val vibEffect = VibrationEffect.createWaveform(longArrayOf(0,800,400,800,400,1000),intArrayOf(0,255,0,255,0,255) , 0)

            ringtone.play()
            vibrator.defaultVibrator.vibrate(vibEffect)
            Log.d("Ring" , "Playing sound")

            return Module.Result.ok()
        } else {
            return Module.Result.error(code = 10)
        }
    }
}