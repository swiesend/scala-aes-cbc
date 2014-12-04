package crypto.aes

import scalatest.UnitSpec
import scala.util.Random

class AESTest extends UnitSpec {

  def randomStringArray(length: Int) = {
    val a: Array[Char] = new Array[Char](length)
    for (i <- 0 until length) {
      a(i) = Random.nextPrintableChar
    }
    new String(a)
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
    val str = "Hælló World"; print(str);

    val enc = AES.encrypt(str, password, salt, AES.InstanceNoPadding); println(enc);
    val dec = AES.decrypt(enc, password, salt, AES.InstanceNoPadding); println(dec);

    assert(str == dec)
  }

  "AES/CBC/NoPadding random String encryption" should "work" in {
    val password = "123"
    val salt = "123"
    val size = Random.nextInt(20); println(size);
    val str = randomStringArray(size); println(str);

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
    val str = randomStringArray(size); println(str);

    val enc = AES.encrypt(str, password, salt, AES.InstancePKCS5Padding); println(enc);
    val dec = AES.decrypt(enc, password, salt, AES.InstancePKCS5Padding); println(dec);

    assert(str == dec)
  }

  "padding ASCII" should "work" in {
    val str = "Hello World"; println(str);

    val padded = AES.pkcs5Pad(str.getBytes("UTF-8")); println(new String(padded));
    val unpadded = AES.pkcs5Unpad(padded); println(unpadded);

    assert(str == new String(unpadded, "UTF-8"))
  }

  "padding UTF-8" should "work" in {
    val str = "Hælló World"; println(str);

    val padded = AES.pkcs5Pad(str.getBytes("UTF-8")); println(new String(padded));
    val unpadded = AES.pkcs5Unpad(padded); println(unpadded);

    assert(str == new String(unpadded, "UTF-8"))
  }

  "padding random String" should "work" in {
    val size = Random.nextInt(20); println(size);
    val str = randomStringArray(size); println(str);

    val padded = AES.pkcs5Pad(str.getBytes("UTF-8")); println(new String(padded));
    val unpadded = AES.pkcs5Unpad(padded); println(unpadded);

    assert(str == new String(unpadded, "UTF-8"))
  }

}