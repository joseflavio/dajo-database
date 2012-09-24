package org.dajo.framework.db.util;

import java.sql.Timestamp;
import java.util.Date;


public final class QueryUtil {

    static public String buildLongOrClause(final String columnName, final int parametersCount) {

        StringBuffer buf = new StringBuffer();

        String columnAssign = " OR " + columnName +"=?";

        for(int i = 0; i < parametersCount; ++i) {
            if (i == 0) {
                buf.append("( " + columnName + "=?");
            }
            else {
                buf.append(columnAssign);
            }
        }// for

        buf.append(" )");

        return buf.toString();

    }

    static public Date toJavaDate(final Timestamp sqlDate) {
        // IS UTC??
        Date javaDate = ( sqlDate != null ? new Date(sqlDate.getTime()) : null );
        return javaDate;
    }

    static public String trim(final String s) {
        if (s == null) {
            return null;
        }
        final String trimmed = s.trim();
        if( trimmed.length() == 0 ) {
            return null;
        }
        return trimmed;
    }

    static public String title(final String original) {

        try {

            if (original == null) {
                return null;
            }

            String[] words = original.split(" ");
            String result = null;
            for (String givenWord : words) {
                String titledWord;
                if (givenWord.length() == 1) {
                    titledWord = givenWord.substring(0, 1).toUpperCase();
                }
                else if (givenWord.length() > 1) {
                    titledWord = givenWord.substring(0, 1).toUpperCase() + givenWord.substring(1).toLowerCase();
                }
                else {
                    titledWord = null;
                }

                if (titledWord != null) {
                    if (result == null) {
                        result = titledWord;
                    }
                    else {
                        result += " " + titledWord;
                    }
                }
            }

            return result;

        }
        catch (Throwable t) {
            t.printStackTrace();
            return original;
        }

    }

}// class
