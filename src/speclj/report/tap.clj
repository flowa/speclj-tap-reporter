(ns speclj.report.tap
  (:require [speclj.reporting]
            [clojure.java.io :as io]))

(def tests-total (atom 0))

(def lines (atom []))

(def tap-result-file "./target/tap-result.tap")

(defn- log [stuff]
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
      (log (str "ok " msg))))
  (report-pending [this pending])
  (report-fail [this fail]
    (let [msg (get-name-from-result fail)]
      (log (str "not ok " msg))))
  (report-runs [this runs]
    (log (str "1.." @tests-total)))
  (report-error [this error]))

(defn reset []
    (reset! tests-total 0)
    (reset! lines []) )
(defn new-tap-reporter []
  (TapReporter. 0 0 0))
