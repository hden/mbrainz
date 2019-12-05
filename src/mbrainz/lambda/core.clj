(ns mbrainz.lambda.core
  (:require [mbrainz.core :as core]))

(defn -get-connection
  ([] (-get-connection [:duct.profile/prod]))
  ([profiles]
   (core/init-key :duct.database/datomic [:duct.profile/prod])))

(def get-connection (memoize -get-connection))

(defn uuid-from-string [s]
  (java.util.UUID/fromString s))
