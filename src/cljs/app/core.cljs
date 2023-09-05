(ns app.core
  (:require
   ["react-dom/client" :as rdom]
   [helix.core :refer [$]]
   [helix.dom :as d])
  (:require-macros
   [lib.helix-wrapper :as lh]))

(lh/defnc page
  []
  (d/p {:className "text-zinc-500"} "test"))

(defonce root (rdom/createRoot (js/document.getElementById "app")))

(defn ^:export init []
  (.render root ($ page)))
