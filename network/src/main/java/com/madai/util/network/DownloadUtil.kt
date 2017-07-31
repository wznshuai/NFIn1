package com.madai.util.network

import com.wzn.libaray.sample.MD5Util
import okhttp3.*
import rx.Observable
import rx.observables.SyncOnSubscribe
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.concurrent.TimeUnit

/**
 * Created by WindFantasy on 2016/12/3.
 */

class DownloadUtil private constructor(val builder: Builder) {
    class Builder {
        lateinit var fileUrl: String
        lateinit var file: File
        var md5: String = ""
        lateinit var callFacatory: Call.Factory
        var loopTime: Long = 300

        fun file(file: File): Builder {
            this.file = file
            return this
        }

        fun fileUrl(fileUrl: String): Builder {
            this.fileUrl = fileUrl
            return this
        }

        fun fileMd5(md5: String): Builder {
            this.md5 = md5
            return this
        }

        fun callFacatory(callFacatory: Call.Factory): Builder {
            this.callFacatory = callFacatory
            return this
        }

        fun loopTime(loopTime: Long): Builder {
            this.loopTime = loopTime
            return this

        }

        fun build(): DownloadUtil {
            if (null == callFacatory)
                callFacatory = OkHttpClient()
            return DownloadUtil(this)
        }
    }

    val downloadingList: MutableList<String> by lazy {
        mutableListOf<String>()
    }

    fun isDownloading(url: String): Boolean {
        return downloadingList.contains(url)
    }


    fun downLoadFile(): Observable<Int> {
        return Observable
                .create<Int>(SyncOnSubscribe
                        .createStateless {
                            if (builder.file.exists() && MD5Util.getFileMD5String(builder.file) == builder.md5) {
                                it.onNext(100)
                                it.onCompleted()
                                return@createStateless
                            }
                            builder.file.deleteOnExit()
                            val request = Request.Builder().url(builder.fileUrl).build()
                            val call = builder.callFacatory.newCall(request)
                            call.enqueue(object : Callback {

                                override fun onFailure(call: Call, e: IOException) {
                                    e.printStackTrace()
                                }

                                @Throws(IOException::class)
                                override fun onResponse(call: Call, response: Response) {
                                    val body = response.body()
                                    try {
                                        var inputStream: InputStream? = null
                                        var outputStream: FileOutputStream? = null

                                        val fileSize = body?.contentLength() ?: 0
                                        var fileSizeDownloaded: Long = 0

                                        try {
                                            val fileReader = ByteArray(4096)

                                            inputStream = body?.byteStream()
                                            outputStream = FileOutputStream(builder.file)

                                            while (true) {
                                                val read = inputStream?.read(fileReader) ?: -1
                                                if (read == -1) {
                                                    break
                                                }
                                                outputStream.write(fileReader, 0, read)
                                                fileSizeDownloaded += read.toLong()
                                                val progress = (fileSizeDownloaded * 1.0f / fileSize * 100).toInt()
                                                it.onNext(progress)
                                                if (progress == 100)
                                                    it.onCompleted()
                                            }
                                            outputStream.flush()
                                        } catch (e: IOException) {
                                            it.onError(e)
                                            it.onCompleted()
                                        } finally {
                                            outputStream?.close()
                                            inputStream?.close()
                                        }
                                    } catch (e: IOException) {
                                        it.onError(e)
                                        it.onCompleted()
                                    }

                                }
                            })
                        }).sample(builder.loopTime, TimeUnit.MILLISECONDS)
    }
}
