package br.com.ivanzao.faccampcrawler.route.v1

import br.com.ivanzao.faccampcrawler.student.StudentService
import org.springframework.stereotype.Component
import ro.pippo.core.route.RouteContext
import ro.pippo.core.route.RouteGroup

@Component
class StudentRoutesV1(private val studentService: StudentService) : RouteGroup("/v1/student") {
    init {
        POST("/retrieveData", { handleRetrieveData(it) })
    }

    private fun handleRetrieveData(routeContext: RouteContext) {
        val request = routeContext.createEntityFromBody(RetrieveRequest::class.java)

        if (request.ra.isEmpty()) throw IllegalArgumentException("No 'ra' was inputted")
        if (request.password.isEmpty()) throw IllegalArgumentException("No 'password' was inputted")

        routeContext.json().send(studentService.retrieveData(request.ra,
                request.password,
                request.forceUpdate))
    }
}

class RetrieveRequest {

    var ra: String = ""
    var password: String = ""
    var forceUpdate: Boolean = false

}