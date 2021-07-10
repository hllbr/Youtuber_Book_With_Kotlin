package com.hllbr.youtuberbookkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val intent = intent
        val info = intent.getStringExtra("info")
        if (info.equals("new")){
            youtuberNameText.setText("")
            videoDaysText.setText("")
            button.visibility = View.VISIBLE
        }else{
            button.visibility = View.INVISIBLE
            val selectedId = intent.getIntExtra("id",1)

            try {
            val database = this.openOrCreateDatabase("Youtuber", MODE_PRIVATE,null)
                val cursor = database.rawQuery("SELECT * FROM youtuber WHERE id = ?", arrayOf(selectedId.toString()))
                val ybrIx =  cursor.getColumnIndex("name")
                val vdIx = cursor.getColumnIndex("video")

            while (cursor.moveToNext()){
                youtuberNameText.setText(cursor.getString(ybrIx))
                videoDaysText.setText(cursor.getString(vdIx))
            }
                cursor.close()
            }catch (e : Exception){
                println("DetailActivity Failed : (Problem) " +e.printStackTrace().toString())
            }
        }
    }
    fun save(view : View){
        val youtuberName = youtuberNameText.text.toString()
        val videoDays = videoDaysText.text.toString()
        try{
            val database = this.openOrCreateDatabase("Youtuber", MODE_PRIVATE,null)
            database.execSQL("CREATE TABLE IF NOT EXISTS youtuber(id INTEGER PRIMARY KEY ,name VARCHAR,video VARCHAR)")
            val sqlString = "INSERT INTO youtuber(name,video) VALUES(?,?)"
            val statement = database.compileStatement(sqlString)
            statement.bindString(1,youtuberName)
            statement.bindString(2,videoDays)
            statement.execute()
        }catch (e : Exception){
            println("DetailActivity Failed = "+e.printStackTrace().toString())
        }
        val intent = Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}