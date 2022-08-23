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
			else (Calendar.getInstance(Locale.KOREA).timeInMillis - game.gameStartTime) / 1000 / 60
		
		val queue = findQueueOutPort.findQueue(game.gameQueueConfigId) ?: return INTERNAL_SERVER_ERROR_MESSAGE
		
		val modeDescription = if (game.gameMode == "CLASSIC") "(${queue.translateQueueDescription()})" else ""
		
		return "찾았다! ${nickname}님은 게임 중이에요!\n" +
			"${game.translateGameMode()}${modeDescription}에서\n" +
			"${champion.name} 챔피언을 ${playingTime}분째 플레이 중이에요!"
	}
}
