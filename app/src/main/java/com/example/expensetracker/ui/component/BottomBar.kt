package com.example.expensetracker.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.expensetracker.ui.util.itemsNavigation

@Composable
fun BottomBar(mainNavController:NavHostController,navBackStackEntry: NavBackStackEntry?){
    NavigationBar(
        containerColor = Color.White
    ) {
        itemsNavigation.forEach{
            items ->
            val isSelected = items.title.lowercase() == navBackStackEntry?.destination?.route
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.White,
                    unselectedIconColor = Color(0xFFACB1D6),
                    unselectedTextColor = Color(0xFFACB1D6)
                ),
                selected = isSelected,
                label = { Text(text = items.title)},
                onClick = { mainNavController.navigate(items.title.lowercase()) },
                icon = {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = if (isSelected) items.selectedIcon else items.unSelectedIcon,
                        contentDescription = items.title
                    )
                }
            )
        }
    }
}
