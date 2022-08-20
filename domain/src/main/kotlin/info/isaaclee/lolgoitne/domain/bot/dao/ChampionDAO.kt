package info.isaaclee.lolgoitne.domain.bot.dao

data class ChampionDAO(
	val data: Map<String, Champion>
)

data class Champion(
	val version: String,
	val id: String,
	val key: String,
	val name: String,
	val title: String,
	val blurb: String,
	val info: Info,
	val image: Image,
	val tags: List<String>,
	val partype: String,
	val stats: Stats
)

data class Image (
	val full: String,
	val sprite: String,
	val group: String,
	val x: Long,
	val y: Long,
	val w: Long,
	val h: Long
)

data class Info (
	val attack: Long,
	val defense: Long,
	val magic: Long,
	val difficulty: Long
)

data class Stats (
	val hp: Short,
	val hpperlevel: Int,
	val mp: Int,
	val mpperlevel: Int,
	val movespeed: Short,
	val armor: Int,
	val armorperlevel: Double,
	val spellblock: Int,
	val spellblockperlevel: Double,
	val attackrange: Int,
	val hpregen: Int,
	val hpregenperlevel: Int,
	val mpregen: Int,
	val mpregenperlevel: Int,
	val crit: Int,
	val critperlevel: Int,
	val attackdamage: Int,
	val attackdamageperlevel: Int,
	val attackspeedperlevel: Double,
	val attackspeed: Double,
)