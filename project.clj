(defproject etcd-experiment "0.1.0-SNAPSHOT"
  :description "Attempt to have zero downtime deployments for Clojure Ring applications"
  :url "https://github.com/uswitch/etcd-experiment"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [ring "1.3.0"]
                 [ring/ring-jetty-adapter "1.3.0"]
                 [com.stuartsierra/component "0.2.1"]
                 [clj-pid "0.1.1"]
                 [etcd-clojure "0.2.1"]]
  :main etcd-experiment.main
  :profiles {:uberjar {:aot :all}})
