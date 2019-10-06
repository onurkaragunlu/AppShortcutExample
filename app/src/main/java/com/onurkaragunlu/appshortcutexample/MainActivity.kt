package com.onurkaragunlu.appshortcutexample

import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.util.*

class MainActivity : AppCompatActivity() {
    var WRITE_ACTION = "com.onurkaragunlu.writing"
    var WRITE_EDIT = "com.onurkaragunlu.edit"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (WRITE_ACTION.equals(intent.action)) {
            //do something
            Log.d("SHORTCUTS", "SHORTCUT:WRITE")
        } else if (WRITE_EDIT.equals(intent.action)) {
            Log.d("SHORTCUTS", "SHORTCUT:EDIT")
        }
        createShortCuts()
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
