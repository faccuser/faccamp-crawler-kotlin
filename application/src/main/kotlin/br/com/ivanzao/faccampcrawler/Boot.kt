package br.com.ivanzao.faccampcrawler

import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.ComponentScan

@ComponentScan(basePackages = arrayOf("br.com.ivanzao.faccampcrawler"))
class Boot {

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            AnnotationConfigApplicationContext(Boot::class.java)
        }
    }

}