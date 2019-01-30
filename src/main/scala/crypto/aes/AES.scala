package crypto.aes

import java.nio.charset.StandardCharsets
import java.security.{MessageDigest, SecureRandom}

import javax.crypto.Cipher
import javax.crypto.spec.{IvParameterSpec, SecretKeySpec}
import org.apache.commons.codec.binary.{Base64, Hex}

import scala.language.postfixOps
import scala.runtime.ScalaRunTime.stringOf

object Secure {

  def nextBytes(length: Int = 16): Array[Byte] = {
    val bytes = new Array[Byte](length)
    val random: SecureRandom = SecureRandom.getInstance("SHA1PRNG")
    random.nextBytes(bytes)
    bytes
  }

}

object Salt {

  def next(length: Int = 32): String = {
    new String(Secure.nextBytes(length))
  }

}

object AES {

  val InstancePKCS5Padding = "AES/CBC/PKCS5Padding"
  val InstanceNoPadding = "AES/CBC/NoPadding"

  def encrypt(decrypted: String, password: String, salt: String, instance: String = InstancePKCS5Padding): String = {
    val sha256 = MessageDigest.getInstance("SHA-256")
    sha256.update((salt + password).getBytes())
    val key = sha256.digest()
    val keyspec = new SecretKeySpec(key, getAlgorithm(instance))

    val iv = Secure.nextBytes(16)
    val ivspec = new IvParameterSpec(iv)
    val ivBase64 = Base64.encodeBase64(iv).filterNot("=".toSet)

    val cipher = Cipher.getInstance(instance)
    cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec)
    val utf8 = decrypted.getBytes(StandardCharsets.UTF_8)
    val MD5 = MessageDigest.getInstance("MD5")
    MD5.update(utf8)

    val padded = padPKCS5(utf8 ++ new String(Hex.encodeHex(MD5.digest())).getBytes(StandardCharsets.UTF_8))
    val encrypted = cipher.doFinal(padded)

    val encBase64 = Base64.encodeBase64(encrypted)
    new String(ivBase64 ++ encBase64, StandardCharsets.UTF_8)
  }

  def decrypt(encrypted: String, password: String, salt: String, instance: String = InstancePKCS5Padding): String = {
    val sha256 = MessageDigest.getInstance("SHA-256")
    sha256.update((salt + password).getBytes())
    val key = sha256.digest()
    val keyspec = new SecretKeySpec(key, getAlgorithm(instance));

    val iv = Base64.decodeBase64(encrypted.take(22) + "==");
    val ivspec = new IvParameterSpec(iv);

    val decoded = Base64.decodeBase64(encrypted.substring(22, encrypted.length()))
    val cipher = Cipher.getInstance(instance);
    cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
    val dec = cipher.doFinal(decoded)
    val decrypted = unpadPKCS5(dec)

    val message = decrypted.take(decrypted.length - 32)
    val md5 = decrypted.takeRight(32)

    val MD5 = MessageDigest.getInstance("MD5")
    MD5.update(message)
    val messageHex = new String(Hex.encodeHex(MD5.digest()))
    if (messageHex != new String(md5, StandardCharsets.UTF_8)) {
      throw new Exception("[error][" + this.getClass().getName() + "] " +
        "Message could not be decrypted correctly.\n" +
        "\tMessage: \"" + new String(message, StandardCharsets.UTF_8) + "\"\n" +
        "The provided hashes are not equal:\n" +
        "\tGenerated hash: " + stringOf(messageHex) + "\n" +
        "\tExpected  hash: " + stringOf(md5) + "\n"
      )
    }
    new String(message, StandardCharsets.UTF_8)
  }

  def padPKCS5(input: Array[Byte], size: Int = 16): Array[Byte] = {
    val padByte: Int = size - (input.length % size);
    return input ++ Array.fill[Byte](padByte)(padByte.toByte);
  }

  def unpadPKCS5(input: Array[Byte]): Array[Byte] = {
    val padByte = input.last
    val length = input.length
    if (padByte > length) throw new Exception("The input was shorter than the padding byte indicates");
    if (!input.takeRight(padByte).containsSlice(Array.fill[Byte](padByte)(padByte))) throw new Exception("Padding format is not as being expected")
    input.take(length - padByte)
  }

  private def getAlgorithm(s: String): String = """(.*?)\/""".r.findFirstMatchIn(s).get.group(1)
}
