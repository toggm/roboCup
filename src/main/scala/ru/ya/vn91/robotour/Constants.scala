package ru.ya.vn91.robotour

import java.text.SimpleDateFormat
import java.util.TimeZone
import java.util.Date

object Constants {

	val registrationLength = 1000L * 60 * 60 * 6

	val secsPerTurn = 10

	val tourBrakeTime = 1000L * 60 * secsPerTurn

	val gameTimeout = 1000L * secsPerTurn * 500

	val zagramGameSettings = "3932noT1F0.60."+secsPerTurn

	val freeInviteTime = 180

	val tournamentName = "regular2"

	def timeLongToString(long: Long) = dateFormatter.format(new Date(long))
	def timeStringToLong(s: String) = dateFormatter.parse(s).getTime

	def timeLongToHours(long: Long) = hoursFormatter.format(new Date(long))

	private val dateFormatter = new SimpleDateFormat("yyyy.MM.dd HH:mm")
	hoursFormatter.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"))

	private val hoursFormatter = new SimpleDateFormat("HH:mm")
	hoursFormatter.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"))

}