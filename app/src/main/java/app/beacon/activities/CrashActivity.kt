package app.beacon.activities

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat


class CrashActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setTitle("CRASH!!!")

        WindowCompat.setDecorFitsSystemWindows(window, false)
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, insets ->
            v.setPadding(0, 0, 0, 0)
            insets
        }

        setContent {

            Column {
                Text(intent.getStringExtra("error") ?: "UnSpecified")
                Text(intent.getStringExtra("message") ?: "No Information")
                Text(intent.getStringExtra("s_tree") ?: "")
            }
        }
    }
}