package com.githubcontributionswidget

import android.appwidget.AppWidgetManager // Importação adicionada
import android.content.ComponentName // Importação adicionada
import com.facebook.react.bridge.Promise // Importação adicionada
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

class MyWidgetModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    override fun getName(): String {
        return "MyWidgetModule"
    }

    @ReactMethod
    fun getActiveWidgetIds(promise: Promise) {
        val appWidgetManager = AppWidgetManager.getInstance(reactApplicationContext)
        val widgetIds = appWidgetManager.getAppWidgetIds(ComponentName(reactApplicationContext, MyWidgetProvider::class.java))
        promise.resolve(widgetIds.toList())
    }

    @ReactMethod
    fun updateWidgetStrings(appWidgetId: Int, title: String, subtitle: String, buttonText: String) {
        currentActivity?.runOnUiThread {
            MyWidgetProvider.updateWidgetStrings(
                reactApplicationContext,
                appWidgetId,
                title,
                subtitle,
                buttonText
            )
        }
    }
}