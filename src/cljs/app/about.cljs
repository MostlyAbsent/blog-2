(ns app.about
  (:require-macros
   [lib.helix-wrapper :as lh])
  (:require
   ["react-markdown$default" :as ReactMarkdown]
   [cljs.pprint :as pprint]
   [helix.core :refer [$ <>]]
   [layout.author-layout :as author-layout]
   [promesa.core :as p]
   [util.metadata :as metadata]
   [helix.hooks :as hooks]))

(lh/defnc about []
  (let [author (:author (metadata/author))
        [body set-body] (hooks/use-state {:body ""})]
    (p/let [response (js/fetch "assets/author.md")
            _response (.text response)]
      (set-body assoc :body _response))
    (set! (. js/document -title) "About")
    (<>
     ($ author-layout/author-layout {& author}
        ($ ReactMarkdown (:body body))))))
