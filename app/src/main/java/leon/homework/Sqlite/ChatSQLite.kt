package leon.homework.Sqlite

import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import leon.homework.AppContext



/**
 * Created by BC on 2017/2/14 0014.
 */

class ChatSQLite():SQLiteOpenHelper(AppContext.instance,"chatlog.db",null,1) {
    var db :SQLiteDatabase? = null
    override fun onCreate(db: SQLiteDatabase) {
        Log.d("StuChatSQlite","建立数据库")
        this.db = db
    }
    override fun onUpgrade(p0: SQLiteDatabase, p1: Int, p2: Int) {

    }
    fun CreateTable(ChatUser:String){
        try {
            db!!.execSQL("create table if not exists "+ChatUser+ "("
                    + "ChatId integer identity(1,1) primary key,"
                    + "Chat_Time varchar(16),"
                    + "UserType varchar(1),"
                    + "Chat_Content varchar2(500));")
        }catch (es:SQLException){
            Log.d("StuChatSQlite",es.message)
        }
    }
    fun InsertMsg(ChatUser:String,UserType:String,Chat_Time:String,Chat_Content:String){
        try {
            if(tabbleIsExist(ChatUser,db!!)){
                db!!.execSQL("Insert into $ChatUser (Chat_Time,UserType,Chat_Content) " +
                        "values ($Chat_Time,$UserType,$Chat_Content);")
            }else{
                CreateTable(ChatUser)
                db!!.execSQL("Insert into $ChatUser (Chat_Time,UserType,Chat_Content) " +
                        "values ($Chat_Time,$UserType,$Chat_Content);")
            }
        }catch (e:Exception){
            Log.d("StuChatSQlite",e.toString())
        }
    }

    private fun tabbleIsExist(tableName: String, db: SQLiteDatabase): Boolean {
        Log.d("DatabaseHelper", "checking tabbleIsExist " + tableName)
        var result = false
        val cursor: Cursor
        try {
            val sql = "select count(*) as c from chatlog where type ='table' and name ='"+
                    tableName.trim { it <= ' ' } + "' "
            cursor = db.rawQuery(sql, null)
            if (cursor.moveToNext()) {
                val count = cursor.getInt(0)
                if (count > 0) {
                    result = true
                }
            }
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "error in tabbleIsExist:" + e.toString())
        }
        Log.d("DatabaseHelper", "checking tabbleIsExist "
                + tableName + " " + result)
        return result
    }
}
