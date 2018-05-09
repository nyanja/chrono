(ns chrono.core
  (:require [chrono.util :as util]
            [clojure.string :as str]
            #?(:cljs goog.string.format)))

(def format-directives
  "Pad     - padding character when length is present,
   Length  - specific length of digital representation,
   Pattern - part of the regexp for parsing a time string
   one of Length or Pattern must be specified"
  {"%Y" {:key :year   :pad 0 :length 4}
   "%y" {:key :year   :trim 2}
   "%m" {:key :month  :pad 0 :length 2}
   "%d" {:key :day    :pad 0 :length 2}
   "%H" {:key :hour   :pad 0 :length 2}
   "%M" {:key :minute :pad 0 :length 2}
   "%S" {:key :second :pad 0 :length 2}})

(def patterns-map
  (->> format-directives
       (map (fn [[k v]] [k (or (:pattern v)
                               (when (:length v)
                                 (str "(\\d{" (:length v) "})")))]))
       (filter (fn [[_ v]] v))
       (into {})))

(def keys-map
  (->> format-directives
       (map (fn [[k v]] [k (:key v)]))
       (into {})))

(def formats
  {:iso "%Y-%m-%dT%H:%M:%S"})

(defn time [t]
  (merge {:type :time
          :year 1900
          :month 1
          :day 1
          :tz :utc} t))

(defn parse [s format]
  (cond
    (keyword? format)
    (parse s (get formats format))

    :else
    (let [re-f (re-pattern (str/replace format #"%." #(get patterns-map
                                                           %
                                                           "(\\d{2})")))
          values (rest (re-find re-f s))
          patterns (re-seq #"%." format)]
      (zipmap (map keys-map patterns)
              (map (fn [n] #?(:clj (Integer. n)
                              :cljs (int n))) values)))))

(defn format [t format]
  (str/replace
   format #"%."
   (fn [f]
     (let [rules (get format-directives f)
           value (get t (get keys-map f))]
       (cond
         (:length rules)
         (#?(:clj clojure.core/format
             :cljs goog.string.format)
          (str \% (:pad rules) (:length rules) \d)
          value)

         (:trim rules)
         (subs (str value) (- (count (str value)) (:trim rules)))

         :else (str value))))))

(defn timestamp [t])

(defn diff [t t'])

(defn add [t i]
  (util/normalize
   (merge-with + t i)))

(defn to-tz [t tz])
