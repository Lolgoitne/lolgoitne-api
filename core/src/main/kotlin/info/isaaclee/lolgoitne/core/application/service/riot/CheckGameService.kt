package info.isaaclee.lolgoitne.core.application.service.riot

import info.isaaclee.lolgoitne.core.application.port.`in`.CheckInGameInPort
import info.isaaclee.lolgoitne.core.application.port.out.http.*
import javax.inject.Named

@Named
class CheckGameService(
	private val findSummonerOutPort: FindSummonerOutPort,
	private val findGameOutPort: FindGameOutPort
): CheckInGameInPort {
	override fun checkInGame(nickname: String): String {
		return ""
	}
}
