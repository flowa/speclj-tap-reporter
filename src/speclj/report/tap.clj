(ns speclj.report.tap
  (:require [speclj.reporting]
            [clojure.test.tap :as tap]))

(defn- log [stuff]
  (print stuff)
  (flush))

(defn- get-name-from-result [result]
  (.name (.characteristic result)))

(defn- get-number-of-tests-from-description [description]
  (let [descriptions (.charcteristics description)]
  (count @descriptions)))

(deftype TapReporter [passes fails results]
  speclj.reporting/Reporter
  (report-message [this message])
  (report-description [this description]
    (tap/print-tap-plan (get-number-of-tests-from-description description)))
  (report-pass [this result]
    (let [msg (get-name-from-result result)]
      (tap/print-tap-pass msg)))
  (report-pending [this pending])
  (report-fail [this fail]
    (let [msg (get-name-from-result fail)]
      (tap/print-tap-fail msg)))
  (report-runs [this runs])
  (report-error [this error]))

(defn new-tap-reporter []
  (TapReporter. 0 0 0))
