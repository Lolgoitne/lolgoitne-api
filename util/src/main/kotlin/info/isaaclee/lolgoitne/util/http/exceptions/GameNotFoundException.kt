package info.isaaclee.lolgoitne.util.http.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class GameNotFoundException: ResponseStatusException(HttpStatus.NOT_FOUND, "game_not_found") {}