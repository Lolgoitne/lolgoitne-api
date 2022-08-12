package info.isaaclee.lolgoitne.botapi.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/bot")
class BotController {
	@GetMapping("")
	fun getBot(): String {
		return "hello bot!"
	}
}