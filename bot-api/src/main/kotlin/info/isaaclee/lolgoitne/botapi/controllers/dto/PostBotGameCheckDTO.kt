package info.isaaclee.lolgoitne.botapi.controllers.dto

class PostBotGameCheckDTO(
	val action: Action
)

class Action(
	val name: String,
	val clientExtra: Any?,
	val params: Params,
	val id: String,
)

class Params(
	val say_name: String
)