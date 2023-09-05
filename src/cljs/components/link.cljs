(ns components.link
  (:require
   [helix.dom :as d])
  (:require-macros
   [lib.helix-wrapper :as lh]))

(lh/defnc link [{:keys [href] :as rest}]
  (d/a {:target "_blank"
        :rel "noopener noreferrer"
        :href href
        & rest}))
