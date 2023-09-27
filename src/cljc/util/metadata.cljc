(ns util.metadata
  (:require
    [components.logo :as logo]))

(def site {:title "Just The Tips"
           :author "Jacob Doran"
           :header-title "Just The Tips"
           :page-title "Just The Tips | "
           :description "A blog for exploratory code"
           :language "en-AU"
           :theme "system"
           :site-url "just-the.tips"
           :site-repo "https://github.com/MostlyAbsent/blog-2"
           :site-logo ""
           :social-banner ""
           :mail "j.doran.admin@gmail.com"
           :github "https://github.com/MostlyAbsent"
           :linkedin "https://www.linkedin.com/in/jacob-doran-2384b272/"
           :locale "en-AU"
           :search {:provider "kbar"
                    :kbar-config {:search-documents-path "search.json"}}})

(def header-nav-links [{:href "/" :title "Home"}
                       {:href "/blog" :title "Blog"}
                       {:href "/tags" :title "Tags"}
                       {:href "/projects" :title "Projects"}
                       {:href "/about" :title "About"}])

(defn author []
  {:author {:name (:author site)
            :avatar logo/logo
            :occupation "Software Developer"
            :mail (:mail site)
            :linkedin (:linkedin site)
            :github (:github site)}})
