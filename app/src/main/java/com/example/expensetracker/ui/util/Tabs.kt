package com.example.expensetracker.ui.util

data class TabsItem(
    val title:String
)

val TabsList = listOf<TabsItem>(
    TabsItem("Oggi"),
    TabsItem("Questo Mese"),
    TabsItem("Quest'anno")
)

val TabsMap = mapOf<Int,String>(
    0 to "Oggi",
    1 to "Questo Mese",
    2 to "Quest'anno"
)

val TabSummaryList = listOf<TabsItem>(
    TabsItem("Mensile"),
    TabsItem("Annuale")
)