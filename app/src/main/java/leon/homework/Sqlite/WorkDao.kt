package leon.homework.Sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log


/**
 * Created by BC on 2017/2/19 0019.
 */
class WorkDao(context: Context) {
    private val TAG = "WorkDao"
    private var context: Context? = null
    private var workhelper: WorkSQLiteHelper? = null
    init {
        this.context = context
        workhelper = WorkSQLiteHelper(context)
    }

    fun execSQL(sql: String) {
        val db: SQLiteDatabase = workhelper!!.writableDatabase      //获得数据库
        val sqlArray = sql.split(";".toRegex()).dropLastWhile(String::isEmpty).toTypedArray() //使用;分隔多项sql语句
        try {
            db.beginTransaction()                                  //开启事务
            for (i in 0..sqlArray.size-2) {                        //最后一条分隔为空
                db.execSQL(sqlArray[i] + ";")
                Log.d("WorkDAO", sqlArray[i] + ";")
            }
            db.setTransactionSuccessful()
            Log.d("WorkDAO ","成功执行")
        } catch (e: Exception) {
            Log.d("WorkDAO","SQL错误")
            Log.e(TAG, "", e.cause)
        } finally {
            db.endTransaction()
            db.close()
        }
    }
}