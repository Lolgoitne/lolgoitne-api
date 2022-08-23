package info.isaaclee.lolgoitne.adapterin.rest.controllers.bot

import info.isaaclee.lolgoitne.adapterin.rest.controllers.bot.dto.*
import info.isaaclee.lolgoitne.core.application.port.`in`.CheckInGameInPort
import info.isaaclee.lolgoitne.core.application.port.`in`.Find7DayGameInPort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/bot")
class BotController(
	private val checkInGameInPort: CheckInGameInPort,
	private val find7DayGameInPort: Find7DayGameInPort,
) {
	@PostMapping("/check-game")
	fun postBotCheckGame(@RequestBody body: PostBotGameCheckDTO): ResponseEntity<BotResponseDTO> {
		val game = this.checkInGameInPort.checkInGame(body.action.params.say_name)
		return ResponseEntity.ok().body(BotResponseDTO(SkillTemplate(listOf(ComponentItem(SimpleText(game))))))
	}

	@PostMapping("/game-in-7days")
	fun postBotGameIn7Days(@RequestBody body: PostBotGameCheckDTO): ResponseEntity<BotResponseDTO> {
		val result = find7DayGameInPort.gameIn7days(body.action.params.say_name)
		return ResponseEntity.ok().body(BotResponseDTO(SkillTemplate(listOf(ComponentItem(SimpleText(result))))))
	}

	@GetMapping("/health")
	fun getHealthCheck(): ResponseEntity<String> {
		return ResponseEntity.ok().body("i`m healthy!")
	}
}