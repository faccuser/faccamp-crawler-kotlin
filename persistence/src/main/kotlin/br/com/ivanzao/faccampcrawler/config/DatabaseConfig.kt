package br.com.ivanzao.faccampcrawler.config

import com.mongodb.MongoClient
import com.mongodb.MongoCredential.createCredential
import com.mongodb.ServerAddress
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractMongoConfiguration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import java.util.Collections.singletonList

@Configuration
@EnableMongoRepositories(basePackages = arrayOf("br.com.ivanzao.faccampcrawler"))
class DatabaseConfig(configService: ConfigService) : AbstractMongoConfiguration() {

    private val DATABASE_ADDRESS = configService.getRequiredString("database.address")
    private val DATABASE_PORT = configService.getRequiredInt("database.port")
    private val DATABASE_NAME = configService.getRequiredString("database.name")
    private val DATABASE_USER = configService.getRequiredString("database.user")
    private val DATABASE_PASSWORD = configService.getRequiredString("database.password")

    override fun mongoClient() : MongoClient {
        return MongoClient(singletonList(ServerAddress(DATABASE_ADDRESS, DATABASE_PORT)),
                singletonList(createCredential(DATABASE_USER, DATABASE_NAME, DATABASE_PASSWORD.toCharArray())))
    }

    override fun getDatabaseName() = DATABASE_NAME
}