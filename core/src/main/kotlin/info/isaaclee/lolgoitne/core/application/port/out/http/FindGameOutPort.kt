package info.isaaclee.lolgoitne.core.application.port.out.http

import info.isaaclee.lolgoitne.core.domain.riot.Game

interface FindGameOutPort {
  fun findGameBySummonerId(summonerId: String): Game
}
