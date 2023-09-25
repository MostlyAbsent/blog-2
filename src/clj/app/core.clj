(ns app.core
  (:require
   [app.posts :as posts]
   [app.projects :as projects]
   [app.tags :as tags]
   [clojure.java.io :as io]
   [muuntaja.core :as m]
   [reitit.ring :as ring]
   [reitit.ring.middleware.muuntaja :as muuntaja]
   [ring.adapter.jetty :as ring-jetty])
  (:gen-class))

(defn index []
  (slurp (io/file "./resources/public/index.html")))

(def app
  (ring/ring-handler
   (ring/router
    ["/"
     ["api/"
      ["tag-data/*" tags/tag-data]
      ["tag-counts" tags/tag-counts]
      ["posts-tagged/*" posts/posts-tagged]
      ["posts" posts/posts]
      ["projects" projects/projects]]
     ["assets/*" (ring/create-file-handler {:root "./resources/public/assets/"})]
     ["" (fn [_] {:body (index) :status 200})]
     ["footer" (fn [_] {:body (index) :status 200})]]
    {:data {:muuntaja m/instance
            :middleware [muuntaja/format-middleware]}})
   (ring/create-default-handler
    {:not-found (constantly {:status 200
                             :body (index)})})))

(defn start []
  (ring-jetty/run-jetty #'app {:port 3000
                               :join? false}))

(defn -main []
  (start))
