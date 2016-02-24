package com.delta.pragyan16;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.Toast;


import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Implementation of App Widget functionality.
 */
public class UpcomingWidget extends AppWidgetProvider {


    static Calendar timenow;
    static int a,b,c,d,checkintent=0;


    public static String MY_WIDGET_UPDATE = "MY_OWN_WIDGET_UPDATE";


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        super.onReceive(context, intent);
        if (intent.getAction().equals(MY_WIDGET_UPDATE)) {
            checkintent=0;
            Toast.makeText(context, "Alarm Intent", Toast.LENGTH_LONG).show();

        }
        else {//if(intent.getAction().equals(WidgetUtils.WIDGET_UPDATE_ACTION)){
            checkintent = 1;
            Toast.makeText(context, "Button Intent", Toast.LENGTH_LONG).show();
        }
       /* else {
            Toast.makeText(context, "Extra Intent", Toast.LENGTH_LONG).show();
            checkintent = 0;
        }*/


        Bundle extras = intent.getExtras();
        if(extras!=null) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName thisAppWidget = new ComponentName(context.getPackageName(), UpcomingWidget.class.getName());
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);

            onUpdate(context, appWidgetManager, appWidgetIds);
        }
        //Toast.makeText(context, "onReceiver()", Toast.LENGTH_LONG).show();

    }

    @Override
    /*public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
    }*/
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        Intent active = new Intent(context, UpcomingWidget.class);
        PendingIntent actionPendingIntent = PendingIntent.getBroadcast(context, 0, active, 0);
        //Toast.makeText(context, "onUpdate()", Toast.LENGTH_LONG).show();
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            // initializing widget layout
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.upcoming_widget2);

            // register for button event
            if(checkintent==1) {
                // active = new Intent(context, UpcomingWidget.class);
                //active.setAction(ACTION_WIDGET_ABOUT);
                actionPendingIntent = PendingIntent.getBroadcast(context, 0, active, 0);
                //remoteViews.setOnClickPendingIntent(R.id.Next, actionPendingIntent);
                remoteViews.setOnClickPendingIntent(R.id.Next, buildButtonPendingIntent(context));
                //Toast.makeText(context, "Button Check", Toast.LENGTH_LONG).show();

                // updating view with initial data

                // request for widget update
                pushWidgetUpdate(context, remoteViews);
            }
            if (checkintent == 0){
                updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
            //Toast.makeText(context, "Alarm? Check", Toast.LENGTH_LONG).show();
             }


        }
    }


    public static PendingIntent buildButtonPendingIntent(Context context) {

        ++StorePresent.pos;//++MyWidgetIntentReceiver.clickCount;  //StorePresent pos
        if(StorePresent.tie>0)
            StorePresent.pos%=StorePresent.tie;
        else
            StorePresent.pos=0;
        // initiate widget update request
        Intent intent = new Intent();

        intent.setAction(WidgetUtils.WIDGET_UPDATE_ACTION);
        //intent.setAction(ACTION_WIDGET_ABOUT);
        return PendingIntent.getBroadcast(context, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);
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
        StorePresent.showsort();

        // Construct the RemoteViews object
        if(StorePresent.tie==0){
            RemoteViews noviews = new RemoteViews(context.getPackageName(), R.layout.no_widget_events);
            //appWidgetManager.updateAppWidget(appWidgetId, noviews);
            pushWidgetUpdate(context.getApplicationContext(),
                    noviews);
        }
        else {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.upcoming_widget2);
           // String[] temp=StorePresent.storepresent[widcount];
            timenow=new GregorianCalendar(TimeZone.getTimeZone("GMT+5:30"));
            if(StorePresent.tie>0)
                StorePresent.pos%=StorePresent.tie;
            else
                StorePresent.pos=0;

            Calendar time5 = new GregorianCalendar(TimeZone.getTimeZone("GMT+5:30"));
            time5.set(timenow.get(Calendar.YEAR), timenow.get(Calendar.MONTH), StorePresent.showtime[StorePresent.pos][0], StorePresent.showtime[StorePresent.pos][1], StorePresent.showtime[StorePresent.pos][2]);

            Calendar time6 = new GregorianCalendar(TimeZone.getTimeZone("GMT+5:30"));
            time6.set(timenow.get(Calendar.YEAR), timenow.get(Calendar.MONTH), StorePresent.showtime[StorePresent.pos][3], StorePresent.showtime[StorePresent.pos][4], StorePresent.showtime[StorePresent.pos][5]);


            //calculates time left for begining an event
            if (time5.after(timenow)) {

                a =((int)((time5.getTimeInMillis() - timenow.getTimeInMillis())/3600000));
                b =((int)((time5.getTimeInMillis() - timenow.getTimeInMillis())/60000))%60;

                if(a!=0)
                    views.setTextViewText(R.id.widgetTime, "begins in " + a + " hours " + b + " mins ");
                else
                    views.setTextViewText(R.id.widgetTime, "begins in "  + b + " mins ");

            }
            //calculates time left for end of an ongoing event
            else if (time6.before(timenow)) {


                views.setTextViewText(R.id.widgetTime, "ends in 0 mins ");
            }
            else {

                c = ((int)((time6.getTimeInMillis() - timenow.getTimeInMillis())/3600000));
                d =((int)((time6.getTimeInMillis() - timenow.getTimeInMillis())/60000))%60;
                if(c!=0)
                    views.setTextViewText(R.id.widgetTime, "ends in " + c + " hours " + d + " mins ");
                else
                    views.setTextViewText(R.id.widgetTime, "ends in " + d + " mins ");

            }
                StorePresent.pos%=StorePresent.tie;
                views.setTextViewText(R.id.widgetEvent, StorePresent.showpresent[StorePresent.pos][0]);
                views.setTextViewText(R.id.widgetLocation, StorePresent.showpresent[StorePresent.pos][1]);
                views.setTextViewText(R.id.widgetCate, StorePresent.showpresent[StorePresent.pos][2]);

                // Instruct the widget manager to update the widget
                //appWidgetManager.updateAppWidget(appWidgetId, views);
            pushWidgetUpdate(context.getApplicationContext(),
                    views);
        }

    }
    public static void pushWidgetUpdate(Context context, RemoteViews remoteViews) {
        ComponentName myWidget = new ComponentName(context,
                UpcomingWidget.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(myWidget, remoteViews);
    }
}

