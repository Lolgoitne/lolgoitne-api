package info.isaaclee.lolgoitne.core.application.port.out.http

import info.isaaclee.lolgoitne.core.domain.riot.Match

interface FindMatchOutPort {
  fun findMatchById(matchId: String): Match
}
