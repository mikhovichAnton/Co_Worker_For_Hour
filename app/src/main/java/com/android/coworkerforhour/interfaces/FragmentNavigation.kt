package com.android.coworkerforhour.interfaces

import androidx.fragment.app.Fragment

interface FragmentNavigation {
    fun navigationFrag(fragment: Fragment, addToStack: Boolean)
}