package es.javiercarrasco.mysqlite.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import es.javiercarrasco.mysqlite.data.model.Editorial
import es.javiercarrasco.mysqlite.data.model.SuperHero

class SupersDBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VER) {

    private val TAG = "SQLite"

    companion object {
        val DATABASE_VER = 1
        val DATABASE_NAME = "SuperHeros.db"

        val TABLE_EDITORIAL = "Editorials"
        val COLUMN_ID = "_id" // valido para las dos tablas
        val COLUMN_EDITORIAL = "editorial"

        val TABLE_SUPER = "Supers"
        val COLUMN_SUPERNAME = "supername"
        val COLUMN_REALNAME = "realname"
        val COLUMN_FAV = "favorite"
        val COLUMN_ID_ED = "idEd"
    }

    // Este método es llamado cuando se crea la base por primera vez. Debe producirse
    // la creación de todas las tablas que formen la base de datos.
    override fun onCreate(db: SQLiteDatabase?) {
        try {
            var query = "CREATE TABLE $TABLE_EDITORIAL " +
                    "($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_EDITORIAL TEXT)"
            db!!.execSQL(query)

            query = "CREATE TABLE $TABLE_SUPER " +
                    "($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_SUPERNAME TEXT," +
                    "$COLUMN_REALNAME TEXT," +
                    "$COLUMN_FAV INTEGER," +
                    "$COLUMN_ID_ED INTEGER," +
                    "FOREIGN KEY($COLUMN_ID_ED) REFERENCES $TABLE_EDITORIAL)"
            db.execSQL(query)

        } catch (e: SQLiteException) {
            Log.e("$TAG onCreate", e.message.toString())
        }
    }

    // Este método se invocará cuando la base de datos necesite ser actualizada. Se utiliza
    // para hacer DROPs, añadir tablas o cualquier acción que actualice el esquema de la BD.
    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        try {
            var query = "DROP TABLE IF EXISTS $TABLE_SUPER"
            db!!.execSQL(query)
            query = "DROP TABLE IF EXISTS $TABLE_EDITORIAL"
            db.execSQL(query)
            onCreate(db)
        } catch (e: SQLiteException) {
            Log.e("$TAG onUpgrade", e.message.toString())
        }
    }

    // Método opcional. Se llamará a este método después de abrir la base de datos,
    // antes de ello, comprobará si está en modo lectura. Se llama justo después de
    // establecer la conexión y crear el esquema.
    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
        Log.d("$TAG onOpen", "¡¡Base de datos abierta!!")
    }

    // Se añade una editorial.
    fun addEditorial(name: String): Long {
        // Se crea un ArrayMap<>() haciendo uso de ContentValues().
        val data = ContentValues().apply {
            put(COLUMN_EDITORIAL, name)
        }

        // Se abre la BD en modo escritura.
        val db = this.writableDatabase
        // Devuelve el id del registro insertado, -1 en caso de error.
        val result = db.insert(TABLE_EDITORIAL, null, data)
        db.close()

        return result
    }

    // Método para eliminar una editorial de la tabla por el identificador.
    fun delEditorial(identifier: Int): Int {
        val args = arrayOf(identifier.toString())

        // Se abre la BD en modo escritura.
        val db = this.writableDatabase

        // Se puede elegir un sistema u otro, teniendo en cuenta que execSQL no devuelve nada.
        val result = db.delete(TABLE_EDITORIAL, "$COLUMN_ID = ?", args)
        // db.execSQL("DELETE FROM TABLE_EDITORIAL WHERE COLUMN_ID = ?", args)

        db.close()
        return result
    }

    // Método para actualizar el nombre de una editorial de la tabla por el id.
    fun updateEditorial(idEditorial: Int, newName: String) {
        val args = arrayOf(idEditorial.toString())

        // Se crea un ArrayMap<>() con los datos nuevos.
        val data = ContentValues()
        data.put(COLUMN_EDITORIAL, newName)

        val db = this.writableDatabase
        db.update(TABLE_EDITORIAL, data, "$COLUMN_ID = ?", args)

        db.close()
    }

    // Se obtienen todas las editoriales.
    fun getEditorials(): MutableList<Editorial> {
        val result: MutableList<Editorial> = mutableListOf()

        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_EDITORIAL;", null)

        if (cursor.moveToFirst()) {
            do {
                result.add(Editorial(cursor.getInt(0), cursor.getString(1)))
                Log.d("Editorials", cursor.getString(1))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return result
    }

    // Se obtienen todas las editoriales.
    fun getEditorialsCursor(): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_EDITORIAL;", null)
    }

    // Añade un súper.
    fun addSuperHero(superHero: SuperHero): Long {
        val data = ContentValues().apply {
            put(COLUMN_SUPERNAME, superHero.superName)
            put(COLUMN_REALNAME, superHero.realName)
            put(COLUMN_FAV, superHero.favorite)
            put(COLUMN_ID_ED, superHero.editorial.id)
        }

        val db = this.writableDatabase
        val result = db.insert(TABLE_SUPER, null, data)
        db.close()

        return result
    }

    // Actualiza un súper.
    fun updateSuperHero(superHero: SuperHero): Int {
        val data = ContentValues().apply {
            put(COLUMN_SUPERNAME, superHero.superName)
            put(COLUMN_REALNAME, superHero.realName)
            put(COLUMN_FAV, superHero.favorite)
            put(COLUMN_ID_ED, superHero.editorial.id)
        }

        val args = arrayOf(superHero.id.toString())

        val db = this.writableDatabase
        val result = db.update(TABLE_SUPER, data, "$COLUMN_ID = ?", args)
        db.close()

        return result
    }

    fun delSuperHero(idSuperHero: Int): Int {
        val args = arrayOf(idSuperHero.toString())

        val db = readableDatabase
        val result = db.delete(TABLE_SUPER, "$COLUMN_ID = ?", args)

        db.close()
        return result
    }

    @SuppressLint("Recycle")
    fun getSuperById(idSuper: Int): SuperHero {
        val args = arrayOf(idSuper.toString())

        val db = this.readableDatabase
        val cursor: Cursor =
            db.rawQuery(
                "SELECT * FROM $TABLE_SUPER INNER JOIN $TABLE_EDITORIAL ON " +
                        "$TABLE_SUPER.$COLUMN_ID_ED = $TABLE_EDITORIAL.$COLUMN_ID " +
                        "WHERE $TABLE_SUPER.$COLUMN_ID = ?;", args
            )

        var result = SuperHero()
        if (cursor.moveToFirst()) {
            result = SuperHero(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getInt(3),
                Editorial(cursor.getInt(5), cursor.getString(6))
            )
        }
        return result
    }

    // Muestra todos los Supers.
    fun getAllSuperHeros(): MutableList<SuperHero> {
        val result: MutableList<SuperHero> = mutableListOf()

        val db = this.readableDatabase
        val cursor: Cursor =
            db.rawQuery(
                "SELECT * FROM $TABLE_SUPER INNER JOIN $TABLE_EDITORIAL ON " +
                        "$TABLE_SUPER.$COLUMN_ID_ED = $TABLE_EDITORIAL.$COLUMN_ID " +
                        "ORDER BY $COLUMN_SUPERNAME;", null
            )

        if (cursor.moveToFirst()) {
            do {
                result.add(
                    SuperHero(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        Editorial(cursor.getInt(5), cursor.getString(6))
                        //(cursor.getInt(3) == 1), // BOOLEAN
                        //Editorial(cursor.getInt(5), cursor.getString(6))
                    )
                )
            } while (cursor.moveToNext())
        }

        cursor.close()
        return result
    }

    // Muestra todos los Supers.
    fun getAllSuperHerosCursor(): Cursor {
        val db = this.readableDatabase
        return db.rawQuery(
            "SELECT * FROM $TABLE_SUPER INNER JOIN $TABLE_EDITORIAL ON " +
                    "$TABLE_SUPER.$COLUMN_ID_ED = $TABLE_EDITORIAL.$COLUMN_ID " +
                    "ORDER BY $COLUMN_SUPERNAME;", null
        )
    }

    // Actualiza el estado favorito.
    fun updateFab(idSuper: Int, stateFav: Int) {
        val args = arrayOf(idSuper.toString())

        val data = ContentValues()
        data.put(COLUMN_FAV, (if (stateFav == 1) 0 else 1))

        val db = this.writableDatabase
        db.update(TABLE_SUPER, data, "$COLUMN_ID = ?", args)
        db.close()
    }
}