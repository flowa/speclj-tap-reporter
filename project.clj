(defproject speclj-tap-reporter "0.0.1-SNAPSHOT"
  :description "A tap reporter for excellent speclj testing library"
  :url "https://github.com/flowa/speclj-tap-reporter"
  :license {:name "MIT"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.6.0"]]
  :profiles {:dev {:dependencies [[speclj "3.2.0"]
                                  [speclj-tap-runner "0.1.0-SNAPSHOT"]]}}
  :plugins [[speclj "3.2.0"]]
  :test-paths ["spec"])
