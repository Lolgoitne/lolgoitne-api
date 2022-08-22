package info.isaaclee.lolgoitne.core.application.service

import info.isaaclee.lolgoitne.core.application.port.`in`.CheckInGameInPort
import info.isaaclee.lolgoitne.core.application.port.out.RiotHttpOutPort
import javax.inject.Named

@Named
class RiotService(
	private val riotHttpOutPort: RiotHttpOutPort
): CheckInGameInPort {
	override fun checkInGame(nickname: String): String {
		riotHttpOutPort.findSummonerByNickname(nickname)
		return ""
	}
}