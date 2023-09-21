(ns layout.tag-list
  (:require-macros
   [lib.helix-wrapper :as lh])
  (:require
   ["github-slugger" :refer [slug]]
   [clojure.string :as str]
   [components.tag :as tag]
   [helix.core :refer [$ <>]]
   [helix.dom :as d]
   [helix.hooks :as hooks]
   [promesa.core :as p]
   [util.date :as date]
   [util.metadata :as metadata]))

(lh/defnc paginator
  [{:keys [pagination]}]
  (let [{:keys [current total]} pagination
        base-path (-> js/document
                      .-location
                      .-pathname
                      (str/split "/")
                      second)
        prev-page (if (> (dec current) 0)
                    (dec current)
                    false)
        next-page (if (>= (inc current) total)
                    false
                    (inc current))]
    (d/div
     {:class-name "space-y-2 pb-8 pt-6 md:space-y-5"}
     (d/nav
      {:class-name "flex justify-between"}
      (if prev-page
        (d/a
         {:href (if (= 1 prev-page)
                  (str "/" base-path "/")
                  (str "/" base-path "/page/" prev-page))
          :rel "prev"}
         "Previous")
        (d/button
         {:class-name "cursor-auto disabled:opacity-50"
          :disabled true}
         "Previous"))
      (d/span
       (str current " of " total))
      (if next-page
        (d/a
         {:href (str "/" base-path "/page/" next-page)
          :rel "next"}
         "Next")
        (d/button
         {:class-name "cursor-auto disabled:opacity-50"
          :disabled true}
         "Next"))))))

(lh/defnc tag-list
  [{:keys [posts #_initial-posts pagination title]}]
  (let [#_pathname #_(-> js/document
                     .-location
                     .-pathname)
        #_[tag-count set-tag-count] #_(hooks/use-state {})
        #_[tag-keys set-tag-keys] #_(hooks/use-state [])]
    #_(hooks/use-effect
      []
      (p/let [res (js/fetch "/api/tag-counts")
              _res (.json res)
              data (js->clj _res)]
        (set-tag-count merge data)))
    #_(hooks/use-effect
      [tag-count]
      (set-tag-keys (->> tag-count
                         (sort-by val >)
                         keys)))
    (<>
     (d/div
      {:class-name "divide-y divide-gray-200 dark:dividi-gray-700"}
      (d/div
       {:class-name "space-y-2 pb-8 pt-6 md:space-y-5"}
       (d/h1
        {:class-name "text-3xl font-extrabold leading-9 tracking-tight text-gray-900 dark:text-gray-100 sm:text-4xl sm:leading-10 md:text-6xl md:leading-14"}
        title)
       (d/div
        {:class-name "realtive max-w-lg"}
        (d/label
         (d/span
          {:class-name "sr-only"}
          "Search Articles")
         (d/input
          {:aria-label "Search articles"
           :type "text"
           :on-change #()
           :placeholder "Search articles"
           :class-name "block w-full rounded-md border border-gray-300 bg-white px-4 py-2 text-gray-900"}))
        (d/svg
         {:className "absolute right-3 top-3 h-5 w-5 text-gray-400 dark:text-gray-300"
          :xmlns "http://www.w3.org/2000/svg"
          :fill "none"
          :viewBox "0 0 24 24"
          :stroke "currentColor"}
         (d/path
          {:strokeLinecap "round"
           :strokeLinejoin "round"
           :strokeWidth 2
           :d "M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"}))))
      (d/ul
       (if (= 0 posts)
         "No posts found."
         (for [{:keys [path date title summary]} posts]
           (d/li
            {:key path
             :class-name "py-4"}
            (d/article
             {:class-name "space-y-2 xl:grid xl:grid-cols-4 xl:items-baseline xl:space-y-0"}
             (d/dl
              (d/dt
               {:class-name "sr-only"}
               "Published on")
              (d/dd
               {:class-name "text-base font-medium leading-6 text-gray-500 dark:text-gray-400"}
               (d/time
                {:date-time date}
                (date/format-date date (metadata/site :locale)))))
             (d/div
              {:class-name "space-y-3 xl:col-span-3"}
              (d/div
               (d/h3
                {:class-name "text-2xl font-bold leading-8 tracking-tight"}
                (d/a
                 {:class-name "text-gray-900 dark:text-gray-100"
                  :href (str "/" path)}
                 title))
               (d/div
                {:class-name "prose max-w-none text-gray-500 dark:text-gray-400"}
                summary)))))))))
     (if (> (:total pagination) 1)
         ($ paginator {:pagination pagination})))))
