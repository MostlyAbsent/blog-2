(ns app.blog
  (:require-macros
   [lib.helix-wrapper :as lh])
  (:require
   [helix.core :refer [$ <>]]
   [helix.hooks :as hooks]
   [layout.list :as list]
   [promesa.core :as p]))

(lh/defnc blog []
  (let [POSTS_PER_PAGE 5
        page 1
        [posts set-posts] (hooks/use-state {:posts []})
        pagination {:current page
                    :total (.ceil js/Math (/ (count (:posts posts)) POSTS_PER_PAGE))}]
    (hooks/use-effect
      []
      (p/let [res (js/fetch "/api/posts")
              json (.json res)
              data (js->clj json :keywordize-keys true)]
        (set-posts assoc :posts data)))
    (set! (. js/document -title) "Blog")
    (<>
     ($ list/post-list {:initial-posts (take POSTS_PER_PAGE (:posts posts))
                        :posts (:posts posts)
                        :pagination pagination
                        :title "All Posts"}))))
