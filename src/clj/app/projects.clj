(ns app.projects
  (:require
   [clojure.edn :as edn]
   [ring.util.response :as r]))

(defn projects [_]
  (let [projects (->> "./resources/public/assets/data/projects.edn"
                  slurp
                  edn/read-string)]
    (r/response projects)))
