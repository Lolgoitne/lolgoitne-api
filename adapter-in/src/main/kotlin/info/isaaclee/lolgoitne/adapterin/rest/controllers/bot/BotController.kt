package info.isaaclee.lolgoitne.adapterin.rest.controllers.bot

import info.isaaclee.lolgoitne.adapterin.rest.controllers.bot.dto.*
import info.isaaclee.lolgoitne.core.application.port.`in`.CheckInGameInPort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/bot")
class BotController(
	private val checkInGameInPort: CheckInGameInPort
) {
	@PostMapping("/check-game")
	fun postBotCheckGame(@RequestBody body: PostBotGameCheckDTO): ResponseEntity<BotResponseDTO> {
		val game = this.checkInGameInPort.checkInGame(body.action.params.say_name)
		return ResponseEntity.ok().body(BotResponseDTO(SkillTemplate(listOf(ComponentItem(SimpleText(game))))))
	}

	@GetMapping("/health")
	fun getHealthCheck(): ResponseEntity<String> {
		return ResponseEntity.ok().body("i`m healthy!")
	}
}