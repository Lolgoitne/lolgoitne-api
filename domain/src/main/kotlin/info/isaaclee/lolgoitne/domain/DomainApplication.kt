package info.isaaclee.lolgoitne.domain

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["info.isaaclee.lolgoitne"])
class DomainApplication

fun main(args: Array<String>) {
	runApplication<DomainApplication>(*args)
}
