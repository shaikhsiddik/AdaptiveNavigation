package com.example.adaptivenavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.window.core.layout.WindowWidthSizeClass
import com.example.adaptivenavigation.ui.theme.AdaptiveNavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AdaptiveNavigationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    var selectedItemIndex by rememberSaveable {
                        mutableIntStateOf(0)
                    }

                    val windowWidthSizeClass = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass

                    NavigationSuiteScaffold(navigationSuiteItems = {

                        Screen.entries.forEachIndexed { index, screen ->

                            item(selected = index == selectedItemIndex ,
                                onClick = {
                                    selectedItemIndex == index

                                },
                                icon = {
                                    Icon(imageVector = screen.icon, contentDescription = screen.title)
                                },
                                label = {

                                    Text(text = screen.title)

                                }
                            )

                        }
                    },
                        layoutType = if (windowWidthSizeClass == WindowWidthSizeClass.EXPANDED){
                           NavigationSuiteType.NavigationDrawer
                        }else{
                            NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(
                                currentWindowAdaptiveInfo()
                            )
                        }
                    ){

                        Box(modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center){

                            Screen.entries.forEachIndexed { index, screen ->

                                if (selectedItemIndex == index){
                                    Text(text = Screen.entries[index].title)
                                }

                            }

                            when(selectedItemIndex){

                                0 -> Text(text = "Home")
                                1 -> Text(text = "Search")
                                2 -> Text(text = "Settings")

                            }

                        }

                    }

                }
            }
        }
    }
}

enum class Screen(val title: String, val icon: ImageVector){
    HOME("Home", Icons.Default.Home),
    SEARCH("Search", Icons.Default.Search),
    SETTINGS("Settings", Icons.Default.Settings)
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AdaptiveNavigationTheme {



    }
}