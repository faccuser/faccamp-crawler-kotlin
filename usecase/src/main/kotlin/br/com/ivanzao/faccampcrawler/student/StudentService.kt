package br.com.ivanzao.faccampcrawler.student

import br.com.ivanzao.faccampcrawler.aynsc.AsyncExecutor
import br.com.ivanzao.faccampcrawler.config.ConfigService
import br.com.ivanzao.faccampcrawler.student.adapter.StudentDataCacheRepository
import br.com.ivanzao.faccampcrawler.student.crawler.StudentDataCrawler
import br.com.ivanzao.faccampcrawler.student.model.StudentData
import br.com.ivanzao.faccampcrawler.student.model.StudentDataCache
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime.now
import java.time.temporal.ChronoUnit.HOURS

@Service
class StudentService(configService: ConfigService,
                     private val executor: AsyncExecutor,
                     private val crawler: StudentDataCrawler,
                     private val cacheService: StudentDataCacheRepository) {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(StudentService::class.java)
    }

    private val cacheTimeHours = configService.getRequiredInt("cache.expiration.time")

    fun retrieveData(ra: String, password: String): StudentData {
        LOGGER.info("Starting retrieve for user with ra: $ra")
        val loginData = crawler.login(ra, password)
        LOGGER.info("Successfully logged in user with ra: $ra")

        LOGGER.info("Retrieve cache for user with ra: $ra")
        val cache = cacheService.getCache(ra)
        if (cache != null && !isExpired(cache)) {
            LOGGER.info("Cache found. Returning cached data for user with ra: $ra")
            return cache.data
        }

        LOGGER.info("Cache not found. Retrieving user data for user with ra: $ra")
        val studentData = crawler.retrieveData(loginData)

        LOGGER.info("Saving new cache data for user with ra: $ra")
        executor.run { cacheService.save(ra, now(), studentData) }

        return studentData
    }

    private fun isExpired(cache: StudentDataCache) = cache.createdAt.until(now(), HOURS) > cacheTimeHours
}