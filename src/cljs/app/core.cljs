(ns app.core
  (:require
   ["react-dom/client" :as rdom]
   [app.about :as about]
   [app.blog :as blog]
   [app.home :as home]
   [app.not-found :as not-found]
   [app.post :as post]
   [clojure.string :as str]
   [components.footer :as footer]
   [components.layout-wrapper :as layout-wrapper]
   [helix.core :refer [$]]
   [helix.hooks :as hooks]
   [promesa.core :as p])
  (:require-macros
   [lib.helix-wrapper :as lh]))

(defonce root (rdom/createRoot (js/document.getElementById "app")))

(def views {:about about/about
            :blog blog/blog
            :footer footer/footer
            :not-found not-found/not-found
            :post post/post
            :home home/home})

(defn view [path]
  (let [splits (str/split path "/")]
    (cond
      (= splits []) :home
      (= "about" (second splits)) :about
      (and
       (= "blog" (second splits))
       (= 3 (count splits))) :post
      (= "blog" (second splits)) :blog
      :else :not-found)))

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
