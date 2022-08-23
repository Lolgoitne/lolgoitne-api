package info.isaaclee.lolgoitne.core.application.port.out.http

import info.isaaclee.lolgoitne.core.domain.riot.Champion

interface FindChampionOutPort {
  fun findChampion(championId: String): Champion?
}
