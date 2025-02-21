package com.example.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.widget.RemoteViews
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetIds: IntArray
    ) {
        Log.d("ABDGCHDGAJSKVCJV", "onUpdate")
        for (appWidgetId in appWidgetIds) {
            val views = RemoteViews(context.packageName, R.layout.widget_layout)

            // Configurar o PendingIntent para o clique no botão de pesquisa
            val intent =
                    Intent(context, MyWidgetProvider::class.java).apply {
                        action = "com.example.widget.SEARCH_COMMITS"
                    }
            val pendingIntent =
                    PendingIntent.getBroadcast(
                            context,
                            appWidgetId,
                            intent,
                            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )
            views.setOnClickPendingIntent(R.id.search_button, pendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("ABDGCHDGAJSKVCJV", "onReceive")
        super.onReceive(context, intent)
        if (intent.action == "com.example.widget.SEARCH_COMMITS") {
            val appWidgetId =
                    intent.getIntExtra(
                            AppWidgetManager.EXTRA_APPWIDGET_ID,
                            AppWidgetManager.INVALID_APPWIDGET_ID
                    )
            val username = "LuisKlap" // Obtenha o nome de usuário de outra forma
            updateWidgetData(context, appWidgetId, username)
        } else if (intent.action == "com.example.widget.UPDATE_WIDGET") {
            val appWidgetId =
                    intent.getIntExtra(
                            AppWidgetManager.EXTRA_APPWIDGET_ID,
                            AppWidgetManager.INVALID_APPWIDGET_ID
                    )
            if (appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                updateWidgetData(context, appWidgetId)
            }
        }
    }

    private fun updateWidgetData(context: Context, appWidgetId: Int) {
        Log.d("ABDGCHDGAJSKVCJV", "updateWidgetData1")
        val views = RemoteViews(context.packageName, R.layout.widget_layout)
        val service = GitHubService()

        CoroutineScope(Dispatchers.IO).launch {
            val totalCommits = service.fetchContributions("LuisKlap")
            Log.d("ABDGCHDGAJSKVCJV", "totalCommits: $totalCommits")
            withContext(Dispatchers.Main) {
                // Atualize o RemoteViews aqui
                views.setTextViewText(R.id.commit_count, "Total Commits: $totalCommits")
                AppWidgetManager.getInstance(context).updateAppWidget(appWidgetId, views)
            }
        }
    }

    private fun updateWidgetData(context: Context, appWidgetId: Int, username: String) {
        Log.d("ABDGCHDGAJSKVCJV", "updateWidgetData2")
        val views = RemoteViews(context.packageName, R.layout.widget_layout)
        Log.d("ABDGCHDGAJSKVCJV", "views: $views")
        views.setTextViewText(R.id.username_display, username)
        val service = GitHubService()
        Log.d("ABDGCHDGAJSKVCJV", "service: $service")

        CoroutineScope(Dispatchers.IO).launch {
            val totalCommits = service.fetchContributions(username)
            Log.d("ABDGCHDGAJSKVCJV", "totalCommits: $totalCommits")
            withContext(Dispatchers.Main) {
                views.setTextViewText(R.id.commit_count, "Total Commits: $totalCommits")
                AppWidgetManager.getInstance(context).updateAppWidget(appWidgetId, views)
            }
        }
    }

    private fun getColorForCommits(commits: Int): Int {
        Log.d("ABDGCHDGAJSKVCJV", "getColorForCommits")
        return when {
            commits > 30 -> Color.parseColor("#216e39") // Verde escuro
            commits > 20 -> Color.parseColor("#30a14e") // Verde médio
            commits > 10 -> Color.parseColor("#40c463") // Verde claro
            commits > 0 -> Color.parseColor("#9be9a8") // Verde muito claro
            else -> Color.parseColor("#ebedf0") // Cinza claro
        }
    }
}
