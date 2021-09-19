# Alg

A simple Alg(ebra) language for demonstrating language design.

The corresponding presentation/slides can be found [here](https://docs.google.com/presentation/d/e/2PACX-1vR-gi-XarSO2jFDI98Ga25OhfGCbJ_bYBRIHERbs5cKoip8fWg-Xxpqo-PFrvAjU3sfRp0-5RFPifc1/pub?start=false&loop=false&delayms=3000).

## Building and Running

```shell
./gradlew installDist
```

### REPL

```shell
./build/install/alg/bin/alg
> x = 5
x = 5.00000
```

### Run a file

```shell
./build/install/alg/bin/alg ./examples/circlePoint.alg r=5 theta=30
x = 0.771257
y = -4.940158
```

### Running with Gradle

By default, Gradle outputs some build status information and doesn't attach stdin to the process.
To disable this behavior, add two flags to `./gradlew run`:

```shell
./gradlew run -q --console=plain
```
