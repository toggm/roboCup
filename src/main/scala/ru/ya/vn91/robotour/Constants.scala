package ru.ya.vn91.robotour

import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

object Constants {

	// Имя турнира?
	// Швейцарка? Импорт рейтов? Проходной рейт? Размер поля? Рейтовость? 
	// Сек/ход? Время ожидания соперника на 1ый ход? 4скрест? 
	// Начальное время? Время регистрации игроков?
	// Швейцарка: количество доп.игр?

	val registrationLength = 1000L * 60 * 60 * 2

	val secsPerTurn = 10

	val tourBrakeTime = 1000L * 60 * secsPerTurn

	val gameTimeout = 1000L * secsPerTurn * 700

	val zagramGameSettings = "3932noT4"+(if (assignRatedGames) "R" else "F")+"0.60."+secsPerTurn

	val tournamentName = """RoboCup XI"""
	// check html templates also!

	val timeWaitingOpponent = 180

	val isKnockout = false

	val assignRatedGames = false

	val rankLimit = 1400

	val importRankInSwiss = true

	def timeLongToString(long: Long) = dateFormatter.format(new Date(long))
	def timeStringToLong(s: String) = dateFormatter.parse(s).getTime

	def timeLongToHours(long: Long) = hoursFormatter.format(new Date(long))

	private val dateFormatter = new SimpleDateFormat("yyyy.MM.dd HH:mm")
	dateFormatter.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"))

	private val hoursFormatter = new SimpleDateFormat("HH:mm")
	hoursFormatter.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"))

}
