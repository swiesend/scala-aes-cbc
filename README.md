scala-aes-cbc
=============

This project provides a simple wrapper API for AES CBC (Symmetric Encryption) made with Scala/Java.

The provided algorihm is also compatible to the PHP encryption function [`mcrypt-encrypt`](http://php.net/manual/de/function.mcrypt-encrypt.php) and takes care also care of the padding.

# Getting started

Just download the repository and build it with the SimpleBuildTool (SBT) for Scala.

    git clone https://github.com/swiesend/scala-aes-cbc.git
    cd scala-aes-cbc
    
    # fetch all dependencies
    sbt update

You can even export a jar or a "fat" jar of the project to use it as library.

For building a jar review:

    sbt package
    
For building a "fat" jar with all dependencies included review:

* [sbt-assembly](https://github.com/sbt/sbt-assembly)

## Padding

You can either use PKCS5 padding or no padding at all.

Chose one of the provided Strings for the Padding instance:

    "AES/CBC/PKCS5Padding"
    
    "AES/CBC/NoPadding"
