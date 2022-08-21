package info.isaaclee.lolgoitne.domain.modules

import info.isaaclee.lolgoitne.domain.bot.dao.*
import info.isaaclee.lolgoitne.domain.bot.exceptions.*
import info.isaaclee.lolgoitne.util.http.HttpClient
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
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
			.exchangeToMono { response ->
				if (response.rawStatusCode() == 404) {
					throw UserNotFoundException()
				}
				response.bodyToMono<SummonerDAO>()
			}
			.blockOptional()
			.get()
	}

	fun getGameInfo(summonerId: String): CurrentGameInfoDAO {
		return this.webClient.get()
			.uri("/spectator/v4/active-games/by-summoner/${summonerId}")
			.header("X-Riot-Token", apiToken)
			.exchangeToMono { response ->
				if (response.rawStatusCode() == 404) {
					throw GameNotFoundException()
				}
				response.bodyToMono<CurrentGameInfoDAO>()
			}
			.blockOptional()
			.get()
	}
}