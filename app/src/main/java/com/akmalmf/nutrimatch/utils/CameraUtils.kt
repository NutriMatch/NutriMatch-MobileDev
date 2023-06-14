package com.akmalmf.nutrimatch.utils

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Created by Akmal Muhamad Firdaus on 2023/06/09 14:24
 * akmalmf007@gmail.com
 */
val timeStamp: String = SimpleDateFormat(
    "dd-MMM",
    Locale.US
).format(System.currentTimeMillis())
fun createCustomTempFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}
fun convertToFile(context: Context, uri: Uri): File {
    val contentResolver: ContentResolver = context.contentResolver
    val file = createCustomTempFile(context)

    val inputStream = contentResolver.openInputStream(uri) as InputStream
    val outputStream: OutputStream = FileOutputStream(file)
    val buf = ByteArray(1024)
    var len: Int
    while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
    outputStream.close()
    inputStream.close()

    return file
}
fun makeTempFile(context: Context): File {
    val timeStamp = SimpleDateFormat("dd-MMM-yyyy", Locale.US).format(System.currentTimeMillis())
    return File.createTempFile(
        timeStamp, ".jpg",
        context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    )
}