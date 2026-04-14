package io.github.solcott.kmp.parcelize.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun App() {
  AppTheme {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
      MainScreen()
    }
  }
}

val sampleObjects =
  listOf(
    FavoriteObject("1", "Item A", "A nice object", 0xFFE91E63, 0xFFFF9800),
    FavoriteObject("2", "Item B", "Favorite Object", 0xFF00E5FF, 0xFF00B0FF),
    FavoriteObject("3", "Item C", "Another object", 0xFF8BC34A, 0xFFFFEB3B),
  )

@Composable
fun MainScreen() {
  // We use rememberSaveable to preserve state across rotation.
  // The UserData data class is @Parcelize annotated, so it can be saved/restored on Android
  // natively.
  var savedUserData by rememberSaveable { mutableStateOf<UserData?>(null) }

  val userData = savedUserData
  if (userData == null) {
    ComposeDataScreen(onSendData = { data -> savedUserData = data })
  } else {
    ViewDetailsScreen(userData = userData, onModifyData = { savedUserData = null })
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeDataScreen(onSendData: (UserData) -> Unit) {
  var name by rememberSaveable { mutableStateOf("") }
  var age by rememberSaveable { mutableStateOf("") }
  val category = rememberTextFieldState("Personal")
  var expanded by remember { mutableStateOf(false) }
  var favoriteObject by rememberSaveable { mutableStateOf<FavoriteObject?>(null) }

  Scaffold(
    topBar = {
      CenterAlignedTopAppBar(
        title = {
          Text(
            "Compose Data",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 28.sp,
            color = MaterialTheme.colorScheme.primary,
          )
        },
        colors =
          TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Unspecified,
            navigationIconContentColor = Color.Unspecified,
            titleContentColor = Color.Unspecified,
            actionIconContentColor = Color.Unspecified,
          ),
      )
    },
    floatingActionButton = {
      ExtendedFloatingActionButton(
        onClick = { onSendData(UserData(name, age, category.text.toString(), favoriteObject)) },
        icon = { Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send") },
        text = { Text("Send Data") },
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = Color.White,
      )
    },
    containerColor = MaterialTheme.colorScheme.background,
  ) { padding ->
    Column(
      modifier =
        Modifier.padding(padding).padding(horizontal = 24.dp).verticalScroll(rememberScrollState()),
      verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
      OutlinedTextField(
        value = name,
        onValueChange = { name = it },
        label = { Text("User Name") },
        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors =
          OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.primary,
          ),
      )

      OutlinedTextField(
        value = age,
        onValueChange = { age = it },
        label = { Text("Age") },
        leadingIcon = { Icon(Icons.Default.Info, contentDescription = null) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors =
          OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.primary,
          ),
      )

      // Category Dropdown
      ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
        OutlinedTextField(
          state = category,
          readOnly = true,
          label = { Text("Select Category") },
          trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
          modifier =
            Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true)
              .fillMaxWidth(),
          shape = RoundedCornerShape(24.dp),
          colors =
            OutlinedTextFieldDefaults.colors(
              focusedBorderColor = MaterialTheme.colorScheme.primary,
              unfocusedBorderColor = MaterialTheme.colorScheme.primary,
              focusedContainerColor = MaterialTheme.colorScheme.secondary,
              unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
            ),
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
          listOf("Personal", "Work", "Hobby").forEach { cat ->
            DropdownMenuItem(
              text = { Text(cat) },
              onClick = {
                category.setTextAndPlaceCursorAtEnd(cat)
                expanded = false
              },
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        items(sampleObjects) { obj ->
          val isSelected = favoriteObject?.id == obj.id
          val scale = if (isSelected) 1.1f else 1.0f
          Box(
            modifier =
              Modifier.size(120.dp * scale)
                .clip(RoundedCornerShape(24.dp))
                .background(
                  Brush.linearGradient(listOf(Color(obj.colorStartHex), Color(obj.colorEndHex)))
                )
                .clickable { favoriteObject = obj },
            contentAlignment = Alignment.Center,
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              if (isSelected) {
                Icon(
                  Icons.Default.Star,
                  contentDescription = null,
                  tint = Color.Black,
                  modifier = Modifier.size(32.dp),
                )
              }
              Text(
                obj.name,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) Color.Black else Color.White,
                fontSize = 20.sp,
              )
              Text(
                obj.description,
                color = if (isSelected) Color.Black else Color.White,
                fontSize = 12.sp,
              )
            }
          }
        }
      }
      Spacer(modifier = Modifier.height(80.dp))
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewDetailsScreen(userData: UserData, onModifyData: () -> Unit) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Column {
            Text("View Details", fontWeight = FontWeight.Bold)
            Text(userData.name, fontSize = 14.sp, color = Color.Gray)
          }
        },
        navigationIcon = {
          IconButton(onClick = onModifyData) {
            Box(
              modifier =
                Modifier.size(40.dp)
                  .clip(RoundedCornerShape(20.dp))
                  .background(MaterialTheme.colorScheme.primary),
              contentAlignment = Alignment.Center,
            ) {
              Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
              )
            }
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
      )
    },
    containerColor = MaterialTheme.colorScheme.background,
  ) { padding ->
    Column(
      modifier =
        Modifier.padding(padding).padding(horizontal = 24.dp).verticalScroll(rememberScrollState()),
      verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
      val fav = userData.favoriteObject ?: sampleObjects[1]
      Box(
        modifier =
          Modifier.fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(
              Brush.linearGradient(listOf(Color(fav.colorStartHex), Color(fav.colorEndHex)))
            ),
        contentAlignment = Alignment.Center,
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Icon(
            Icons.Default.Star,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(48.dp),
          )
          Text(fav.name, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 28.sp)
          Text("Selected ${fav.description}", color = Color.White, fontSize = 14.sp)
        }
      }

      InfoCard(Icons.Default.Person, "User Name:", userData.name)
      InfoCard(Icons.Default.Info, "Age:", userData.age)
      InfoCard(Icons.Default.Build, "Category:", userData.category)

      Spacer(modifier = Modifier.height(16.dp))

      Button(
        onClick = onModifyData,
        modifier = Modifier.fillMaxWidth().height(56.dp),
        shape = RoundedCornerShape(28.dp),
        colors =
          ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = Color.Black,
          ),
      ) {
        Icon(Icons.Default.Edit, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text("Modify Data", fontWeight = FontWeight.Bold)
      }
    }
  }
}

@Composable
fun InfoCard(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
  Card(
    shape = RoundedCornerShape(24.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = Modifier.fillMaxWidth(),
  ) {
    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
      Box(
        modifier =
          Modifier.size(48.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center,
      ) {
        Icon(icon, contentDescription = null, tint = Color.White)
      }
      Spacer(modifier = Modifier.width(16.dp))
      Column {
        Text(label, fontSize = 12.sp, color = Color.Gray)
        Text(value, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
      }
    }
  }
}
