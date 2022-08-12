package info.isaaclee.lolgoitne.util.http.dto

//https://developer.riotgames.com/apis#spectator-v4/GET_getCurrentGameInfoBySummoner
class CurrentGameInfoDTO(
	val gameId: Long?,
	val gameType: String?,
	val gameStartTime: Long?,
	val mapId: Long?,
	val gameLength: Long?,
	val platformId: String?,
	val gameMode: String?,
	val bannedChampions: List<BannedChampion>?,
	val gameQueueConfigId: Long?,
	val observers: Observer?,
	val participant: CurrentGameParticipant?
)

class BannedChampion(
	val pickTurn: Int,
	val championId: Long,
	val teamId: Long
)

class Observer(
	val encryptionKey: String
)

class CurrentGameParticipant(
	val championId: Long,
	val perks: Perks,
	val profileIconId: Long,
	val bot: Boolean,
	val teamId: Long,
	val summonerName: String,
	val summonerId: String,
	val spell1Id: Long,
	val spell2Id: Long,
	val gameCustomizationObject: GameCustomizationObject
)

class Perks(
	val perkIds: List<Long>,
	val perkStyle: Long,
	val perkSubStyle: Long
)

class GameCustomizationObject(
	val category: String,
	val content: String,
)