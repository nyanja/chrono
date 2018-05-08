(ns chrono.core
  (:require [chrono.util :as util]
            [clojure.string :as str]))

(def keys-map
  {"%Y" :year
   "%m" :month
   "%d" :day
   "%H" :hour
   "%M" :minute
   "%S" :second})

(def formats
  {:iso "%Y-%m-%dT%H:%M:%S"})

(defn time [t]
  (merge {:type :time
          :year 1900
          :month 1
          :day 1
          :tz :utc} t))

(defn parse [s f]
  (cond
    (keyword? f)
    (parse s (get formats f))

    :else
    (let [re-f (re-pattern (str/replace f #"%." #(get {"%Y" "(\\d{4})"}
                                                      %
                                                      "(\\d{2})")))
          values (rest (re-find re-f s))
          patterns (re-seq #"%." f)]
      (zipmap (map keys-map patterns)
              (map (fn [n] #?(:clj (Integer. n)
                              :cljs (int n))) values)))))

(defn format [t format])

(defn timestamp [t])

(defn diff [t t'])

(defn add [t i]
  (util/normalize
   (merge-with + t i)))

(defn to-tz [t tz])
