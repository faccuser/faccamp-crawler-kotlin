package br.com.ivanzao.faccampcrawler.student.document

import br.com.ivanzao.faccampcrawler.student.model.*
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Document(collection = "studentDataCache")
class StudentDataCacheDocument {

    constructor()

    constructor(cache: StudentDataCache) {
        this.ra = cache.ra
        this.createdAt = cache.createdAt
        this.data = StudentDataDomain(cache.data)
    }

    @Id
    var id: String? = UUID.randomUUID().toString()

    @Indexed
    var ra: String? = null

    var createdAt: LocalDateTime? = null

    var data: StudentDataDomain? = null

    fun toModel(): StudentDataCache {
        return StudentDataCache(ra = this.ra!!,
                createdAt = this.createdAt!!,
                data = this.data!!.toModel())
    }
}

class StudentDataDomain {

    constructor()

    constructor(data: StudentData) {
        this.ra = data.ra
        this.name = data.name
        this.program = ProgramDomain(data.program)
        this.courses = data.courses.map { CourseDataDomain(it) }
    }

    var ra: String? = null
    var name: String? = null
    var program: ProgramDomain? = null
    var courses: List<CourseDataDomain>? = null

    fun toModel(): StudentData {
        return StudentData(
                ra = this.ra!!,
                name = this.name!!,
                program = this.program!!.toModel(),
                courses = this.courses!!.map { it.toModel() }
        )
    }
}

class ProgramDomain {

    constructor()

    constructor(program: Program) {
        this.code = program.code
        this.name = program.name
        this.period = program.period
    }

    var code: String? = null
    var name: String? = null
    var period: Int? = null

    fun toModel(): Program {
        return Program(
                code = this.code!!,
                name = this.name!!,
                period = this.period!!
        )
    }
}

class CourseDataDomain {

    constructor()

    constructor(courseData: CourseData) {
        this.code = courseData.code
        this.name = courseData.name
        this.partialAverage = courseData.partialAverage
        this.finalAverage = courseData.finalAverage
        this.absencesLimit = courseData.absencesLimit
        this.absences = courseData.absences
        this.grades = courseData.grades.map { GradeDomain(it) }
        this.edpGrades = courseData.edpGrades.map { GradeDomain(it) }
    }

    var code: String? = null
    var name: String? = null
    var partialAverage: Double? = null
    var finalAverage: Double? = null
    var absencesLimit: Int? = null
    var absences: Int? = null
    var grades: List<GradeDomain>? = null
    var edpGrades: List<GradeDomain>? = null

    fun toModel(): CourseData {
        return CourseData(
                code = this.code!!,
                name = this.name!!,
                partialAverage = this.partialAverage,
                finalAverage = this.finalAverage,
                absencesLimit = this.absencesLimit,
                absences = this.absences,
                grades = this.grades!!.map { it.toModel() }.toMutableList(),
                edpGrades = this.edpGrades!!.map { it.toModel() }.toMutableList()
        )
    }
}

class GradeDomain {

    constructor()

    constructor(grade: Grade) {
        this.mark = grade.mark
        this.date = grade.date
    }

    var mark: Double? = null
    var date: LocalDate? = null

    fun toModel(): Grade {
        return Grade(
                mark = this.mark!!,
                date = this.date!!
        )
    }
}