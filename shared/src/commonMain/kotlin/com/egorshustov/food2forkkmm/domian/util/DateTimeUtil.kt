package com.egorshustov.food2forkkmm.domian.util

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

object DateTimeUtil {

    fun now(): LocalDateTime {
        val currentMoment: Instant = Clock.System.now()
        return currentMoment.toLocalDateTime(TimeZone.UTC)
    }

    fun toLocalDate(date: Double): LocalDateTime =
        Instant.fromEpochMilliseconds(date.toLong()).toLocalDateTime(TimeZone.UTC)

    fun toEpochMilliseconds(date: LocalDateTime): Double = date.toInstant(TimeZone.UTC).toEpochMilliseconds().toDouble()

    fun convertDateToString(date: LocalDateTime?): String? = date?.toString()

    // States: yesterday, today, tomorrow and everything else
    @ExperimentalStdlibApi
    fun humanizeDatetime(date: LocalDateTime?): String {
        val sb = StringBuilder()
        date?.run {
            val hour = if (this.hour > 12) {
                (this.hour - 12).toString() + "pm"
            } else {
                if (this.hour != 0) this.hour.toString() + "am" else "midnight"
            }

            val today = now()
            val tomorrow = Clock.System.now()
                .plus(1, DateTimeUnit.DAY, TimeZone.UTC)
                .toLocalDateTime(TimeZone.UTC)

            when (this.date) {
                today.date -> sb.append("Today at $hour")
                tomorrow.date -> sb.append("Tomorrow at $hour")
                else -> sb.append(this.date.month.name.lowercase() + " ${this.date.dayOfMonth}")
            }
        } ?: sb.append("Unknown")
        return sb.toString()
    }

}