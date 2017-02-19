package leon.homework.Utils

import android.graphics.*
import android.net.Uri
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

/**
 * Created by mjhzds on 2017/1/22.
 */

object Utils {
    /**
     * Save image to the SD card

     * @param photoBitmap
     * *
     * @param photoName
     * *
     * @param path
     */
    fun savePhoto(photoBitmap: Bitmap?, path: String, photoName: String): String {
        var localPath: String? = null
        val dir = File(path)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val photoFile = File(path, photoName + ".jpg")
        var fileOutputStream: FileOutputStream? = null
        try {
            fileOutputStream = FileOutputStream(photoFile)
            if (photoBitmap != null) {
                if (photoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)) { // 转换完成
                    localPath = photoFile.path
                    fileOutputStream.flush()
                }
            }
        } catch (e: FileNotFoundException) {
            photoFile.delete()
            localPath = null
            e.printStackTrace()
        } catch (e: IOException) {
            photoFile.delete()
            localPath = null
            e.printStackTrace()
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return localPath!!
    }

    /**
     * 转换图片成圆形
     * @param bitmap
     * *            传入Bitmap对象
     * *
     * @param tempUri
     * *
     * @return
     */
    fun toRoundBitmap(bitmap: Bitmap, tempUri: Uri): Bitmap {
        var width = bitmap.width
        var height = bitmap.height
        val roundPx: Float
        val left: Float
        val top: Float
        val right: Float
        val bottom: Float
        val dst_left: Float
        val dst_top: Float
        val dst_right: Float
        val dst_bottom: Float
        if (width <= height) {
            roundPx = (width / 2).toFloat()
            left = 0f
            top = 0f
            right = width.toFloat()
            bottom = width.toFloat()
            height = width
            dst_left = 0f
            dst_top = 0f
            dst_right = width.toFloat()
            dst_bottom = width.toFloat()
        } else {
            roundPx = (height / 2).toFloat()
            val clip = ((width - height) / 2).toFloat()
            left = clip
            right = width - clip
            top = 0f
            bottom = height.toFloat()
            width = height
            dst_left = 0f
            dst_top = 0f
            dst_right = height.toFloat()
            dst_bottom = height.toFloat()
        }

        val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)

        val color = 0xff424242.toInt()
        val paint = Paint()
        val src = Rect(left.toInt(), top.toInt(), right.toInt(),
                bottom.toInt())
        val dst = Rect(dst_left.toInt(), dst_top.toInt(),
                dst_right.toInt(), dst_bottom.toInt())
        val rectF = RectF(dst)

        paint.isAntiAlias = true// 设置画笔无锯齿

        canvas.drawARGB(0, 0, 0, 0) // 填充整个Canvas
        paint.color = color

        // 以下有两种方法画圆,drawRounRect和drawCircle
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);//
        // 画圆角矩形，第一个参数为图形显示区域，第二个参数和第三个参数分别是水平圆角半径和垂直圆角半径。
        canvas.drawCircle(roundPx, roundPx, roundPx, paint)

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)// 设置两张图片相交时的模式,参考http://trylovecatch.iteye.com/blog/1189452
        canvas.drawBitmap(bitmap, src, dst, paint) // 以Mode.SRC_IN模式合并bitmap和已经draw了的Circle

        return output
    }

    fun setid(): String {
        val time = System.currentTimeMillis().toString();
        val num = (Math.random()*900+100).toInt()
        val id = time+num.toString()
        return id
    }
}
