package com.wx.dex.high.level.features_ui.main.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.mediumTopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wx.dex.high.level.features_ui.main.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            mainComposable(content = { paddingvalues ->
                contentComposable(viewModel, paddingvalues)
            })
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun mainComposable(bottomLayout: (@Composable () -> Unit) = { BottomLayout() }, content: @Composable (PaddingValues) -> Unit) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(modifier = Modifier
                .fillMaxWidth()
                .background(Color.Blue)
                .height(81.dp), colors = mediumTopAppBarColors(
                containerColor = Color.Blue,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ), title = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "Dex的高阶用法", fontSize = 18.sp, color = Color.White, style = TextStyle.Default)
                }
            })
        }, bottomBar = bottomLayout
    ) { innerPadding ->
        content(innerPadding)
    }
}

@Composable
fun BottomLayout() {
    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
    ) {
        Text(
            text = "我是底部栏", fontSize = 18.sp, color = Color.Red, style = TextStyle.Default, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun contentComposable(viewModel: MainViewModel, innerPadding: PaddingValues) {

    FlowColumn(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = {
                    viewModel.saveJson()
                }, modifier = Modifier
                    .weight(1.0f)
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(5.dp)
            ) {
                Text("模拟从网络存入json")
            }

            Button(
                onClick = {
                    viewModel.readJson()
                }, modifier = Modifier
                    .weight(1.0f)
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(5.dp)
            ) {
                Text("从存入json加载出来")
            }
        }


        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { viewModel.saveProtobuf() }, modifier = Modifier
                    .weight(1.0f)
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(5.dp)
            ) {
                Text("模拟从网络存入protobuf")
            }

            Button(
                onClick = { viewModel.readProtobuf() }, modifier = Modifier
                    .weight(1.0f)
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(5.dp)
            ) {
                Text("从存入的protobuf加载转入")
            }
        }


        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = {
                    viewModel.saveDexFile()
                }, modifier = Modifier
                    .weight(1.0f)
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(5.dp)
            ) {
                Text("模拟从网络下载dex文件")
            }

            Button(
                onClick = {
                    viewModel.loadDexFile()
                }, modifier = Modifier
                    .weight(1.0f)
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(5.dp)
            ) {
                Text("加载dex文件")
            }
        }

        Button(
            onClick = {
                viewModel.load()
            }, modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(5.dp)
        ) {
            Text("从apk内的加载")
        }

        Button(
            onClick = {
                viewModel.deletedexPlugin()
            }, modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(5.dp)
        ) {
            Text("删除下载的从包内加载")
        }

        Button(
            onClick = {
                viewModel.strategyMode()
            }, modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(5.dp)
        ) {
            Text("策略模式加载包内还是插件dex")
        }
    }
}