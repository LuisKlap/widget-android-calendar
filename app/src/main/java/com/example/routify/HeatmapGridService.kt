package com.example.routify

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import android.util.Log

class HeatmapGridService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return HeatmapGridRemoteViewsFactory(this.applicationContext, intent)
    }

    companion object {
        const val EXTRA_CELLS = "extra_cells"
    }

    fun updateGridView(context: Context, views: RemoteViews) {
        try {
            views.setInt(R.id.heatmap_grid, "setBackgroundResource", R.color.gray)
            Log.d("ROUTIFYLOG.HeatmapGridService.updateGridView", "heatmap_grid encontrado no layout")
        } catch (e: Exception) {
            Log.e("ROUTIFYLOG.HeatmapGridService.updateGridView", "heatmap_grid não encontrado no layout", e)
        }
    }
}

class HeatmapGridRemoteViewsFactory(private val context: Context, private val intent: Intent) :
        RemoteViewsService.RemoteViewsFactory {

    private val cells = mutableListOf<HeatmapCell>()

    override fun onCreate() {
        Log.d("ROUTIFYLOG.HeatmapGridRemoteViewsFactory.onCreate", "Factory created")
        val parcelableCells =
                intent.getParcelableArrayListExtra<HeatmapCell>(HeatmapGridService.EXTRA_CELLS)
        cells.addAll(parcelableCells ?: emptyList())
        Log.d("ROUTIFYLOG.HeatmapGridRemoteViewsFactory.onCreate", "Cells loaded: $cells")
    }

    override fun getViewAt(position: Int): RemoteViews {
        val cell = cells[position]
        val remoteViews = RemoteViews(context.packageName, R.layout.heatmap_cell)
        remoteViews.setInt(R.id.cell_view, "setBackgroundResource", cell.colorResId)
        Log.d("ROUTIFYLOG.HeatmapGridRemoteViewsFactory.getViewAt", "Returning view for position $position with cell: $cell")
        return remoteViews
    }

    override fun getCount(): Int {
        Log.d("ROUTIFYLOG.HeatmapGridRemoteViewsFactory.getCount", "Count: ${cells.size}")
        return cells.size
    }

    override fun getViewTypeCount(): Int {
        Log.d("ROUTIFYLOG.HeatmapGridRemoteViewsFactory.getViewTypeCount", "ViewTypeCount: 1")
        return 1
    }

    override fun getItemId(position: Int): Long {
        Log.d("ROUTIFYLOG.HeatmapGridRemoteViewsFactory.getItemId", "ItemId: $position")
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        Log.d("ROUTIFYLOG.HeatmapGridRemoteViewsFactory.hasStableIds", "HasStableIds: true")
        return true
    }

    override fun onDataSetChanged() {
        Log.d("ROUTIFYLOG.HeatmapGridRemoteViewsFactory.onDataSetChanged", "Data set changed")
    }

    override fun onDestroy() {
        Log.d("ROUTIFYLOG.HeatmapGridRemoteViewsFactory.onDestroy", "Factory destroyed")
    }

    override fun getLoadingView(): RemoteViews? {
        Log.d("ROUTIFYLOG.HeatmapGridRemoteViewsFactory.getLoadingView", "LoadingView: null")
        return null
    }
}
