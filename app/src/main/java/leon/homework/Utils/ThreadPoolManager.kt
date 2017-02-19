package leon.homework.Utils

import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * Created by BC on 2017/2/18 0018.
 */

internal class ThreadPoolManager{

    private val executor: ThreadPoolExecutor

    init {
        /**
         * 给corePoolSize赋值：当前设备可用处理器核心数*2 + 1,能够让cpu的效率得到最大程度执行（有研究论证的）
         */
        val corePoolSize = Runtime.getRuntime().availableProcessors() * 2 + 1
        val maximumPoolSize = corePoolSize
        val keepAliveTime: Long = 1
        val unit = TimeUnit.HOURS
        executor = ThreadPoolExecutor(
                corePoolSize, //当某个核心任务执行完毕，会依次从缓冲队列中取出等待任务
                maximumPoolSize, //5,先corePoolSize,然后new LinkedBlockingQueue<Runnable>(),然后maximumPoolSize,但是它的数量是包含了corePoolSize的
                keepAliveTime, //表示的是maximumPoolSize当中等待任务的存活时间
                unit,
                LinkedBlockingQueue<Runnable>(), //缓冲队列，用于存放等待任务，Linked的先进先出
                Executors.defaultThreadFactory(), //创建线程的工厂
                ThreadPoolExecutor.AbortPolicy()//用来对超出maximumPoolSize的任务的处理策略
        )
    }

    /**
     * 执行任务
     */
    fun execute(runnable: Runnable?) {
        if (runnable == null) return

        executor.execute(runnable)
    }

    /**
     * 从线程池中移除任务
     */
    fun remove(runnable: Runnable?) {
        if (runnable == null) return
        executor.remove(runnable)
    }

    companion object {
        /**
         * 单例设计模式（饿汉式）
         * 单例首先私有化构造方法，然后饿汉式一开始就开始创建，并提供get方法
         */
        val instance = ThreadPoolManager()
    }

}
