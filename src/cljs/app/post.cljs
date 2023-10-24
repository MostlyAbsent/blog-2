(ns app.post
  (:require-macros
   [lib.helix-wrapper :as lh])
  (:require
   ["html-react-parser" :as hrp]
   [clojure.string :as str]
   [components.logo :as logo]
   [helix.core :refer [$]]
   [helix.dom :as d]
   [helix.hooks :as hooks]
   [promesa.core :as p]
   [util.date :as date]
   [util.metadata :as metadata]))

(lh/defnc post []
  (let [blog-name (-> js/document
                      .-location
                      .-pathname
                      (str/split "/blog/")
                      second)
        [text set-text] (hooks/use-state "")
        [meta set-meta] (hooks/use-state ())]
    (hooks/use-effect
     []
     (p/let [p (js/fetch (str "/api/post/" blog-name))
             j (.json p)
             c (js->clj j :keywordize-keys true)]
       (set-meta (:metadata c))
       (set-text (:html c))))
    (set! (. js/document -title) (str (:page-title metadata/site) (first (:title meta))))
    (d/section
     {:class-name "mx-auto max-w-3xl px-4 sm:px-6 xl:max-w-5xl xl:px-0"}
     (d/div
      {:class-name "fixed bottom-8 right-8 hidden flex-col gap-3 md:flex"}
      (d/button
       {:class-name "rounded-full bg-gray-200 p-2 text-gray-500 transition-all hover:bg-gray-300 dark:bg-gray-700 dark:text-gray-400 dark:hover:bg-gray-600"}
       (d/svg
        {:class "h-5 w-5"
         :viewBox "0 0 20 20"
         :fill "currentColor"}
        (d/path
         {:fill-rule "evenodd"
          :d "M3.293 9.707a1 1 0 010-1.414l6-6a1 1 0 011.414 0l6 6a1 1 0 01-1.414 1.414L11 5.414V17a1 1 0 11-2 0V5.414L4.707 9.707a1 1 0 01-1.414 0z"
          :clip-rule "evenodd"}))))
     (d/article
      (d/div
       {:class-name "xl:divide-y xl:divide-gray-200 xl:dark:divide-gray-700"}
       (d/header
        {:class-name "pt-6 xl:pb-6"}
        (d/div
         {:class-name "space-y-1 text-center"}
         (d/dl
          {:class-name "space-y-10"}
          (d/div
           (d/dt
            {:class-name "sr-only"}
            "Published on")
           (d/dd
            {:class-name "text-base font-medium leading-6 text-gray-500 dark:text-gray-400"}
            (d/time
             (let [d (js/Date. (first (:date meta)))]
               {:dateTime  (if d (.toISOString d))}
               (if d (date/format-date d (:locale metadata/site))))))))
         (d/div
          (d/h1
           {:class-name "text-3xl font-extrabold leading-9 tracking-tight text-gray-900 dark:text-gray-100 sm:text-4xl sm:leading-10 md:text-5xl md:leading-14"}
           (:title meta)))))
       (d/div
        {:class-name "grid-rows-[auto_1fr] divide-y divide-gray-200 pb-8 dark:divide-gray-700 xl:grid xl:grid-cols-1 xl:gap-x-6 xl:divide-y-0"}
        (d/dl
         {:class-name "pb-10 pt-6 xl:border-b xl:border-gray-200 xl:pt-11 xl:dark:border-gray-700"}
         (d/dt
          {:class-name "sr-only"}
          "Author")
         (d/dd
          (d/ul
           {:class-name "flex flex-wrap justify-center gap-4 sm:space-x-12 xl:block xl:space-x-0 xl:space-y-8"}
           (d/li
            {:class-name "flex items-center space-x-2"}
            ($ logo/logo)
            (d/dl
             {:class-name "whitespace-nowrap text-sm font-medium leading-5"}
             (d/dt
              {:class-name "sr-only"}
              "Name")
             (d/dd
              {:class-name "text-gray-900 dark:text-gray-100"}
              (:author metadata/site))))))
         (d/div
          {:class-name "divide-y divide-gray-200 dark:divide-gray-700 xl:col-span-3 xl:row-span-2 xl:pb-0"})
         ($ "div"
            {:className "prose max-w-none pb-8 pt-10 dark:prose-invert"}
            (hrp text))))
       (d/footer
        (d/div
         {:class-name "divide-gray-200 text-sm font-medium leading-5 dark:divide-gray-700 xl:col-start-1 xl:row-start-2 xl:divide-y"}
         (if (:tags meta)
           (d/div
            {:class-name "py-4 xl:py-8"}
            (d/h2
             {:class-name "text-xs uppercase tracking-wide text-gray-500 dark:text-gray-400"}
             "Tags")
            (d/div
             {:class-name "flex flex-wrap"}
             (for [t (:tags meta)]
               (d/a
                {:class-name "mr-3 text-sm font-medium uppercase text-primary-500 hover:text-primary-600 dark:hover:text-primary-400"
                 :href (str "/tags/" t)}
                t))))))
        (d/div
         {:class-name "pt-4 xl:pt-8"}
         (d/a
          {:class-name "text-primary-500 hover:text-primary-600 dark:hover:text-primary-400"
           :aria-label "Back to the blog"
           :href "/blog"}
          "← Back to the blog"))))))))
