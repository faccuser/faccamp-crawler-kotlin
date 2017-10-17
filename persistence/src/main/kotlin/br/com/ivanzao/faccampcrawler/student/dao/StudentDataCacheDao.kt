package br.com.ivanzao.faccampcrawler.student.dao

import br.com.ivanzao.faccampcrawler.student.adapter.StudentDataCacheDao
import br.com.ivanzao.faccampcrawler.student.document.StudentDataCacheDocument
import br.com.ivanzao.faccampcrawler.student.model.StudentDataCache
import br.com.ivanzao.faccampcrawler.student.repository.StudentDataCacheRepository
import org.springframework.stereotype.Repository

@Repository
class StudentDataCacheDaoImpl(private val repository: StudentDataCacheRepository) : StudentDataCacheDao {

    override fun get(ra: String): StudentDataCache? {
        val caches = repository.findTop1ByRaOrderByCreatedAtDesc(ra)

        if (caches.isEmpty()) { return null }

        return caches[0].toModel()
    }

    override fun save(cache: StudentDataCache) {
        repository.save(StudentDataCacheDocument(cache))
    }

}