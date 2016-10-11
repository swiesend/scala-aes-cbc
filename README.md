scala-aes-cbc
=============

This project provides a simple wrapper API for AES CBC (Symmetric Encryption) made with Scala/Java.

The provided algorithm is also compatible to the PHP encryption function [`mcrypt-encrypt`](http://php.net/manual/de/function.mcrypt-encrypt.php) and can handle padding.

> By the way you most likely do not want to use AES with [ECB](https://en.wikipedia.org/wiki/Block_cipher_mode_of_operation#Electronic_Codebook_.28ECB.29) block cipher mode - tl;dr: just don't do it.


# Getting started

Just download the repository and build it with the [SimpleBuildTool (SBT)](http://www.scala-sbt.org/download.html) for Scala. Make sure that you have `git` and `sbt` installed.

```bash
git clone https://github.com/swiesend/scala-aes-cbc.git
cd scala-aes-cbc

# fetch all dependencies; this can take a while...
sbt update
```
## Usage

```scala
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
```

> For further examples review [AESTest.scala](src/test/scala/crypto/aes/AESTest.scala).

# Deploy

You can export a jar or even a fat jar (with all dependencies included) of the project to use it as library.

Deploying a jar:

    sbt package

For building a fat jar do:

    sbt assembly

>  Review [sbt-assembly](https://github.com/sbt/sbt-assembly) for further information, if this should fail.


# Develop


## Testing

You can test the everything by simple running:

```bash
# test the module
sbt test
```

## Algorithm / Cipher / Padding

You can either use PKCS5 padding or no padding at all. The functions `encrypt` and `decrypt` assume a PKCS5 padding by default.

Chose one of the provided Strings in [AES.scala](src/main/scala/crypto/aes/AES.scala) for cipher instance (`javax.crypto.Cipher`):

    "AES/CBC/PKCS5Padding"
    "AES/CBC/NoPadding"

> NOTE: For further Ciphers review: https://docs.oracle.com/javase/8/docs/api/javax/crypto/Cipher.html

## Problems

If you get the following exception during the tests, than this is caused by the JVM Security policy which by default just allows 128 bit encryption ciphers. As you can see in [AES.scala](src/main/scala/crypto/aes/AES.scala) we use a SHA-256.

```java
java.security.InvalidKeyException: Illegal key size
```

There are two ways to get around this, but both are clunky:

1. Lower to 128 bit
2. Download and install the "Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy", but this only solves the problem on that machine

For futher discussion on this review:

http://stackoverflow.com/questions/6481627/java-security-illegal-key-size-or-default-parameters

> If you know a better solution, please let me know.


## Contribute

If you want to make this better, just fork the project and push a change request. Every help is welcome.
