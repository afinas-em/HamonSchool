package com.test.hamonschool.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.hamonschool.studentList.StudentListAdapter

//@BindingAdapter("app:firebaseImageUrl")
//fun ImageView.setImageUrl(url: String) {
//
//    if (!TextUtils.isEmpty(url))
//        GlideApp.with(this)
//            .load(FirebaseStorage.getInstance().getReference(url))
//            .placeholder(ColorDrawable(Color.parseColor("#F2FAFC")))
//            .centerCrop()
//            .into(this)
//    else
//        setImageDrawable(ColorDrawable(Color.parseColor("#F2FAFC")))
//}

@BindingAdapter("app:student_adapter")
fun RecyclerView.setStudentsAdapter(adapter: StudentListAdapter?) {
    if(adapter!=null) {
        this.adapter = adapter
        layoutManager = LinearLayoutManager(context)
    }
}
