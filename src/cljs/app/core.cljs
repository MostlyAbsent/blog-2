(ns app.core
  (:require
   ["react-dom/client" :as rdom]
   [app.home :as home]
   [app.not-found :as not-found]
   [components.footer :as footer]
   [components.layout-wrapper :as layout-wrapper]
   [helix.core :refer [$]]
   [helix.hooks :as hooks]
   [promesa.core :as p])
  (:require-macros
   [lib.helix-wrapper :as lh]))

(defonce root (rdom/createRoot (js/document.getElementById "app")))

(def views {:home home/home
            :footer footer/footer
            :not-found not-found/not-found})

(def pathname
  (.-pathname (.-location js/document)))

(def view (get {"/" :home
                "/footer" :footer} pathname :not-found))

(lh/defnc main-view [{:keys [children]}]
  (let [[current-view] (hooks/use-state view)]
    ($ layout-wrapper/layout-wrapper
     ($ (current-view views) {:data children}))))

(defn ^:export init []
  (p/let [_response (js/fetch "/api/posts/")
          response (.json _response)
          data (js->clj response :keywordize-keys true)]
    (.render root ($ main-view data))))
