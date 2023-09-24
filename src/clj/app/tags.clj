(ns app.tags
  (:require
   [clojure.java.io :as io]
   [clojure.string :as str]
   [ring.util.response :as r]))

(defn files-in-folder
  [path]
  (-> path
      io/file
      file-seq))

(defn markdown-filter [f]
  (= (second (re-find #"(\.[a-zA-Z0-9]+)$" (.getName f))) ".md"))

(defn generate-meta [match-group]
  (for [match match-group]
    (let [key (keyword (second match))
          value (nth match 2)]
      (cond
        (= key :tags) (map second (re-seq #"'(.+?)'" value))
        :else nil))))

(defn grab-meta-block [t]
  (->> (second (str/split t #"---"))
       (re-seq #"(.+): (.+)")
       generate-meta
       (remove nil?)
       (reduce merge)))

(defn tag-counter [acc f]
  (let [t (slurp f)
        metadata (grab-meta-block t)
        tags (reduce (fn [acc x] (assoc acc x 1)) {} metadata)]
    (println acc)
    (println tags)
    (reduce (fn [acc y]
              (let [x (first y)]
                (if (get acc x)
                  (merge acc {x (inc (get acc x))})
                  (merge acc y))))
            acc tags)))

(defn tag-counts
  [_]
  (r/response
   (->> "./resources/public/assets/data/blog/"
        files-in-folder
        (filter markdown-filter)
        (reduce tag-counter {}))))
