package info.isaaclee.lolgoitne.botapi.middlewares

import lombok.extern.slf4j.Slf4j
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.*
import java.nio.charset.Charset
import java.util.UUID
import javax.servlet.FilterChain
import javax.servlet.http.*

@Slf4j
@Component
class RequestFilter: OncePerRequestFilter() {
	override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
		val wrappingRequest = ContentCachingRequestWrapper(request)
		val wrappingResponse = ContentCachingResponseWrapper(response)

		val traceId = UUID.randomUUID().toString()

		request.setAttribute("traceId", traceId)
		request.setAttribute("requestURI", wrappingRequest.requestURI)

		val currentMillis = System.currentTimeMillis()
		filterChain.doFilter(wrappingRequest, wrappingResponse)
		val timeTaken = System.currentTimeMillis() - currentMillis
		MDC.put("traceId", traceId)

		val requestBody = byteArrayToString(wrappingRequest.contentAsByteArray, request.characterEncoding).replace("\n", "").replace(" ", "")
		val responseBody = byteArrayToString(wrappingResponse.contentAsByteArray, response.characterEncoding).replace("\n", "").replace(" ", "")

		if (response.status > 399) {
			logger.info("$traceId - HTTP [${wrappingRequest.method}] ${response.status} ${wrappingRequest.requestURI} ${timeTaken}ms body $requestBody")
		} else {
			logger.info("$traceId - HTTP [${wrappingRequest.method}] ${response.status} ${wrappingRequest.requestURI} ${timeTaken}ms body $requestBody response $responseBody")
		}

		wrappingResponse.copyBodyToResponse()
	}

	fun byteArrayToString(byteArray: ByteArray, encoding: String): String {
		try {
			return String(byteArray, 0, byteArray.size, Charset.forName(encoding))
		} catch (_: Exception) {}
		return ""
	}
}