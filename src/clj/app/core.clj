(ns app.core
  (:require
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
   {"markdown" 3
    "code" 3
    "features" 3
    "next-js" 5
    "math" 1
    "ols" 1
    "github" 1
    "guide" 4
    "tailwind" 2
    "holiday" 1
    "canada" 1
    "images" 1
    "writings" 1
    "book" 1
    "reflection" 1
    "multi-author" 1
    "feature" 1}))

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
     :tags [{:title "test"
             :key 1}
            {:title "two"
             :key 2}]
     :title "test"}
    {:slug "/two"
     :path "/two"
     :key 2
     :date "2003"
     :summary "second"
     :tags [{:title "test"
             :key 1}]
     :title "test"}
    {:slug "/three"
     :path "/three"
     :key 3
     :date "2003"
     :summary "third"
     :tags [{:title "test"
             :key 1}]
     :title "test"}
    {:slug "/fourth"
     :path "/fourth"
     :key 4
     :date "2003"
     :summary "fourth"
     :tags [{:title "test"
             :key 1}]
     :title "test"}
    {:slug "/fifth"
     :path "/fifth"
     :key 5
     :date "2003"
     :summary "fifth"
     :tags [{:title "test"
             :key 1}]
     :title "test"}]))

(def app
  (ring/ring-handler
   (ring/router
    ["/"
     ["api/"
      ["tag-data" tag-data]
      ["posts" posts]
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
