(ns components.page-title
  (:require-macros
    [lib.helix-wrapper :as lh])
  (:require
    [helix.dom :as d]))

(lh/defnc page-title [{:keys [children]}]
  (d/h1
   {:class-name "text-3xl font-extrabold leading-9 tracking-tight text-gray-900 dark:text-gray-100 sm:text-4xl sm:leading-10 md:text-5xl md:leading-14"}
   children))
