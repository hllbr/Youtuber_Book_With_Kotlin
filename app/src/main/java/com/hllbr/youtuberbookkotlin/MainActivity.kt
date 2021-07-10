package com.hllbr.youtuberbookkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var youtuberNameList = ArrayList<String>()
        var youtuberIdList = ArrayList<Int>()

        var arrayAdapter = ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,youtuberNameList)
        listView.adapter = arrayAdapter

        try{
            var database = this.openOrCreateDatabase("Youtuber", MODE_PRIVATE,null)

            var cursor = database.rawQuery("SELECT * FROM youtuber",null)

            var ytbrIx = cursor.getColumnIndex("name")
            var idIx = cursor.getColumnIndex("id")

            while (cursor.moveToNext()){
                youtuberNameList.add(cursor.getString(ytbrIx))
                youtuberIdList.add(cursor.getInt(idIx))

            }
            arrayAdapter.notifyDataSetChanged()

            cursor.close()
        }catch (e : Exception){
            println("MainActivity Problem : "+e.printStackTrace().toString())
        }
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val intent = Intent(this,MainActivity2::class.java)
            intent.putExtra("info","old")
            intent.putExtra("id",youtuberIdList[position])
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //Inflater
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.add_youtuber,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.add_youtuber_item){
            val intent = Intent(this,MainActivity2::class.java)
            intent.putExtra("info","new")
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}