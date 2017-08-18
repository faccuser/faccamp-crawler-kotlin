package br.com.ivanzao.faccampcrawler.student.config

import org.springframework.core.env.Environment
import org.springframework.stereotype.Service

@Service
class ConfigService(private val env: Environment) {

    fun getString(property: String, default: String) = getProperty(property) ?: default

    fun getRequiredString(property: String) = getRequiredProperty(property)

    fun getInt(property: String, default: Int) = getProperty(property)?.toInt() ?: default

    fun getRequiredInt(property: String) = getRequiredProperty(property).toInt()

    fun getBoolean(property: String, default: Boolean) = getProperty(property)?.toBoolean() ?: default

    fun getRequiredBoolean(property: String) = getRequiredProperty(property).toBoolean()

    private fun getRequiredProperty(property: String): String {
        return getProperty(property) ?: throw IllegalArgumentException("Required configuration property $property not found.")
    }

    private fun getProperty(property: String): String? {
        return env.getProperty(property)
    }

}