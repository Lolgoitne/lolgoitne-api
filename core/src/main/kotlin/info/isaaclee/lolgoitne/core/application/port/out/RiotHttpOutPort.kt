package info.isaaclee.lolgoitne.core.application.port.out

import info.isaaclee.lolgoitne.core.bot.dao.*

interface RiotHttpOutPort {
	fun findSummonerByNickname(nickname: String): SummonerDAO
	fun findGameBySummonerId(summonerId: String): CurrentGameInfoDAO
	fun findMatchIds(puuid: String, query: Map<String, String>): List<String>
	fun findMatchById(matchId: String): MatchDAO
}