scala-aes-cbc
=============

This project provides a simple wrapper API for AES CBC (Symmetric Encryption) made with Scala/Java.

The provided algorihm is also compatible to the PHP encryption function [`mcrypt-encrypt`](http://php.net/manual/de/function.mcrypt-encrypt.php) and can handle padding.

> By the way you most likely do not want to use AES with [ECB](https://en.wikipedia.org/wiki/Block_cipher_mode_of_operation#Electronic_Codebook_.28ECB.29) block cipher mode - tl;dr: just don't do it.


# Getting started

Just download the repository and build it with the [SimpleBuildTool (SBT)](http://www.scala-sbt.org/download.html) for Scala. Make sure that you have `git` and `sbt` installed.

```bash
git clone https://github.com/swiesend/scala-aes-cbc.git
cd scala-aes-cbc

# fetch all dependencies; this can take a while...
sbt update
```


# Deploy

You can export a jar or even a fat jar (with all dependencies included) of the project to use it as library.

Deploying a jar:

    sbt package

For building a fat jar do:

    sbt assembly

>  Review [sbt-assembly](https://github.com/sbt/sbt-assembly) for further information, if this should fail.


# Develop

## Useage

For an example usage review [AESTest.scala](src/test/scala/crypto/aes/AESTest.scala).

## Testing

You can test the everything by simple running:

```bash
# test the module
sbt test
```

## Padding

You can either use PKCS5 padding or no padding at all. The functions `encrypt` and `decrypt` assume a PKCS5 padding by default.

Chose one of the provided Strings in [AES.scala](src/main/scala/crypto/aes/AES.scala) for cipher instance (`javax.crypto.Cipher`):

    "AES/CBC/PKCS5Padding"
    "AES/CBC/NoPadding"


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
