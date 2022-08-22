package info.isaaclee.lolgoitne.adapterout.http.riot

import info.isaaclee.lolgoitne.core.application.port.out.RiotHttpOutPort
import info.isaaclee.lolgoitne.core.bot.dao.*
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Component
class RiotHttpClient: RiotHttpOutPort {
	private val webClient = WebClient.create("https://kr.api.riotgames.com/lol")

	override fun findSummonerByNickname(nickname: String): SummonerDAO {
		return this.webClient.get()
			.uri("/summoner/v4/summoners/by-name/${nickname}")
			.header("X-Riot-Token", "")
			.retrieve()
			.bodyToMono<SummonerDAO>()
			.blockOptional()
			.get()
	}

	override fun findGameBySummonerId(summonerId: String): CurrentGameInfoDAO {
		TODO("Not yet implemented")
	}

	override fun findMatchIds(puuid: String, query: Map<String, String>): List<String> {
		TODO("Not yet implemented")
	}

	override fun findMatchById(matchId: String): MatchDAO {
		TODO("Not yet implemented")
	}

}