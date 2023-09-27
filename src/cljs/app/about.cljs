(ns app.about
  (:require-macros
   [lib.helix-wrapper :as lh])
  (:require
   ["react-markdown$default" :as ReactMarkdown]
   [helix.core :refer [$ <>]]
   [helix.hooks :as hooks]
   [layout.author-layout :as author-layout]
   [promesa.core :as p]
   [util.metadata :as metadata]))

(lh/defnc about []
  (let [author (:author (metadata/author))
        [body set-body] (hooks/use-state {:body ""})]
    (p/let [response (js/fetch "/assets/data/author.md")
            _response (.text response)]
      (set-body assoc :body _response))
    (set! (. js/document -title) (str (:page-title metadata/site) "About"))
    (<>
     ($ author-layout/author-layout {& author}
        ($ ReactMarkdown (:body body))))))
