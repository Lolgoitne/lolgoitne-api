package info.isaaclee.lolgoitne.util.http.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class UnauthorizedException: ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized") {}