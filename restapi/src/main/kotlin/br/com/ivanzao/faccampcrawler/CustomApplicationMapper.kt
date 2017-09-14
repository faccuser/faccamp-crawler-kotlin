package br.com.ivanzao.faccampcrawler

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JSR310Module
import ro.pippo.core.Application
import ro.pippo.jackson.JacksonJsonEngine

class CustomApplicationMapper : JacksonJsonEngine() {

    override fun init(application: Application) {
        super.init(application)

        super.objectMapper.registerModule(JSR310Module())
        super.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    }

}