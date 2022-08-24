package info.isaaclee.lolgoitne.core.application.service.riot

import info.isaaclee.lolgoitne.core.application.port.`in`.FindLastGameInPort
import info.isaaclee.lolgoitne.core.application.port.out.http.*
import info.isaaclee.lolgoitne.core.application.service.riot.exceptions.MatchNotFoundException
import info.isaaclee.lolgoitne.core.application.service.riot.exceptions.SummonerNotFoundException
import info.isaaclee.lolgoitne.core.domain.riot.Match
import javax.inject.Named

@Named
class FindLastMatchService(
  private val findSummonerOutPort: FindSummonerOutPort,
  private val findMatchesOutPort: FindMatchesOutPort,
  private val findMatchOutPort: FindMatchOutPort
): FindLastGameInPort {
  override fun lastPlayingDate(nickname: String, puuid: String?): String {
    val match: Match
    try {
      var puuidRef = puuid
      if (puuidRef == null) {
        puuidRef = findSummonerOutPort.findSummonerByNickname(nickname).puuid
      }
      val queryParams: MutableMap<String, String> = mutableMapOf()
      queryParams["count"] = "1"
  
      val matchIds = findMatchesOutPort.findMatchIds(puuidRef, queryParams)
      if (matchIds.isEmpty()) return "${nickname}님은 게임 전적이 없어요!"
      match = findMatchOutPort.findMatchById(matchIds[0])
    } catch (ex: SummonerNotFoundException) {
      return "${nickname}님을 찾을 수 없었어요!"
    } catch (ex: MatchNotFoundException) {
      return "${nickname}님은 게임 전적이 없어요!"
    } catch (ex: Exception) {
      return INTERNAL_SERVER_ERROR_MESSAGE
    }

    return "앗! ${nickname}님은 지금 게임 중이 아니네요.\n" +
        "마지막으로 게임을 했던 시간은 ${match.endDate("M월 d일 H시 m분")}이에요.\n" +
        "오늘로부터 ${match.lastDateFromNow()} 전에 했네요!"
  }
}
