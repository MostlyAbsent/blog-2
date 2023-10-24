(ns layout.post-list
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

(lh/defnc post-list
  [{:keys [posts initial-posts pagination title]}]
  (let [pathname (-> js/document
                     .-location
                     .-pathname)
        [tag-count set-tag-count] (hooks/use-state {})
        [tag-keys set-tag-keys] (hooks/use-state [])]
    (hooks/use-effect
      []
      (p/let [res (js/fetch "/api/tag-counts")
              _res (.json res)
              data (js->clj _res)]
        (set-tag-count merge data)))
    (hooks/use-effect
      [tag-count]
      (set-tag-keys (->> tag-count
                         (sort-by val >)
                         keys)))
    (<>
     (d/div
      (d/div
       {:class-name "pb-6 pt-6"}
       (d/h1
        {:class-name "sm:hidden text-3xl font-extrabold leading-9 tracking-tight text-gray-900 dark:text-gray-100 sm:text-4xl sm:leading-10 md:text-6xl md:leading-14"}
        title))
      (d/div
       {:class-name "flex sm:space-x-24"}
       (d/div
        {:class-name "hidden max-h-screen h-full sm:flex flex-wrap bg-gray-50 dark:bg-gray-900/70 shadow-md pt-5 dark:shadow-gray-800/40 rounded min-w-[280px] max-w-[280px] overflow-auto"}
        (d/div
         {:class-name "py-4 px-6"}
         (if (str/starts-with? pathname "/blog")
           (d/h3
            {:class-name "text-primary-500 font-bold uppercase"}
            "All Posts")
           (d/a
            {:class-name "font-bold uppercase text-gray-700 dark:text-gray-300 hover:text-primary-500 dark:hover:text-primary-500"
             :href "/blog"}
            "All Posts"))
         (d/ul
          (for [t tag-keys]
            (d/li
             {:key t
              :class-name "my-3"}
             (if (= (slug t) (second (str/split pathname "/tags/")))
               (d/h3
                {:class-name "inline py-2 px-3 uppercase text-sm font-bold text-primary-500"}
                (str t " (" (get tag-count t) ")"))
               (d/a
                {:href (str "/tags/" (slug t))
                 :class-name "py-2 px-3 uppercase text-sm font-medium text-gray-500 dark:text-gray-300 hover:text-primary-500 dark:hover:text-primary-500"
                 :aria-label (str "View posts tagged " t)}
                (str t " (" (get tag-count t) ")"))))))))
       (d/div
        (d/ul
         (for [{{:keys [path date title summary tags]} :metadata} initial-posts]
           (d/li
            {:key path
             :class-name "py-5"}
            (d/article
             {:class-name "space-y-2 flex flex-col xl:space-y-0"}
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
              {:class-name "space-y-3"}
              (d/div
               (d/h2
                {:class-name "text-2xl font-bold leading-8 tracking-tight"}
                (d/a
                 {:href (str "/" path)
                  :class-name "text-gray-900"}
                 title)
                (d/div
                 {:class-name "flex flex-wrap"}
                 (for [t tags]
                   ($ tag/tag {:key t :title (:title t)})))))
              (d/div
               {:class-name "prose max-w-none text-gray-500 dark:text-gray-400"}
               summary))))))
        (if (> (:total pagination) 1)
          ($ paginator {:pagination pagination}))))))))
