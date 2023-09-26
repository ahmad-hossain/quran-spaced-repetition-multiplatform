package com.github.ahmad_hossain.quranhifzrevision.android.feature_pages.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.stopScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Today
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.github.ahmad_hossain.quranhifzrevision.SharedRes
import com.github.ahmad_hossain.quranhifzrevision.android.App.Companion.deps
import com.github.ahmad_hossain.quranhifzrevision.android.stringResource
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.presentation.PagesEvent
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.presentation.PagesViewModel
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.presentation.UiEvent
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.presentation.UiTabs
import com.github.ahmad_hossain.quranhifzrevision.feature_pages.util.now
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.datetime.LocalDate
import kotlin.math.roundToInt

private val FabHeight = 56.dp
private val ScaffoldFabSpacing = 16.dp
val BottomBarHeight = 80.dp

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)
@RootNavGraph(start = true)
@Destination
@Composable
fun PagesScreen(
    navigator: DestinationsNavigator,
    viewModel: PagesViewModel = PagesViewModel(deps.pageDataSource)
) {
    val state by viewModel.state.collectAsState()
    val lazyListState = rememberLazyListState()
    val isListScrolled by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex > 0
        }
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val bottomBarHeightPx = with(LocalDensity.current) { BottomBarHeight.roundToPx().toFloat() }
    val bottomBarOffsetHeightPx = remember { mutableStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val deltaY = available.y
                val newOffset = bottomBarOffsetHeightPx.value + deltaY
                bottomBarOffsetHeightPx.value = newOffset.coerceIn(-bottomBarHeightPx, 0f)

                return Offset.Zero
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect {
            when (it) {
                is UiEvent.ScrollToIndex -> lazyListState.scrollToItem(it.index)
                is UiEvent.ExpandTopAndBottomBars -> {
                    lazyListState.stopScroll()
                    scrollBehavior.nestedScrollConnection.onPreScroll(Offset.Infinite, NestedScrollSource.Drag)
                    nestedScrollConnection.onPreScroll(Offset.Infinite, NestedScrollSource.Drag)
                }
            }
        }
    }

    GradePageDialog(
        isVisible = state.isGradeDialogVisible,
        pageNumber = state.lastClickedPageNumber,
        onDismissRequest = { viewModel.onEvent(PagesEvent.GradeDialogDismissed) },
        onConfirm = { viewModel.onEvent(PagesEvent.GradeDialogConfirmed) },
        onDismiss = { viewModel.onEvent(PagesEvent.GradeDialogDismissed) },
        selectedGrade = state.selectedGrade,
        onSelectGrade = { viewModel.onEvent(PagesEvent.GradeSelected(it)) },
    )
    SearchDialog(
        isVisible = state.isSearchDialogVisible,
        searchQuery = state.searchQuery,
        searchQueryError = state.searchQueryError,
        onSearchQueryChanged = { viewModel.onEvent(PagesEvent.SearchQueryChanged(it)) },
        onSearchDialogConfirmed = { viewModel.onEvent(PagesEvent.SearchDialogConfirmed) },
        onSearchDialogDismissed = { viewModel.onEvent(PagesEvent.SearchDialogDismissed) },
    )

    Scaffold(
        modifier = Modifier
            .nestedScroll(nestedScrollConnection)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            val containerColor = if (isListScrolled) MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp) else MaterialTheme.colorScheme.surface
            CenterAlignedTopAppBar(
                title = { Text(stringResource(SharedRes.strings.revision)) },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = containerColor,
                    scrolledContainerColor = containerColor
                )
            )
        },
        bottomBar = {
            CustomBottomBar(
                modifier = Modifier.offset { IntOffset(x = 0, y = -bottomBarOffsetHeightPx.value.roundToInt()) },
                currentScreen = Screen.Home,
                onSettingsClicked = { TODO() },
            )
        },
        floatingActionButton = {
            if (state.displayedPages.isEmpty()) return@Scaffold
            FloatingActionButton(
                modifier = Modifier.offset { IntOffset(x = 0, y = -bottomBarOffsetHeightPx.value.roundToInt()) },
                onClick = { viewModel.onEvent(PagesEvent.SearchFabClicked) },
                content = { Icon(imageVector = Icons.Outlined.Search, contentDescription = null) }
            )
        },
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(
                    PaddingValues(
                        top = innerPadding.calculateTopPadding(),
                        start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                        end = innerPadding.calculateEndPadding(LayoutDirection.Ltr)
                    )
                )
        ) {
            TabsSection(
                containerColor = if (isListScrolled) MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp) else TabRowDefaults.containerColor,
                selectedTab = state.selectedTab,
                onTabClicked = { viewModel.onEvent(PagesEvent.TabClicked(it)) }
            )
            if (state.displayedPages.isEmpty())
                NoPagesDueMessage()
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = BottomBarHeight + FabHeight + ScaffoldFabSpacing * 2),
                state = lazyListState
            ) {
                stickyHeader {
                    if (state.displayedPages.isNotEmpty())
                        TableHeader()
                }
                items(state.displayedPages) { page ->
                    val shouldGradePage = page.dueDate == null || page.dueDate!! <= LocalDate.now().toEpochDays()
                    PageItem(
                        modifier =
                        if (shouldGradePage) Modifier.clickable { viewModel.onEvent(PagesEvent.PageClicked(page)) } else Modifier.alpha(0.38f),
                        page = page
                    )
                }
            }
        }
    }
}

@Composable
private fun NoPagesDueMessage() {
    BoxWithConstraints(Modifier.fillMaxSize()) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height((this@BoxWithConstraints.maxHeight * 0.3f - BottomBarHeight)))
            Icon(
                modifier = Modifier.size(136.dp),
                imageVector = Icons.Rounded.Done,
                contentDescription = null,
                tint = Color.Gray
            )
            Text(
                text = stringResource(SharedRes.strings.no_pages_due_for_review),
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

@Composable
private fun TabsSection(
    selectedTab: UiTabs,
    onTabClicked: (UiTabs) -> Unit,
    containerColor: Color,
) {
    TabRow(
        containerColor = containerColor,
        selectedTabIndex = selectedTab.ordinal,
    ) {
        Tab(
            selected = selectedTab == UiTabs.TODAY,
            onClick = { onTabClicked(UiTabs.TODAY) },
            text = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(imageVector = Icons.Outlined.Today, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text(text = stringResource(SharedRes.strings.today))
                }
            },
        )
        Tab(
            selected = selectedTab == UiTabs.ALL,
            onClick = { onTabClicked(UiTabs.ALL) },
            text = { Text(text = stringResource(SharedRes.strings.all)) },
        )
    }
}

@Composable
private fun TableHeader() {
    val bold = FontWeight.Bold
    Column {
        Row(Modifier.background(MaterialTheme.colorScheme.background)) {
            TableCell(fontWeight = bold, text = stringResource(SharedRes.strings.page_number_abbrev), weight = 1f)
            TableCell(fontWeight = bold, text = stringResource(SharedRes.strings.interval), weight = 1f)
            TableCell(fontWeight = bold, text = stringResource(SharedRes.strings.repetitions), weight = 1f)
            TableCell(fontWeight = bold, text = stringResource(SharedRes.strings.due_date), weight = 1f)
        }
        Divider(color = Color.Gray, thickness = 1.dp)
    }
}