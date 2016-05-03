# shapeshift-scala

[![Build Status](https://travis-ci.org/alexdupre/shapeshift-scala.png?branch=master)](https://travis-ci.org/alexdupre/shapeshift-scala)

An asynchronous / non-blocking Scala library for the ShapeShift API.

## Artifacts

The library is cross-compiled with different versions of Dispatch-Reboot
and Play-WS, two well known HTTP Scala libraries, to fit every scenario.

Choose the appropriate artifact based on your needs:

| Artifact Id             | HTTP Provider   | AHC   | Netty   | Play-JSON |
| ----------------------- | --------------- | ----- | ------- | --------- |
| shapeshift-dispatch0112 | Dispatch 0.11.2 | 1.8.x | 3.9.x   | 2.3.x     |
| shapeshift-dispatch0113 | Dispatch 0.11.3 | 1.9.x | 3.10.x  | 2.4.x     |
| shapeshift-playws23     | Play WS 2.3.x   | 1.8.x | 3.9.x   | 2.3.x     |
| shapeshift-playws24     | Play WS 2.4.x   | 1.9.x | 3.10.x  | 2.4.x     |
| shapeshift-playws25     | Play WS 2.5.x   | 2.0.x | 4.0.x   | 2.5.x     |

and then, if you're using SBT, add the following line to your build file:

```scala
libraryDependencies += "com.alexdupre.shapeshift" %% "<artifactId>" % "1.0"
```

## Initialization

To initialize the library you just need to instantiate a client with
the chosen provider.

For Dispatch:

```scala
import com.alexdupre.shapeshift.ShapeShiftAPI
import com.alexdupre.shapeshift.provider.ShapeShiftDispatchProvider

val client: ShapeShiftAPI = ShapeShiftDispatchProvider.newClient()
```

For Play:

```scala
import com.alexdupre.shapeshift.ShapeShiftAPI
import com.alexdupre.shapeshift.provider.ShapeShiftPlayProvider

val client: ShapeShiftAPI = ShapeShiftPlayProvider.newClient(WS.client)
```

## Usage

The [ShapeShiftAPI](https://github.com/alexdupre/shapeshift-scala/blob/master/common/src/main/scala/com/alexdupre/shapeshift/ShapeShiftAPI.scala) trait
contains all the public methods that can be called on the client object.

Eg. if you like to trade Bitcoin for Ether you can obtain a deposit address
with the following code:

```scala
import com.alexdupre.shapeshift.models._

val market = Market("BTC", "ETH")
val outputAddress = "0xB368D70EF5F3466c8Fbd5B66cebE384D4E4C3d27"
val order: Future[OpenOrder] = client.createOpenTransaction(market, outputAddress)
```
