package info.isaaclee.lolgoitne.core.bot.dao

data class QueueDAO(
	val data: List<Queue>
)

data class Queue(
	val queueId: Long,
	val map: String,
	val description: String?,
	val notes: String?
)