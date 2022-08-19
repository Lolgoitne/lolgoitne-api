package info.isaaclee.lolgoitne.domain.bot

import info.isaaclee.lolgoitne.util.http.RiotHttpClient
import info.isaaclee.lolgoitne.util.http.exceptions.GameNotFoundException
import info.isaaclee.lolgoitne.util.http.exceptions.UserNotFoundException
import org.springframework.stereotype.Service

@Service
class BotService(
	private val riotHttpClient: RiotHttpClient
) {
	fun checkInGame(nickname: String): String {
		try {
			//닉네임을 가지고 롤 API 에 accountId 를 가져올 수 있도록 함
			val summoner = this.riotHttpClient.getSummonerInfo("RGAPI-a0e937ca-8094-46fb-907a-9919413395c6", nickname).block()
			//가져온 accountId 로 현재 게임중인지 검색하는 API 요청
			val game = this.riotHttpClient.getGameInfo("RGAPI-a0e937ca-8094-46fb-907a-9919413395c6", summoner!!.id).block()
			//성공했다면 카카오 메세지로 보내줌

			return "${nickname}님은 게임 중이에요!"
		} catch (ex: UserNotFoundException) {
			return "${nickname}님을 찾을 수 없어요!"
		} catch (ex: GameNotFoundException) {
			return "${nickname}님은 현재 게임 중이 아니에요!"
		} catch (ex: Exception) {
			return "현재 서버가 많이 아파요"
		}
	}
}