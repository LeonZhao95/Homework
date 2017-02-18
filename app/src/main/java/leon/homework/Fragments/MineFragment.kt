package leon.homework.Fragments

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import leon.homework.AppContext
import leon.homework.R
import leon.homework.UI.MasterLayout
import leon.homework.Utils.Downloader
import org.jetbrains.anko.async
import org.jetbrains.anko.support.v4.toast






class MineFragment : BaseFragment(){
    override val layoutResourceId: Int = R.layout.fragment_mine
    private var mParam1: String? = null
    private var masterLayout :MasterLayout? = null
    val ERROR = -1
    //下载完毕的标记
    val downloadOver = 1
    //更新下载进度标记
    val UPDATE_PROGRESS = 0
    private val mhandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                downloadOver -> {
                    masterLayout!!.finalAnimation()
                    toast("下载完成")
                }
                UPDATE_PROGRESS ->{
                    masterLayout!!.cusview.setupprogress(msg.arg1)
                }
                ERROR ->{
                    masterLayout!!.reset()
                    toast("服务器异常，重试")
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
            Downloader().Download()
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
