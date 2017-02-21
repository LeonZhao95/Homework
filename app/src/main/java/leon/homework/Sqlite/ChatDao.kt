package leon.homework.Sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import java.sql.SQLException

/**
 * Created by BC on 2017/2/21 0021.
 */
class ChatDao(context: Context) {
    private val TAG = "ChatDao"
    private var context: Context? = null
    private val TABLENAME = "ChatLog"
    private var chathelper: ChatSQLiteHelper? = null
    init {
        this.context = context
        chathelper = ChatSQLiteHelper(context)
    }

    fun execSQL(sql: String) {
        val db: SQLiteDatabase = chathelper!!.writableDatabase      //获得数据库
        try {
            db.beginTransaction()
            db.execSQL(sql)
            db.setTransactionSuccessful()
            Log.d(TAG,"成功执行")
        } catch (e: Exception) {
            Log.d(TAG,"SQL错误")
            Log.e(TAG, "", e.cause)
        } finally {
            db.endTransaction()
            db.close()
        }
    }
    fun CreateTable(ChatUser:String){
        val db: SQLiteDatabase = chathelper!!.writableDatabase
        try {
            db.execSQL("create table if not exists "+ChatUser+ "("
                    + "ChatId integer primary key ,"
                    + "Chat_Time varchar(16),"
                    + "UserType varchar(1),"
                    + "Chat_Content varchar2(500));")
        }catch (e: SQLException){
            Log.d(TAG,e.message)
        }finally {
            db.close()
        }
    }
}