(ns app.core
  (:require
   [app.posts :as posts]
   [app.tags :as tags]
   [clojure.java.io :as io]
   [muuntaja.core :as m]
   [reitit.ring :as ring]
   [reitit.ring.middleware.muuntaja :as muuntaja]
   [ring.adapter.jetty :as ring-jetty]
   [ring.util.response :as r])
  (:gen-class))

(defn index []
  (slurp (io/resource "public/index.html")))

(defn projects [_]
  (r/response [{:title "breadit"
                :description "A Full Stack Reddit clone."
                :img-src "/assets/data/img/breadit.png"
                :href "breadit.just-the.tips"}]))

(def app
  (ring/ring-handler
   (ring/router
    ["/"
     ["api/"
      ["tag-data/*" tags/tag-data]
      ["tag-counts" tags/tag-counts]
      ["posts-tagged/*" posts/posts-tagged]
      ["posts" posts/posts]
      ["projects" projects]]
     ["assets/*" (ring/create-resource-handler {:root "public/assets"})]
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
