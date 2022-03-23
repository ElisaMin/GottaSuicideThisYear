
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.material.icons.twotone.Refresh
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import lab.heizi.money.ui.component.PlanTitle
import lab.heizi.money.internals.Plans
import lab.heizi.money.internals.Plans.free
import lab.heizi.money.ui.Statues
import lab.heizi.money.ui.component.Plans
import lab.heizi.money.ui.theme.MaicanTheme


@Preview
@Composable
fun AppbarPv(){
    Appbar()
}

@Composable
fun Appbar(
    isWaiting:Boolean = true,
    isGithubBtnShow:Boolean = false,
    onGithubBtnClicked:()->Unit = {},
    onRefreshBtnClicked:()->Unit = {},
) {
    Column {
        LargeTopAppBar(
            title ={ Text(text = "卖惨能赚钱吗？")},
            actions = {
                if (isGithubBtnShow) IconButton(onClick = onGithubBtnClicked) {
                    Icon(Icons.TwoTone.Star, contentDescription = "github",)
                }
                IconButton(onClick = onRefreshBtnClicked) {
                    Icon(Icons.TwoTone.Refresh, contentDescription = "refresh",)
                }
            }
        )
        if (isWaiting) LinearProgressIndicator(Modifier.fillMaxWidth())
    }
}

interface ScreenViewModel {
    var isWaiting : Boolean
    var isGithubBtnShow:Boolean
    var uiStatues: Statues
    fun shouldPay(plan:Int):Boolean {
        return plan != free
    }
    fun clicked(plan: Int,isWechat:Boolean){}
    fun onRefreshBtnClicked(){}
    fun onGithubBtnClicked(){}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen(
    viewModel: ScreenViewModel
) {


    Scaffold(
        content = { Inside(viewModel::clicked,viewModel::shouldPay) },
        topBar = { Appbar(viewModel.isWaiting,viewModel.isGithubBtnShow,viewModel::onGithubBtnClicked,viewModel::onRefreshBtnClicked)},
    )

}
@Preview
@Composable
private fun Screen() {

    MaicanTheme {
        Screen(remember { object : ScreenViewModel {
            override var uiStatues: Statues by mutableStateOf(Statues.Nothings)
            override var isGithubBtnShow: Boolean = true
            override var isWaiting: Boolean by mutableStateOf(true)
        }})
    }
}

@Composable
private fun Inside(
    clicked: (Int,isWechat:Boolean) -> Unit,
    shouldPay:(Int)->Boolean
) {

    var isWechat:Boolean? by remember {
        mutableStateOf(null)
    }
    var currentPlan by remember {
        mutableStateOf(-1)
    }
    if (currentPlan!=-1 && shouldPay(currentPlan)) {
        isWechat = false
        Popup(Alignment.BottomCenter, onDismissRequest = {
            isWechat = null
            currentPlan = -1
        } ) {
            Surface(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f), modifier = Modifier
                .fillMaxSize()
                .clickable { currentPlan = -1 }) {
                Box {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .background(
                                MaterialTheme.colorScheme.background,
                                RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp)
                            )
                            .padding(16.dp)
                        ,
                        verticalArrangement = Arrangement.spacedBy(8.dp)

                    ) {
                        PlanTitle(title = "付款")
                        if (isWechat==true) {
//                            Button(
//                                content = { Text(text = "微信支付") },
//                                onClick = { isWechat = true },
//                                modifier = Modifier.fillMaxWidth()
//                            )
                            FilledTonalButton(
                                content = { Text(text = "支付宝") },
                                onClick = { isWechat = false },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        if (isWechat==false) {
//                            FilledTonalButton(
//                                content = { Text(text = "微信支付") },
//                                onClick = { isWechat = true },
//                                modifier = Modifier.fillMaxWidth()
//                            )
                            Button(
                                content = { Text(text = "支付宝") },
                                onClick = { isWechat = false },
                                modifier = Modifier.fillMaxWidth()
                            )

                        }

                        ElevatedButton(
                            content = { Text(text = "下一步") },
                            onClick = {
                                clicked(currentPlan,isWechat!!)
                                currentPlan = -1
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }


    val scroll = rememberScrollState()

    Column(modifier = Modifier
        .padding(8.dp)
        .wrapContentSize()
        .verticalScroll(scroll)
    ) {

        Text(text = "如果知识是付费的、演出是付费的、一首歌是能大卖、快手带货头部主播日进10w+、艺人被罚1e的话，我想要一点钱，就一点点。他妈的就一份无聊的论文要我三十块钱，字都没写几个，全是水份，怎么，他妈的，不应该是，免费的呢？", modifier = Modifier.padding(8.dp))
        Text(text = "你要买惨吗？5块钱一份大杯的惨，1块钱一份中杯的惨，超大杯的惨是免费的，你只需要照照镜子就能买到超大杯的惨，你知道什么叫豪华版的惨吗？我们这里卖镜子，10块钱一个月，25.9包季，99.9包年。买断250块钱，单次租赁2块钱。", modifier = Modifier.padding(8.dp))
        Plans(clicked = {
            if (!shouldPay(it)) clicked(it,false)
            else currentPlan = it
        })

    }
}
