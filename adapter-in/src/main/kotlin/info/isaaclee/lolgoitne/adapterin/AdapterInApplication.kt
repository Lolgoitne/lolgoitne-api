package info.isaaclee.lolgoitne.adapterin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["info.isaaclee.lolgoitne"])
class AdapterInApplication

fun main(args: Array<String>) {
	runApplication<AdapterInApplication>(*args)
}
