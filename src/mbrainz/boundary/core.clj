(ns mbrainz.boundary.core
  (:refer-clojure :exclude [find])
  (:require [duct.database.datomic]
            [datomic.client.api :as datomic]))

(defprotocol Queryable
  (find [db arg-map])
  (pull [db arg-map])
  (transact! [db arg-map]))

(extend-protocol Queryable
  duct.database.datomic.Boundary
  (find [{:keys [connection]} arg-map]
    (let [db (datomic/db connection)]
      (datomic/q (update arg-map :args #(into [db] %)))))

  (pull [{:keys [connection]} arg-map]
    (let [db (datomic/db connection)]
      (datomic/pull db arg-map)))

  (transact! [{:keys [connection]} arg-map]
    (datomic/transact connection arg-map)))
