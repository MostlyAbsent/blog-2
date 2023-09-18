(ns components.header
  (:require
   [components.logo :as logo]
   [components.mobile-nav :as mobile-nav]
   [components.search-button :as search-button]
   [helix.core :refer [$]]
   [helix.dom :as d]
   [util.metadata :as metadata])
  (:require-macros
   [lib.helix-wrapper :as lh]))

(lh/defnc header []
  (d/header
   {:class-name "flex items-center justify-between py-10"}
   (d/div
    (d/a
     {:href "/"
      :aria-label (metadata/site :header-title)}
     (d/div
      {:class-name "flex items-center justify-between"}
      (d/div
       {:class-name "mr-3"}
       ($ logo/logo)
       (d/div
        {:class-name "hidden h-6 text-2xl font-semibold sm:block"}
        (metadata/site :header-title))))))
   (d/div
    {:class-name "flex items-center leading-5 space-x-4 sm:space-x-6"}
    (->> metadata/header-nav-links
         (filter (fn [link] (not= (:href link) "/")))
         (map (fn [{:keys [title href]}]
                (d/a
                 {:key title
                  :href href
                  :class-name "hidden sm:block font-mediem text-gray-900 dark:text-gray-100"}
                 title)))))
   ;;($ search-button/search-button)
   ;; theme switch button
   ($ mobile-nav/mobile-nav)))
