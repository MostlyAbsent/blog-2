(ns util.date)

(defn format-date
  ([date]
   (format-date date "en-AU"))
  ([date locale]
   (let [options (clj->js {:year "numeric" :month "long" :day "numeric"})]
     (.toLocaleDateString (js/Date. date) locale options))))
