(ns speclj-tap-runner.core-spec
  (:require [speclj.core :refer :all]
            [speclj.report.tap :as tap])
  (:import  [speclj.components Characteristic Description]))

(defn- make-characteristics [x]
  (atom (take x (repeat (Characteristic. "foo" "" "")))))

(defn- generate-description-with-x-charcteristics [x]
  (let [characteristics (make-characteristics x)]
    (Description. "foo" "" "" "" characteristics "" "" "" "" "" "" "" "" "")))

(describe "Tap Plan is printed correctly"

          (around [it]
                  (with-out-str (it)))

          (with reporter (tap/new-tap-reporter))

          (it "Prints 1..5 as tap description"
              (let [description (generate-description-with-x-charcteristics 5)]
                (should= "1..5\n" 
                         (with-out-str 
                           (.report-description @reporter description))))))
