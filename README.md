scala-aes-cbc
=============

This project provides a simple wrapper API for AES CBC (Symmetric Encryption) made with Scala/Java.

The provided algorihm is also compatible to the PHP encryption function [`mcrypt-encrypt`](http://php.net/manual/de/function.mcrypt-encrypt.php) and takes care also care of the padding.

# Getting started

Just download the repository and build it with the SimpleBuildTool (SBT) for Scala.

```bash
git clone https://github.com/swiesend/scala-aes-cbc.git
cd scala-aes-cbc
    
# fetch all dependencies
sbt update
```

# Deploy

You can export a jar or even a "fat" jar of the project to use it as library.

For building a jar review:

    sbt package
    
For building a "fat" jar (with all dependencies included) review:

* [sbt-assembly](https://github.com/sbt/sbt-assembly)

# Develop

## Testing

You can test the everything by simple running:

```bash
# test the module
sbt test
```

## Padding

You can either use PKCS5 padding or no padding at all.

Chose one of the provided Strings for the Padding instance:

    "AES/CBC/PKCS5Padding"
    
    "AES/CBC/NoPadding"

##Problems

If get the following Exception during the testing, than this is caused by the JVM Security policy which by default just allows 128Bit Encryption cyphers. As you can see in [AES.scala](src/main/scala/crypto/aes/AES.scala) we use a SHA-256.

```java
java.security.InvalidKeyException: Illegal key size
```

There are two ways to get around this, but both are clunky:

1. Lower to 128 bit
2. Download and install the "Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy", but this only solves the problem on that machine

For futher discussion on this review:

http://stackoverflow.com/questions/6481627/java-security-illegal-key-size-or-default-parameters

