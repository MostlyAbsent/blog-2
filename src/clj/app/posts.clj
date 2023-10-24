(ns app.posts
  (:require
   [clojure.java.io :as io]
   [clojure.string :as str]
   [markdown.core :as md]
   [ring.util.response :as r]))

(defn files-in-folder
  [path]
  (-> path
      io/file
      file-seq))

(defn markdown-filter [f]
  (= (second (re-find #"(\.[a-zA-Z0-9]+)$" (.getName f))) ".md"))

(defn deep-merge [v & vs]
  (letfn [(rec-merge [v1 v2]
            (if (and (map? v1) (map? v2))
              (merge-with deep-merge v1 v2)
              v2))]
    (if (some identity vs)
      (reduce #(rec-merge %1 %2) v vs)
      (last vs))))

(defn md->formatted-map [acc f]
  (let [t (slurp f)
        pre (md/md-to-html-string-with-meta t
                                            :footnotes? true)]
    (conj acc
          (deep-merge pre
                      {:metadata {:tags (str/split (first (:tags (:metadata pre))) #", ")
                                  :slug (second (re-find #"(.+?)\." (.getName f)))
                                  :path (str "blog/" (second (re-find #"(.+?)\." (.getName f))))}}))))

(defn posts [_]
  (r/response
   (->> "./resources/public/assets/data/blog/"
        files-in-folder
        (filter markdown-filter)
        (reduce md->formatted-map [])
        (sort-by (comp :date :metadata))
        reverse)))

(defn posts-tagged [{:keys [path-params]}]
  (let [t (:tag path-params)
        posts (->> "./resources/public/assets/data/blog/"
                   files-in-folder
                   (filter markdown-filter)
                   (reduce md->formatted-map [])
                   (sort-by (comp :date :metadata))
                   reverse
                   (filter (fn [{{:keys [tags]} :metadata}]
                             (some #{t} tags))))]
    (r/response posts)))

(defn post [{:keys [path-params]}]
  (let [f-name (str "./resources/public/assets/data/blog/" (:blog path-params) ".md")
        f (io/file f-name)]
    (if (.exists f)
      (r/response
       (let [pre (md/md-to-html-string-with-meta (slurp f)
                                                 :footnotes? true)
             tags (str/split (first (:tags (:metadata pre))) #", ")]
         (deep-merge pre {:metadata {:tags tags}})))
      (r/response {:status 404}))))
