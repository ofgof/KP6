package org.example.project

import App
import DriverFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var driverFactory = DriverFactory(this@MainActivity)
            App(driverFactory)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    var driverFactory = DriverFactory(LocalContext.current)
    App(driverFactory)
}