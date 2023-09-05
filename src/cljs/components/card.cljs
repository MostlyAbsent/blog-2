(ns components.card
  (:require
   [components.link :as link]
   [helix.core :refer [$]]
   [helix.dom :as d])
  (:require-macros
   [lib.helix-wrapper :as lh]))

(lh/defnc card [{:keys [img-src title description href]}]
  (d/div {:class-name (str (if img-src "h-full " "") "overflow-hidden rounded-md border-2 border-gray-200 border-opacity-60 dark:border-gray-700")}
         (if href
           ($ link/link
              {:href href :aria-label (str "Link to " title)}
              ($ "img"
                 {:alt title
                  :src img-src
                  :class-name "object-cover object-center md:h-36 lg:h-48"
                  :width "544"
                  :height "306"}))
           ($ "img"
              {:alt title
               :src img-src
               :class-name "object-cover object-center md:h-36 lg:h-48"
               :width "544"
               :height "306"}))
         (d/div {:class-name "p-6"}
                (d/h2 {:class-name "mb-3 text-2xl font-bold leading-8 tracking-tight"}
                      (if href
                        ($ link/link
                           {:href href
                            :aria-label (str "Link to " title)} title)
                        title)))
         (d/p {:class-name "prose mb-3 max-w-none text-gray-500 dark:text-gray-400"}
              description)
         (if href
           ($ link/link
              {:href href
               :class-name "text-base font-medium leading-6 text-primary-500 hover:text-primary-600 dark:hover:text-primarf-400"
               :aria-label (str "Link to " title)}
              "Learn more &#8012;")))) ;; html entities
