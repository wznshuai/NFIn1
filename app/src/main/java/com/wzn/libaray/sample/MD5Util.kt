package com.wzn.libaray.sample

import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.experimental.and

object MD5Util {
    /**
     * 123456加密后是：123456:E10ADC3949BA59ABBE56E057F20F883E
     */

    /** * 16进制字符集  */
    private val HEX_DIGITS = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')

    /** * 指定算法为MD5的MessageDigest  */
    private var messageDigest: MessageDigest? = null

    /** * 初始化messageDigest的加密算法为MD5  */
    init {
        try {
            messageDigest = MessageDigest.getInstance("MD5")
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

    }

    /**
     * * 获取文件的MD5值

     * @param file
     * *            目标文件
     * *
     * *
     * @return MD5字符串
     */
    fun getFileMD5String(file: File): String {
        var ret = ""
        var `in`: FileInputStream? = null
        var ch: FileChannel? = null
        try {
            `in` = FileInputStream(file)
            ch = `in`.channel
            val byteBuffer = ch!!.map(FileChannel.MapMode.READ_ONLY, 0,
                    file.length())
            messageDigest!!.update(byteBuffer)
            ret = bytesToHex(messageDigest!!.digest())
        } catch (e: IOException) {
            e.printStackTrace()

        } finally {
            if (`in` != null) {
                try {
                    `in`.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
            if (ch != null) {
                try {
                    ch.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
        return ret.toLowerCase()
    }

    /**
     * * 获取文件的MD5值

     * @param fileName
     * *            目标文件的完整名称
     * *
     * *
     * @return MD5字符串
     */
    fun getFileMD5String(fileName: String): String {
        return getFileMD5String(File(fileName))
    }

    /**
     * * MD5加密字符串

     * @param str
     * *            目标字符串
     * *
     * *
     * @return MD5加密后的字符串
     */

    fun getMD5String(str: String): String {

        return getMD5String(str.toByteArray()).toLowerCase()
    }

    /**
     * * MD5加密以byte数组表示的字符串

     * @param bytes
     * *            目标byte数组
     * *
     * *
     * @return MD5加密后的字符串
     */

    fun getMD5String(bytes: ByteArray): String {
        messageDigest!!.update(bytes)
        return bytesToHex(messageDigest!!.digest())
    }

    /**
     * * 校验密码与其MD5是否一致

     * @param pwd
     * *            密码字符串
     * *
     * *
     * @param md5
     * *            基准MD5值
     * *
     * *
     * @return 检验结果
     */
    fun checkPassword(pwd: String, md5: String): Boolean {
        return getMD5String(pwd).equals(md5, ignoreCase = true)
    }

    /**
     * * 校验密码与其MD5是否一致

     * @param pwd
     * *            以字符数组表示的密码
     * *
     * *
     * @param md5
     * *            基准MD5值
     * *
     * *
     * @return 检验结果
     */
    fun checkPassword(pwd: CharArray, md5: String): Boolean {
        return checkPassword(String(pwd), md5)

    }

    /**
     * * 检验文件的MD5值

     * @param file
     * *            目标文件
     * *
     * *
     * @param md5
     * *            基准MD5值
     * *
     * *
     * @return 检验结果
     */
    fun checkFileMD5(file: File, md5: String): Boolean {
        return getFileMD5String(file).equals(md5, ignoreCase = true)

    }

    /**
     * * 检验文件的MD5值

     * @param fileName
     * *            目标文件的完整名称
     * *
     * *
     * @param md5
     * *            基准MD5值
     * *
     * *
     * @return 检验结果
     */
    fun checkFileMD5(fileName: String, md5: String): Boolean {
        return checkFileMD5(File(fileName), md5)

    }

    /**
     * * 将字节数组中指定区间的子数组转换成16进制字符串

     * @param bytes
     * *            目标字节数组
     * *
     * *
     * @param start
     * *            起始位置（包括该位置）
     * *
     * *
     * @param end
     * *            结束位置（不包括该位置）
     * *
     * *
     * @return 转换结果
     */
    @JvmOverloads fun bytesToHex(bytes: ByteArray, start: Int = 0, end: Int = bytes.size): String {
        val sb = StringBuilder()
        for (i in start..start + end - 1) {
            sb.append(byteToHex(bytes[i]))
        }
        return sb.toString()

    }

    /**
     * * 将单个字节码转换成16进制字符串

     * @param bt
     * *            目标字节
     * *
     * *
     * @return 转换结果
     */
    fun byteToHex(bt: Byte): String {
//        return HEX_DIGITS[(bt & 0xf0) >> 4] + "" + HEX_DIGITS[bt & 0xf];
        return HEX_DIGITS[(bt and 0xf0.toByte()).toInt() shr 4] + "" + HEX_DIGITS[(bt and 0xf.toByte()).toInt()]

    }

}
