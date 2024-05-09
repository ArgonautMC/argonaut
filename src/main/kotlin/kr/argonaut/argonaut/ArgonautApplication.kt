package kr.argonaut.argonaut

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ArgonautApplication

fun main(args: Array<String>) {
    runApplication<ArgonautApplication>(*args)
}
