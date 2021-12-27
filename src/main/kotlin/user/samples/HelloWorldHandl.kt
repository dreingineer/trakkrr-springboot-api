package user.samples

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
class HelloWorldHandl {
    //pwede nang gumawa ng endpoints or routes - where other apps connects to the api
    @GetMapping("api/hello")
    fun hello():String = "Hello World!-Andrei"

    @GetMapping("api/now")
    fun now(): String {
        return LocalDate.now().toString()
    }
}