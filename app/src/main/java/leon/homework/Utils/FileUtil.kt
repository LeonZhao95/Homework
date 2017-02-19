package leon.homework.Utils

import java.io.BufferedReader
import java.io.File
import java.io.FileReader


/**
 * Created by BC on 2017/2/19 0019.
 */
object FileUtil{
    fun loadFile(filepath:String):String{
        val sb = StringBuffer()
        try {
            val file = File(filepath)
            val br = BufferedReader(FileReader(file))
            var readline = ""
            readline = br.readLine()
            while (readline!= null) {
                sb.append(readline+"\n")
                readline = br.readLine()
            }
            br.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return sb.toString()
    }

    fun fileIsExists(filepath:String): Boolean {
        try {
            val f = File(filepath)
            if (!f.exists()) {
                return false
            }
        } catch (e: Exception) {
            // TODO: handle exception
            return false
        }
        return true
    }
}