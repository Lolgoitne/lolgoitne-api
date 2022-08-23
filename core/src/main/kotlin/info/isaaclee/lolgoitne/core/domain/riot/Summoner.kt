package info.isaaclee.lolgoitne.core.domain.riot

data class Summoner(
	val accountId: String,
	val profileIconId: Int,
	val revisionDate: Long,
	val name: String,
	val id: String,
	val puuid: String,
	val summonerLevel: String,
)
