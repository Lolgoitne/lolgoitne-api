package info.isaaclee.lolgoitne.domain.modules

import info.isaaclee.lolgoitne.domain.bot.dao.*

interface RiotHttpClient {
	fun getSummonerInfo(nickname: String): SummonerDAO

	fun getGameInfo(summonerId: String): CurrentGameInfoDAO
}