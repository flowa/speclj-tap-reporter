# speclj-tap-reporter

A Plugin for spelcj. Reports test in
[TAP](http://testanything.org/) format. Useful in
Continuous integration systems for example.

## Installation

Add the following to your project.clj under the :dev profile:

    :dependencies [[speclj-tap-reporter "0.0.1-SNAPSHOT"]]

Speclj 3.2.0 or later is required.

## Usage.

    lein spec -f tap

Test results are recorded to target/tap-result.tap file.

## TODO

* Directives support 

## License

Copyright (C) 2015 Flowa

Distributed under the The MIT License.
