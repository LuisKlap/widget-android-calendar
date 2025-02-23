package com.example.routify

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.util.Log
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 */
class MainWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        Log.d("ROUTIFYLOG.MainWidget.onUpdate", "Updating widgets: ${appWidgetIds.joinToString()}")
        // Delegue a atualização para o HeatmapWidget
        HeatmapWidget().onUpdate(context, appWidgetManager, appWidgetIds)
    }

    override fun onEnabled(context: Context) {
        Log.d("ROUTIFYLOG.MainWidget.onEnabled", "Widget enabled")
        // Delegue a habilitação para o HeatmapWidget
        HeatmapWidget().onEnabled(context)
    }

    override fun onDisabled(context: Context) {
        Log.d("ROUTIFYLOG.MainWidget.onDisabled", "Widget disabled")
        // Delegue a desabilitação para o HeatmapWidget
        HeatmapWidget().onDisabled(context)
    }
}