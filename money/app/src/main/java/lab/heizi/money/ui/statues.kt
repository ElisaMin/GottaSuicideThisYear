package lab.heizi.money.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable

enum class Statues {
    Nothings,
    Payed,
    PayFailed
}
@Composable
fun ShowStatues(
    uiStatues:Statues,
    onDismiss:()->Unit,
    context: Context
) { when(uiStatues) {
    Statues.Payed -> {
        Toast
            .makeText(context, "支付成功!", Toast.LENGTH_LONG)
            .show()
        onDismiss()
    }

    Statues.PayFailed -> {

        Toast
            .makeText(context, "支付失败!", Toast.LENGTH_LONG)
            .show()
        onDismiss()
//            Dialog(onDismissRequest = { uiStatues = Statues.Nothings }) {
//                Column(
//                    Modifier
//                        .padding(16.dp)) {
//                    Text(text = "支付失败", style = MaterialTheme.typography.titleMedium)
//                    Spacer(modifier = Modifier.padding(top = 16.dp))
//                    TextButton(onClick = {
//                        uiStatues = Statues.Nothings
//                    }, modifier = Modifier.align(Alignment.End)) {
//                        Text(text = "关闭")
//                    }
//                }
//            }
    }
    Statues.Nothings -> Unit
} }