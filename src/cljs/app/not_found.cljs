(ns app.not-found
  (:require
   [helix.dom :as d]
   [util.metadata :as metadata])
  (:require-macros
   [lib.helix-wrapper :as lh]))

(lh/defnc not-found []
  (set! (. js/document -title) (str (:page-title metadata/site) "Not Found!"))
  (d/div
   {:class-name "flex flex-col items-start justify-start md:mt-24 md:flex-row md:items-center md:justify-center md:space-x-6"}
   (d/div
    {:class-name "space-x-2 pb-8 pt-6 md:space-y-5"}
    (d/h1
     {:class-name "text-6xl font-extrabold leading-9 tracking-tight text-gray-900 dark:text-gray-100 md:border-r-2 md:px-6 md:text-8xl md:leading-14"}
     "404"))
   (d/div
    {:class-name "max-w-md"}
    (d/p
     {:class-name "mb-4 text-xl font-bold leading-normal md:text-2xl"}
     "Sorry we couldn't find this page.")
    (d/p
     {:class-name "mb-8"}
     "But dont worry, you can find plenty of other things on our homepage.")
    (d/a
     {:href "/"
      :class-name "focus:shadow-outline-blue inline rounded-lg border border-transparent bg-blue-600 px-4 py-2 text-sm font-medium leading-5 text-white shadow transition-colors duriation-150 hover:bg-blue-700 focuss:outline-none dark:hover:bg-blue-500"}
     "Back to homepage"))))
