package leon.homework.Utils

import android.os.Message
import android.util.Log
import leon.homework.AppContext
import leon.homework.Data.Const
import java.io.File
import java.io.FileInputStream
import java.io.RandomAccessFile
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by BC on 2017/2/18 0018.
 */
class Downloader{
    var activeThread = 0
    val threadCount = 3
    var curDownCount = 0
    var maxDownCount = 0
    var isError= false
    var handler = AppContext.downloadhandler
    /**
     * @param url 地址
     * @param path 文件路径
     */
    fun Download(url:String,path:String,filename:String) {
        if (FileUtil.fileIsExists(path + filename)) {
            val msg = Message()
            msg.what = Const.DOWNLOAD_FILEEXIST
            handler!!.sendMessage(msg)
        } else {
            try {
                Log.d("download:", path)
                //构造URL地址
                val murl = URL(url)
                //打开连接
                val conn = murl.openConnection() as HttpURLConnection
                //设置请求超时的时间
                conn.connectTimeout = 5000
                //设置请求方式
                conn.requestMethod = "GET"
                //获取相应码
                val code = conn.responseCode
                if (code == 200) {//请求成功
                    //获取请求数据的长度
                    val length = conn.contentLength
                    //设置进度条的最大值
                    /*pb!!.max = length*/
                    maxDownCount = length
                    Log.d(">>>>>>", "请求成功")
                    val dirFirstFolder = File(path)
                    if (!dirFirstFolder.exists()) {
                        dirFirstFolder.mkdirs()
                    }
                    //在客户端创建一个跟服务器文件大小相同的临时文件
                    val raf = RandomAccessFile(path + filename, "rwd")
                    //指定临时文件的长度
                    raf.setLength(length.toLong())
                    raf.close()
                    //假设3个线程去下载资源
                    //平均每一个线程要下载的文件的大小
                    val blockSize = length / threadCount
                    for (threadId in 1..threadCount) {
                        //当前线程下载数据的开始位置
                        val startIndex = blockSize * (threadId - 1)
                        //当前线程下载数据的结束位置
                        var endIndex = blockSize * threadId - 1
                        //确定最后一个线程要下载数据的最大位置
                        if (threadId == threadCount) {
                            endIndex = length
                        }
                        //显示下载数据的区间
                        //Log.d("Downloader","线程【$threadId】开始下载：$startIndex---->$endIndex")
                        //开启下载的子线程
                        ThreadPoolManager.instance.execute(DownloadThread(url, path + filename, threadId, startIndex, endIndex))
                        //DownloadThread(this.url,this.path, threadId, startIndex, endIndex).start()
                        //当前下载活动的线程数加1
                        activeThread++
                        //Log.d("Downloader","当前活动的线程数：" + activeThread)
                    }
                } else {//请求失败
                    Log.d("Downloader", "服务器异常，下载失败！")
                    val msg = Message()
                    msg.what = Const.DOWNLOAD_ERROR
                    handler!!.sendMessage(msg)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("Downloader", "服务器异常，下载失败！")
                val msg = Message()
                msg.what = Const.DOWNLOAD_ERROR
                handler!!.sendMessage(msg)
            }
        }
    }
    /**
     * 构造方法
     * @param path 下载文件的路径
     * *
     * @param threadId 下载文件的线程
     * *
     * @param startIndex 下载文件开始的位置
     * *
     * @param endIndex 下载文件结束的位置
     */
    inner class DownloadThread(private val Url: String,private val filepath:String,private val threadId: Int, private var startIndex: Int, private val endIndex: Int) : Thread() {
        override fun run() {
            //构造URL地址
            try {
                val tempFile = File("sdcard/$threadId.txt")
                //检查记录是否存在,如果存在读取数据，设置真实下载开始的位置
                if (tempFile.exists()) {
                    val fis = FileInputStream(tempFile)
                    val temp = ByteArray(1024)
                    val length = fis.read(temp)
                    //读取到已经下载的位置
                    val downloadNewIndex = Integer.parseInt(String(temp, 0, length))
                    //计算出已经下载的数据长度
                    val areadyDown = downloadNewIndex - startIndex
                    //累加已经下载的数据量
                    curDownCount += areadyDown
                    //设置进度条已经下载的数据量
                    val msg = Message()
                   // pb!!.setProgress(curDownCount)
                    msg.arg1 = curDownCount*100/maxDownCount
                    msg.what = Const.DOWNLOAD_UPDATE_PROGRESS
                    handler!!.sendMessage(msg)
                    //设置重新开始下载的开始位置
                    startIndex = downloadNewIndex
                    fis.close()
                    //显示真实下载数据的区间
                    //Log.d("Downloader","线程【$threadId】真实开始下载数据区间：$startIndex---->$endIndex")
                }

                val url = URL(Url)
                val conn = url.openConnection() as HttpURLConnection
                conn.connectTimeout = 5000
                conn.requestMethod = "GET"
                //设置请求属性，请求部分资源
                conn.setRequestProperty("Range", "bytes=$startIndex-$endIndex")
                val code = conn.responseCode
                if (code == 206) {//下载部分资源，正常返回的状态码为206
                    val ism = conn.inputStream//已经设置了请求的位置，所以返回的是对应的部分资源
                    //构建随机访问文件
                    val raf = RandomAccessFile(filepath, "rwd")
                    //设置 每一个线程随机写文件开始的位置
                    raf.seek(startIndex.toLong())
                    //开始写文件
                    val buffer = ByteArray(1024)
                    //该线程已经下载数据的长度
                    var len = ism.read(buffer)
                    var total = 0
                    while (len != -1) {//读取输入流
                        //记录当前线程已下载数据的长度
                        val file = RandomAccessFile("sdcard/$threadId.txt", "rwd")
                        raf.write(buffer, 0, len)//写文件
                        total += len//更新该线程已下载数据的总长度
                        //Log.d("Downloader","线程【" + threadId + "】已下载数据：" + (total + startIndex))
                        //将已下载数据的位置记录写入到文件
                        file.write(((startIndex + total).toString() + "").toByteArray())
                        //累加已经下载的数据量
                        curDownCount += len
                        //更新进度条【进度条的更新可以在非UI线程直接更新，具体见底层源代码】
                        //更新下载进度
                        val msg = Message.obtain()
                        msg.what = Const.DOWNLOAD_UPDATE_PROGRESS
                        msg.arg1 = curDownCount*100/maxDownCount
                        handler!!.sendMessage(msg)
                        len = ism.read(buffer)
                        file.close()
                    }
                    ism.close()
                    raf.close()
                    //提示下载完毕
                    Log.d("Downloader","线程【$threadId】下载完毕")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("Downloader","线程【$threadId】下载出现异常！！")
                val msg = Message()
                msg.what = Const.DOWNLOAD_ERROR
                handler!!.sendMessage(msg)
                isError =true
            } finally {
                //活动的线程数减少
                activeThread--
                if (activeThread === 0) {
                    for (i in 1..threadCount) {
                        val tempFile = File("sdcard/$i.txt")
                        tempFile.delete()
                    }
                    Log.d("Downloader","下载完毕，已清除全部临时文件")
                    //界面消息提示下载完毕
                    if(!isError){
                        val msg = Message()
                        msg.what = Const.DOWNLOAD_OVER
                        handler!!.sendMessage(msg)
                    }
                }
            }

        }
    }
}