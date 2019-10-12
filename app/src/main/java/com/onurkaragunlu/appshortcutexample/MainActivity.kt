package com.onurkaragunlu.appshortcutexample

import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    var WRITE_ACTION = "com.onurkaragunlu.writing"
    var WRITE_EDIT = "com.onurkaragunlu.edit"
    var DELETE = "com.onurkaragunlu.delete"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (WRITE_ACTION.equals(intent.action)) {
            //do something
            Log.d("SHORTCUTS", "SHORTCUT:WRITE")
        } else if (WRITE_EDIT.equals(intent.action)) {
            Log.d("SHORTCUTS", "SHORTCUT:EDIT")
        } else if (DELETE.equals(intent.action)) {
            Log.d("SHORTCUTS", "SHORTCUT:DELETE")
        }
        createShortCuts()

        button.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val shortcutManager = getSystemService(ShortcutManager::class.java)
               var pinnedShortcutList = shortcutManager.pinnedShortcuts
                for (pinnedShortcut in pinnedShortcutList){
                    if(pinnedShortcut.id.equals("delete")){
                        Toast.makeText(applicationContext,"Daha Önce Eklendi.",Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }
                }

                var shortcut = ShortcutInfo.Builder(this, "delete").apply {
                    setShortLabel(applicationContext.getString(R.string.shortcut_shortname3))
                    setLongLabel(applicationContext.getString(R.string.shortcut_longname3))
                    setIcon(Icon.createWithResource(applicationContext, R.drawable.shortcut_delete))
                    setIntent(Intent(applicationContext, MainActivity::class.java).apply {
                        action = DELETE
                    })
                }.build()
                shortcutManager.requestPinShortcut(shortcut, null);

            }

        }
    }

    fun createShortCuts() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            val shortcutManager = getSystemService(ShortcutManager::class.java)
            if (shortcutManager.dynamicShortcuts.size <= 0) {
                var shortcut = ShortcutInfo.Builder(this, "edit").apply {
                    setShortLabel(applicationContext.getString(R.string.shortcut_shortname2))
                    setLongLabel(applicationContext.getString(R.string.shortcut_longname2))
                    setIcon(Icon.createWithResource(applicationContext, R.drawable.shortcut_edit))
                    setIntent(Intent(applicationContext, MainActivity::class.java).apply {
                        action = WRITE_EDIT
                    })
                }.build()
                shortcutManager.setDynamicShortcuts(Arrays.asList(shortcut));
            }
        }
    }

}
