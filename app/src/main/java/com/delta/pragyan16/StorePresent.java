package com.delta.pragyan16;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by Lenovo on 2/21/2016.
 */
public class StorePresent {
    static int si=0;
    static String[][] storepresent=null;
    static int[][] storetime=null;
    static String[][] showpresent=null;
    static int[][] showtime=null;
    static int tie=0;
    static int pos=1;

    static void showsort(){    //function to extract needed elements from database

        Calendar timenow=new GregorianCalendar(TimeZone.getTimeZone("GMT+5:30"));
        String[] temp;
        int[] tem;
        int ti;
        tie=0;


        int timelimit=1;

        int[][] prtime=new int[si][6];
        String[][] store=new String[si][3];

        for(int i=0;i<si;i++){  //sorts list of event according to event start time, uses bubble sort

            for(int j=i;j<si-1-i;j++){
                Calendar time1=new GregorianCalendar(TimeZone.getTimeZone("GMT+5:30"));
                Calendar time2=new GregorianCalendar(TimeZone.getTimeZone("GMT+5:30"));
                time1.set(timenow.get(Calendar.YEAR), timenow.get(Calendar.MONTH), storetime[j][0], storetime[j][1], storetime[j][2]);
                time2.set(timenow.get(Calendar.YEAR), timenow.get(Calendar.MONTH), storetime[j + 1][0], storetime[j + 1][1], storetime[j + 1][2]);


                if(time1.after(time2)){
                    temp=storepresent[j];
                    storepresent[j]=storepresent[j+1];
                    storepresent[j+1]=temp;

                    tem=storetime[j];
                    storetime[j]=storetime[j+1];
                    storetime[j+1]=tem;


                }
            }
        }


        Calendar time3=new GregorianCalendar(TimeZone.getTimeZone("GMT+5:30"));   //current time plus 1/2 hours depending on time limit selected in spinner list
        time3.set(Calendar.YEAR,timenow.get(Calendar.YEAR));
        time3.set(Calendar.MONTH,timenow.get(Calendar.MONTH));
        time3.set(Calendar.DATE,timenow.get(Calendar.DATE));
        time3.set(Calendar.HOUR_OF_DAY,timenow.get(Calendar.HOUR_OF_DAY)+timelimit);
        time3.set(Calendar.MINUTE,timenow.get(Calendar.MINUTE));


        //checks if time 3 extends over to next day i.e. it crosses midnight
        if(time3.get(Calendar.HOUR_OF_DAY)>23){
            time3.set(Calendar.HOUR_OF_DAY,((timenow.get(Calendar.HOUR_OF_DAY))-24));
            time3.set(Calendar.DATE,(timenow.get(Calendar.DATE)+1));
        }


        ti=0;


        //to select events based on category selected in spinner list
        for(int k=0;k<si;k++) {

            Calendar time4 = new GregorianCalendar(TimeZone.getTimeZone("GMT+5:30"));
            time4.set(timenow.get(Calendar.YEAR), timenow.get(Calendar.MONTH), storetime[k][0], storetime[k][1], storetime[k][2]);

            Calendar time5 = new GregorianCalendar(TimeZone.getTimeZone("GMT+5:30"));
            time5.set(timenow.get(Calendar.YEAR), timenow.get(Calendar.MONTH), storetime[k][3], storetime[k][4], storetime[k][5]);

            if ((time4.before(time3)) && timenow.before(time5)) {




                    store[ti] = storepresent[k];
                    prtime[ti] = storetime[k];
                    ti++;

            }
        }



        showpresent=new String[ti][3];
        showtime=new int[ti][6];

        //final sorted event list
        for (int q = 0; q < ti; q++) {

            showpresent[q] = store[q];
            showtime[q]=prtime[q];


        }
        tie=ti;
    }

}
