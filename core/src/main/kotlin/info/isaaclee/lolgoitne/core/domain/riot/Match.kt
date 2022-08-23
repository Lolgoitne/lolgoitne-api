package info.isaaclee.lolgoitne.core.domain.riot

import java.text.SimpleDateFormat
import java.util.*

//https://developer.riotgames.com/apis#match-v5/GET_getMatch
data class Match(
  val metadata: Metadata,
  val info: MatchInfo
) {

	fun endDate(pattern: String): String {
		val cal = Calendar.getInstance()
		cal.timeInMillis = info.gameEndTimestamp
		if (TimeZone.getDefault() == TimeZone.getTimeZone("UTC")) {
			cal.add(Calendar.HOUR, 9)
		}
		return SimpleDateFormat(pattern).format(cal.time)
	}

	fun lastDateFromNow(): String {
		var msFromNow = (Calendar.getInstance(Locale.KOREA).timeInMillis - info.gameEndTimestamp) / (1000 * 60)
		var date = ""
		var divideNum: Int
		while (msFromNow > 0L) {
			if (msFromNow >= 43200) {
				divideNum = 60 * 24 * 30
				date += "${msFromNow / divideNum}개월 "
			} else if (msFromNow >= 1440) {
				divideNum = 60 * 24
				date += "${msFromNow / divideNum}일 "
			} else if (msFromNow >= 60) {
				divideNum = 60
				date += "${msFromNow / divideNum}시간 "
			} else {
				divideNum = 1
				date += "${msFromNow}분"
				msFromNow = 0
			}
			msFromNow %= divideNum
		}
		return date
	}
}

data class Metadata(
	val dataVersion: String,
	val matchId: String,
	val participants: List<String>
)

data class MatchInfo(
	val gameCreation: Long,
	val gameDuration: Long,
	val gameEndTimestamp: Long,
	val gameId: String,
	val gameMode: String,
	val gameName: String,
	val gameStartTimestamp: Long,
	val gameType: String,
	val gameVersion: String,
	val mapId: Int,
	val participants: Any?
)
