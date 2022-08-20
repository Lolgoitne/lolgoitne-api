package info.isaaclee.lolgoitne.util.http

import info.isaaclee.lolgoitne.util.http.dto.CurrentGameInfoDTO
import info.isaaclee.lolgoitne.util.http.dto.SummonerDTO
import info.isaaclee.lolgoitne.util.http.exceptions.ForbiddenException
import info.isaaclee.lolgoitne.util.http.exceptions.GameNotFoundException
import info.isaaclee.lolgoitne.util.http.exceptions.UnauthorizedException
import info.isaaclee.lolgoitne.util.http.exceptions.UserNotFoundException
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono

@Component
class RiotHttpClient: HttpClient("https://kr.api.riotgames.com/lol") {
	fun getSummonerInfo(header: String, nickname: String): Mono<SummonerDTO> {
		return this.webClient.get()
			.uri("/summoner/v4/summoners/by-name/${nickname}")
			.header("X-Riot-Token", header)
			.exchangeToMono { response ->
				if (response.rawStatusCode() == 404) {
					throw UserNotFoundException()
				} else if (response.rawStatusCode() == 401) {
					throw UnauthorizedException()
				} else if (response.rawStatusCode() == 403) {
					throw ForbiddenException()
				}
				response.bodyToMono()
			}
	}

	fun getGameInfo(header: String, summonerId: String): Mono<CurrentGameInfoDTO> {
		return this.webClient.get()
			.uri("/spectator/v4/active-games/by-summoner/${summonerId}")
			.header("X-Riot-Token", header)
			.exchangeToMono { response ->
				if (response.rawStatusCode() == 404) {
					throw GameNotFoundException()
				} else if (response.rawStatusCode() == 401) {
					throw UnauthorizedException()
				} else if (response.rawStatusCode() == 403) {
					throw ForbiddenException()
				}
				println(response.toString())
				response.bodyToMono()
			}
	}
}