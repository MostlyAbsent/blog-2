(ns app.tags
  (:require-macros
   [lib.helix-wrapper :as lh])
  (:require
   [components.tag :as tag]
   [helix.core :refer [$ <>]]
   [helix.dom :as d]
   [helix.hooks :as hooks]
   [promesa.core :as p]
   [util.metadata :as metadata]))

(lh/defnc tags []
  (let [[tag-counts set-tag-count] (hooks/use-state {})
        [sorted-tags set-tag-keys] (hooks/use-state [])]
    (hooks/use-effect
     []
     (p/let [res (js/fetch "/api/tag-counts")
             _res (.json res)
             data (js->clj _res)]
       (set-tag-count merge data)))
    (hooks/use-effect
     [tag-counts]
     (set-tag-keys (->> tag-counts
                        (sort-by val >)
                        keys)))
    (set! (. js/document -title) (str (:page-title metadata/site) "Tags"))
    (set! (. js/document -description) "Things I blog about.")
    (<>
     (d/div
      {:class-name "flex flex-col items-start justify-start divide-y divide-gray-200 dark:divide-gray-700 md:mt-24 md:flex-row md:items-center md:justify-center md:space-x-6 md:divide-y-0"}
      (d/div
       {:class-name "space-x-2 pb-8 pt-6 md:space-y-5"}
       (d/h1
        {:class-name "text-3xl font-extrabold leading-9 tracking-tight text-gray-900 dark:text-gray-100 sm:text-4xl sm:leading-10 md:border-r-2 md:px-6 md:text-6xl md:leading-14"}
        "Tags"))
      (d/div
       {:class-name "flex max-w-lg flex-wrap"}
       (if (= 0 (count sorted-tags))
         "No tags found."
         (for [t sorted-tags]
           (d/div
            {:key t
             :class-name "mb-2 mr-5 mt-2"}
            ($ tag/tag {:title t})
            (str " (" (get tag-counts t) ")")))))))))
