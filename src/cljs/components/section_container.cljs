(ns components.section-container
  (:require-macros
   [lib.helix-wrapper :as lh])
  (:require
   [helix.dom :as d]))

(lh/defnc section-container [{:keys [children]}]
  (d/section
   {:class-name "mx-auto max-w-3xl px-4 sm:px-6 xl:max-w-5xl xl:px-0"}
   children))
