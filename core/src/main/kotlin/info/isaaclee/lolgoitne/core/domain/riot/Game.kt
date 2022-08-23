package info.isaaclee.lolgoitne.core.domain.riot

//https://developer.riotgames.com/apis#spectator-v4/GET_getCurrentGameInfoBySummoner
data class Game(
  val gameId: Long,
  val gameType: String,
  val gameStartTime: Long,
  val mapId: Long,
  val gameLength: Long,
  val platformId: String,
  val gameMode: String,
  val bannedChampions: List<BannedChampion>,
  val gameQueueConfigId: Long,
  val observers: Observer?,
  val participants: List<CurrentGameParticipant>
)

data class BannedChampion(
	val pickTurn: Int,
	val championId: Long,
	val teamId: Long
)

data class Observer(
	val encryptionKey: String
)

data class CurrentGameParticipant(
  val championId: Long,
  val perks: Perks,
  val profileIconId: Long,
  val bot: Boolean,
  val teamId: Long,
  val summonerName: String,
  val summonerId: String,
  val spell1Id: Long,
  val spell2Id: Long,
  val gameCustomizationObject: GameCustomizationObject?
)

data class Perks(
	val perkIds: List<Long>,
	val perkStyle: Long,
	val perkSubStyle: Long
)

data class GameCustomizationObject(
	val category: String,
	val content: String,
)
