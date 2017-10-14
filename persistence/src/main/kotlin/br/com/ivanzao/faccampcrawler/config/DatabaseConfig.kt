package br.com.ivanzao.faccampcrawler.config

import com.mongodb.MongoClient
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractMongoConfiguration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@EnableMongoRepositories(basePackages = arrayOf("br.com.ivanzao.faccampcrawler"))
class DatabaseConfig(configService: ConfigService) : AbstractMongoConfiguration() {

    private val DATABASE_ADDRESS = configService.getRequiredString("database.address")
    private val DATABASE_PORT = configService.getRequiredInt("database.port")
    private val DATABASE_NAME = configService.getRequiredString("database.name")

    override fun mongoClient() = MongoClient(DATABASE_ADDRESS, DATABASE_PORT)

    override fun getDatabaseName() = DATABASE_NAME
}