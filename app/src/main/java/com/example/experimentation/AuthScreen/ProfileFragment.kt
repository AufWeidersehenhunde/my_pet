package com.example.experimentation.AuthScreen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ShapeDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.compose.rememberImagePainter
import com.bumptech.glide.Glide
import com.example.experimentation.API.RepositoryRemoteItemLatest
import com.example.experimentation.API.RepositoryRemoteItemSale
import com.example.experimentation.Items
import com.example.experimentation.R
import com.example.experimentation.databinding.FragmentProfileBinding
import com.google.android.material.shape.Shapeable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment() : Fragment() {
    private val viewModel: ProfileViewModel by viewModels()

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
      private fun UiBinding(viewModel: ProfileViewModel){
          val list = viewModel.latestItems.collectAsState().value
                MaterialTheme {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AppBar(viewModel)
                        CardView()
                        list?.let { MyRecyclerView(items = it) }
                        MyButton(viewModel.ctn)
                    }
                }
            }



    @Composable
    private fun AppBar(viewModel: ProfileViewModel) {
        TopAppBar(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth(),

            title = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .padding(end = 20.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 22.sp,
                        fontStyle = FontStyle.Normal,
                        text = "Мой профиль",
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            },
            backgroundColor = Color(0xFF6AAC40),
            navigationIcon = {
                IconButton(onClick = { viewModel.routeToHome() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = { viewModel.routeToEdit()}, Modifier.padding(end = 0.dp)) {
                    Icon(painter = painterResource(id = R.drawable.edit), contentDescription = null)
                }
            },
            elevation = 0.dp
        )
    }

    @Composable
    private fun CardView() {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = 20.dp,
            backgroundColor = Color.LightGray
        ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        Arrangement.Center,
                        Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.max),
                            contentDescription = "Image",
                            modifier = Modifier
                                .padding(5.dp)
                                .size(90.dp)
                                .clip(CircleShape)
                        )
                        Icon(painter = painterResource(id = R.drawable.ic_search), contentDescription = null, Modifier.clickable { } )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Имя",
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
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

    @Composable
    fun MyRecyclerView(items: List<RepositoryRemoteItemLatest>) {
        LazyColumn(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            items(items) { item ->
                Row(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = rememberImagePainter(item.image_url),
                        contentDescription = stringResource(R.string.latest),
                        modifier = Modifier
                            .size(70.dp)
                    )
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        item.name?.let { Text(text = it, modifier = Modifier.padding(8.dp)) }
                        item.category?.let { Text(text = it, modifier = Modifier.padding(8.dp)) }
                    }
                }
                Divider()
            }
        }
    }





