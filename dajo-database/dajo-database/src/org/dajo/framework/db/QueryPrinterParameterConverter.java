package org.dajo.framework.db;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public interface QueryPrinterParameterConverter {

    public String convertParameterToPrint(final Object paramValue);

    static public class OldDefault implements QueryPrinterParameterConverter {
        @Override
        public String convertParameterToPrint(final Object paramValue) {
            final String printedParam;
            if (paramValue instanceof String) {
                printedParam = "'" + (String) paramValue + "'";
            } else if (paramValue instanceof Date) {
                printedParam = "'" + paramValue.toString() + "'";
            } else {
                printedParam = paramValue.toString();
            }
            return printedParam;
        }
    }

    static public class Default implements QueryPrinterParameterConverter {
        @Override
        public String convertParameterToPrint(final Object paramValue) {
            final String printedParam;
            if (paramValue == null) {
                printedParam = "NULL";
            } else if (paramValue instanceof String) {
                printedParam = "'" + (String) paramValue + "'";
            } else if (paramValue instanceof Integer) {
                printedParam = ((Integer) paramValue).toString();
            } else if (paramValue instanceof Long) {
                printedParam = ((Long) paramValue).toString();
            } else if (paramValue instanceof Date) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); // Maybe add ZZZ
                printedParam = "'" + format.format((Date) paramValue) + "'";
            } else if (paramValue instanceof QueryTypeGmtTimeInMillis) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                printedParam = "'" + format.format(new Date(((QueryTypeGmtTimeInMillis) paramValue).getMillis())) + "'";
            } else if (paramValue instanceof QueryTypeGmtTimeInNanos) {
                long gmtTimeInNanos = ((QueryTypeGmtTimeInNanos) paramValue).getNanos();
                long gmtTimeInMillis = TimeUnit.MILLISECONDS.convert(gmtTimeInNanos, TimeUnit.NANOSECONDS);
                long onlyNanos = (gmtTimeInNanos - TimeUnit.NANOSECONDS.convert(gmtTimeInMillis, TimeUnit.MILLISECONDS));
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                printedParam = "'" + format.format(new Date(gmtTimeInMillis)) + " " + onlyNanos + "'";
            } else {
                printedParam = "!!!'" + paramValue.toString() + "'!!!";
            }
            return printedParam;
        }
    }

}// class
