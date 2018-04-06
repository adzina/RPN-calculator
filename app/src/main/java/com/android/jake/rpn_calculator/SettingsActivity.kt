package com.android.jake.rpn_calculator

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_settings.*


class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val extras = intent.extras ?: return
        extras.getString("Parametr")
        button.setOnClickListener{
            finish()
        }

    }
    override fun finish(){
        val data = Intent()
        val text = editText.getText().toString()
        if(text=="")
            data.putExtra("returnString","0")
        else
            data.putExtra("returnString",text)
        setResult(Activity.RESULT_OK,data)

        super.finish()
    }
}
