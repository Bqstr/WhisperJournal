package kz.bqstech.whisperJournal.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

object Utils {



    fun formatDateTime(rfc3339: String): Pair<String, String> {
        // Parse RFC3339 (example: 2025-09-07T19:52:43+05:00)
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
        val date = parser.parse(rfc3339) ?: return "" to ""

        val localCal = Calendar.getInstance()
        localCal.time = date

        val todayCal = Calendar.getInstance()

        // Date part
        val isToday = localCal.get(Calendar.YEAR) == todayCal.get(Calendar.YEAR) &&
                localCal.get(Calendar.DAY_OF_YEAR) == todayCal.get(Calendar.DAY_OF_YEAR)

        val dateStr = if (isToday) {
            "Today, ${localCal.get(Calendar.DAY_OF_MONTH)} " +
                    "${localCal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())}, " +
                    "${localCal.get(Calendar.YEAR)}"
        } else {
            "${localCal.get(Calendar.DAY_OF_MONTH)} " +
                    "${localCal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())}, " +
                    "${localCal.get(Calendar.YEAR)}"
        }

        // Time part (HH:mm 24h)
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val timeStr = timeFormat.format(date)

        return dateStr to timeStr
    }


}