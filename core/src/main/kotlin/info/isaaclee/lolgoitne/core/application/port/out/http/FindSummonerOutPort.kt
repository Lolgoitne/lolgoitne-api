package info.isaaclee.lolgoitne.core.application.port.out.http

import info.isaaclee.lolgoitne.core.domain.riot.Summoner

interface FindSummonerOutPort {
  fun findSummonerByNickname(nickname: String): Summoner
}
