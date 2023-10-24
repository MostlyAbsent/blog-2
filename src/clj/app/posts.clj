(ns app.posts
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
        (= key :title) {:title (second (re-find #"'(.*)'" value))}
        (= key :date)  {:date  (second (re-find #"'(.*)'" value))}
        (= key :tags)  {:tags  (map second (re-seq #"'(.+?)'" value))}
        (= key :summary) {:summary (second (re-find #"'(.*)'" value))}
        :else nil))))

(defn grab-meta-block [t]
  (->> (second (str/split t #"---"))
       (re-seq #"(.+): (.+)")
       generate-meta
       (remove nil?)
       (reduce merge)))

(defn post-metadada [acc f]
  (let [t (slurp f)
        metadata (grab-meta-block t)]
    (conj acc
          (merge {:slug (second (re-find #"(.+?)\." (.getName f)))
                  :path (str "blog/" (second (re-find #"(.+?)\." (.getName f))))}
                 metadata))))

(defn posts [_]
  (r/response
   (->> "./resources/public/assets/data/blog/"
        files-in-folder
        (filter markdown-filter)
        (reduce post-metadada [])
        (sort-by :date))))

(defn posts-tagged [{:keys [path-params]}]
  (let [t (second (first path-params))
        posts (->> "./resources/public/assets/data/blog/"
                   files-in-folder
                   (filter markdown-filter)
                   (reduce post-metadada [])
                   (sort-by :date)
                   reverse
                   (filter (fn [{:keys [tags]}]
                             (some #{t} tags))))]
    (r/response posts)))
