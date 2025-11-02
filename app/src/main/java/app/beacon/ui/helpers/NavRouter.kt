package app.beacon.ui.helpers

import android.content.Context
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController

/*
 * 
 *
 *  this is a sample doc  */
interface NavRouter {
    fun getName() : String
    fun getIcon() : ImageVector? { return null }
    fun getDescription(context: Context): String
    fun onClick(context: Context , navHostController: NavHostController?)
}
