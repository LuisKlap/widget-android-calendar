package com.example.routify

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService

class HeatmapGridService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return HeatmapGridRemoteViewsFactory(this.applicationContext, intent)
    }

    companion object {
        const val EXTRA_CELLS = "extra_cells"
    }
}

class HeatmapGridRemoteViewsFactory(private val context: Context, private val intent: Intent) :
        RemoteViewsService.RemoteViewsFactory {

    private val cells = mutableListOf<HeatmapCell>()

    override fun onCreate() {
        // Inicialize as células
        val parcelableCells =
                intent.getParcelableArrayListExtra<HeatmapCell>(HeatmapGridService.EXTRA_CELLS)
        cells.addAll(parcelableCells ?: emptyList())
    }

    override fun getViewAt(position: Int): RemoteViews {
        val cell = cells[position]
        val remoteViews = RemoteViews(context.packageName, R.layout.heatmap_cell)
        remoteViews.setInt(R.id.cell_view, "setBackgroundResource", cell.colorResId)
        return remoteViews
    }

    override fun getCount(): Int = cells.size

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long = position.toLong()

    override fun hasStableIds(): Boolean = true

    override fun onDataSetChanged() {}

    override fun onDestroy() {}

    override fun getLoadingView(): RemoteViews? {
        return null
    }
}
