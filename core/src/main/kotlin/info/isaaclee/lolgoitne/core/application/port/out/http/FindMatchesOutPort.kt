package info.isaaclee.lolgoitne.core.application.port.out.http

interface FindMatchesOutPort {
  fun findMatchIds(puuid: String, query: Map<String, String>): List<String>
}
