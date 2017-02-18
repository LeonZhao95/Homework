package leon.homework.Extensions

import leon.homework.Activities.BaseActivity

fun BaseActivity.loading(msg: Int) = handler.sendEmptyMessage(msg)


