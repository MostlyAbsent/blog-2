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

(defn tag-data [_]
  (r/response
   {:title "guide"
    :description "A guide to a topic."}))

(defn projects [_]
  (r/response [{:title "breadit"
                :description "A Full Stack Reddit clone."
                :img-src "/assets/data/img/breadit.png"
                :href "breadit.just-the.tips"}]))

(defn posts [_]
  (r/response
   [{:slug "/one"
     :path "/one"
     :key 1
     :date "2003"
     :summary "first"
     :tags [{:title "guide"
             :key 1}
            {:title "two"
             :key 2}]
     :title "guide"}
    {:slug "/two"
     :path "/two"
     :key 2
     :date "2003"
     :summary "second"
     :tags [{:title "guide"
             :key 1}]
     :title "guide"}
    {:slug "/three"
     :path "/three"
     :key 3
     :date "2003"
     :summary "third"
     :tags [{:title "guide"
             :key 1}]
     :title "guide"}
    {:slug "/fourth"
     :path "/fourth"
     :key 4
     :date "2003"
     :summary "fourth"
     :tags [{:title "guide"
             :key 1}]
     :title "guide"}
    {:slug "/fifth"
     :path "/fifth"
     :key 5
     :date "2003"
     :summary "fifth"
     :tags [{:title "guide"
             :key 1}]
     :title "guide"}]))

(def app
  (ring/ring-handler
   (ring/router
    ["/"
     ["api/"
      ["tag-data/*" tag-data]
      ["tag-counts" tags/tag-counts]
      ["posts-tagged/*" posts]
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
