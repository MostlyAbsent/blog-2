(ns components.mobile-nav
  (:require
   [helix.core :refer [<>]]
   [helix.dom :as d]
   [helix.hooks :as hooks]
   [util.metadata :as metadata])
  (:require-macros
   [lib.helix-wrapper :as lh]))

(lh/defnc mobile-nav []
  (let [[nav-show set-nav-show] (hooks/use-state false)
        toggle-nav (fn []
                     (set-nav-show
                      (fn [status]
                        (if status
                          (set! (.. js/document -body -style -overflow) "auto")
                          (set! (.. js/document -body -style -overflow) "hidden"))
                        (not status))))]
    (<>
     (d/button
      {:aria-label "Toggle Menu"
       :on-click toggle-nav
       :class-name "sm:hidden"}
      (d/svg
       {:xmlns "http://www.w3.org/2000/svg"
        :viewBox "0 0 20 20"
        :fill "currentColor"
        :className "text-gray-900 dark:text-gray-100 h-8 w-8"}
       (d/path
        {:fillRule "evenodd"
         :d "M3 5a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zM3 10a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zM3 15a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1z"
         :clipRule "evenodd"})))
     (d/div
      {:class-name (str "fixed left-0 top-0 z-10 h-full w-full transform opacity-95 dark:opacity-[0.98] bg-white duration-300 dark:bg-gray-950" (if nav-show " translate-x-0" " translate-x-full"))}
      (d/div
       {:class-name "flex justify-end"}
       (d/button
        {:class-name "mr-8 mt-11 h-8 w-8"
         :aria-label "Toggle Menu"
         :on-click toggle-nav}
        (d/svg
         {:xmlns "http://www.w3.org/2000/svg"
          :viewBox "0 0 20 20"
          :fill "currentColor"
          :className "text-gray-900 dark:text-gray-100 h-8 w-8"}
         (d/path
          {:fillRule "evenodd"
           :d "M3 5a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zM3 10a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zM3 15a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1z"
           :clipRule "evenodd"}))))
      (d/nav
       {:class-name "fixed mt-8 h-full"}
       (map (fn [{:keys [title href]}]
              (d/div
               {:key title
                :class-name "px-12 py-4"}
               (d/a
                {:href href
                 :class-name "text-2xl font-bold tracking-widest text-gray-900 dark:text-gray-100"
                 :on-click toggle-nav}
                title)) ) metadata/header-nav-links))))))
