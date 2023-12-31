(ns app.home
  (:require-macros
   [lib.helix-wrapper :as lh])
  (:require
   [components.tag :as tag]
   [helix.core :refer [$ <>]]
   [helix.dom :as d]
   [util.date :as date]
   [util.metadata :as metadata]))

(defn post [{{:keys [slug date title summary tags]} :metadata}]
  (d/li
   {:class-name "py-12"
    :key slug}
   ($ "article"
      (d/div
       {:class-name "space-y-2 xl:grid xl:grid-cols-4 xl:items-baseline"}
       (d/dl
        (d/dt
         {:class-name "sr-only"}
         "Published on")
        (d/dd
         {:class-name "text-base font-medium leading-6 text-gray-500 dark:text-gray-400"}
         (d/time
          {:date-time (first date)}
          (date/format-date (first date) (metadata/site :locale)))))
       (d/div
        {:class-name "space-y-5 xl:col-span-3"}
        (d/div
         {:class-name "space-y-6"}
         (d/div
          (d/h2
           {:class-name "text-2xl font-bold leading-8 tracking-tight"}
           (d/a
            {:href (str "/blog/" slug)
             :class-name "text-gray-900 dark:text-gray-100"}
            title))
          (d/div
           {:class-name "flex flex-wrap"}
           (for [t tags]
             (d/span
              {:key t
               :class-name "mb-2 mr-5 mt-2"}
              ($ tag/tag {:title t})))))
         (d/div
          {:class-name "prose max-w-none text-gray-500 dark:text-gray-400"}
          summary)
         (d/div
          {:class-name "text-base font-medium leading-6"}
          (d/a
           {:href (str "/blog/" slug)
            :class-name "text-primary-500 hover:text-primary-600"
            :aria-label (str "Read " title)}
           "Read more →"))))))))

(lh/defnc home [{:keys [data]}]
  (set! (. js/document -title) "Just The Tips")
  (let [MAX_DISPLAY 5]
    (if data
      (<>
       (d/div
        {:class-name "divide-y divide-gray-200 dark:divide-gray-700"}
        (d/div
         {:class-name "space-y-2 pb-8 pt-6 md:space-y-5"}
         (d/h1
          {:class-name "text-3xl font-extrabold leading-9 tracking-tight text-gray-900 dark:text-gray-100 sm:text-4xl sm:leading-10 md:text-6xl md:leading-14"}
          "Latest")
         (d/p
          {:class-name "text-lg leading-7 text-gray-500 dark:text-gray-400"}
          (metadata/site :description)))
        (d/ul
         {:class-name "divide-y divide-gray-200 dark:divide-gray-700"}
         (if (= 0 (count data))
           "No posts found."
           (->> data
                (take MAX_DISPLAY)
                (map post)))))
       (if (> (count data) MAX_DISPLAY)
         (d/div
          {:class-name "flex justify-end text-base font-medium leading-6"}
          (d/a
           {:href "/blog/"
            :class-name "text-primary-500 hover:text-primary-600 dark:hover:text-primary-400"
            :aria-label "All posts"}
           "All posts →")))))))
