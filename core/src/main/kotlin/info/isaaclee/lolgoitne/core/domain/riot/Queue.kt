package info.isaaclee.lolgoitne.core.domain.riot

data class Queue(
	val queueId: Long,
	val map: String,
	val description: String?,
	val notes: String?
) {
	
	fun translateQueueDescription(): String {
		if (description == null) {
			return "커스텀 게임"
		}
		if (description.contains("Blind")) {
			return "비공개 선택"
		}
		if (description.contains("Ranked")) {
			return "랭크"
		}
		return "이벤트 게임"
	}
}
