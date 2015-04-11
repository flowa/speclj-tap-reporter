(ns speclj.report.tap
  (:require [speclj.reporting]
            [clojure.pprint :as pprint]
            [clojure.java.io :as io]))

(def tests-total (atom 0))

(def lines (atom []))

(def tap-result-file "./target/tap-result.tap")

(defn write-to-file [results]
  (with-open [writer (io/writer tap-result-file)]
    (doseq [line @lines] (.write writer (str line "\n")))))

(defn- write-line-to-report! [stuff]
  (swap! lines conj stuff))

(defn- get-name-from-result [result]
  (.name (.characteristic result)))

(defn- get-number-of-tests-from-description [description]
  (let [descriptions (.charcteristics description)]
    (count @descriptions)))

(deftype TapReporter [passes fails results]
  speclj.reporting/Reporter
  (report-message [this message])
  (report-description [this description]
    (let [tests-in-current-plan (get-number-of-tests-from-description description)]
      (swap! tests-total #(+ % tests-in-current-plan))))
  (report-pass [this result]
    (let [msg (get-name-from-result result)]
      (write-line-to-report! (str "ok " msg))))
  (report-pending [this pending])
  (report-fail [this fail]
    (let [msg (get-name-from-result fail)]
      (write-line-to-report! (str "not ok " msg))))
  (report-runs [this results]
    (write-line-to-report! (str "1.." @tests-total))
    (write-to-file @lines))
  (report-error [this error]))

(defn reset []
  (reset! tests-total 0)
  (reset! lines []))

(defn new-tap-reporter []
  (TapReporter. 0 0 0))
