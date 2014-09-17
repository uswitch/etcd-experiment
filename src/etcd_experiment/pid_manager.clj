(ns etcd-experiment.pid-manager
  (:require
    [com.stuartsierra.component :refer [Lifecycle]]
    [clj-pid.core               :as pid]
    [etcd-experiment.util       :refer [etcd-swap-value]]))

(defrecord PidManager [root-key service]
  Lifecycle
  (start [component]
    (let [pid (pid/current)]
      (etcd-swap-value (str root-key "/pid") pid)
      (clojure.pprint/pprint {:service-pid pid})
      (assoc component :pid pid)))

  (stop [component]
    component))

(defn pid-manager-component
  [root-key]
  (map->PidManager {:root-key root-key}))
