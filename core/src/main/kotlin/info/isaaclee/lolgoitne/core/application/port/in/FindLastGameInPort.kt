package info.isaaclee.lolgoitne.core.application.port.`in`

interface FindLastGameInPort {
	fun lastPlayingDate(nickname: String, puuid: String?): String
}