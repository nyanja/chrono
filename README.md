# chrono

Pure clojure time made simple for clj & cljs

## Usage

```clj


(def t (ch/time
          {:year 2018
           :month 1
           :day 29
           :hour 10
           :minute 30
           :second 15
           :tz :utc}))

(ch/now) => time

(ch/add t {:minutes 100}) => time
(ch/add t {:minutes -100}) => time

(ch/to-tz t :ny)
(ch/to-tz t :utc)

(ch/format t :iso) => "2018-01-29T10:30:15"
(ch/format t "%H:%M") => "10:30"

(ch/parse s :iso)



```

## License

Copyright © 2018 niquola

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
