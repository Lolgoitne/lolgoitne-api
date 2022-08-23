package info.isaaclee.lolgoitne.core.application.service.riot

import info.isaaclee.lolgoitne.core.application.port.`in`.CheckInGameInPort
import info.isaaclee.lolgoitne.core.application.port.out.http.*
import info.isaaclee.lolgoitne.core.application.service.riot.exceptions.*
import info.isaaclee.lolgoitne.core.domain.riot.*
import java.util.*
import javax.inject.Named

@Named
class CheckGameService(
	private val findSummonerOutPort: FindSummonerOutPort,
	private val findGameOutPort: FindGameOutPort,
	private val findChampionOutPort: FindChampionOutPort,
	private val findQueueOutPort: FindQueueOutPort,
	private val findLastMatchService: FindLastMatchService
): CheckInGameInPort {
	override fun checkInGame(nickname: String): String {
		var summoner: Summoner? = null
		val game: Game
		try {
			summoner = findSummonerOutPort.findSummonerByNickname(nickname)
			game = findGameOutPort.findGameBySummonerId(summoner.id)
		} catch (ex: SummonerNotFoundException) {
			return "${nickname}님을 찾을 수 없었어요!"
		} catch (ex: GameNotFoundException) {
			return findLastMatchService.lastPlayingDate(nickname, summoner!!.puuid)
		} catch (ex: Exception) {
			return INTERNAL_SERVER_ERROR_MESSAGE
		}
		
		val inGame = game.participants.find { participant -> participant.summonerId == summoner.id }
		
		val champion = findChampionOutPort.findChampion(inGame?.championId.toString()) ?: return INTERNAL_SERVER_ERROR_MESSAGE
		
		val playingTime = if (game.gameLength < 60) 0
			else {
				val time = Calendar.getInstance()
				time.timeInMillis = game.gameStartTime
				time.add(Calendar.HOUR, 9)
				(System.currentTimeMillis() - time.timeInMillis) / 1000 / 60
			}
		
		val queue = findQueueOutPort.findQueue(game.gameQueueConfigId) ?: return INTERNAL_SERVER_ERROR_MESSAGE
		
		val modeDescription = if (game.gameMode == "CLASSIC") "(${translateQueueDescription(queue.description)})" else ""
		
		return "찾았다! ${nickname}님은 게임 중이에요!\n" +
			"${translateGameMode(game.gameMode)}${modeDescription}에서\n" +
			"${champion.name} 챔피언을 ${playingTime}분째 플레이 중이에요!"
	}
	
	private fun translateGameMode(gameMode: String) = when (gameMode) {
		"CLASSIC" -> "소환사의 협곡"
		"ARAM" -> "칼바람 나락"
		else -> gameMode
	}
	
	private fun translateQueueDescription(description: String?): String {
		if (description == null) {
			return "커스텀 게임"
		}
		if (description.contains("Blind")) {
			return "비공개 선택"
		}
		if (description.contains("Ranked")) {
			return "랭크"
		}
		return "이벤트 게임"
	}
}
