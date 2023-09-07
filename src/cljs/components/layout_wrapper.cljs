(ns components.layout-wrapper
  (:require-macros
    [lib.helix-wrapper :as lh])
  (:require
   [app.main :as main]
   [components.footer :as footer]
   [components.header :as header]
   [components.section-container :as section-container]
   [helix.core :refer [$]]
   [helix.dom :as d]))

(lh/defnc layout-wrapper [{:keys [children]}]
  ($ section-container/section-container
     (d/div
      {:class-name "latin flex h-screen flex-col justify-between font-sans"}
      ($ header/header)
      ($ main/home
         {:class-name "mb-auto"}
         children)
      ($ footer/footer))))
