package leon.homework.Sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import leon.homework.JavaBean.ChoiceExercise
import leon.homework.JavaBean.JudgExercise
import leon.homework.JavaBean.ShortExercise


/**
 * Created by BC on 2017/2/19 0019.
 */
class WorkQuesDao(context: Context) {
    private val TAG = "WorkQuesDao"
    private var context: Context? = null
    private var workhelper: WorkQuesSQLiteHelper? = null
    init {
        this.context = context
        workhelper = WorkQuesSQLiteHelper(context)
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

    fun selectChoice(choId:String):ChoiceExercise{
        val db = workhelper!!.readableDatabase
        val cursor = db.query("choicques", arrayOf("choic_sbj", "choic_A", "choic_B","choic_C","choic_D","choic_img"), "choic_id=?", arrayOf(choId), null, null, null)
        var ChoiceEX:ChoiceExercise? = null
        while (cursor.moveToNext()) {
            val content = cursor.getString(cursor.getColumnIndex("choic_sbj"))
            val A = cursor.getString(cursor.getColumnIndex("choic_A"))
            val B = cursor.getString(cursor.getColumnIndex("choic_B"))
            val C = cursor.getString(cursor.getColumnIndex("choic_C"))
            val D = cursor.getString(cursor.getColumnIndex("choic_D"))
            val cs = arrayOf(A,B,C,D)
            val img = cursor.getString(cursor.getColumnIndex("choic_img"))
            ChoiceEX = ChoiceExercise(choId,content,cs,img)
        }
        return ChoiceEX!!
    }
    fun selectJudg(judgId:String): JudgExercise {
        val db = workhelper!!.readableDatabase
        val cursor = db.query("tfques", arrayOf("tf_sbj","tf_ans","tf_img"), "tf_id=?", arrayOf(judgId), null, null, null)
        var JudgEX:JudgExercise? = null
        while (cursor.moveToNext()) {
            val tf_sbj = cursor.getString(cursor.getColumnIndex("tf_sbj"))
            val tf_ans = cursor.getInt(cursor.getColumnIndex("tf_ans"))
            val tf_img = cursor.getString(cursor.getColumnIndex("tf_img"))
            JudgEX = JudgExercise(judgId,tf_sbj,tf_ans,tf_img)
        }
        return JudgEX!!
    }
    fun selectSho(shoId:String): ShortExercise {
        val db = workhelper!!.readableDatabase
        val cursor = db.query("ansques", arrayOf("ans_sbj","ans_ans","ans_img"), "ans_id=?", arrayOf(shoId), null, null, null)
        var ShoEX:ShortExercise? = null
        while (cursor.moveToNext()) {
            val ans_sbj = cursor.getString(cursor.getColumnIndex("ans_sbj"))
            val ans_ans = cursor.getString(cursor.getColumnIndex("ans_ans"))
            val ans_img = cursor.getString(cursor.getColumnIndex("ans_img"))
            ShoEX = ShortExercise(shoId,ans_sbj,ans_ans,ans_img)
        }
        return ShoEX!!
    }
}