(ns mbrainz.core
  (:require [datomic.client.api :as datomic]
            [duct.core :as duct]
            [integrant.core :as ig]))

(duct/load-hierarchy)

(defn init-key
  "Read, build, prep and initiate a configuration of modules"
  ([]
   (init-key :duct/handler))
  ([key]
   (init-key key [:duct.profile/prod]))
  ([key profiles]
   (-> (duct/resource "mbrainz/config.edn")
       (duct/read-config)
       (duct/prep-config profiles)
       (ig/init [key])
       (get-in [key]))))
