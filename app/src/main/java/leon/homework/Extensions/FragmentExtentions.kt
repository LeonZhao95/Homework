package com.vslimit.kotlindemo.extensions

import leon.homework.Activities.BaseActivity
import leon.homework.Extensions.loading
import leon.homework.Fragments.BaseFragment
import org.jetbrains.anko.support.v4.act
/**
 * Created by vslimit on 16/12/24.
 */

fun BaseFragment.loading(msg: Int) = (act as BaseActivity).loading(msg)
