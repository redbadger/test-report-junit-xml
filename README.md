# test-report-junit-xml

[![CircleCI](https://circleci.com/gh/redbadger/test-report-junit-xml.svg?style=svg)](https://circleci.com/gh/redbadger/test-report-junit-xml)

test-report-junit-xml is a tool for producing JUnit XML reports from clojure.test test suites.

## Motivation

JUnit XML test reports are the de-facto standard for machine-readable test metadata.
Continuous integration tools like [Jenkins](https://wiki.jenkins-ci.org/display/JENKINS/JUnit+Plugin) and [CircleCI](https://circleci.com/docs/test-metadata/) use it to produce test summaries.

There is already a [plugin](https://github.com/ruedigergad/test2junit) available for [Leiningen](https://github.com/technomancy/leiningen) to produce JUnit XML.
We ran into an [issue](https://github.com/ruedigergad/test2junit/issues/10) with the output that made it more difficult to trace back to the source of failures in CI.
We therefore have rolled our own which aims to:

* produce comprehensive output, including context provided by [`clojure.test/testing`](https://clojure.github.io/clojure/clojure.test-api.html#clojure.test/testing) statements;
* hook into the existing `lein test` task, rather than creating a separate task;
* preserve the default test output written to stdout; and
* allow customization of the output.

## Installation

The easiest way to get started is to add the lein-test-report-junit-xml plugin to your Leiningen project map.
As it's only used in tests, it's best to add it only to the `:test` profile:

```clojure
:profiles {:test {:plugins [[lein-test-report-junit-xml "0.2.0"]]}}
```

## Usage

test-report-junit-xml is built on top of [test-report](https://github.com/redbadger/test-report), which hooks into the [`clojure.test/run-tests`](https://clojure.github.io/clojure/clojure.test-api.html#clojure.test/run-tests) function, so will automatically be included when running `lein test`.

An example project demonstrating usage with CircleCI is available [here](https://github.com/redbadger/test-report-junit-xml-example).

### Output location

By default, reports are written to a directory called `test-reports` created in the `target` directory.
This location can be overridden by specifying the `:output-dir` option under `:test-report-junit-xml` in the project map (again, this should probably go under the `:test` profile):

```clojure
:test-report-junit-xml {:output-dir "somewhere/else"}
```

The environment variable `TEST_REPORT_JUNIT_XML_OUTPUT_DIR`, if present, takes precedence over the value specified in the project.

The directory (and its parents) will be created if they do not already exist.

### Customizing output

TODO


## License

Copyright © 2017 Red Badger

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
