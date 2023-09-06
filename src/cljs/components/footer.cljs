(ns components.footer
  (:require
   [components.social-icons :as social-icons]
   [helix.core :refer [$]]
   [helix.dom :as d]
   [util.metadata :as metadata])
  (:require-macros
   [lib.helix-wrapper :as lh]))

(lh/defnc footer []
  ($ "footer"
     (d/div {:class-name "mt-16 flex flex-col items-center"}
            (d/div {:class-name "mb-3 flex space-x-4"}
                   ($ social-icons/social-icons {:href (str "mailto:" (metadata/site :mail))
                                                 :kind "mail"
                                                 :size "6"})
                   ($ social-icons/social-icons {:href (metadata/site :github)
                                                 :kind "github"
                                                 :size "6"})
                   ($ social-icons/social-icons {:href (metadata/site :linkedin)
                                                 :kind "linkedin"
                                                 :size "6"}))
            (d/div {:class-name "mb-2 flex space-x-2 text-sm text-gray-500 dark:text-gray-400"}
                   (d/div (metadata/site :author))
                   (d/div " • ")
                   (d/div (str "© " (.getFullYear (js/Date.))))
                   (d/div " • ")
                   (d/a {:href "/"}
                        (metadata/site :title)))
            (d/div {:class-name "mb-8 text-sm text-gray-500 dark:text-gray-400"}
                   (d/a {:href "https://github.com/timlrx/tailwind-nextjs-starter-blog"} "Based on Tailwind Nextjs Theme")))))
