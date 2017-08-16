package br.com.ivanzao.faccampcrawler

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.ListableBeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import ro.pippo.core.Application
import ro.pippo.core.Pippo
import ro.pippo.core.PippoRuntimeException
import ro.pippo.core.route.CSRFHandler
import ro.pippo.core.route.RouteGroup
import java.lang.Exception

@Configuration
@PropertySource("classpath:/application.properties")
class PippoConfiguration(private val env: Environment) {

    private val LOGGER = LoggerFactory.getLogger(PippoConfiguration::class.java)!!

    @Bean
    open fun startPippo(beanFactory: ListableBeanFactory): Pippo {
        return Pippo(Application().apply {
            addErrorHandlers(this)
            addSettings(this)

            beanFactory.getBeansOfType(RouteGroup::class.java).values.forEach {
                addRouteGroup(it)
            }
        }).apply {
            addPublicResourceRoute()
            start()
        }
    }

    private fun addErrorHandlers(application: Application) {
        application.apply {
            errorHandler.setExceptionHandler(IllegalArgumentException::class.java, { exception, routeContext ->
                LOGGER.warn(exception.message, exception)
                routeContext.status(400)
                routeContext.json().send(mapOf("error" to exception.message))
            })

            errorHandler.setExceptionHandler(PippoRuntimeException::class.java, { exception, routeContext ->
                LOGGER.warn(exception.message, exception)
                routeContext.status(400)
                routeContext.json().send(mapOf("error" to exception.message))
            })

            errorHandler.setExceptionHandler(Exception::class.java, { exception, routeContext ->
                LOGGER.error(exception.message, exception)
                routeContext.status(500)
                routeContext.json().send(mapOf("error" to exception.message))
            })
        }
    }

    private fun addSettings(application: Application) {
        application.apply {
            pippoSettings.overrideSetting("application.name", env.getProperty("application.name"))
            pippoSettings.overrideSetting("application.version", env.getProperty("application.version"))
            pippoSettings.overrideSetting("application.languages", env.getProperty("application.languages"))

            pippoSettings.overrideSetting("server.port", env.getProperty("server.port"))
            pippoSettings.overrideSetting("server.host", env.getProperty("server.host"))
            pippoSettings.overrideSetting("server.contextPath", env.getProperty("server.contextPath"))

            pippoSettings.overrideSetting("jetty.minThreads", env.getProperty("jetty.minThreads"))
            pippoSettings.overrideSetting("jetty.maxThreads", env.getProperty("jetty.maxThreads"))
            pippoSettings.overrideSetting("jetty.idleTimeout", env.getProperty("jetty.idleTimeout"))
        }
    }

}