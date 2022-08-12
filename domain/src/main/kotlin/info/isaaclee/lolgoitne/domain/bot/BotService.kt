package info.isaaclee.lolgoitne.domain.bot

import info.isaaclee.lolgoitne.util.http.RiotHttpClient
import info.isaaclee.lolgoitne.util.http.exceptions.UserNotFoundException
import org.springframework.stereotype.Service

@Service
class BotService(
	private val riotHttpClient: RiotHttpClient
) {
	fun checkInGame(nickname: String): String {
		//닉네임을 가지고 롤 API 에 accountId 를 가져올 수 있도록 함
		val summoner = this.riotHttpClient.getSummonerInfo("RGAPI-e88455fa-b11e-4946-945d-b473c895f209", nickname).block()
		//가져온 accountId 로 현재 게임중인지 검색하는 API 요청
		val game = this.riotHttpClient.getGameInfo("RGAPI-e88455fa-b11e-4946-945d-b473c895f209", summoner!!.id).block()
		//성공했다면 카카오 메세지로 보내줌
		return "${nickname}님은 게임 중이에요!"
	}
}