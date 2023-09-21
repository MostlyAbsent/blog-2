(ns app.projects
  (:require-macros
   [lib.helix-wrapper :as lh])
  (:require
   [cljs.pprint :as pp]
   [components.card :as card]
   [helix.core :refer [$ <>]]
   [helix.dom :as d]
   [helix.hooks :as hooks]
   [promesa.core :as p]))

(lh/defnc projects []
  (let [[project-list set-project-list] (hooks/use-state {:projects []})]
    (hooks/use-effect
      []
      (p/let [res (js/fetch "/api/projects")
              json (.json res)
              data (js->clj json :keywordize-keys true)]
        (set-project-list assoc :projects data)))
    (.log js/console (pp/pprint (:projects project-list)))
    (set! (. js/document -title) "Projects")
    (<>
     (d/div
      {:class-name "divide-y divide-gray-200 dark:divide-gray-700"}
      (d/div
       {:class-name "space-y-2 pb-8 pt-6 md:space-y-5"}
       (d/h1
        {:class-name "text-3xl font-extrabold leading-9 tracking-tight text-gray-900 dark:text-gray-100 sm:text-4xl sm:leading-10 md:text-6xl md:leading-14"}
        "Projects")))
     (d/div
      {:class-name "container py-12"}
      (d/div
       {:class-name "-m-4 flex flex-wrap"}
       (for [{:keys [title description href img-src]} (:projects project-list)]
         ($ card/card {:key title
                       :title title
                       :description  description
                       :href href
                       :img-src img-src})))))))
