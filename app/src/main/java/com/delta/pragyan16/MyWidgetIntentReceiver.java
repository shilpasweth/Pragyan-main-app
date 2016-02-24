package com.delta.pragyan16;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;


import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by Lenovo on 2/24/2016.
 */
public class MyWidgetIntentReceiver extends BroadcastReceiver {
    public static int clickCount = 0;
    private String msg[] = {"cat","dog","rat"};
    static Calendar timenow;
    static int a,b,c,d;


    @Override
    public void onReceive(Context context, Intent intent) {

       // if (intent.getAction().equals(WidgetUtils.WIDGET_UPDATE_ACTION)) {


            updateWidgetPictureAndButtonListener(context);
       // }
    }


    private void updateWidgetPictureAndButtonListener(Context context) {

        /*RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.upcoming_widget);*/
        StorePresent.showsort();

        // Construct the RemoteViews object
        if(StorePresent.tie==0){
            RemoteViews noviews = new RemoteViews(context.getPackageName(), R.layout.no_widget_events);
            noviews.setOnClickPendingIntent(R.id.Next,
                    UpcomingWidget.buildButtonPendingIntent(context));
            UpcomingWidget.pushWidgetUpdate(context.getApplicationContext(),
                    noviews);
        }
        else {

            if(StorePresent.tie>0)
                StorePresent.pos%=StorePresent.tie;
            else
                StorePresent.pos=0;
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.upcoming_widget2);
           // String[] temp=StorePresent.storepresent[widcount];
            timenow=new GregorianCalendar(TimeZone.getTimeZone("GMT+5:30"));

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


                views.setTextViewText(R.id.widgetTime, "ends in 0 hours 0 mins ");
            }
            else {

                c = ((int)((time6.getTimeInMillis() - timenow.getTimeInMillis())/3600000));
                d =((int)((time6.getTimeInMillis() - timenow.getTimeInMillis())/60000))%60;
                if(c!=0)
                    views.setTextViewText(R.id.widgetTime, "ends in " + c + " hours " + d + " mins ");
                else
                    views.setTextViewText(R.id.widgetTime, "ends in " + d + " mins ");

            }

                views.setTextViewText(R.id.widgetEvent, StorePresent.showpresent[StorePresent.pos][0]);
                views.setTextViewText(R.id.widgetLocation, StorePresent.showpresent[StorePresent.pos][1]);
                views.setTextViewText(R.id.widgetCate, StorePresent.showpresent[StorePresent.pos][2]);

                // Instruct the widget manager to update the widget
            views.setOnClickPendingIntent(R.id.Next,
                    UpcomingWidget.buildButtonPendingIntent(context));
            UpcomingWidget.pushWidgetUpdate(context.getApplicationContext(),
                    views);

        }

    }


}