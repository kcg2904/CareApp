package com.KingsStory.Application

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DataPutExtra {

    //하나의 editText의 text값을 전달하는 함수
    //데이터를 전송하는 activity와 데이터를 받으려는 activity를 받고 버튼을 받아서 그 버튼을 클릭시 editText의 값을 key라는 태그를 달고 보냄
    fun EditTextputExtra(
        start: Activity,
        destination: Activity,
        button: View,
        editText: EditText,
        key: String
    ) {
        val intent = Intent(start, destination::class.java)

        button.setOnClickListener {
            DataReset(start, key)
            var dialog = ProgressDialog.show(start, "", "지도를 불러오는 중 입니다...", true)
            if (editText.text != null && editText.text.toString() != "") {

//                if (DataGetExtra().StringExtra(start, key) == null || DataGetExtra().StringExtra(start, key) == "null") {
                    intent.putExtra(key, "${editText.text}")
                    destination.startActivity(intent)
                    DataReset(start,key)
                    start.finish()
                    dialog.dismiss()
//                }
            } else {
                dialog.dismiss()
                Toast.makeText(start, "잘못된 검색어 입니다", Toast.LENGTH_SHORT).show()

            }
        }
    }

    fun DataReset(activity: Activity, key: String) {
        val shar = activity.getSharedPreferences(key, MODE_PRIVATE)
        val editor = shar.edit()
        editor.putString(key, null)
        editor.commit()

    }
}