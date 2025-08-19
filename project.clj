(defproject org.clojars.gaelrech/tapp "0.0.10"
  :description "Debugging tools for general usage"
  :url "https://github.com/gaelrech/tapp"
  :license {:name "The MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :test-paths ["test"]
  :release-tasks [["change" "version" "leiningen.release/bump-version" "release"]
                  ["vcs" "commit"]
                  ["vcs" "tag" "v" "--no-sign"]
                  ["deploy"]
                  ["change" "version" "leiningen.release/bump-version"]
                  ["change" "version" "leiningen.release/bump-version"]
                  ["vcs" "commit"]
                  ["vcs" "push"]]
  :deploy-repositories [["clojars" {:url "https://clojars.org/repo"
                                    :sign-releases false
                                    :username :env/clojars_username
                                    :password :env/clojars_password}]]
  :dependencies [[org.clojure/clojure "1.12.0"]
                 [funcool/cats "2.4.2"]
                 [djblue/portal "0.58.5"]
                 [mvxcvi/puget "1.3.4"]
                 [clj-stacktrace "0.2.8"]])
