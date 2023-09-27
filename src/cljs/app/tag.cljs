(ns app.tag
  (:require-macros
   [lib.helix-wrapper :as lh])
  (:require
   [clojure.string :as str]
   [helix.core :refer [$]]
   [helix.hooks :as hooks]
   [layout.post-list :as list]
   [promesa.core :as p]
   [util.metadata :as metadata]))

(lh/defnc tag []
  (let [tag-name (-> js/document
                     .-location
                     .-pathname
                     (str/split "/tags/")
                     second)
        POSTS_PER_PAGE 5
        page 1
        [tag-data set-tag-data] (hooks/use-state {})
        [tagged-posts set-tagged-posts] (hooks/use-state {})
        pagination {:current page
                    :total (.ceil js/Math (/ (count (:posts tagged-posts)) POSTS_PER_PAGE))}]
    (hooks/use-effect
      []
      (p/let [d-res (js/fetch (str "/api/tag-data/" tag-name))
              d-json (.json d-res)
              data (js->clj d-json :keywordize-keys true)
              p-res (js/fetch (str "/api/posts-tagged/" tag-name))
              p-json (.json p-res)
              posts (js->clj p-json :keywordize-keys true)]
        (set-tag-data assoc :tag data)
        (set-tagged-posts assoc :posts posts)))
    (if (:tag tag-data)
      (let [t (:tag tag-data)
            title (str/join " " (-> (:title t)
                                    str/capitalize
                                    (str/split "-")))]
        (set! (. js/document -title) (str (:page-title metadata/site) title))
        ($ list/post-list
           {:posts (:posts tagged-posts)
            :initial-posts (:posts tagged-posts)
            :pagination pagination
            :title title})))))
