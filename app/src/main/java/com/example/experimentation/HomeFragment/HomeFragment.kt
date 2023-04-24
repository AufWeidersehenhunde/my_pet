package com.example.experimentation.HomeFragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Paint.Align
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.animation.core.Animatable
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.compose.rememberImagePainter
import com.bumptech.glide.Glide
import com.example.experimentation.API.RepositoryRemoteItemLatest
import com.example.experimentation.HomeFragment.items.ItemWithHeader
import com.example.experimentation.HomeFragment.items.ItemWithHeaderSale
import com.example.experimentation.HomeFragment.items.SimpleItem
import com.example.experimentation.Items
import com.example.experimentation.NotificationService
import com.example.experimentation.R
import com.example.experimentation.databinding.FragmentHomeBinding
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.GenericItem
import com.mikepenz.fastadapter.adapters.ItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner).also { strategy ->
            setViewCompositionStrategy(strategy)
        }
        setContent {
            UiBinding(viewModel = viewModel)
        }
    }
}

@Composable
private fun UiBinding(viewModel: HomeViewModel) {
    val list11 = viewModel.items.collectAsState().value
    val list22 = viewModel.latestItems.collectAsState().value
    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppBar(viewModel)
            list11?.let { MyRecyclerView(items = it) }
            list22?.let { MyRecyclerView2(items = it) }
            MyButton(viewModel.ctx)
        }
    }
}


@Composable
private fun AppBar(viewModel: HomeViewModel) {
    TopAppBar(
        modifier = Modifier
            .height(76.dp)
            .fillMaxWidth(),
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier,
                    fontSize = 22.sp,
                    fontStyle = FontStyle.Normal,
                    text = "Мой",
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier,
                    fontSize = 22.sp,
                    fontStyle = FontStyle.Normal,
                    text = " питомец",
                    color = Color(0xFF6AAC40),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.weight(1f))
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 10.dp)
                        .clickable { viewModel.routeToProfile() },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painterResource(id = R.drawable.max),
                        contentDescription = "profile",
                        modifier = Modifier
                            .padding(start = 0.dp)
                            .size(30.dp)
                            .clip(CircleShape),
                        Alignment.TopCenter
                    )
                    Text(
                        modifier = Modifier,
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Normal,
                        text = "Вы ",
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }
            }
        },
        backgroundColor = Color.White,
        elevation = 0.dp
    )
}

@Composable
fun MyRecyclerView(items: List<Items>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp),
        horizontalArrangement = Arrangement.Start,
    ) {
        items(items) { item ->
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = rememberImagePainter(item.image),
                    contentDescription = stringResource(R.string.latest),
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color(0xFF6AAC40), CircleShape)
                )
                item.name?.let {
                    Text(
                        text = it
                    )
                }
            }
            Spacer(modifier = Modifier.padding(5.dp))
        }
    }
}
@Composable
fun AnimatedBorder(
    color1: Color,
    color2: Color,
    borderWidth: Dp,
    content: @Composable () -> Unit
) {
    var color by remember { mutableStateOf(color1) }

    LaunchedEffect(Unit) {
        while (true) {
            color = color2
            delay(500)
            color = color1
            delay(500)
        }
    }

    Box(
        modifier = Modifier
            .border(
                width = borderWidth,
                color = color,
                shape = RoundedCornerShape(8.dp)
            )
            .fillMaxSize()
    ) {
        content()
    }
}
@Composable
fun MyRecyclerView2(items: List<RepositoryRemoteItemLatest>) {
    LazyColumn(
        modifier = Modifier.padding(5.dp),
        horizontalAlignment = Alignment.Start
    ) {
        items(items) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
            ) {
                    Image(
                        painter = rememberImagePainter(item.image_url),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(top = 7.dp)
                            .clip(CircleShape)
                            .size(60.dp)
                    )
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        item.name?.let { Text(text = it, modifier = Modifier.padding(8.dp)) }
                        Divider(modifier = Modifier.padding(start = 10.dp, end = 10.dp))
                        item.category?.let { Text(text = it, modifier = Modifier.padding(8.dp)) }
                    }
            }
        }
    }
}
@Composable
fun MyButton(context: Context) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Button(
            onClick = {
                Toast.makeText(
                    context,
                    "This is not required",
                    Toast.LENGTH_SHORT
                ).show()
            },
            modifier = Modifier
                .padding(end = 12.dp, bottom = 12.dp)
                .width(64.dp)
                .height(64.dp)
                .clip(CircleShape),
            shape = MaterialTheme.shapes.medium,
            contentPadding = PaddingValues(8.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF6AAC40)
            ),
            content = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        modifier = Modifier.size(32.dp),
                        tint = Color.White
                    )
                }
            }
        )
    }
}