package com.example.draganddraw

class DragAndDrawActivity : SingleFragmentActivity() {
    override fun createFragment() = DragAndDrawFragment.newInstance()
}
