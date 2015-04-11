(defproject speclj-tap-reporter "0.1.1"
  :description "A tap reporter for excellent speclj testing library"
  :url "https://github.com/flowa/speclj-tap-reporter"
  :license {:name "MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :scm {:name "git"
        :url "https://github.com/flowa/speclj-tap-reporter.git"}
  :signing {:gpg-key "F2AE6777"}
  :deploy-repositories [["clojars" {:creds :gpg}]]

  :dependencies [[org.clojure/clojure "1.6.0"]]
  :profiles {:dev {:dependencies [[speclj "3.2.0"]
                                  [speclj-tap-reporter "0.1.1"]]}}
  :plugins [[speclj "3.2.0"]]
  :test-paths ["spec"])
