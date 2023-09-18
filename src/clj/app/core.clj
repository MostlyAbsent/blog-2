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

(defn posts [_]
  (r/response
   [{:slug "/one"
     :key 1
     :date "2003"
     :summary "first"
     :tags [{:title "test"
             :key 1}]
     :title "test"}
    {:slug "/two"
     :key 2
     :date "2003"
     :summary "second"
     :tags [{:title "test"
             :key 1}]
     :title "test"}
    {:slug "/three"
     :key 3
     :date "2003"
     :summary "third"
     :tags [{:title "test"
             :key 1}]
     :title "test"}
    {:slug "/fourth"
     :key 4
     :date "2003"
     :summary "fourth"
     :tags [{:title "test"
             :key 1}]
     :title "test"}
    {:slug "/fifth"
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
      ["posts/" posts]]
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
