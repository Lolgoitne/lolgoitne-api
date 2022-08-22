package info.isaaclee.lolgoitne.adapterin.rest.controllers.bot.dto

class BotResponseDTO(
	val template: SkillTemplate
) {
	val version = "2.0"
}

class SkillTemplate(
	val outputs: List<ComponentItem>
)

class ComponentItem(
	val simpleText: SimpleText
)

class SimpleText(
	val text: String
)

enum class Component(
	val value: String
) {
	SIMPLETEXT("simpleText"),
	SIMPLEIMAGE("simpleImage"),
	BASICCARD("basicCard"),
	COMMERCECARD("commerceCard"),
	LISTCARD("listCard")
}