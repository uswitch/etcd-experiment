(ns etcd-experiment.main
  (:require
    [com.stuartsierra.component  :refer (start stop using system-map)]
    [etcd-experiment.system      :refer [jetty-component]]
    [etcd-experiment.pid-manager :refer [pid-manager-component]])
  (:gen-class))

(def handler
  (let [random-number (rand-int 5000)]
    (clojure.pprint/pprint {:random-number random-number})
    (fn [request]
      {:status 200
       :body   (str "A number: " random-number)})))

(defn build-system
  [service-key routes]
  (system-map :jetty (jetty-component service-key routes)
              :pid   (using (pid-manager-component service-key) {:service :jetty})))

(defn restart-system
  [old-system]
  (when old-system
    (stop old-system))
  (let [new-system (build-system "uswitch/experiment" handler)]
    (start new-system)
    new-system))

(def restart
  (let [system (atom nil)]
    (fn [] (swap! system restart-system))))

(defn -main
  [& args]
  (restart))
