package com.orlandev.testimagekitio

import android.R.attr.bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.imagekit.android.ImageKit
import com.imagekit.android.ImageKitCallback
import com.imagekit.android.entity.TransformationPosition
import com.imagekit.android.entity.UploadError
import com.imagekit.android.entity.UploadResponse
import com.orlandev.testimagekitio.ui.theme.TestImageKitIOTheme


class MainActivity : ComponentActivity(),ImageKitCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        ImageKit.init(
            context = applicationContext,
            publicKey = "",
            urlEndpoint = "",
            transformationPosition = TransformationPosition.PATH,
            authenticationEndpoint = ""
        )

        val d = resources.getDrawable(com.orlandev.testimagekitio.R.drawable.test_image)
        val currentState = d.current
        if (currentState is BitmapDrawable) {
            val bitmap = (currentState as BitmapDrawable).bitmap

            ImageKit.getInstance().uploader().upload(
                file = bitmap!!,
                fileName = "image.jpg",
                useUniqueFilename = false,
                tags = arrayOf("nice", "copy", "books"),
                folder = "/dummy/folder/",
                imageKitCallback = this
            )

        }else{
            Log.e("ImageKit","currentState is not BitmapDrawable")
        }
        setContent {
            TestImageKitIOTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }

    override fun onError(uploadError: UploadError) {
        Log.e("ImageKit", "Error: ${uploadError.message}")
    }

    override fun onSuccess(uploadResponse: UploadResponse) {
        Log.d("ImageKit", "Url: ${uploadResponse.url}")
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TestImageKitIOTheme {
        Greeting("Android")
    }
}