(ns app.core
  (:require
   ["react-dom/client" :as rdom]
   [app.home :as home]
   [helix.core :refer [$]]
   [promesa.core :as p])
  (:require-macros
   [lib.helix-wrapper :as lh]))

(defonce root (rdom/createRoot (js/document.getElementById "app")))

(defn ^:export init []
  (p/let [_response (js/fetch "/api/posts/")
          response (.json _response)
          data (js->clj response :keywordize-keys true)]
    (.render root ($ home/home data))))
