package info.isaaclee.lolgoitne.botapi.controllers

import info.isaaclee.lolgoitne.botapi.controllers.dto.*
import info.isaaclee.lolgoitne.domain.bot.BotService
import info.isaaclee.lolgoitne.util.http.exceptions.GameNotFoundException
import info.isaaclee.lolgoitne.util.http.exceptions.UserNotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/bot")
class BotController(
	private val botService: BotService
) {
	@PostMapping("/check-game")
	fun postBotCheckGame(@RequestBody body: PostBotGameCheckDTO): ResponseEntity<BotResponseDTO> {
		val game = this.botService.checkInGame(body.action.params.say_name)
		return ResponseEntity.ok().body(BotResponseDTO(SkillTemplate(listOf(ComponentItem(SimpleText(game))))))
	}

//	@ExceptionHandler(GameNotFoundException::class, UserNotFoundException::class)
//	fun handleException(e: ResponseStatusException): ResponseEntity<ResponseDTO<Nothing>> {
//		return ResponseEntity.ok().body(ResponseDTO(e.reason, null))
//	}
}