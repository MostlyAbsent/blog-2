(ns layout.author-layout
  (:require-macros
   [lib.helix-wrapper :as lh])
  (:require
   [components.social-icons :as social-icons]
   [helix.core :refer [$ <>]]
   [helix.dom :as d]
   [util.metadata :as metadata]))

(lh/defnc author-layout [{:keys [avatar name occupation children]}]
  (<>
   (d/div
    {:class-name "divide-y divide-gray-200 dark:divide-gray-700"}
    (d/div
     {:class-name "space-y-2 pb-8 pt-6 md:space-y-5"}
     (d/h1
      {:class-name "text-3xl font-extrabold leading-9 tracking-tight text-gray-900 dark:text-gray-100 sm:text-4xl sm:leading-10 md:text-6xl md:leading-14"}
      "About"))
    (d/div
     {:class-name "items-start space-y-2 xl:grid xl:grid-cols-3 xl:gap-x-8 xl:space-y-0"}
     (d/div
      {:class-name "flex flex-col items-center space-x-2 pt-8"}
      (if avatar ($ avatar {:width 192
                            :height 192
                            :class-name "h-48 w-48 rounded-full"
                            :alt "Avatar"}))
      (d/h3
       {:class-name "pb-2 pt-4 text-2xl font-bold leading-8 tracking-tight"}
       name)
      (d/div
       {:class-name "text-gray-500 dark:text-gray-400"}
       occupation)
      (d/div
       {:class-name "flex space-x-3 pt-6"}
       ($ social-icons/social-icons
          {:kind "mail"
           :href (str "mailto:" (:mail metadata/site))})
       ($ social-icons/social-icons
          {:kind "github"
           :href (:github metadata/site)})
       ($ social-icons/social-icons
          {:kind "linkedin"
           :href (:linkedin metadata/site)})))
     (d/div
      {:class-name "prose max-w-none pb-8 pt-8 dark:prose-invert xl:col-span-2"}
      children)))))
