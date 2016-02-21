package com.delta.pragyan16;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class UpcomingWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        if(StorePresent.storepresent==null){
            RemoteViews noviews = new RemoteViews(context.getPackageName(), R.layout.no_widget_events);
            appWidgetManager.updateAppWidget(appWidgetId, noviews);
        }
        else {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.upcoming_widget);
            views.setTextViewText(R.id.widgetEvent, StorePresent.storepresent[0][0]);
            views.setTextViewText(R.id.widgetLocation, StorePresent.storepresent[0][1]);
            views.setTextViewText(R.id.widgetCate, StorePresent.storepresent[0][2]);

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}

