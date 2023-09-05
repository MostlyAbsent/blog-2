(ns components.tag
  (:require
   ["github-slugger" :refer [slug]]
   [clojure.string :as string]
   [components.link :as link]
   [helix.core :refer [$]])
  (:require-macros
   [lib.helix-wrapper :as lh]))

(lh/defnc tag [{:keys [text]}]
  ($ link/link
     {:href (str "/tags/" (slug text))
      :class-name "mr-3 text-sm font-medium uppercase text-primary-500 hover:text-primary-600 dark:hover:text-primary-400"}
     (as-> text t
         (string/split t #" ")
         (string/join "-" t))))
