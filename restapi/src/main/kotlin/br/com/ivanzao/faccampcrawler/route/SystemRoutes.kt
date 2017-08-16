package br.com.ivanzao.faccampcrawler.route

import org.springframework.stereotype.Component
import ro.pippo.core.route.RouteGroup

@Component
class SystemRoutes : RouteGroup("/") {
    init {
        GET("/", { routeContext -> routeContext.redirect("/health") })
        GET("/health", { routeContext -> routeContext.json().send(mapOf("status" to "up")) })
    }
}