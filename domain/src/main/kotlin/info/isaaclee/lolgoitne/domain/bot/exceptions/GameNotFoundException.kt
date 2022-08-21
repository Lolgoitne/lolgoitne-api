package info.isaaclee.lolgoitne.domain.bot.exceptions

import info.isaaclee.lolgoitne.domain.bot.GAME_NOT_FOUND
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class GameNotFoundException: ResponseStatusException(HttpStatus.NOT_FOUND, GAME_NOT_FOUND) {}