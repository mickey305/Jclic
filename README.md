# Jclic(Command line wrapper component for Java)

| | branch | travis ci | JitPack |
|:---:|:---|:---|:---|
| release | gh-pages | [![Build Status](https://travis-ci.org/mickey305/Jclic.svg?branch=gh-pages)](https://travis-ci.org/mickey305/Jclic) | [![](https://jitpack.io/v/mickey305/Jclic.svg)](https://jitpack.io/#mickey305/Jclic) |
| - | develop | [![Build Status](https://travis-ci.org/mickey305/Jclic.svg?branch=develop)](https://travis-ci.org/mickey305/Jclic) |  |

# Installation(Pattern 1)
## 1 - Register repository in local library

```
repositories {
  maven { url 'http://mickey305.github.io/Jclic/repository/' }
  ...
}
```

## 2 - Compile library

```
dependencies {
  // newest version
  compile 'com.mickey305:jclic:+@jar'
  ...
}
```

```
dependencies {
  // target version - e.g. version 0.0.1-SNAPSHOT
  compile 'com.mickey305:jclic:0.0.1-SNAPSHOT'
  ...
}
```

# Installation(Pattern 2) - how to use the JitPack service
## 1 - Register repository in local library

```
repositories {
  maven { url 'https://jitpack.io' }
  ...
}
```

## 2 - Compile library

```
dependencies {
  // target version - e.g. version 0.0.1-SNAPSHOT
  compile 'com.github.mickey305:Jclic:0.0.1-SNAPSHOT'
  ...
}
```

# History
 * version 0.0.1-SNAPSHOT deploy(Test version) - 2017-5-08
