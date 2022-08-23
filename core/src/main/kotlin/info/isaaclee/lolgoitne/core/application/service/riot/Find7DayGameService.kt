package info.isaaclee.lolgoitne.core.application.service.riot

import info.isaaclee.lolgoitne.core.application.port.`in`.Find7DayGameInPort
import info.isaaclee.lolgoitne.core.application.port.out.http.FindMatchesOutPort
import info.isaaclee.lolgoitne.core.application.port.out.http.FindSummonerOutPort
import info.isaaclee.lolgoitne.core.application.service.riot.exceptions.SummonerNotFoundException
import info.isaaclee.lolgoitne.core.domain.riot.Summoner
import java.util.*
import javax.inject.Named

@Named
class Find7DayGameService(
	private val findSummonerOutPort: FindSummonerOutPort,
	private val findMatchesOutPort: FindMatchesOutPort
): Find7DayGameInPort {
	override fun gameIn7days(nickname: String): String {
		val summoner: Summoner
		try {
			summoner = findSummonerOutPort.findSummonerByNickname(nickname)
		} catch (ex: SummonerNotFoundException) {
			return "${nickname}님을 찾을 수 없었어요!"
		} catch (ex: Exception) {
			return INTERNAL_SERVER_ERROR_MESSAGE
		}
		var count = 0
		val queryParams = mutableMapOf<String, String>()
		val cal = Calendar.getInstance(Locale.KOREA)
		val endTime = (cal.timeInMillis / 1000).toString()
		cal.add(Calendar.HOUR, -168)

		queryParams["startTime"] = (cal.timeInMillis / 1000).toString()
		queryParams["endTime"] = endTime
		queryParams["count"] = "100"

		while (count % 100 == 0) {
			queryParams["start"] = count.toString()
			val matches = findMatchesOutPort.findMatchIds(summoner.puuid, queryParams)
			if (matches.isEmpty()) break
			count += matches.size
			queryParams.remove("start")
		}
		return "${nickname}님은 지난 7일 동안 게임을 ${count}판 했어요."
	}
}