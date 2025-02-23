package com.example.routify

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.widget.RemoteViews

class HeatmapWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        Log.d("ROUTIFYLOG.HeatmapWidget.onUpdate", "Updating widgets: ${appWidgetIds.joinToString()}")
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        Log.d("ROUTIFYLOG.HeatmapWidget.onEnabled", "Widget enabled")
    }

    override fun onDisabled(context: Context) {
        Log.d("ROUTIFYLOG.HeatmapWidget.onDisabled", "Widget disabled")
    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        Log.d("ROUTIFYLOG.HeatmapWidget.updateAppWidget", "Updating app widget with ID: $appWidgetId")
        val views = RemoteViews(context.packageName, R.layout.main_widget)

        // Verifique se o GridView está presente no layout
        try {
            views.setInt(R.id.heatmap_grid, "setBackgroundResource", R.color.gray)
            Log.d("ROUTIFYLOG.HeatmapWidget.updateAppWidget", "heatmap_grid encontrado no layout")
        } catch (e: Exception) {
            Log.e("ROUTIFYLOG.HeatmapWidget.updateAppWidget", "heatmap_grid não encontrado no layout", e)
        }

        // Crie uma lista de células (84 células para 12 colunas x 7 linhas)
        val cells = List(84) { HeatmapCell(R.color.gray) }
        Log.d("ROUTIFYLOG.HeatmapWidget.updateAppWidget", "Created cells: $cells")

        // Configure o adapter para o GridView
        val intent = Intent(context, HeatmapGridService::class.java)
        intent.putParcelableArrayListExtra(HeatmapGridService.EXTRA_CELLS, ArrayList(cells))
        intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
        views.setRemoteAdapter(R.id.heatmap_grid, intent)
        Log.d("ROUTIFYLOG.HeatmapWidget.updateAppWidget", "Set RemoteViews adapter")

        // Atualize o widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
        Log.d("ROUTIFYLOG.HeatmapWidget.updateAppWidget", "App widget updated")
    }
}

data class HeatmapCell(val colorResId: Int) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(colorResId)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<HeatmapCell> {
        override fun createFromParcel(parcel: Parcel): HeatmapCell {
            return HeatmapCell(parcel)
        }

        override fun newArray(size: Int): Array<HeatmapCell?> {
            return arrayOfNulls(size)
        }
    }
}
