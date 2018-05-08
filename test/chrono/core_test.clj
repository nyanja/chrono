(ns chrono.core-test
  (:require [clojure.test :refer :all]
            [chrono.core :refer :all]))

(deftest add-test
  (testing "intervals"
    (is (= (add {:type :interval
                 :second 321}
                {:minute 58})
           {:type :time
            :hour 1
            :minute 3
            :second 21})))

  (testing "time"
    (is (= (add (time {:year 1999
                       :month 6
                       :day 28})
                {:hour 24
                 :month 8
                 :minute 10})
           {:type :time
            :tz :utc
            :year 2000
            :month 2
            :day 29
            :hour 0
            :minute 10
            :second 0})))

  (testing "raw intervals"
    (is (= (add {:minute 40} {:minute 30})
           {:minute 70}))))
