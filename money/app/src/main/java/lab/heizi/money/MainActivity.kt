package lab.heizi.money

import Screen
import ScreenViewModel
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.content.edit
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import lab.heizi.money.internals.Pays
import lab.heizi.money.internals.Plans
import lab.heizi.money.internals.Plans.free
import lab.heizi.money.internals.Repo
import lab.heizi.money.ui.ShowStatues
import lab.heizi.money.ui.Statues
import lab.heizi.money.ui.theme.MaicanTheme

class MainActivity : ComponentActivity() {

    private val viewModel by lazy {
        object : ScreenViewModel {
            override var uiStatues: Statues by mutableStateOf(Statues.Nothings)
            override var isWaiting: Boolean by mutableStateOf(false)
            override var isGithubBtnShow: Boolean by mutableStateOf(config.getBoolean(key_big,false))
            override fun clicked(plan: Int, isWechat: Boolean) {
                onClicked(plan, isWechat)
            }

            override fun onRefreshBtnClicked() {
                lifecycleScope.launch(IO) {
                    isWaiting = true
                    refreshState()
                    isWaiting = false
                }
                super.onRefreshBtnClicked()
            }

            override fun onGithubBtnClicked() {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/ElisaMin/GottaSuicideThisYear#%E6%9D%80%E6%AD%BB%E9%82%A3%E4%BA%9B%E5%86%85%E5%90%91%E5%88%B0%E6%9E%81%E7%AB%AF%E7%9A%84%E4%BA%BA")))
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaicanTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val vmd = remember { viewModel }
                    Screen(viewModel)
                    ShowStatues(vmd.uiStatues,{vmd.uiStatues = Statues.Nothings},this)
                }
            }
        }
    }
    private fun onClicked(plan:Int,isWechat:Boolean) = with(Plans) {
        when (plan) {
            free -> callCamera()
            big, small -> payForNothing(plan == big,isWechat)
            else -> payForCamera(plan,isWechat)
        }
        Unit
    }
    private fun payForNothing(isBig:Boolean,isWechat: Boolean) {
        Log.d(TAG, "payForNothing: clicked by big is $isBig and $isWechat is wechat ")
        lifecycleScope.launch(IO) {
//            viewModel.isWaiting = true
            checkResult(Pays.byPrice(if (isBig) 5.0f else 1.0f,isWechat,this@MainActivity))
//            refreshState()
        }
    }
    private fun callCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).let {
            it.putExtra("android.intent.extras.CAMERA_FACING", 1)
            startActivity(it)
        }
    }
    private fun payForCamera(plan: Int,isWechat: Boolean) {
        lifecycleScope.launch {
            checkResult(Pays.byPrice(0.0f,isWechat,this@MainActivity))
        }
    }
    private fun checkResult(result:Boolean) {
        viewModel.uiStatues = if (result)
            Statues.Payed
        else
            Statues.PayFailed
    }

    private val config get() = getSharedPreferences("default", MODE_PRIVATE)
    /**
     * call from server
     */
    private suspend fun refreshState() {
        viewModel.isWaiting=true
        delay(1000)
        Repo.refresh().forEach {
            when(it) {
                Plans.big ->config.let { config ->
                    if (!config.getBoolean(key_big,false)) config.edit(commit = true) {
                        putBoolean(key_big,true)
                    }
                }
            }
        }
        viewModel.isWaiting = false
    }
    companion object {
         const val key_big = "brought-Big"
    }
}
val Any.TAG get() = this::class.simpleName?:"Any???"
