package info.isaaclee.lolgoitne.adapterout.http.riot

import info.isaaclee.lolgoitne.core.application.port.out.http.*
import info.isaaclee.lolgoitne.core.application.service.riot.exceptions.*
import info.isaaclee.lolgoitne.core.domain.riot.*
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono
import java.util.function.Predicate

@Component
class RiotHttpClient: FindSummonerOutPort, FindGameOutPort, FindMatchesOutPort, FindMatchOutPort {
	private val webClient = WebClient.create("https://kr.api.riotgames.com/lol")
	private val apiToken = ""

	override fun findSummonerByNickname(nickname: String): Summoner {
		return this.webClient.get()
			.uri("/summoner/v4/summoners/by-name/${nickname}")
			.header("X-Riot-Token", "")
			.retrieve()
			.onStatus(Predicate.isEqual(HttpStatus.NOT_FOUND)) { Mono.error(SummonerNotFoundException()) }
			.bodyToMono<Summoner>()
			.blockOptional()
			.get()
	}

	override fun findGameBySummonerId(summonerId: String): Game {
		return this.webClient.get()
			.uri("/spectator/v4/active-games/by-summoner/${summonerId}")
			.header("X-Riot-Token", apiToken)
			.retrieve()
			.onStatus(Predicate.isEqual(HttpStatus.NOT_FOUND)) { Mono.error(GameNotFoundException()) }
			.bodyToMono<Game>()
			.blockOptional()
			.get()
	}

	override fun findMatchIds(puuid: String, query: Map<String, String>): List<String> {
		return this.webClient.get()
			.uri { uri ->
				uri
					.host("asia.api.riotgames.com")
					.path("/match/v5/matches/by-puuid/${puuid}/ids")
					.queryParams(LinkedMultiValueMap<String, String>().apply { setAll(query) })
					.build()
			}
			.header("X-Riot-Token", apiToken)
			.retrieve()
			.onStatus(Predicate.isEqual(HttpStatus.NOT_FOUND)) { Mono.error(MatchNotFoundException()) }
			.bodyToMono<List<String>>()
			.blockOptional()
			.get()
	}

	override fun findMatchById(matchId: String): Match {
		return this.webClient.get()
			.uri { uri ->
				uri
					.host("asia.api.riotgames.com")
					.path("/match/v5/matches/${matchId}")
					.build()
			}
			.header("X-Riot-Token", apiToken)
			.retrieve()
			.bodyToMono<Match>()
			.blockOptional()
			.get()
	}

}
