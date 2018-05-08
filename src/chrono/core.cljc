(ns chrono.core
  (:require [chrono.util :as util]))

(defn time [t]
  (merge {:type :time
          :year 1900
          :month 1
          :day 1
          :tz :utc} t))

(defn parse [s format])

(defn format [t format])

(defn timestamp [t])

(defn diff [t t'])

(defn add [t i]
  (util/normalize
   (merge-with + t i)))

(defn to-tz [t tz])
