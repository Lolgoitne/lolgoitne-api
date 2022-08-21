package info.isaaclee.lolgoitne.domain.bot.exceptions

import info.isaaclee.lolgoitne.domain.bot.USER_NOT_FOUND
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class UserNotFoundException: ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_FOUND) {}