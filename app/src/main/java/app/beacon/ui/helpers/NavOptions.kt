package app.beacon.ui.helpers

import android.content.Context
import androidx.navigation.NavHostController

/*
 * 
 *
 *  this is a sample doc  */
interface NavOptions {
    fun getName() : String
    fun getDescription(context: Context): String
    fun onClick(context: Context , navHostController: NavHostController?)
}
