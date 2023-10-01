package com.github.ahmad_hossain.quranhifzrevision.android

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.startActivity
import com.github.ahmad_hossain.quranhifzrevision.SharedRes
import com.github.ahmad_hossain.quranhifzrevision.Strings
import com.github.ahmad_hossain.quranhifzrevision.android.feature_pages.presentation.PermissionDialog
import com.github.ahmad_hossain.quranhifzrevision.android.ui.theme.QuranHifzRevisionTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dev.icerock.moko.resources.StringResource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO request notif. perms & create notif. channel
        // TODO schedule alarm

        val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
        setContent {
            QuranHifzRevisionTheme {
                Surface {
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
                        AlarmPermissionDialog(prefs)
                    }
                    DestinationsNavHost(navGraph = NavGraphs.root)
                }
            }
        }
    }
}

/** API check handled */
@SuppressLint("InlinedApi")
@Composable
private fun AlarmPermissionDialog(prefs: SharedPreferences) {
    val isAlarmDialogVisible = rememberSaveable {
        mutableStateOf(prefs.getBoolean("showAlarmDialog", true))
    }

    fun hideAndDisableAlarmDialog() {
        isAlarmDialogVisible.value = false
        prefs.edit().putBoolean("showAlarmDialog", false).apply()
    }

    val ctx = LocalContext.current
    if (isAlarmDialogVisible.value) {
        PermissionDialog(
            icon = painterResource(id = R.drawable.ic_alarm_filled),
            permission = stringResource(SharedRes.strings.schedule_alarms),
            text = stringResource(SharedRes.strings.app_name) + " " + stringResource(SharedRes.strings.alarm_permission_dialog_text),
            confirmButtonText = stringResource(SharedRes.strings.go_to_settings),
            dismissButtonText = stringResource(id = SharedRes.strings.cancel),
            onConfirmClicked = {
                startActivity(ctx, Intent(ACTION_REQUEST_SCHEDULE_EXACT_ALARM), null)
                hideAndDisableAlarmDialog()
            },
            onDismissed = ::hideAndDisableAlarmDialog
        )
    }
}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Composable
fun stringResource(id: StringResource, vararg args: Any): String {
    return Strings(LocalContext.current).get(id, args.toList())
}

@Preview
@Composable
fun DefaultPreview() {
    QuranHifzRevisionTheme {
        GreetingView("Hello, Android!")
    }
}
