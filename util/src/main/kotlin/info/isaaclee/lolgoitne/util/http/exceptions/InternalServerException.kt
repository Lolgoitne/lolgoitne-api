package info.isaaclee.lolgoitne.util.http.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class InternalServerException: ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "internal_server_error") {
}