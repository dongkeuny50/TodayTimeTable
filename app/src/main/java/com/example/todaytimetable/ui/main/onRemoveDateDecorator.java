package com.example.todaytimetable.ui.main;

import android.annotation.SuppressLint;
import android.graphics.Color;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class onRemoveDateDecorator implements DayViewDecorator {

        private CalendarDay date;
        public onRemoveDateDecorator(String saveddate) throws ParseException {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-M-d");

            Date to = transFormat.parse(String.valueOf(saveddate));
            date = CalendarDay.from(to);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return false;
        }

        @Override
        public void decorate(DayViewFacade view) {
        }

        /**
         * We're changing the internals, so make sure to call {@linkplain MaterialCalendarView#invalidateDecorators()}
         */
        public void setDate(Date date) {
            this.date = CalendarDay.from(date);
        }
    }


