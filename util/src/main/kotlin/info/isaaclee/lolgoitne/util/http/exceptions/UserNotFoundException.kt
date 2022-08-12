package info.isaaclee.lolgoitne.util.http.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class UserNotFoundException: ResponseStatusException(HttpStatus.NOT_FOUND, "user_not_found") {}