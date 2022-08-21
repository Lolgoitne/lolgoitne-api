package info.isaaclee.lolgoitne.domain.bot

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import info.isaaclee.lolgoitne.domain.bot.dao.*
import info.isaaclee.lolgoitne.domain.modules.RiotHttpClient
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class BotService(
	private val riotHttpClient: RiotHttpClient
) {
	fun checkInGame(nickname: String): String {
		var summoner: SummonerDAO?
		var game: CurrentGameInfoDAO?
		try {
			summoner = this.riotHttpClient.getSummonerInfo(nickname)
			game = this.riotHttpClient.getGameInfo(summoner.id)
		} catch (ex: ResponseStatusException) {
			if (ex.message == null) {
				return INTERNAL_SERVER_ERROR_MESSAGE
			} else if (ex.message.contains(GAME_NOT_FOUND)) {
				return "${nickname}님은 현재 게임 중이 아니에요!"
			} else if (ex.message.contains(USER_NOT_FOUND)) {
				return "${nickname}님을 찾을 수 없었어요!"
			}
			return INTERNAL_SERVER_ERROR_MESSAGE
		}
		val myInGameInfo = game.participants.find { participant -> participant.summonerId == summoner.id }

		val champion = getChampionInfo(myInGameInfo?.championId.toString())

		val playingTime = if (game.gameLength < 60) 0 else (System.currentTimeMillis() - game.gameStartTime) / 1000 / 60

		val queue = getQueue(game.gameQueueConfigId)

		val modeDescription = if (game.gameMode == "CLASSIC") "(${translateQueueDescription(queue?.description)})" else ""

		return "찾았다! ${nickname}님은 게임 중이에요! ${translateGameMode(game.gameMode)}${modeDescription}에서 ${champion?.name} 챔피언을 ${playingTime}분째 플레이 중이에요!"
	}

	fun getQueue(queueId: Long): Queue? {
		val mapper = jacksonObjectMapper()
		val queues = mapper.readValue(ClassPathResource("queue.json").inputStream, QueueDAO::class.java).data
		for (queue in queues) {
			if (queue.queueId == queueId) {
				return queue
			}
		}
		return null
	}

	fun getChampionInfo(championId: String): Champion? {
		val mapper = jacksonObjectMapper()
		val champions = mapper.readValue(ClassPathResource("champion.json").inputStream, ChampionDAO::class.java).data
		for (entry in champions.entries.iterator()) {
			if (entry.value.key == championId) {
				return entry.value
			}
		}
		return null
	}

	fun translateGameMode(gameMode: String) = when (gameMode) {
		"CLASSIC" -> "소환사의 협곡"
		"ARAM" -> "칼바람 나락"
		else -> gameMode
	}

	fun translateQueueDescription(description: String?): String {
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