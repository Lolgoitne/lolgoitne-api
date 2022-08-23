package info.isaaclee.lolgoitne.adapterin.rest.controllers.bot.dto

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