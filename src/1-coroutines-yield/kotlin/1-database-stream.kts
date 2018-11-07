@file:Suppress("EXPERIMENTAL_FEATURE_WARNING", "UNUSED_PARAMETER")

import kotlin.coroutines.experimental.buildSequence



class AnimalsDbTable {
    fun readRows(): List<Row> {
        val dbConnection = DbConnection()
        try {
            val result = ArrayList<Row>()

            val resultSet = dbConnection.runQuery("select * from Animals")
            while (resultSet.hasNext()) {
                result.add(resultSet.next())
            }

            return result
        } finally {
            dbConnection.close()
        }
    }
}

data class Row(
    val id: Int,
    val value: String
)





AnimalsDbTable()
    .readRows()
    .forEach { row -> println(row) }













class AnimalsDbTableV2 {
    fun readRows(): Sequence<Row> {
        return buildSequence {
            val dbConnection = DbConnection()
            try {
                val resultSet = dbConnection.runQuery("select * from Animals")
                while (resultSet.hasNext()) {
                    yield(resultSet.next())
                }
            } finally {
                dbConnection.close()
            }
        }
    }
}














class DbConnection {
    private var closed = false

    fun runQuery(sql: String): ResultSet<Row> {
        return buildSequence {
            if (closed) error("Can't read from DbConnection because it's closed")
            yield(Row(1, "🐶"))
            if (closed) error("Can't read from DbConnection because it's closed")
            yield(Row(2, "🐷"))
            if (closed) error("Can't read from DbConnection because it's closed")
            yield(Row(3, "🙈"))
            if (closed) error("Can't read from DbConnection because it's closed")
        }.iterator()
    }

    fun close() {
        closed = true
        println("DbConnection closed")
    }
}

typealias ResultSet<T> = Iterator<T>
