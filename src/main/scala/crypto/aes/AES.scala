package crypto.aes

import scala.language.postfixOps
import scala.runtime.ScalaRunTime.stringOf

import org.apache.commons.codec.binary.Base64

import com.roundeights.hasher.Implicits.byteArrayToHasher
import com.roundeights.hasher.Implicits.stringToHasher

import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AES {

  val InstancePKCS5Padding = "AES/CBC/PKCS5Padding"
  val InstanceNoPadding = "AES/CBC/NoPadding"

  def encrypt(decrypted: String, password: String, salt: String, instance: String): String = {
    val key = (salt + password).sha256.bytes
    val keyspec = new SecretKeySpec(key, "AES");

    val iv = new Array[Byte](16);
    val ivspec = new IvParameterSpec(iv);
    val ivBase64 = Base64.encodeBase64(iv).filterNot("=".toSet)

    val cipher = Cipher.getInstance(instance);
    cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
    val utf8 = decrypted.getBytes("UTF-8")
    val encrypted = cipher.doFinal(pkcs5Pad(utf8 ++ utf8.md5.hex.getBytes("UTF-8")));

    val encBase64 = Base64.encodeBase64(encrypted);
    new String(ivBase64 ++ encBase64, "UTF-8")
  }

  def decrypt(encrypted: String, password: String, salt: String, instance: String): String = {
    val key = (salt + password).sha256.bytes
    val keyspec = new SecretKeySpec(key, "AES");

    val iv = Base64.decodeBase64(encrypted.take(22) + "==");
    val ivspec = new IvParameterSpec(iv);
    
    val decoded = Base64.decodeBase64(encrypted.substring(22, encrypted.length()))
    val cipher = Cipher.getInstance(instance);
    cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);    
    val dec = cipher.doFinal(decoded)
    val decrypted = pkcs5Unpad(dec)

    val message = decrypted.take(decrypted.length - 32)
    val md5 = decrypted.takeRight(32)

    if (message.md5.hex != new String(md5, "UTF-8")) {
      throw new Exception("[error][" + this.getClass().getName() + "] " +
        "Message could not be decrypted correctly.\n" +
        "\tMessage: \"" + new String(message, "UTF-8") + "\"\n" +
        "Hashes are not equal.\n" +
        "\tGenerated hash: " + stringOf(message.md5.hex) + "\n" +
        "\tExpected hash:  " + stringOf(md5) + "\n" +
        "\tGenerated HEX:  " + stringOf(message.md5.bytes.map(_.toHexString)) + "\"\n" +
        "\tExpected HEX:   " + stringOf(md5.map(_.toHexString)) + "\"\n");
    }
    new String(message, "UTF-8")
  }

  def pkcs5Pad(input: Array[Byte], size: Int = 16): Array[Byte] = {
    val padByte: Int = size - (input.length % size);
    return input ++ Array.fill[Byte](padByte)(padByte.toByte);
  }

  def pkcs5Unpad(input: Array[Byte]): Array[Byte] = {
    val padByte = input.last
    val length = input.length
    if (padByte > length) throw new Exception("The input was shorter than the padding byte indicates");
    if (!input.takeRight(padByte).containsSlice(Array.fill[Byte](padByte)(padByte))) throw new Exception("Padding format is not as being expected")
    input.take(length - padByte)
  }
}

