(ns etcd-experiment.system
  (:require
    [com.stuartsierra.component :refer [Lifecycle]]
    [etcd-experiment.util       :refer [etcd-swap-value]]
    [ring.adapter.jetty         :refer [run-jetty]]))

(defrecord JettyComponent [root-key routes]
  Lifecycle
  (start [component]
    (let [server (run-jetty routes {:join? false
                                    :port  0})
          server-port (.getLocalPort (first (.getConnectors server)))]
      (etcd-swap-value (str root-key "/port") server-port)
      (clojure.pprint/pprint {:service-port server-port})
      (-> component
          (assoc :server server)
          (assoc :server-port server-port))))

  (stop [component]
    (let [server (:server component)]
      (.stop server)
      component)))

(defn jetty-component
  [root-key routes]
  (map->JettyComponent {:root-key root-key
                        :routes   routes}))
