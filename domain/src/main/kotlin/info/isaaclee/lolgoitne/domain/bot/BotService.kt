package info.isaaclee.lolgoitne.domain.bot

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import info.isaaclee.lolgoitne.domain.bot.dao.Champion
import info.isaaclee.lolgoitne.domain.bot.dao.ChampionDAO
import info.isaaclee.lolgoitne.domain.bot.dao.Queue
import info.isaaclee.lolgoitne.domain.bot.dao.QueueDAO
import info.isaaclee.lolgoitne.util.http.RiotHttpClient
import info.isaaclee.lolgoitne.util.http.exceptions.GameNotFoundException
import info.isaaclee.lolgoitne.util.http.exceptions.UserNotFoundException
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import kotlin.math.abs

@Service
class BotService(
	private val riotHttpClient: RiotHttpClient
) {
	fun checkInGame(nickname: String): String {
		try {
			val summoner = this.riotHttpClient.getSummonerInfo("RGAPI-a2bf0ef5-4ef9-4726-b123-578af4e0f9fc", nickname).blockOptional().get()

			val game = this.riotHttpClient.getGameInfo("RGAPI-a2bf0ef5-4ef9-4726-b123-578af4e0f9fc", summoner.id).blockOptional().get()

			val inGameInfo = game.participants.find { participant -> participant.summonerId == summoner.id }
				?: throw GameNotFoundException()

			val champion = getChampionInfo(inGameInfo.championId.toString())

			val gameMode = translateGameMode(game.gameMode)

			val time = if (game.gameLength < 60) 0 else abs((game.gameStartTime - System.currentTimeMillis()) / 1000 / 60)

			val queue = getQueue(game.gameQueueConfigId)

			val modeDescription = if (gameMode == "CLASSIC") "(${translateQueueDescription(queue?.description)})" else ""

			return "찾았다! ${nickname}님은 게임 중이에요! ${gameMode}${modeDescription}에서 ${champion?.name} 챔피언을 ${time}분째 플레이 중이에요!"
		} catch (ex: UserNotFoundException) {
			return "${nickname}님을 찾을 수 없어요!"
		} catch (ex: GameNotFoundException) {
			return "${nickname}님은 현재 게임 중이 아니에요!"
		} catch (ex: Exception) {
			println(ex)
			return "현재 서버가 많이 아파요"
		}
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