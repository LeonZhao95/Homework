package leon.homework.Sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log



/**
 * Created by BC on 2017/2/14 0014.
 */

class ChatSQLiteHelper(context: Context, name: String="ChatLog.db", factory: SQLiteDatabase.CursorFactory?=null, version: Int=1) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase) {
        Log.d("StuChatSQlite","建立数据库")
        db.execSQL("create table aiye ("
                + "ChatId integer primary key AUTOINCREMENT,"
                + "Chat_Time varchar(16),"
                + "UserType varchar(1),"
                + "Chat_Content varchar2(500));")
    }
    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {

    }
    /*fun CreateTable(ChatUser:String){
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
    }*/
}
