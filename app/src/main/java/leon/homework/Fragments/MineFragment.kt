package leon.homework.Fragments

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import leon.homework.AppContext
import leon.homework.Data.Const
import leon.homework.R
import leon.homework.Sqlite.WorkQuesDao
import leon.homework.UI.MasterLayout
import leon.homework.Utils.Downloader
import leon.homework.Utils.FileUtil
import leon.homework.Utils.ZipUtil
import org.jetbrains.anko.async
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.toast
import java.io.File

class MineFragment : BaseFragment(){
    override val layoutResourceId: Int = R.layout.fragment_mine
    private var mParam1: String? = null
    private var masterLayout :MasterLayout? = null
    private val mhandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                Const.DOWNLOAD_OVER -> {
                    masterLayout!!.finalAnimation()
                    toast("下载完成")
                    info("开始解压")
                    ZipUtil.unzipFiles(File(Const.DOWNLOAD_PATH+"1.zip"),Const.DATA_PATH)
                    info("解压完成")
                    WorkQuesDao(AppContext.instance!!).execSQL(FileUtil.loadFile(Const.DATA_PATH+"ques_tfques.sql"))
                    WorkQuesDao(AppContext.instance!!).execSQL(FileUtil.loadFile(Const.DATA_PATH+"ques_choicques.sql"))
                }
                Const.DOWNLOAD_UPDATE_PROGRESS ->{
                    masterLayout!!.cusview.setupprogress(msg.arg1)
                }
                Const.DOWNLOAD_ERROR ->{
                    masterLayout!!.reset()
                    toast("服务器异常，重试")
                }
                Const.DOWNLOAD_FILEEXIST->{
                    info("已存在")
                    val s = FileUtil.loadFile(Const.DATA_PATH+"ques_tfques.sql")
                    WorkQuesDao(AppContext.instance!!).execSQL(s)
                    WorkQuesDao(AppContext.instance!!).execSQL(FileUtil.loadFile(Const.DATA_PATH+"ques_choicques.sql"))
                }
                else -> {
                    toast("???")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
        }
        AppContext.downloadhandler = mhandler
    }




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(layoutResourceId ,container,false)
        masterLayout = view.findViewById(R.id.MasterLayout) as MasterLayout
        masterLayout!!.setOnClickListener{
            masterLayout!!.animation()   //Need to call this method for animation and progression
            when (masterLayout!!.flg_frmwrk_mode) {
                1 -> {
                    download()
                    //masterLayout!!.cusview.setupprogress(90)
                    //Start state. Call your method
                }
                2 -> {
                    //Running state. Call your method
                }
                3 -> {
                    //End state. Call your method
                }
            }
        }
        return view
    }

    fun download(){
        async() {
            val url = Const.DownloadQuestionsUrl
            val path = Const.DOWNLOAD_PATH
            Downloader().Download(url,path,"1.zip")
            Downloader().Download(url,path,"2.zip")
        }
    }


    companion object {
        private val ARG_PARAM1 = "param1"
        fun newInstance(param1: String): MineFragment {
            val fragment = MineFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            fragment.arguments = args
            return fragment
        }
    }
}
