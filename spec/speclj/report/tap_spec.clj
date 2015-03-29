(ns speclj-tap-runner.core-spec
  (:require [speclj.core :refer :all]
            [speclj.report.tap :as tap]))

(describe "a test"
          (it "FIXME, I fail."
              (should= 0 1))
          (it "DO NOT FIXME, I pass"
              (should= 1 1)))
