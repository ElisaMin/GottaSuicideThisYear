package lab.heizi.money.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lab.heizi.money.internals.Plans
import lab.heizi.money.ui.theme.MaicanTheme

@Preview(showBackground = true)
@Composable
fun PlansPv() {
    MaicanTheme {
        val state = rememberScrollState()
        Plans(modifier = Modifier.verticalScroll(state),clicked = {})
    }
}
@Composable
fun Plans (
    modifier: Modifier = Modifier,
    clicked:(Int) -> Unit,
){
    @Composable
    fun plant(id: Int,title: String,subtitle: String,price:String,labelOfPricing: String?=null,isHot: Boolean =false) {
        if (isHot) HotPlanItem(Modifier,id, title, subtitle, price,labelOfPricing,clicked)
        else PlanItem(Modifier,id, title, subtitle, price,labelOfPricing,clicked)
    }
    Column(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        PlanTitle(title = "热卖推荐")
        plant(id = Plans.free, title = "超大杯", subtitle = "在点击后会跳转到魔镜页面，它会告诉你世界上最惨的人是谁，我们拭目以待的吧。", price = "免费", isHot = true)
        Spacer(modifier = Modifier.padding(top = 8.dp))
        PlanTitle(title = "基础套餐")
        plant(id = Plans.big, title = "大杯", subtitle = "购买完成后会获得一个Github链接，里面写有一点无聊的事情，引以为戒吧。", price = "5.0元", isHot = false)
        plant(id = Plans.small, title = "中杯", subtitle = "中杯的惨都买不起，那你是真的惨，我比你更惨，至少我变成了网络乞丐。", price = "1.0元", isHot = false)
        Spacer(modifier = Modifier.padding(top = 8.dp))
        PlanTitle(title = "豪华版", subtitle = "豪华版的惨的话，镜子是收费的呢。")
        plant(id = Plans.ultraSingle, title = "单次租赁", subtitle = "获得单次镜子使用权", price = "2.0元", isHot = false)
        plant(id = Plans.ultraKeepMonths, title = "连续包月", subtitle = "一个月内连可乐都要少喝两瓶，惨。", price = "7.9元", labelOfPricing = "原价10元 立2元", isHot = false)
        plant(id = Plans.ultraMonth, title = "包月", subtitle = "获得一个月的镜子使用权。", price = "10元", isHot = false)
        plant(id = Plans.ultraYear, title = "包年", subtitle = "获得一整年的镜子使用权。", price = "99.9元", labelOfPricing = "原价120 立省24.9", isHot = false)
        plant(id = Plans.ultraQ, title = "包季", subtitle = "获得三个月的镜子使用权。", price = "24.9", labelOfPricing = "原价30 立省5元", isHot = false)
        plant(id = Plans.ultraOff, title = "买断", subtitle = "镜子完全使用权和流浪汉资助勋章。", price = "250", isHot = false)

    }
}

@Preview(showBackground = true)
@Composable
fun PlanPreview() = MaicanTheme {

    Spacer(modifier = Modifier.padding(8.dp))
    Column(modifier = Modifier.padding(8.dp)) {
        PlanTitle(title = "Hot Plan")
        Spacer(modifier = Modifier.padding(8.dp))
        HotPlanItem(Modifier, labelOfPricing = "15% Off")
        Spacer(modifier = Modifier.padding(8.dp))
        PlanTitle(title = "other","other is better like more is less")
        Spacer(modifier = Modifier.padding(8.dp))
        PlanItem(Modifier, title = "Ashit Or Sth", subtitle = "ashit ot xsahqlwrjkgamkfga,mgbvnacmg,aDFLKTGKQRWT")
    }
}
@Composable
fun PlanTitle(
    title: String,
    subtitle: String?=null
) {

    Column {
        Text(text = title, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.padding(bottom = 4.dp))
        if (subtitle!=null) Text(text = subtitle, style = MaterialTheme.typography.titleSmall)

    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HotPlanItem(
    modifier: Modifier = Modifier,
    id:Int = 0,
    title:String = "title",
    subtitle:String = "a description",
    price:String = "15$",
    labelOfPricing: String?=null,
    onSelected:(Int)->Unit = {},
) {
    ElevatedCard(onClick = {onSelected(id)}, modifier = modifier) {
        PlanContent(title = title, subtitle = subtitle, price = price,labelOfPricing)
    }
}
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun PlanItem(
    modifier: Modifier = Modifier,
    id:Int = 0,
    title:String = "title",
    subtitle:String = "a description",
    price:String = "15$",
    labelOfPricing: String?=null,
    onSelected:(Int)->Unit = {},
) {
    Card(onClick = {onSelected(id)},modifier=modifier) {
        PlanContent(title = title, subtitle = subtitle, price = price, labelOfPricing)
    }
}
@Composable
private fun PlanContent(title: String,subtitle: String,price: String,labelOfPricing:String?) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
        Column(
            Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)) {
            Text(text = title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.padding(4.dp))
            Text(text = subtitle)
        }
        Box(
            Modifier
                .padding(16.dp)
                .wrapContentHeight()

        ) {
            Column(Modifier.align(Alignment.Center)) {
                Text(text = price, style = MaterialTheme.typography.displaySmall,modifier = Modifier.align(
                    Alignment.CenterHorizontally
                ))
                if (labelOfPricing!=null) Text(text = labelOfPricing, style = MaterialTheme.typography.labelSmall, modifier = Modifier.align(
                    Alignment.CenterHorizontally
                ))
            }
        }
    }
}