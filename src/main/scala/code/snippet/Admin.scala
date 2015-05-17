package code.snippet

import code.comet.GlobalStatusSingleton
import code.comet.TournamentStatus._
import net.liftweb.common.Loggable
import net.liftweb.http._
import net.liftweb.http.js.JsCmd
import net.liftweb.http.js.JsCmds._
import ru.ya.vn91.robotour.Constants._
import ru.ya.vn91.robotour._
import ru.ya.vn91.robotour.zagram.{ AssignGame, MessageToZagram, PlayerInfo }
import scala.concurrent.duration._

object Admin extends Loggable {

	def setTime() = SHtml.onSubmit { timeAsString =>
		try {
			logger.info(s"setting tournament time: $timeAsString")
			val startTime = stringToDateTime(timeAsString).getMillis
			val regTime = startTime - registrationPeriod.toMillis
			if (startTime < System.currentTimeMillis ||
				startTime > System.currentTimeMillis + 28.days.toMillis) {
				Alert("Кажется, вы ошиблись с датой, она выглядит неправильно. \n" +
					"На всякий случай я вас остановлю.")
			} else {
				Core.core ! StartRegistration(regTime)
				SetValById("timeSetter", "time set.")
			}
		} catch {
			case t: Exception => Alert("Неправильный формат даты.")
		}
	}

	def register = SHtml.onSubmit { nick =>
		val trimmed = nick.trim
		if (trimmed.nonEmpty) {
			logger.info(s"registered $trimmed")
			Core.core ! TryRegister(PlayerInfo(trimmed, 1200, 0, 0, 0))
			SetValById("playerRegistrar", "")
		} else {
			logger.debug(s"tried to register, but failed: '$trimmed'")
			Alert("Пустое или некорректное имя, не буду добавлять в список игроков.")
		}
	}

	def setStatus() = SHtml.onSubmit { status =>
		logger.info(s"setting status $status")
		GlobalStatusSingleton ! CustomStatus(status)
		SetValById("setStatus", "")
	}

	def writeToZagram() = SHtml.onSubmit { message =>
		logger.info(s"writing to zagram message: $message")
		Core.toZagramActor ! MessageToZagram(message)
		SetValById("writeToZagram", "")
	}

	def winGame = SHtml.onSubmit { twoPlayers =>
		val split = twoPlayers.split('/')
		if (split.length != 2) {
			Alert("ERROR")
		} else {
			val winner = split(0)
			val looser = split(1)
			logger.info(s"assigning game result: $winner > $looser")
			Core.core ! GameWon(winner, looser)
			SetValById("winGame", "OK, game result sent")
		}
	}

	def assignGame = SHtml.onSubmit { twoPlayers =>
		(for {
			first <- twoPlayers.split('/').lift(0)
			second <- twoPlayers.split('/').lift(1)
			if twoPlayers.split('/').length == 2
		} yield {
			logger.info(s"assigning game: $twoPlayers")
			Core.toZagramActor ! AssignGame(first.trim, second.trim, infiniteTime = false)
			SetValById("assignGame", "OK, assigned")
		}).getOrElse[JsCmd] {
			Alert("ERROR")
		}
	}

}
