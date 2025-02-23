package com.example.routify

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import android.widget.RemoteViews

class HeatmapWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        val views = RemoteViews(context.packageName, R.layout.widget_heatmap)

        // Crie uma lista de células (por exemplo, 365 dias)
        val cells = List(365) { HeatmapCell(R.color.gray) }

        // Configure o adapter para o GridView
        val intent = Intent(context, HeatmapGridService::class.java)
        intent.putParcelableArrayListExtra(HeatmapGridService.EXTRA_CELLS, ArrayList(cells))
        intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
        views.setRemoteAdapter(R.id.heatmap_grid, intent)

        // Atualize o widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}

data class HeatmapCell(val colorResId: Int) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(colorResId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HeatmapCell> {
        override fun createFromParcel(parcel: Parcel): HeatmapCell {
            return HeatmapCell(parcel)
        }

        override fun newArray(size: Int): Array<HeatmapCell?> {
            return arrayOfNulls(size)
        }
    }
}
