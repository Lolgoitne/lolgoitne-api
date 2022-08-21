package info.isaaclee.lolgoitne.domain.modules

import info.isaaclee.lolgoitne.domain.bot.dao.*
import info.isaaclee.lolgoitne.util.http.HttpClient
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.client.bodyToMono

@Component
class RiotHttpClient(
	environment: Environment
): HttpClient("https://kr.api.riotgames.com/lol") {
	private val apiToken: String = environment.getRequiredProperty("riot.apiKey")

	fun getSummonerInfo(nickname: String): SummonerDAO {
		return this.webClient.get()
			.uri("/summoner/v4/summoners/by-name/${nickname}")
			.header("X-Riot-Token", apiToken)
			.retrieve()
			.bodyToMono<SummonerDAO>()
			.blockOptional()
			.get()
	}

	fun getGameInfo(summonerId: String): CurrentGameInfoDAO {
		return this.webClient.get()
			.uri("/spectator/v4/active-games/by-summoner/${summonerId}")
			.header("X-Riot-Token", apiToken)
			.retrieve()
			.bodyToMono<CurrentGameInfoDAO>()
			.blockOptional()
			.get()
	}

	fun getMatchIds(puuid: String, query: MultiValueMap<String, String>): List<String> {
		return this.webClient.get()
			.uri { uri ->
				uri
					.host("asia.api.riotgames.com")
					.path("/match/v5/matches/by-puuid/${puuid}/ids")
					.queryParams(query)
					.build()
			}
			.header("X-Riot-Token", apiToken)
			.retrieve()
			.bodyToMono<List<String>>()
			.blockOptional()
			.get()
	}

	fun getMatch(matchId: String): MatchDAO {
		return this.webClient.get()
			.uri { uri ->
				uri
					.host("asia.api.riotgames.com")
					.path("/match/v5/matches/${matchId}")
					.build()
			}
			.header("X-Riot-Token", apiToken)
			.retrieve()
			.bodyToMono<MatchDAO>()
			.blockOptional()
			.get()
	}
}