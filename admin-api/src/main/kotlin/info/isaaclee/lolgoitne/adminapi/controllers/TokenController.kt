package info.isaaclee.lolgoitne.adminapi.controllers

import info.isaaclee.lolgoitne.util.jwt.JwtModule
import org.springframework.web.bind.annotation.*
import java.time.Duration
import java.time.Instant
import java.util.*

@RestController
@RequestMapping("/api/admin/token")
class TokenController(
	private val jwtModule: JwtModule
) {
	@GetMapping("")
	@ResponseBody
	fun postTokenGenerate(): String {
		return this.jwtModule.createToken("bot1", Date.from(Instant.now().plus(Duration.ofHours(8760))))
	}
}