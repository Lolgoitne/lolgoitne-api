package info.isaaclee.lolgoitne.util.http

import info.isaaclee.lolgoitne.domain.bot.dao.CurrentGameInfoDAO
import info.isaaclee.lolgoitne.domain.bot.dao.SummonerDAO
import info.isaaclee.lolgoitne.domain.modules.RiotHttpClient
import info.isaaclee.lolgoitne.util.http.exceptions.ForbiddenException
import info.isaaclee.lolgoitne.util.http.exceptions.GameNotFoundException
import info.isaaclee.lolgoitne.util.http.exceptions.UnauthorizedException
import info.isaaclee.lolgoitne.util.http.exceptions.UserNotFoundException
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.bodyToMono

@Component
class RiotHttpClientImpl(
	environment: Environment
): HttpClient("https://kr.api.riotgames.com/lol"), RiotHttpClient {
	private val apiToken: String = environment.getRequiredProperty("riot.apiKey")

	override fun getSummonerInfo(nickname: String): SummonerDAO {
		return this.webClient.get()
			.uri("/summoner/v4/summoners/by-name/${nickname}")
			.header("X-Riot-Token", apiToken)
			.exchangeToMono { response ->
				if (response.rawStatusCode() == 404) {
					throw UserNotFoundException()
				} else if (response.rawStatusCode() == 401) {
					throw UnauthorizedException()
				} else if (response.rawStatusCode() == 403) {
					throw ForbiddenException()
				}
				response.bodyToMono<SummonerDAO>()
			}
			.blockOptional()
			.get()
	}

	override fun getGameInfo(summonerId: String): CurrentGameInfoDAO {
		return this.webClient.get()
			.uri("/spectator/v4/active-games/by-summoner/${summonerId}")
			.header("X-Riot-Token", apiToken)
			.exchangeToMono { response ->
				if (response.rawStatusCode() == 404) {
					throw GameNotFoundException()
				} else if (response.rawStatusCode() == 401) {
					throw UnauthorizedException()
				} else if (response.rawStatusCode() == 403) {
					throw ForbiddenException()
				}
				response.bodyToMono<CurrentGameInfoDAO>()
			}
			.blockOptional()
			.get()
	}
}