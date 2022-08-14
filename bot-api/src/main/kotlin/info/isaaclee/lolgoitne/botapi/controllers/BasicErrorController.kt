package info.isaaclee.lolgoitne.botapi.controllers

import org.slf4j.*
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.servlet.error.ErrorAttributes
import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
class BasicErrorController(val errorAttributes: ErrorAttributes): AbstractErrorController(errorAttributes) {
	companion object {
		const val ERROR_PATH = "\${error.path:/error}"
	}

	private val logger = LoggerFactory.getLogger("BasicErrorController")

	@RequestMapping(ERROR_PATH)
	fun error(request: HttpServletRequest): ResponseEntity<Map<String, Any>> {
		val status = getStatus(request)
		val body = getErrorAttributes(request, ErrorAttributeOptions.defaults())
		if (status == HttpStatus.INTERNAL_SERVER_ERROR) {
			val traceId = request.getAttribute("traceId").toString()
			MDC.put("traceId", traceId)
			logger.error("$traceId - HTTP [${request.method}] $status ${request.getAttribute("requestURI")}")
		}
		return ResponseEntity.status(status).body(body)
	}
}