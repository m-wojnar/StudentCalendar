package com.broprojects.studentcalendar.database.calendar

import androidx.room.*

@Entity(tableName = "Subjects_table")
data class Subjects(

    @PrimaryKey
    var name: String = "",
    //Nie wiem jak będzie wyglądać odniesienie do ikony (?)
    var icon: String = "",
    var color: String = ""

)

@Entity(tableName = "People_table")
data class People(

    @PrimaryKey(autoGenerate = true)
    var personId: Int,
    var firstName: String = "",
    var secondName: String = "",
    var title: String = "",
    var email: String = "",
    var phone: String = "",
    var office: Int = -1,
    var info: String = ""
)

@Entity(tableName = "Schedule_table")
data class Schedule(

    @PrimaryKey(autoGenerate = true)
    var scheduleId: Int,
    var title: String = "",
    //Typ date-time?
    //var from:
    //var to:
    var description: String = "",
    var where: String = ""
)

@Entity(tableName = "Tasks_table")
data class Tasks(

    //Nie działa format LocalDateTime, nie ogarniam dlaczego
    //var time: LocalDateTime? = LocalDateTime.now(),
    @PrimaryKey(autoGenerate = true)
    var taskId: Int,
    var title: String = "",
    var description: String = "",
    var done: Boolean = false,
    var where: String = "",
    var priority: Int = -1,
    var taskPersonId: Int

)

@Entity(tableName = "Tests_table")
data class Tests(

    //Nie działa format LocalDateTime, nie ogarniam dlaczego
    //var time: LocalDateTime? = LocalDateTime.now(),
    @PrimaryKey(autoGenerate = true)
    var testId: Int,
    var title: String = "",
    var description: String = "",
    var where: String = ""

)

//Relacja wiele do wielu People i Subjects
@Entity(primaryKeys = ["personId", "name"])
data class PeopleSubjectsCrossRef(
    val personId: Long,
    val name: Long
)
//Relacja Subjects i People w obie strony

data class PeopleWithSubjects(
    @Embedded val person: People,
    @Relation(
        parentColumn = "personId",
        entityColumn = "name",
        associateBy = Junction(PeopleSubjectsCrossRef::class)
    )
    val subjects: List<Subjects>
)

data class SubjectWithPeople(
    @Embedded val subject: Subjects,
    @Relation(
        parentColumn = "name",
        entityColumn = "personId",
        associateBy = Junction(PeopleSubjectsCrossRef::class)
    )
    val people: List<People>
)

//relacja Subjects z Schedule (1 do wielu)
data class SubjectWithSchedule(
    @Embedded val subject: Subjects,
    @Relation(
        parentColumn = "name",
        entityColumn = "scheduleId"
    )
    val schedule: List<Schedule>
)

//relacja Subjects z Tasks (1 do wielu)
data class SubjectWithTasks(
    @Embedded val subject: Subjects,
    @Relation(
        parentColumn = "name",
        entityColumn = "taskId"
    )
    val tasks: List<Tasks>
)

//relacja Subjects z Tests (1 do wielu)
data class SubjectWithTests(
    @Embedded val subject: Subjects,
    @Relation(
        parentColumn = "name",
        entityColumn = "testId"
    )
    val tests: List<Tests>
)
