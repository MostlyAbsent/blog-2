(ns app.core
  (:require
   ["react-dom/client" :as rdom]
   [app.about :as about]
   [app.blog :as blog]
   [app.home :as home]
   [app.not-found :as not-found]
   [app.post :as post]
   [app.projects :as projects]
   [app.tags :as tags]
   [clojure.string :as str]
   [components.layout-wrapper :as layout-wrapper]
   [helix.core :refer [$]]
   [helix.hooks :as hooks]
   [promesa.core :as p]
   [app.tag :as tag])
  (:require-macros
   [lib.helix-wrapper :as lh]))

(defonce root (rdom/createRoot (js/document.getElementById "app")))

(def views {:about about/about
            :blog blog/blog
            :home home/home
            :not-found not-found/not-found
            :post post/post
            :projects projects/projects
            :tag tag/tag
            :tags tags/tags})

(defn view [path]
  (let [splits (str/split path "/")
        path (second splits)]
    (cond
      (= splits [])               :home
      (and
       (= "tags" path)
       (= 3 (count splits)))      :tag
      (and
       (= "blog" path)
       (= 3 (count splits)))      :post
      (= "about" path)            :about
      (= "blog" path)             :blog
      (= "projects" path)         :projects
      (= "tags" path)             :tags
      :else                       :not-found)))

(lh/defnc main-view [{:keys [children]}]
  (let [pathname (.-pathname (.-location js/document))
        [current-view] (hooks/use-state (view pathname))]
    ($ layout-wrapper/layout-wrapper
       ($ (current-view views) {:data children}))))

(defn ^:export init []
  (p/let [_response (js/fetch "/api/posts")
          response (.json _response)
          data (js->clj response :keywordize-keys true)]
    (.render root ($ main-view data))))
