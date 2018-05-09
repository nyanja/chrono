(ns chrono.core-test
  (:require [clojure.test :refer :all]
            [chrono.core :as sut]))

(deftest add-test
  (testing "intervals"
    (is (= (sut/add {:type :interval
                     :second 321}
                    {:minute 58})
           {:type :interval
            :hour 1
            :minute 3
            :second 21})))

  (testing "time"
    (is (= (sut/add (sut/time {:year 1999
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
    (is (= (sut/add {:minute 40} {:minute 30})
           {:minute 70}))))

(deftest parse-test
  (is (= (sut/parse "2000-03-23T19:44:03" "%Y-%m-%dT%H:%M:%S")
         (sut/parse "2000-03-23T19:44:03" :iso)
         {:year 2000
          :month 3
          :day 23
          :hour 19
          :minute 44
          :second 3})))

(deftest format-test
  (is (= (sut/format {:year 2025
                      :month 3
                      :day 23
                      :hour 19
                      :minute 44
                      :second 3}
                     "Date: %d.%m.%y (%Y) %H:%M:%S")
         "Date: 23.03.25 (2025) 19:44:03")))
