package info.isaaclee.lolgoitne.adminapi.middlewares

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@EnableWebSecurity
class SecurityConfiguration: WebSecurityConfigurerAdapter() {
	override fun configure(http: HttpSecurity) {
		http
			.csrf()
			.disable()
			.formLogin()
			.disable()
	}
}