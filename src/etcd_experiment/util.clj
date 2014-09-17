(ns etcd-experiment.util
  (:require [etcd-clojure.core :as etcd]))

(defn etcd-swap-value
  [key value]
  (let [current-key  (str key "/current")
        previous-key (str key "/previous")]
    (when-let [current-value (try (etcd/get current-key) (catch Exception e nil))]
      (etcd/set previous-key current-value))
    (etcd/set current-key value)))
