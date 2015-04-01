(ns speclj-tap-runner.core-spec
  (:require [speclj.core :refer :all]
            [speclj.results :as results]
            [speclj.report.tap :as tap]
            [clojure.pprint :as pprint]
            [speclj.components :as components])
  (:import  [speclj.components Characteristic Description]))

(defn- make-characteristics [x]
  (atom (take x (repeat (Characteristic. "foo" "" "")))))

(defn- generate-description-with-x-charcteristics [x]
  (let [characteristics (make-characteristics x)]
    (Description. "foo" "" "" "" characteristics "" "" "" "" "" "" "" "" "")))

(defn- generate-test-result [name]
  (let [characteristic (components/->Characteristic name "" "")]
    (results/->PassResult characteristic 0)))

(defn- get-last-line []
  (let [l tap/lines] 
    (last @l)))

(defn- assert-report-description-simple [description reporter result]
  (.report-description reporter description)
  (.report-runs reporter "")
  (should= result (get-last-line)))

(describe "Tap Reporter tests"

          (with reporter (tap/new-tap-reporter))

            (describe "Tap Plan tests"
                      (after (tap/reset))

                      (around [it]
                              (with-redefs [tap/write-to-file (fn [foo] nil)
                                            tap/lines (atom [])] (it)))

                      (it "Prints 1..5 as tap description"
                          (let [description (generate-description-with-x-charcteristics 5)]
                            (assert-report-description-simple description @reporter "1..5")))

                      (it "Prints 1..0 as tap description. Library we use always starts from 1"
                          (let [description (generate-description-with-x-charcteristics 0)]
                            (assert-report-description-simple description @reporter "1..0")))

                      (it "Prints 1..1000 as tap description"
                          (let [description (generate-description-with-x-charcteristics 1000)]
                            (assert-report-description-simple description @reporter "1..1000"))))

            (describe "Testline tests"
                      (after (tap/reset))

                      (around [it]
                              (with-redefs [tap/write-to-file (fn [foo] nil)
                                            tap/lines (atom [])] (it)))

                      (it "Prints ok testcase 1 when testcase 1 is successful"
                          (let [testcaseresult (generate-test-result "testcase 1")]
                            (.report-pass @reporter testcaseresult)    
                            (should= "ok testcase 1" (get-last-line))))

                      (it "Prints not ok testcase 1 when testcase 1 is not successfull"
                          (let [testcaseresult (generate-test-result "testcase 1")]
                            (.report-fail @reporter testcaseresult)
                            (should= "not ok testcase 1" (get-last-line))))))

