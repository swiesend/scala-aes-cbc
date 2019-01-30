package crypto.aes

import java.nio.charset.StandardCharsets

import scalatest.UnitSpec

import scala.util.Random

object Helper {
  def randomPrintableChars(length: Int): Array[Char] = Array.fill(length)(Random.nextPrintableChar)
  def randomPrintableString(length: Int): String = new String(randomPrintableChars(length))
}

class AESTest extends UnitSpec {

  "Usage Example" should "work" in {
    import crypto.aes.{AES, Salt}

    // We start with a strong password
    val password = "a strong passphrase is important for symmetric encryption"

    // Nonetheless we generate a salt on a per user base. This salt
    // should not be exposed (as it functions more like a pepper).
    val salt = Salt.next(42)

    // And of course you have something to encrypt
    val data = "Lorem ipsum dolor sit amet"

    // Now we have all we need
    val encrypted = AES.encrypt(data, password, salt)
    val decrypted = AES.decrypt(encrypted, password, salt)

    assert(data == decrypted)
  }

  "AES/CBC/NoPadding ASCII encryption" should "work" in {
    val password = "123"
    val salt = "123"
    val str = "Hello World"; println(str);

    val enc = AES.encrypt(str, password, salt, AES.InstanceNoPadding); println(enc);
    val dec = AES.decrypt(enc, password, salt, AES.InstanceNoPadding); println(dec);

    assert(str == dec)
  }

  "AES/CBC/NoPadding UTF-8 encryption" should "work" in {
    val password = "123"
    val salt = "123"
    val str = "Hælló World"; println(str);

    val enc = AES.encrypt(str, password, salt, AES.InstanceNoPadding); println(enc);
    val dec = AES.decrypt(enc, password, salt, AES.InstanceNoPadding); println(dec);

    assert(str == dec)
  }

  "AES/CBC/NoPadding random String encryption" should "work" in {
    val password = "123"
    val salt = "123"
    val size = Random.nextInt(20); println(size);
    val str = Helper.randomPrintableString(size); println(str);

    val enc = AES.encrypt(str, password, salt, AES.InstanceNoPadding); println(enc);
    val dec = AES.decrypt(enc, password, salt, AES.InstanceNoPadding); println(dec);

    assert(str == dec)
  }

  "AES/CBC/PKCS5Padding ASCII encryption" should "work" in {
    val password = "123"
    val salt = "123"
    val str = "Hello World"; println(str);

    val enc = AES.encrypt(str, password, salt, AES.InstancePKCS5Padding); println(enc);
    val dec = AES.decrypt(enc, password, salt, AES.InstancePKCS5Padding); println(dec);

    assert(str == dec)
  }

  "AES/CBC/PKCS5Padding UTF-8 encryption" should "work" in {
    val password = "123"
    val salt = "123"
    val str = "Hælló World"; println(str);

    val enc = AES.encrypt(str, password, salt, AES.InstancePKCS5Padding); println(enc);
    val dec = AES.decrypt(enc, password, salt, AES.InstancePKCS5Padding); println(dec);

    assert(str == dec)
  }

  "AES/CBC/PKCS5Padding random String encryption" should "work" in {
    val password = "123"
    val salt = "123"
    val size = Random.nextInt(20); println(size);
    val str = Helper.randomPrintableString(size); println(str);

    val enc = AES.encrypt(str, password, salt, AES.InstancePKCS5Padding); println(enc);
    val dec = AES.decrypt(enc, password, salt, AES.InstancePKCS5Padding); println(dec);

    assert(str == dec)
  }

  "padding ASCII" should "work" in {
    val str = "Hello World"; println(str);

    val padded = AES.padPKCS5(str.getBytes(StandardCharsets.UTF_8)); println(new String(padded));
    val unpadded = AES.unpadPKCS5(padded); println(unpadded);

    assert(str == new String(unpadded, StandardCharsets.UTF_8))
  }

  "padding UTF-8" should "work" in {
    val str = "Hælló World"; println(str);

    val padded = AES.padPKCS5(str.getBytes(StandardCharsets.UTF_8)); println(new String(padded));
    val unpadded = AES.unpadPKCS5(padded); println(unpadded);

    assert(str == new String(unpadded, StandardCharsets.UTF_8))
  }

  "padding random String" should "work" in {
    val size = Random.nextInt(20); println(size);
    val str = Helper.randomPrintableString(size); println(str);

    val padded = AES.padPKCS5(str.getBytes(StandardCharsets.UTF_8)); println(new String(padded));
    val unpadded = AES.unpadPKCS5(padded); println(unpadded);

    assert(str == new String(unpadded, StandardCharsets.UTF_8))
  }

}
