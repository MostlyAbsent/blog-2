(ns app.core
  (:require
   [clojure.java.io :as io]
   [ring.adapter.jetty :as ring-jetty]
   [reitit.ring :as ring]
   [reitit.ring.middleware.muuntaja :as muuntaja]
   [muuntaja.core :as m]
   [ring.util.response :as r])
  (:gen-class))

(defn index []
  (slurp (io/resource "public/index.html")))

(defn posts [_]
  (r/response
   [{:slug "/one"
     :date "2003"
     :summary "first"
     :tags ["test"]
     :title "test"}
    {:slug "/two"
     :date "2003"
     :summary "second"
     :tags ["test"]
     :title "test"}
    {:slug "/three"
     :date "2003"
     :summary "third"
     :tags ["test"]
     :title "test"}
    {:slug "/fourth"
     :date "2003"
     :summary "fourth"
     :tags ["test"]
     :title "test"}
    {:slug "/fifth"
     :date "2003"
     :summary "fifth"
     :tags ["test"]
     :title "test"}]))

(def app
  (ring/ring-handler
   (ring/router
    ["/"
     ["api/"
      ["posts/" posts]]
     ["assets/*" (ring/create-resource-handler {:root "public/assets"})]
     ["" (fn [_] {:body (index) :status 200})]]
    {:data {:muuntaja m/instance
            :middleware [muuntaja/format-middleware]}})))

(defn start []
  (ring-jetty/run-jetty #'app {:port 3000
                               :join? false}))
