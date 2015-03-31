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

(defn- assert-report-description-simple [description reporter result]
  (.report-description reporter description)
  (should= result
           (with-out-str
             (.report-runs reporter ""))))

(describe "Tap Reporter tests"

          (around [it]
                  (with-out-str (it)))

          (with reporter (tap/new-tap-reporter))

          (describe "Tap Plan tests"

                    (it "Prints 1..5 as tap description"
                        (let [description (generate-description-with-x-charcteristics 5)]
                          (assert-report-description-simple description @reporter "1..5\n")))

                    (it "Prints 1..0 as tap description. Library we use always starts from 1"
                        (let [description (generate-description-with-x-charcteristics 0)]
                          (assert-report-description-simple description @reporter "1..0\n")))

                    (it "Prints 1..1000 as tap description"
                        (let [description (generate-description-with-x-charcteristics 1000)]
                          (assert-report-description-simple description @reporter "1..1000\n"))))

          (describe "Testline tests"

                    (it "Prints ok testcase 1 when testcase 1 is successful"
                        (let [testcaseresult (generate-test-result "testcase 1")]
                          (should= "ok testcase 1\n"
                                   (with-out-str
                                     (.report-pass @reporter testcaseresult)))))

                    (it "Prints not ok testcase 1 when testcase 1 is not successfull"
                        (let [testcaseresult (generate-test-result "testcase 1")]
                          (should= "not ok testcase 1\n"
                                  (with-out-str
                                    (.report-fail @reporter testcaseresult)))))))

