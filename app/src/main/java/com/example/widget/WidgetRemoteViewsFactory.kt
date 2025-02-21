package com.example.widget

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WidgetRemoteViewsFactory(private val context: Context, intent: Intent) : RemoteViewsService.RemoteViewsFactory {

    private var totalCommits: Int = 0

    override fun onCreate() {
        // Inicialização
    }

    override fun onDataSetChanged() {
        CoroutineScope(Dispatchers.IO).launch {
            val service = GitHubService()
            totalCommits = service.fetchContributions("username")
        }
    }

    override fun onDestroy() {
        // Limpeza
    }

    override fun getCount(): Int {
        return 84 // 7 dias por semana * 12 semanas
    }

    override fun getViewAt(position: Int): RemoteViews {
        val views = RemoteViews(context.packageName, R.layout.widget_item)
        views.setInt(R.id.square, "setBackgroundColor", getColorForCommits(totalCommits))
        return views
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    private fun getColorForCommits(commits: Int): Int {
        return when {
            commits > 30 -> Color.parseColor("#216e39") // Verde escuro
            commits > 20 -> Color.parseColor("#30a14e") // Verde médio
            commits > 10 -> Color.parseColor("#40c463") // Verde claro
            commits > 0 -> Color.parseColor("#9be9a8") // Verde muito claro
            else -> Color.parseColor("#ebedf0") // Cinza claro
        }
    }
}