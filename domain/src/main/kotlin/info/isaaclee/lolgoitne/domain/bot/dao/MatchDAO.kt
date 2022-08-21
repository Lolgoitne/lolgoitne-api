package info.isaaclee.lolgoitne.domain.bot.dao

//https://developer.riotgames.com/apis#match-v5/GET_getMatch
data class MatchDAO(
	val metadata: Metadata,
	val info: MatchInfo
)

data class Metadata(
	val dataVersion: String,
	val matchId: String,
	val participants: List<String>
)

data class MatchInfo(
	val gameCreation: Long,
	val gameDuration: Long,
	val gameEndTimestamp: Long,
	val gameId: String,
	val gameMode: String,
	val gameName: String,
	val gameStartTimestamp: Long,
	val gameType: String,
	val gameVersion: String,
	val mapId: Int,
	val participants: Any?
)