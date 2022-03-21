package lab.heizi.money

import Screen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import lab.heizi.money.internals.Plans.free
import lab.heizi.money.internals.androidId
import lab.heizi.money.ui.theme.MaicanTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaicanTheme {
                var androidId by remember {
                    mutableStateOf(androidId)
                }
                if (androidId.isNotEmpty()) {
                    Snackbar(dismissAction = {androidId = ""}, content = { Text(text = androidId)})
                }
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Screen(shouldPay = { it != free },clicked = {_,_->

                    })
                }
            }
        }

//        WindowCompat.setDecorFitsSystemWindows(window,true)
//        WindowCompat.getInsetsController(window,window.decorView)?.isAppearanceLightStatusBars =
//        (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
    }
//    fun

//    override fun onStart() {
//        if ((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) != Configuration.UI_MODE_NIGHT_YES)
//            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//         else
//         window.decorView.systemUiVisibility = 0
//        super.onStart()
//    }

}
