package core.util

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object TimeHelper {
    fun getRecordingOutputFileName(date: Instant): String {
        val localDateTime = date.toLocalDateTime(TimeZone.UTC)

        val formattedDate = "${localDateTime.year}-" +
                localDateTime.monthNumber.toString().padStart(2, '0') + "-" +
                localDateTime.dayOfMonth.toString().padStart(2, '0')

        val hour = if (localDateTime.hour > 12) localDateTime.hour - 12 else if (localDateTime.hour == 0) 12 else localDateTime.hour
        val amPm = if (localDateTime.hour < 12) "AM" else "PM"
        val formattedTime = "${hour.toString().padStart(2, '0')}." +
                localDateTime.minute.toString().padStart(2, '0') + "." +
                localDateTime.second.toString().padStart(2, '0') +
                " $amPm"

        return "Screen Recording $formattedDate at $formattedTime"
    }

    fun getRecordingOutputFileName(): String {
        return getRecordingOutputFileName(Clock.System.now())
    }

}