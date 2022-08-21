package info.isaaclee.lolgoitne.botapi.controllers

import info.isaaclee.lolgoitne.botapi.controllers.dto.*
import info.isaaclee.lolgoitne.domain.bot.BotService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/bot")
class BotController(
	val botService: BotService
) {
	@PostMapping("/check-game")
	fun postBotCheckGame(@RequestBody body: PostBotGameCheckDTO): ResponseEntity<BotResponseDTO> {
		val game = this.botService.checkInGame(body.action.params.say_name)
		return ResponseEntity.ok().body(BotResponseDTO(SkillTemplate(listOf(ComponentItem(SimpleText(game))))))
	}

	@GetMapping("/health")
	fun getHealthCheck(): ResponseEntity<String> {
		return ResponseEntity.ok().body("i`m healthy!")
	}
}